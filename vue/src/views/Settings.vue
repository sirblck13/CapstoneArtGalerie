<template>
  <div>
    <h1>Settings</h1>

    <register-others />

    <div>
      <make-available/>
    </div>

    <div v-show="$store.state.user.authorities[0].name == 'ROLE_ADMIN'">
      <fee-settings/>
    </div>
    

    <div v-if="$store.state.user.authorities[0].name == 'ROLE_ADMIN'">
      <h2 class="override">Override Default Fees</h2>
      <div class="art-container">
        <settings-display-art
          v-for="artPiece in $store.state.artPieceData" 
          v-bind:key="artPiece.artID"
          v-bind:artPiece="artPiece"
          v-show="!artPiece.sold"
        />
      </div>
    </div>

  </div>
</template>

<script>
import transactionService from "@/services/TransactionService.js";
import SettingsDisplayArt from "../components/SettingsDisplayArt.vue";
import artPieceService from "@/services/ArtPieceService.js";
import RegisterOthers from "../components/RegisterOthers.vue";
import FeeSettings from '../components/FeeSettings.vue';
import MakeAvailable from '../components/MakeAvailable.vue';


export default {
  name: "settings",
  components: {
    SettingsDisplayArt,
    RegisterOthers,
    FeeSettings,
    MakeAvailable
  },

  data() {
    return {
      currentDefaultFees: {},
    };
  },
  created() {
    transactionService
      .getCurrentDefaultFees()
      .then((response) => {
        this.currentDefaultFees = response.data;
      })
      .catch((error) => {
        const response = error.response;
        console.log(response);
      });

    artPieceService
      .getAllListings()
        .then((response) => {
          this.$store.commit("SET_ART_DATA", response.data);
      })
      .catch((err) => console.error(err));
  },

  methods: {
    setFee() {
      transactionService
        .setDefaultFee(this.currentDefaultFees)
        .then((response) => {
          if (response.status == 200) {
            alert("Fees Updated");
          }
        })
        .catch((error) => {
          const response = error.response;
          console.log(response);
        });
    },
  },
};
</script>

<style scoped>
h2 {
  color: #fff;
  font-family: "Quicksand", sans-serif;
  font-weight: 700;
  line-height: 25px;
  margin-top: 1em;
  margin-bottom: 1em;
  text-shadow: 2px 2px #2b2929fb;
  text-align: center;
  font-style: italic;
}

.art-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
  align-items: center;
  max-width: 75%;
  margin: auto;
}
</style>