<template>
  <div>
    <h1>Confirm Transaction</h1>
    <div id="confirm-grid">
      <div id="left-confirm">
        <img class="artImg" :src="imgFile" v-bind:alt="artPiece.imgFileName" />
      </div>
      <div id="right-confirm">
        <h3>Title: {{ artPiece.title }}</h3>
        <h3>Date: {{ artPiece.dateCreated }}</h3>
        <h3>Artist: {{ artPiece.artist }}</h3>
        <h3>Price: ${{ artPiecePrice}}</h3>
        <h5>+ Additional Fees</h5>
        <h3>Total Price: ${{ gettotalPrice.toFixed(2) }}</h3>
        <button id="confirm" @click="startTransaction()">Confirm</button>

        <router-link
          v-bind:to="{ name: 'ArtDetails', params: { artId: artPiece.artID } }"
        >
          <button id="confirm">Cancel</button>
        </router-link>
        <div class="failed">
          <h2 v-if="statusMessage">{{ statusMessage }}</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
import artPieceService from "@/services/ArtPieceService.js";
import transactionService from "@/services/TransactionService.js";
import emailjs from 'emailjs-com';

export default {
  name: "transaction-details",
  data() {
    return {
      artPiece: null,
      imgFile: {},
      foundId: 0,
      statusMessage: null,
      transaction: {},
      currentDefaultFees: {}
    };
  },
computed: {
    artPiecePrice(){
      let price = this.artPiece.price;
      return parseFloat(price).toFixed(2);
    },
    gettotalPrice(){
      let total = 0;
              
      if (this.artPiece.hasOverride) {
       
       let artFeeOverride =
          (this.artPiece.feeOverride / 100.0) * this.artPiece.price;
       let artComOverride =
          (this.artPiece.commissionOverride / 100.0) * this.artPiece.price;

        total = artFeeOverride +
                artComOverride +
                this.artPiece.price;
      } else {
        total =  (this.currentDefaultFees.fee / 100) * this.artPiece.price +
        (this.currentDefaultFees.commission / 100) * this.artPiece.price +
        this.artPiece.price;
      }
      return total;
    }

},
  methods: {
    sendEmail(templateParams) {
      emailjs.send('service_wqs5fy5', 'template_jnzoczd', templateParams,
      'user_9KaBtBto1Nl9wAegVd3Uh', {
      }).then((result) => {
          console.log('SUCCESS!', result.status, result.text);
      }, (error) => {
          console.log('FAILED...', error);
      });

      //Reset form field
      this.name = ''
      this.email = ''
      this.message = ''
    },
    isOverride() {
      if (this.artPiece.hasOverride) {
        this.transaction.commission = this.artPiece.commissionOverride;
        this.transaction.fee = this.artPiece.feeOverride;

        this.transaction.fee =
          (this.artPiece.feeOverride / 100) * this.artPiece.price;
        this.transaction.commission =
          (this.artPiece.commissionOverride / 100) * this.artPiece.price;
        this.transaction.totalPrice =
          (this.artPiece.feeOverride / 100) * this.artPiece.price +
          (this.artPiece.commissionOverride / 100) * this.artPiece.price +
          this.artPiece.price;
      }
    },
    startTransaction() {
      transactionService
        .postTransaction(this.transaction)
        .then((response) => {
          if (response.status == 201) {
            alert("Order has been confirmed! \nThank you for your purchase!");
            this.getArtistEmail();
            this.getDealerEmail();
            this.getCustomerEmail();
            this.$router.push({ path: "/" });
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status !== 201) {
            this.statusMessage = "There were problems placing your order...";
          }
        });
    },
    getArtistEmail(){
      transactionService
        .getEmail(this.artPiece.artist)
          .then((email) => {
            let templateParams = {};
            templateParams.email = email.data;
            templateParams.message = `Your item : ${this.artPiece.title} just got sold!'`;
            templateParams.name = this.artPiece.artist;       
            this.sendEmail(templateParams);     
          });
    },
    getDealerEmail(){
      transactionService
        .getEmail(this.artPiece.dealer)
          .then((email) => {
            let templateParams = {};
            templateParams.email = email.data;
            templateParams.message = `Your item : ${this.artPiece.title} just got sold!'`;            
            templateParams.name = this.artPiece.dealer; 
            this.sendEmail(templateParams);          
          })
    },
    getCustomerEmail(){
      transactionService
        .getEmail(this.$store.state.user.username)
          .then((email) => {
            let templateParams = {};
            templateParams.email = email.data;
            templateParams.message = `You just bought : ${this.artPiece.title}! For the price of $${this.totalPrice} Thank you for your purchase!'`;
            templateParams.name = this.$store.state.user.username;       
            this.sendEmail(templateParams);     
          });
    },
    returnToArtDetail() {
      this.$router.push({ path: "/home" });
    },
  },
  created() {
    this.foundId = this.$route.params.artId;
    artPieceService
      .getListingByArtId(this.foundId)
      .then((response) => {
        this.artPiece = response.data;
        this.transaction.artID = this.foundId;
        this.transaction.customerId = this.$store.state.customerId;
        let storage = firebase.storage();
        let storageRef = storage.ref();
        let imgRef = storageRef.child(this.artPiece.imgFileName);

        imgRef.getDownloadURL().then((url) => {
          this.imgFile = url;
        });
      })
      .catch(console.log("not working"));
    transactionService
      .getCurrentDefaultFees()
      .then((response) => {
        this.currentDefaultFees = response.data;
        this.transaction.fee =
          (this.currentDefaultFees.fee / 100) * this.artPiece.price;
        this.transaction.commission =
          (this.currentDefaultFees.commission / 100) * this.artPiece.price;
        this.transaction.totalPrice =
          (this.currentDefaultFees.fee / 100) * this.artPiece.price +
          (this.currentDefaultFees.commission / 100) * this.artPiece.price +
          this.artPiece.price;
        this.isOverride();
      })
      .catch((error) => {
        const response = error.response;
        console.log(response);
      });
  },
};
</script>

<style scoped>
#confirm-grid {
  background-color: #ab3f294b;
  border-radius: 20px;
  color: #fff;
  font-family: "Quicksand", sans-serif;
  margin: 20px auto;
  padding: 20px;
  width: fit-content;
  max-width: 700px;
  height: auto;
}

.artImg {
  width: 100%;
}

#left-confirm {
  grid-area: image;
  align-self: center;
  margin-right: 20px;
}

#right-confirm {
  grid-area: details;
  align-self: center;
  padding: 5px;
}

#right-confirm > h3 {
line-height: 2rem;
}

div#confirm-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit);
  grid-template-areas: "image details";
  justify-content: center;
}

@media (max-width: 720px) {
  div#confirm-grid {
    grid-template-areas:
      "image"
      "details";
    padding: 20px;
    max-width: 300px;
  }
}

button {
  margin-top: 10px;
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  width: 100%;
}
</style>