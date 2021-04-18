<template>
  <div id="listing">
    <div class="success">
      <h2 v-if="statusMessage">{{ statusMessage }}</h2>
    </div>
    <form>
      <div>
        <div class="label">
          <label id="artist-name" for="artistName">Artist Name: </label>
        </div>
        <div v-if="$store.state.user.authorities[0].name == 'ROLE_ARTIST'">
          <input
            type="text"
            name="artistName"
            id="artistName"
            :value="$store.state.user.username"
            v-bind="setCurrentArtist()"
            readonly
          />  
        </div>  
        <div v-else>
          <input
            type="text"
            name="artistName"
            id="artistName"
            v-model.trim="artPiece.artist"
          />
        </div>
      </div>
      <div>
        <div class="label">
          <label for="title">Title: </label>
        </div>
        <input
          type="text"
          name="title"
          id="title"
          v-model.trim="artPiece.title"
        />
      </div>
      <div>
        <div class="label">
          <label for="dateCreated">Date Created: </label>
        </div>
        <input
          type="date"
          name="dateCreated"
          id="dateCreated"
          v-model.trim="artPiece.dateCreated"
        />
      </div>
      <div>
        <div class="label">
          <label for="price">Price:</label>
        </div>
        <input
          type="number"
          step="0.01"
          min="0.01"
          name="price"
          id="price"
          v-model.trim="artPiece.price"
        />
      </div>
      <div>
        <div class="label">
          <label>Dealer Name: </label>
        </div>
        <input type="text" name="dealerName" v-model.trim="artPiece.dealer" />
      </div>
      <div>
        <div class="label">
          <label for="image">Image: </label>
        </div>
        <input type="file" id="file" @change="onFileChanged" accept="image/*" />
      </div>
    </form>
    <div>Or</div>
    <div id="file-drag-drop">
      <form id="dragDrop" ref="fileform" @change="onFileChanged">
        <span class="drop-files">Drop Image Here</span>
      </form>
    </div>
    <p v-if="files[0]">{{ files[0].name }}</p>
    <p v-if="selectedFile">{{ selectedFile.name }}</p>
    <div id="preview">
      <img v-if="url" v-bind:src="url" />
    </div>
    <div class="remove-container">
      <button
        class="remove"
        v-if="files[0] || selectedFile"
        v-on:click="removeFile(files[0])"
      >
        Reset Image
      </button>
    </div>
    <div class="tag-boxes">
      <label for="tags">
        <br />
        Select a tag that describe the artwork: <br
      /></label>
      <input
        type="radio"
        id="abstract"
        name="tag"
        value="2"
        v-model="artPiece.tagID"
      />
      <label for="Abstract"> Abstract</label><br />
      <input
        type="radio"
        id="figurative"
        name="tag"
        value="3"
        v-model="artPiece.tagID"
      />
      <label for="Figurative"> Figurative</label><br />
      <input
        type="radio"
        id="geometric"
        name="tag"
        value="4"
        v-model="artPiece.tagID"
      />
      <label for="Geometric"> Geometric</label><br />
      <input
        type="radio"
        id="minimalist"
        name="tag"
        value="5"
        v-model="artPiece.tagID"
      />
      <label for="Minimalist"> Minimalist</label><br />
      <input
        type="radio"
        id="nature"
        name="tag"
        value="6"
        v-model="artPiece.tagID"
      />
      <label for="Nature"> Nature</label><br />
      <input
        type="radio"
        id="pop"
        name="tag"
        value="7"
        v-model="artPiece.tagID"
      />
      <label for="Pop"> Pop</label><br />
      <input
        type="radio"
        id="surrealist"
        name="tag"
        value="8"
        v-model="artPiece.tagID"
      />
      <label for="Surrealist"> Surrealist</label><br />
      <input
        type="radio"
        id="typography"
        name="tag"
        value="9"
        v-model="artPiece.tagID"
      />
      <label for="Typography"> Typography</label><br />
      <input
        type="radio"
        id="urban"
        name="tag"
        value="10"
        v-model="artPiece.tagID"
      />
      <label for="Urban"> Urban</label><br />
      <input
        type="radio"
        id="cubism"
        name="tag"
        value="11"
        v-model="artPiece.tagID"
      />
      <label for="Cubism"> Cubism</label><br />
      <input
        type="radio"
        id="other"
        name="tag"
        value="1"
        v-model="artPiece.tagID"
      />
      <label for="other"> Other</label>
    </div>
    <div class="type-box">
      <label for="type">
        <br />
        Select the artform: <br
      /></label>
      <input
        type="radio"
        id="drawing"
        name="type"
        value="1"
        v-model="artPiece.typeID"
      />
      <label for="drawing"> Drawing</label><br />
      <input
        type="radio"
        id="Painting"
        name="type"
        value="2"
        v-model="artPiece.typeID"
      />
      <label for="painting"> Painting</label><br />
      <input
        type="radio"
        id="sculpture"
        name="type"
        value="3"
        v-model="artPiece.typeID"
      />
      <label for="sculpture"> Sculpture</label><br />
      <input
        type="radio"
        id="photography"
        name="type"
        value="4"
        v-model="artPiece.typeID"
      />
      <label for="photography"> Photography</label><br />
      <input
        type="radio"
        id="digital"
        name="type"
        value="5"
        v-model="artPiece.typeID"
      />
      <label for="digital"> Digital</label><br />
    </div>
    <div class="label">
      <input
        type="submit"
        v-bind:disabled="!isFormValid"
        v-on:click="createListingForArtPiece()"
      />
    </div>
    <div>
      <button class="cancel" @click="returnHome()">Return Home</button>
    </div>
  </div>
</template>
<script>
import artPieceService from "@/services/ArtPieceService.js";
import firebase from "firebase";
export default {
  name: "createListing",
  data() {
    return {
      selectedFile: null,   
      artPiece: {
          artist: ""
      },
      listingError: false,
      listingErrorMessage: "",
      dragAndDropCapable: false,
      files: [],
      url: null,
      uploadPercentage: 0,
      statusMessage: "",
    };
  },
  mounted() {
    this.dragAndDropCapable = this.determineDragAndDropCapable();
    if (this.dragAndDropCapable) {
      [
        "drag",
        "dragstart",
        "dragend",
        "dragover",
        "dragenter",
        "dragleave",
        "drop",
      ].forEach(
        function (evt) {
          this.$refs.fileform.addEventListener(
            evt,
            function (e) {
              e.preventDefault();
              e.stopPropagation();
            }.bind(this),
            false
          );
        }.bind(this)
      );
      this.$refs.fileform.addEventListener(
        "drop",
        function (e) {
          if (/\.(jpe?g|png|gif)$/i.test(e.dataTransfer.files[0].name)) {
            this.removeFile();
            this.files.push(e.dataTransfer.files[0]);
            this.showPreview(this.files[0]);
            this.selectedFile = null;
            this.resetInputText();
          }
        }.bind(this)
      );

      let today = new Date();
      let dd = today.getDate();
      let mm = today.getMonth() + 1; //January is 0!
      let yyyy = today.getFullYear();
      if (dd < 10) {
        dd = "0" + dd;
      }
      if (mm < 10) {
        mm = "0" + mm;
      }
      today = yyyy + "-" + mm + "-" + dd;
      document.getElementById("dateCreated").setAttribute("max", today);
    }
  },
  computed: {
    isFormValid() {
      return (
        (this.files[0] || this.selectedFile) &&
        this.artPiece.artist &&
        this.artPiece.title &&
        this.artPiece.price &&
        this.artPiece.dateCreated
      );
    },
  },
  methods: {
    setCurrentArtist() {
      //if(this.$store.state.user.authorities[0].name == 'ROLE_ARTIST') {
        this.artPiece.artist = this.$store.state.user.username;
      // } else {
      //   this.artPiece.artist = "";
      // }
    },
    resetInputText() {
      document.getElementById("file").value = "";
    },
    returnHome() {
      this.$router.push({ path: "/" });
    },
    clearForm() {
      this.removeFile();
      this.selectedFile = null;
      this.artPiece = {};
      this.listingError = false;
      this.listingErrorMessage = "";
      this.dragAndDropCapable = false;
      this.files = [];
      this.url = null;
      this.uploadPercentage = 0;
    },
    removeFile(key) {
      this.files.splice(key, 1);
      this.url = null;
      this.selectedFile = null;
      this.showPreview(this.url);
      this.resetInputText();
    },
    showPreview(file) {
      if (file) {
        this.url = URL.createObjectURL(file);
      } else {
        return;
      }
    },
    determineDragAndDropCapable() {
      var divDrop = document.createElement("div");
      return (
        ("draggable" in divDrop ||
          ("ondragstart" in divDrop && "ondrop" in divDrop)) &&
        "FormData" in window &&
        "FileReader" in window
      );
    },
    onFileChanged(event) {
      this.selectedFile = event.target.files[0];
      this.artPiece.imgFileName = this.selectedFile.name;
      this.showPreview(this.selectedFile);
    },
    onUpload() {
      const storageRef = firebase.storage().ref();
      if (!this.selectedFile) {
        storageRef.child(this.files[0].name).put(this.files[0]);
        this.artPiece.imgFileName = this.files[0].name;
      } else {
        storageRef.child(this.selectedFile.name).put(this.selectedFile);
      }
    },
    createListingForArtPiece() {
      this.onUpload();
      artPieceService
        .createListing(this.artPiece)
        .then((response) => {
          if (response.status == 201) {
            this.clearForm();
            this.statusMessage = "Listing created!";
          }
        })
        .catch((error) => {
          const response = error.response;
          this.listingError = true;
          if (response.status !== 201) {
            this.statusMessage = "There were problems creating this listing.";
          }
        });
    },
  },
};
</script>
<style scoped>
#listing {
  background-color: #ab3f294b;
  border-radius: 20px;
  color: #fff;
  font-family: "Quicksand", sans-serif;
  width: 40%;
  min-width: 300px;
  padding: 0px 20px 10px 20px;
  margin: auto;
  display: flex;
  text-align: center;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
}
#artist-name {
  margin-top: 0;
  padding-top: 0;
  line-height: 0;
}
img {
  width: 30%;
  margin: auto;
  display: block;
  margin-bottom: 10px;
}
p {
  line-height: 2rem;
}
#file {
  /* margin-left: 20px; */
  margin-top: 5px;
  margin-bottom: 10px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 7px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
}
.remove {
  margin: 10px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 2px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  padding: 5px 15px;
  cursor: pointer;
}
.label {
  padding-top: 20px;
}
#dragDrop {
  display: block;
  height: 80px;
  width: 90%;
  background: #ccc;
  margin: auto;
  margin-top: 5px;
  text-align: center;
  border-radius: 4px;
}
#preview {
  display: flex;
  justify-content: center;
  align-items: center;
}
#preview img {
  width: 50%;
  max-height: 100%;
}
.cancel {
  margin: 10px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 2px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  padding: 5px 15px;
  cursor: pointer;
}
input[type="submit"] {
  background-color: #ab3f29;
  color: #fff;
  border-radius: 2px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  padding: 5px 15px;
  cursor: pointer;
}
input[type="submit"]:disabled {
  background-color: #ab3f293f;
  color: #f4f4f43a;
  border-radius: 2px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f083a;
  padding: 5px 15px;
  cursor: default;
}
input[type="file"] {
  color: #fff;
  width: 250px;
  border-radius: 2px;
}
#dragDrop {
  display: flex;
  justify-content: center;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 7px;
  border: none;
  border: inset;
  border-color: #310f08b7;
  /* box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7; */
}
#dragDrop:hover {
  display: flex;
  justify-content: center;
  background-color: #702a1bc9;
  color: #fff;
  border-radius: 7px;
  border: none;
  border: inset;
  border-color: #310f08b7;
}
.drop-files {
  margin-top: 15px;
}

h2 {
  padding-top: 1.5rem;
}

::-webkit-input-placeholder {
  /* Edge */
  color: #000;
}

:-ms-input-placeholder {
  /* Internet Explorer 10-11 */
  color: #000;
}

::placeholder {
  color: #000;
}

/* .tag-boxes {
  text-align: left;
  margin: 20px;
} */
</style>