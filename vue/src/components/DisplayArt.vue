<template>
  <router-link
    v-bind:to="{ name: 'ArtDetails', params: { artId: artPiece.artID } }"
  >
    <div class="card">
      <img class="myImg" :src="imgFile" v-bind:alt="artPiece.imgFileName" />
      <h2>{{ artPiece.title }}</h2>
      <h3 id="mainArtist">{{ artPiece.artist }}</h3>
    </div>
    <div
      class="banner"
      id="coming-soon"
      v-if="!artPiece.available && !artPiece.sold"
    >
      Available Soon!
    </div>
    <div class="banner" id="sold" v-if="artPiece.sold">SOLD</div>
  </router-link>
</template>

<script>
import firebase from "firebase";
export default {
  name: "display-art",
  props: ["artPiece"],

  data() {
    return {
      imgFile: {},
    };
  },

  created() {
    let storage = firebase.storage();
    let storageRef = storage.ref();
    let imgRef = storageRef.child(this.artPiece.imgFileName);

    imgRef.getDownloadURL().then((url) => {
      this.imgFile = url;
    });
  },
};
</script>

<style scoped>
.card {
  background-color: #ab3f2969;
  border-width: 3px;
  box-shadow: 2px 2px 2px 2px #ab3f2985;
  border-color: #ab3f29;
  border-radius: 5px;
  width: 400px;
  height: auto;
  padding: 15px;
  color: #fff;
  font-family: "Quicksand", sans-serif;
}
@media (max-width: 500px) {
  .card {
    width: 250px;
  }
}

a:link {
  text-decoration: none;
}

.myImg {
  display: block;
  width: 100%;
  margin: auto;
  margin-bottom: 10px;
  border-color: #e06614;
  border-radius: 5px;
  box-shadow: 3px 3px 3px 3px #863121;
}

h2 {
  font-size: 1.25rem;
  font-style: italic;
}

#mainArtist {
  font-size: 1rem;
}

.banner {
  align-self: flex-end;
  padding: 5px;
  margin-bottom: 5px;
  color: #fff;
  border-radius: 5px 0 0 5px;
}

#coming-soon {
  background: #ab3f29bb;
  width: 150px;
  margin-left: -145px;
}

#sold {
  background: rgba(0, 0, 0, 0.75);
  width: 100px;
  margin-left: -95px;
}

/* #triangle-topright {
  width: 0;
  height: 0;
  border-top: 20px solid red;
  border-left: 20px solid transparent;
} */
</style>