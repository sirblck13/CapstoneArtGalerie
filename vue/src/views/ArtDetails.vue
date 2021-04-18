<template>
  <div class="card">
    <img class="myImg" :src="imgFile" v-bind:alt="artPiece.imgFileName" />
    <div class="info">
      <h2>
        <i>{{ artPiece.title }}</i>
      </h2>
      <h3>{{getYear}}</h3>
      <h4>{{ artPiece.artist }}</h4>
      <h5 id="artPrice" v-if="!artPiece.sold">
        ${{ artPiece.price.toFixed(2) }}
      </h5>
      <h5 id="soldArt" v-if="artPiece.sold">
        ${{ artPiece.price.toFixed(2) }}
      </h5>
      <h5 id="soldTag" v-if="artPiece.sold">SOLD</h5>
      <h4 id="unavailibleTag" v-if="!artPiece.available && !artPiece.sold">
        Available Soon!
      </h4>
    </div>
    <div>
      <router-link v-bind:to="{ name: 'home' }">
        <button id="goBack">BACK</button>
      </router-link>
    </div>
    <div>
      <router-link
        v-bind:to="{ name: 'Transaction', params: { artId: artPiece.artID } }"
        v-if="$store.state.user.authorities[0].name != 'ROLE_ADMIN'"
      >
        <button v-if="!artPiece.sold && artPiece.available" class="buy">
          BUY
        </button>
      </router-link>
    </div>
    <div>
      <div
        id="editAuth"
        v-if="$store.state.user.authorities[0].name == 'ROLE_ADMIN'"
      >
        <router-link
          v-bind:to="{ name: 'EditArt', params: { artId: artPiece.artID } }"
        >
          <button v-if="!artPiece.sold" id="editArt">EDIT</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
import artPieceService from "@/services/ArtPieceService.js";
export default {
  name: "art-details",

  data() {
    return {
      artPiece: null,
      imgFile: {},
      foundId: 0,
    };
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
      .catch((error) => {
        const response = error.response;
        console.log(response);
      });
  },
  computed: {
    getYear() {
      let date = this.artPiece.dateCreated;
      return date.substring(0, date.indexOf('-'));
    }
  }
};
</script>

<style scoped>
* {
  color: #fff;
}
.myImg {
  width: 100%;
  height: auto;
}

button {
  border: none;
  float: right;
  padding: 5px;
  width: 50px;
  background-color: #ab3f29;
  border-radius: 5px;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  margin: 5px;
  margin-top: -30px;
}

#artPrice {
  float: right;
  margin-top: -60px;
}

.card {
  margin: auto;
  margin-bottom: 20px;
  background-color: #ab3f2969;
  border-width: 3px;
  box-shadow: 2px 2px 2px 2px #ab3f2985;
  border-color: #ab3f29;
  border-radius: 5px;
  width: 400px;
  height: auto;
  padding: 15px;
}

@media (max-width: 500px) {
  .card {
      width: 300px;
  }
}
#soldArt {
  text-decoration: line-through;
  float: right;
  margin-top: -57px;
}

#soldTag {
  float: right;
  margin-top: -32px;
  margin-left: 20px;
  font-size: 30px;
  color: #000000;
}

#goBack {
  margin-right: 0;
}
</style>