<template>
  <div>
    <h1>Edit Art</h1>
    <div id="edit-grid">
      <div id="left-edit">
        <img
          class="artImgEdit"
          :src="imgFile"
          v-bind:alt="artPiece.imgFileName"
        />
      </div>
      <div id="right-edit">
        <form>
          <div>
            <label>Artist: </label>
          </div>
          <input type="text" v-model.trim="artPiece.artist" />

          <div>
            <label>Title: </label>
          </div>
          <input type="text" v-model.trim="artPiece.title" />

          <div>
            <label>Date: </label>
          </div>
          <input type="date" v-model.trim="artPiece.dateCreated" />

          <div>
            <label>Price: </label>
          </div>
          <input type="number" v-model.trim="artPiece.price" />

          <div>
            <label>Dealer: </label>
          </div>
          <input type="text" v-model.trim="artPiece.dealer" />
        </form>

        <button id="confirmEdit" @click="sendEditedArtPiece()">
          Confirm Edit
        </button>
        <button id="deleteListing" @click="deleteArtPiece()">
          Delete Listing
        </button>
        <router-link
      v-bind:to="{ name: 'ArtDetails', params: { artId: artPiece.artID } }"
  >
        <button id="backDetails">Back</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
import artPieceService from "@/services/ArtPieceService.js";
export default {
  name: "edit-art",
  data() {
    return {
      artPiece: {},
      imgFile: {},
      foundId: 0,
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
  },

  created() {
    this.foundId = this.$route.params.artId;
    artPieceService
      .getListingByArtId(this.foundId)
      .then((response) => {
        this.artPiece = response.data;
        // this.title = this.artPiece.title;

        let storage = firebase.storage();
        let storageRef = storage.ref();
        let imgRef = storageRef.child(this.artPiece.imgFileName);

        imgRef.getDownloadURL().then((url) => {
          this.imgFile = url;
        });
      })
      .catch(console.log("not working"));
  },
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
  padding: 20px;
  width: 80%;
  max-width: 1000px;
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
  grid-template-areas: "image details";
  justify-content: center;
}

@media (max-width: 720px) {
  div#edit-grid {
    grid-template-areas:
      "image"
      "details";
    padding: 20px;
    width: 400px;
  }
}

#confirmEdit {
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



#backDetails {
  padding: 5px 15px;
  margin-top: 10px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  width: 100%;
  
}
</style>