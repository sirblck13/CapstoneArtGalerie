<template>
  <router-link
    v-bind:to="{ name: 'FeeOverride', params: { artId: artPiece.artID } }"
  >
    <div class="settingsCard">
      <img
        class="settingsMyImg"
        :src="imgFile"
        v-bind:alt="artPiece.imgFileName"
      />
      <h2>{{ artPiece.title }}</h2>
      <h3 id="settingsMainArtist">{{ artPiece.artist }}</h3>
    </div>
  </router-link>
</template>

<script>
import firebase from "firebase";
export default {
  name: "settings-display-art",
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
.settingsCard {
  background-color: #ab3f2969;
  border-width: 3px;
  box-shadow: 2px 2px 2px 2px #ab3f2985;
  border-color: #ab3f29;
  border-radius: 5px;
  width: 150px;
  height: auto;
  padding: 15px;
  margin: 20px;
  display: flex;
  flex-direction: column;
  color: #f4f4f4eb;
  font-family: "Quicksand", sans-serif;
}
@media (max-width: 500px) {
  .settingsCard {
    width: 250px;
  }
}
a:link {
  text-decoration: none;
}

.settingsMyImg {
  width: 100%;
  height: auto;
  border-color: #e06614;
  border-radius: 5px;
  box-shadow: 3px 3px 3px 3px #863121;
}

h2 {
  font-size: 1.25rem;
  font-style: italic;
  padding-top: 5px;
}

#settingsMainArtist {
  font-size: 1rem;
}
</style>