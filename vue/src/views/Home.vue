<template>
  <div>
    <h1>Featured Art</h1>
    <div class="searchFunctions">
      <div>
        <label for="">Search By Artist: </label>
        <input type="text" v-model="artistSelection"/>
      </div>
      <div id="dropdownMenu">
        <label for="tag">Choose a Art Tag: </label>
        <select name="tag" id="tag" v-model="tagSelection">
          <option value="0">All</option>
          <option value="2">Abstract</option>
          <option value="3">Figurative</option>
          <option value="4">Geometric</option>
          <option value="5">Minimalist</option>
          <option value="6">Nature</option>
          <option value="7">Pop</option>
          <option value="8">Surrealist</option>
          <option value="9">Typography</option>
          <option value="10">Urban</option>
          <option value="11">Cubism</option>
          <option value="1">Other</option>
        </select>
      </div>
      <div id="dropdownMenu">
        <label for="type">Choose a Art Type: </label>
        <select name="type" id="type" v-model="typeSelection">
          <option value="0">All</option>
          <option value="1">Drawing</option>
          <option value="2">Painting</option>
          <option value="3">Sculpture</option>
          <option value="4">Photography</option>
          <option value="5">Digital</option>
        </select>
      </div>
    </div>
    <div class="art-container">
      <display-art class="art-card"
        v-for="artPiece in getTypeSelection"
        v-bind:key="artPiece.artID"
        v-bind:artPiece="artPiece"
      />
    </div>
  </div>
</template>

<script>
import DisplayArt from "../components/DisplayArt.vue";
import artPieceService from "@/services/ArtPieceService.js";

export default {
  name: "home",
  data(){
    return{
      ArtPieces: [],
      tagSelection: 0,
      typeSelection: 0,
      artistSelection: ""
    }
  },
  components: {
    DisplayArt,
  },
  created() {
    artPieceService
      .getAllListings()
      .then((response) => {
        this.$store.commit("SET_ART_DATA", response.data);
        this.ArtPieces = response.data;
      })
      .catch((err) => console.error(err));
  },
  computed: {
    getTypeSelection(){
      let filteredArtPiece = this.ArtPieces;
      if(this.typeSelection > 0){
        filteredArtPiece = filteredArtPiece.filter((x) => {
          return x.typeID == this.typeSelection;
        });
      }

      if(this.tagSelection > 0){
        filteredArtPiece = filteredArtPiece.filter((x) => {
          return x.tagID == this.tagSelection;
        });        
      }
      if(this.artistSelection != ""){
        filteredArtPiece = filteredArtPiece.filter((x) => {
          return x.artist.toUpperCase().includes(this.artistSelection.toUpperCase());
        });
      }
      return filteredArtPiece;
    }
  }
};
</script>

<style scoped>
.art-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
  max-width: 75%;
  margin: auto;
}

.art-card {
  display: flex;
  align-self: center;
  justify-self: center;
  margin: 30px;
  margin-top: 0;
  width: 400px;
}

.searchFunctions {
  display: flex;
  justify-content: center;
  margin: 10px 0px;
  color: whitesmoke;
  max-width: 75%;
  font-weight: bold;
  flex-wrap: wrap;
  margin: auto;

}

.searchFunctions > * {
  display: flex;
  align-self: center;
  justify-self: center;
  margin: 10px;
}

label {
  margin-right: 2px;
}

#dropdownMenu {
  margin-left: 20px;
}

</style>