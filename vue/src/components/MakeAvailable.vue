<template>
  <div>
    <div>
        <h1>Listing Availability</h1>
    </div>
    <div class="dragdrop">
        <div class='drop-zone-true'
        @drop='onDrop($event, true)'
        @dragover.prevent
        @dragenter.prevent 
        >
            <h2 class="dropheader">
                Available
            </h2>
            <div v-for='art in availableArt' 
            :key='art.artID' 
            class='drag-el'
            draggable
            @dragstart='startDrag($event, art)'
            >
            
            <h4>{{ art.title }} | {{art.artist}} | {{art.dateCreated}}</h4>    
            </div>
        </div>
            <div class='drop-zone-false'
            @drop='onDrop($event, false)'
            @dragover.prevent
            @dragenter.prevent 
            >
            <h2 id="dropfalse">
                Unavailable
            </h2>
                <div v-for='art in unavailableArt' 
                :key='art.artID' 
                class='drag-el'
                draggable
                @dragstart='startDrag($event, art)'
                >
                    <h4>{{ art.title }} | {{art.artist}} | {{art.dateCreated}}</h4>   
                </div>
            </div>

    </div>
  </div>
</template>


<script>
import artPieceService from "@/services/ArtPieceService.js";

export default {
    name: 'MakeAvailable',

    data(){
        return{
            unsoldArt: []
        
        }
    },

    created(){
        artPieceService
            .getAllListings()
                .then((response) => {
                    this.$store.commit("SET_ART_DATA", response.data);
                    this.assignAvailability();
            })
            .catch((err) => console.error(err));
    },

    computed: {

        availableArt(){
            return this.unsoldArt.filter(art => art.available);
        },

        unavailableArt(){
            return this.unsoldArt.filter(art => !art.available);
        }
    },

    methods: {

        assignAvailability(){
            this.unsoldArt = this.$store.state.artPieceData.filter((art) => {
                return !art.sold;
            });
        },

        startDrag: (evt, art) => {
            evt.dataTransfer.dropEffect = 'move'
            evt.dataTransfer.effectAllowed = 'move'
            evt.dataTransfer.setData('artID', art.artID) 
        },

        onDrop (evt, available) {
            const artID = evt.dataTransfer.getData('artID')
            const artPiece = this.unsoldArt.find(artPiece => artPiece.artID == artID)
           
            artPieceService.flipAvailability(artPiece.artID)
            .then(
                  artPiece.available = available
            ).catch((err) => console.error(err));
            

          
        }

    },




}
</script>

<style scoped>
  .drop-zone-true {
    background-color: #eee;
    height: 400px;
    margin: 10px;
    padding: 30px;
    width: 300px;
    background-color: #ab3f2969;
  border-width: 3px;
  /* box-shadow: 2px 2px 2px 2px #ab3f2985; */
  border-color: #ab3f29;
  border-radius: 5px;
  width: 400px;
  height: auto;
  /* padding: 15px; */
  color: rgb(255, 255, 255);
  font-family: "Quicksand", sans-serif;
  border-style: dashed;
   margin-bottom: 50px;
  }

  .drop-zone-false {
    background-color: #eee;
    height: 400px;
      margin: 10px;
    margin-bottom: 50px;
    padding: 30px;
    width: 300px;
    background-color: #ab3f2969;
  border-width: 3px;
  /* box-shadow: 2px 2px 2px 2px #ab3f2985; */
  border-color: #ab3f29;
  border-radius: 5px;
  width: 400px;
  height: auto;
  /* padding: 15px; */
  color: rgb(255, 255, 255);
  font-family: "Quicksand", sans-serif;
   border-style: dashed;
  }

  .drag-el {
    background-color: #ab3f2969;
    margin-bottom: 10px;
    padding: 5px;
    border-color: #ffffff;
    border-radius: 10px;
    border-width: 5px;
    box-shadow: 2px 2px 2px 2px #6f281a85; 

  }
  
  .drag-el:active {

       cursor: grabbing;

  }
  .dragdrop {
      display: flex;
      justify-content: center;
      cursor: grab;
      
      

  }

  h2 {
  color: #fff;
  font-family: "Quicksand", sans-serif;
  font-weight: 700;
  line-height: 25px;
  margin-top: 1em;
  margin-bottom: 1em;
  text-shadow: 2px 2px #2b2929fb;
  text-align: center;
  }
  h4{
     text-align: center;
  }
</style>