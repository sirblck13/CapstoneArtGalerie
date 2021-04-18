<template>
  <div>
    <div id="reportStatus">
      <h1>Status Report</h1>
      <div class="report">
        <table id="overview" class="statusTable">
          <tr>
            <th class="stat">Overview</th>
          </tr>
          <tr>
            <td># of art in gallery</td>
            <td>{{ totalNumberOfArt }}</td>
          </tr>
          <td># of sold art</td>
          <td>{{ totalNumberSold }}</td>
          <tr>
            <td># of available art</td>
            <td>{{ totalAvailable }}</td>
          </tr>
          <tr>
            <td># of unavailable art</td>
            <td>{{ totalUnavailable }}</td>
          </tr>
        </table>

        <table id="allListings" class="statusTable">
          <tr>
            <th class="stat">Art Title</th>
            <th class="stat">Status</th>
          </tr>
          <listing-status
            v-for="artPiece in $store.state.artPieceData"
            v-bind:key="artPiece.artID"
            v-bind:artPiece="artPiece"
          />
        </table>
      </div>
    </div>

    <div class="pie-container" v-if="buttonAllStatus">
      <h2>All Time Sales</h2>
      <PieChart />
    </div>
    <div class="pie-container" v-if="button30Status">
      <h2>Last 30 Days of Sales</h2>
      <ThirtyDays />
    </div>
    <div class="pie-container" v-if="button7Status">
      <h2>Last 7 Days of Sales</h2>
      <SevenDays />
    </div>
    <div id="buttonContainer">
     <button v-bind:class="{disabled: buttonAllStatus}" v-on:click="flipStatusAll(buttonAllStatus)">
        All Time
      </button>
      <button v-bind:class="{disabled: button30Status}" v-on:click="flipStatus30(button30Status)">
        Last 30 Days
      </button>
      <button v-bind:class="{disabled: button7Status}" v-on:click="flipStatus7(button7Status)">
        Last 7 Days
      </button>
    </div>
  </div>
</template>

<script>
import artPieceService from "@/services/ArtPieceService.js";
import ListingStatus from "../components/ListingStatus.vue";
import PieChart from "../components/PieChart.vue";
import ThirtyDays from "../components/ThirtyDaysPieChart.vue";
import SevenDays from "../components/SevenDaysPieChart.vue";

export default {
  name: "ReportStatus",
  components: {
    PieChart,
    ListingStatus,
    ThirtyDays,
    SevenDays,
  },
  data() {
    return {
      artPieces: [],
      transactions: null,
      buttonAllStatus: true,
      button30Status: false,
      button7Status: false
    };
  },

  computed: {
    totalNumberOfArt() {
      let num = 0;

      if ((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")) {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.artist == this.$store.state.user.username);
        });
        num = listOfArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_DEALER") {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.dealer == this.$store.state.user.username);
        });
        num = listOfArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_ADMIN") {
        num = this.artPieces.length;
      }

      return num;
    },

    totalNumberSold() {
      let num = 0;

      if ((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")) {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.artist == this.$store.state.user.username);
        });
        const soldArt = listOfArt.filter((x) => {
          return x.sold;
        });

        num = soldArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_DEALER") {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.dealer == this.$store.state.user.username);
        });
        const soldArt = listOfArt.filter((x) => {
          return x.sold;
        });

        num = soldArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_ADMIN") {
        const soldArt = this.artPieces.filter((x) => {
          return x.sold;
        });

        num = soldArt.length;
      }

      return num;
    },

    totalAvailable() {
      let num = 0;

      if ((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")) {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.artist == this.$store.state.user.username);
        });
        const availableArt = listOfArt.filter((x) => {
          return x.available;
        });

        num = availableArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_DEALER") {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.dealer == this.$store.state.user.username);
        });
        const availableArt = listOfArt.filter((x) => {
          return x.available;
        });

        num = availableArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_ADMIN") {
        const availableArt = this.artPieces.filter((x) => {
          return x.available;
        });

        num = availableArt.length;
      }
      return num;
    },

    totalUnavailable() {
      let num = 0;

      if ((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")) {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.artist == this.$store.state.user.username);
        });
        const unavailableArt = listOfArt.filter((x) => {
          return !x.available && !x.sold;
        });

        num = unavailableArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_DEALER") {
        const listOfArt = this.artPieces.filter((x) => {
          return (x.dealer == this.$store.state.user.username);
        });
        const unavailableArt = listOfArt.filter((x) => {
          return !x.available && !x.sold;
        });

        num = unavailableArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_ADMIN") {
        const unavailableArt = this.artPieces.filter((x) => {
          return !x.available && !x.sold;
        });

        num = unavailableArt.length;
      }
      return num;
    },
  },

  created() {
    artPieceService
      .getAllListings()
      .then((response) => {
        this.artPieces = response.data;
        this.$store.commit("SET_ART_DATA", response.data);
      })
      .catch((err) => console.error(err));
  },
  methods: {
      flipStatusAll(buttonAllStatus) {
      if(this.buttonAllStatus == false){
        this.buttonAllStatus = !buttonAllStatus;
        this.button30Status = false;
        this.button7Status = false;
      }
    },
    flipStatus30(button30Status) {
      if(this.button30Status == false){
        this.button30Status = !button30Status;
        this.buttonAllStatus = false;
        this.button7Status = false;
      }
    },
    flipStatus7(button7Status) {
      if(this.button7Status == false){
        this.button7Status = !button7Status;
        this.buttonAllStatus = false;
        this.button30Status = false;
      }
    }
  }
};
</script>

<style scoped>
#reportStatus {
  background-color: #ab3f294b;
  border-radius: 20px;
  width: fit-content;
  padding: 40px;
  margin: auto;
  margin-bottom: 20px;
  /* display: flex; */
  /* flex-direction: column; */
  align-items: center;
  color: #fff;
  font-family: "Quicksand", sans-serif;
}

#allListings {
  width: 300px;
  /* padding: 10px; */
  text-align: left;
}

.stat {
  font-size: 18px;
}

.report {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 100px;
}

.statusTable {
  margin-left: 20px;
  margin-right: 20px;
}

#overview {
  width: 225px;
  border-right: solid;
  border-right-width: 3px;
  padding-right: 35px;
}

#buttonContainer {
  display: flex;
  justify-content: center;
}

button {
  border: none;
  padding: 5px 15px;
  width: 120px;
  background-color: #ab3f29;
  border-radius: 2px;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  margin: 10px;
  color: #fff;
}

button.disabled {
  background-color: #ab3f293f;
  color: #f4f4f43a;
  border-radius: 2px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f083a;
  cursor: default;
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
  font-style: italic;
}
</style>