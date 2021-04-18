<template>
  <div>
    <h1>Override Fee</h1>
    <div id="edit-grid">
      <div id="left-edit">
        <img
          class="artImgEdit"
          :src="imgFile"
          v-bind:alt="artPiece.imgFileName"
        />
      </div>
      <div id="right-edit">
        <h2 id="currentDefaultFee">Default Fee: {{currentDefaultFees.fee}}%</h2>
        <h2 id="currentDefaulFee">Default Commission: {{currentDefaultFees.commission}}%</h2>
        <form>
          <div>
            <label>Fee: </label>
          </div>
          <input type="number" v-model.trim="artPiece.feeOverride" />

          <div>
            <label>Commission: </label>
          </div>
          <input type="number" v-model.trim="artPiece.commissionOverride" />

        </form>

        <button id="confirmOverride" @click="sendOverride()">
          Confirm Override
        </button>

          <button id="resetToDefault" @click="resetToDefault()">
            Reset Fees To Default
          </button>
     

      <div>
        <router-link v-bind:to="{ name: 'Settings' }"> 
          <button id="backToSettings">Go Back</button>
        </router-link>
      </div>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
import artPieceService from "@/services/ArtPieceService.js";
import transactionService from "@/services/TransactionService.js";
export default {
  name: "fee-override",
  data() {
    return {
      artPiece: {},
      imgFile: {},
      foundId: 0,
      currentDefaultFees: {},
    };
  },
  methods: {
    sendEditedArtPiece() {
      artPieceService
        .editListing(this.artPiece)
        .then((response) => {
          if (response.status == 200) {
            alert("Listing has been edited!");
            this.$router.push({
              name: "ArtDetails",
              params: { artId: this.artPiece.artID },
            });
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status !== 200) {
            alert("There was a problem editing your listing");
          }
        });
    },

    deleteArtPiece() {
      if (confirm("Are you sure you want to delete this listing?")) {
        artPieceService
          .deleteListing(this.artPiece.artID)
          .then((response) => {
            if (response.status == 204) {
              alert("Listing has been deleted!");
              this.$router.push({ path: "/" });
            }
          })
          .catch((error) => {
            const response = error.response;
            if (response.status !== 204) {
              alert("There was a problem deleting your listing");
            }
          });
      }
    },

    sendOverride(){
          artPieceService
            .updateOverrideCommission(this.artPiece)
              .then((response) => {
                if(response.status == 200){
                  alert("Successfully overridden commission");
                }
            }).catch((error) => {
              const response = error.response
              console.log(response);
            });
       
          artPieceService
            .updateOverrideFee(this.artPiece)
              .then((response) => {
                if(response.status == 200){
                  alert("Successfully overridden fee");
                }
            }).catch((error) => {
              const response = error.response
              console.log(response);
            });
      

    },

    resetToDefault(){
        artPieceService
          .updateFeesToDefault(this.artPiece.artID)
            .then((response) => {
                    if(response.status == 200){
                      alert("Fees reset to default");
                    }
                }).catch((error) => {
                  const response = error.response
                  console.log(response);
                });

        artPieceService
        .getListingByArtId(this.foundId)
          .then((response) => {
            this.artPiece = response.data;

            let storage = firebase.storage();
            let storageRef = storage.ref();
            let imgRef = storageRef.child(this.artPiece.imgFileName);

          imgRef.getDownloadURL().then((url) => {
            this.imgFile = url;
          });
        })
        .catch(this.response.error);
    }

  },

  created() {
    this.foundId = this.$route.params.artId;
    artPieceService
      .getListingByArtId(this.foundId)
        .then((response) => {
          this.artPiece = response.data;

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
     }).catch((error) => {
      const response = error.response
      console.log(response);
      });
  }
};
</script>

<style scoped>
#edit-grid {
  background-color: #ab3f294b;
  border-radius: 20px;
  color: #fff;
  font-family: "Quicksand", sans-serif;
  margin: auto;
  margin-bottom: 20px;
  padding: 40px;
  width: 75%;
  max-width: 800px;
  height: auto;
}

.artImgEdit {
  width: 100%;
}

#left-edit {
  grid-area: image;
  align-self: center;
  margin-right: 20px;
}

#right-edit {
  grid-area: details;
  padding: 5px;
}

form > div {
  margin-top: 5px;
}

form :last-child {
  margin-bottom: 10px;
}

div#edit-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit);
  grid-template-areas: ". image details";
  justify-content: center;
}

@media (max-width: 800px) {
  div#edit-grid {
    grid-template-areas:
      "image"
      "details";
    padding: 20px;
  }
}

#confirmOverride {
  margin-top: 7px;
  margin-bottom: 10px;
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  width: 100%;
}

#deleteListing {
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  width: 100%;
}

#resetToDefault{
  margin-top: 7px;
  margin-bottom: 10px;
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  width: 100%;
}

#backToSettings{
  margin-top: 7px;
  margin-bottom: 10px;
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