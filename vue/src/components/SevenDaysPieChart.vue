<script>
import artPieceService from "@/services/ArtPieceService.js";
import transactionService from "@/services/TransactionService.js";
import { Pie } from "vue-chartjs";
export default {
    extends: Pie,
     data() {
    return {
      // grossRevenue: 0,
      // netProfits: 0,
      // totalCommissionsPaid: 0,
      // totalPaidToArtists: 0,
      artPieces: [],
      transactions: [],
      numberOfArtPieces: 0,
      numOfArtPiecesSold: 0,
      numOfArtPiecesForSale: 0
      // transactions: null,
    };
  },
    methods: {
        getArtistTransactions(){
            this.currentArtistTransaction = this.transactions.filter((x) => {
            return x.artist === this.$store.state.user.username;
            });
        },
        getDealerTransactions(){
            this.currentDealerTransaction = this.transactions.filter((x) => {
            return x.dealer === this.$store.state.user.username;
            });
        }
    }
    ,
    mounted() {
    this.gradient = this.$refs.canvas
      .getContext("2d")
      .createLinearGradient(0, 0, 0, 450);
    this.gradient2 = this.$refs.canvas
      .getContext("2d")
      .createLinearGradient(0, 0, 0, 450);
    this.gradient.addColorStop(0, "rgba(255, 0,0, 0.5)");
    this.gradient.addColorStop(0.5, "rgba(255, 0, 0, 0.25)");
    this.gradient.addColorStop(1, "rgba(255, 0, 0, 0)");
    this.gradient2.addColorStop(0, "rgba(0, 231, 255, 0.9)");
    this.gradient2.addColorStop(0.5, "rgba(0, 231, 255, 0.25)");
    this.gradient2.addColorStop(1, "rgba(0, 231, 255, 0)");
    this.renderChart(
      {
        labels: ["Sold", "Unsold"],
        datasets: [
          {
            backgroundColor: [this.gradient, this.gradient2, "#00D8FF"],
            data: [this.totalNumberSold, (this.totalNumberOfArt - this.totalNumberSold)]
          }
        ]
      },
      { responsive: true, maintainAspectRatio: false }
    );
  },
  computed: {
   totalNumberOfArt() {
      let num = 0;

      if ((this.$store.state.user.authorities[0].name == "ROLE_ARTIST")
          || (this.$store.state.user.authorities[0].name == "ROLE_SELLER")) {
        const listOfArt = this.$store.state.artPieceData.filter((x) => {
          return (x.artist == this.$store.state.user.username);
        });
        num = listOfArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_DEALER") {
        const listOfArt = this.$store.state.artPieceData.filter((x) => {
          return (x.dealer == this.$store.state.user.username);
        });
        num = listOfArt.length;
      }

      if (this.$store.state.user.authorities[0].name == "ROLE_ADMIN") {
        num = this.$store.state.artPieceData.length;
      }

      return num;
    },
    totalNumberSold() {
        let numOfArtSold = 0;

        // admin art - all art sold in last 30 days
        if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
             let soldArt = this.$store.state.transactionData7;
            numOfArtSold = soldArt.length;
        }
        
        
        //artist sold - all art sold in last 30 days by ARTIST name
        if ((this.$store.state.user.authorities[0].name == 'ROLE_ARTIST') || (this.$store.state.user.authorities[0].name == 'ROLE_SELLER')){
            const soldForArtist = this.$store.state.transactionData7.filter((x) => {
            return x.artist === this.$store.state.user.username;
            });
         numOfArtSold = soldForArtist.length;
        }

        //dealer sold
        if (this.$store.state.user.authorities[0].name == 'ROLE_DEALER'){
            const soldForDealer = this.$store.state.transactionData7.filter((x) => {
            return x.dealer === this.$store.state.user.username;
            });
         numOfArtSold = soldForDealer.length;
        }
            return numOfArtSold;
    },
    totalAvailable() {
        let numOfAvailableArt = 0;
        if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
            const availableArt = this.$store.state.artPieceData.filter((x) => {
                return x.available;
         });
            numOfAvailableArt = availableArt.length;
        }
        let availableArt = [];
        availableArt = this.$store.state.artPieceData.filter((x) => {
            return x.available;
        });
        if ((this.$store.state.user.authorities[0].name == 'ROLE_ARTIST') || (this.$store.state.user.authorities[0].name == 'ROLE_SELLER')){
            const availableForArtist = availableArt.filter((x) => {
            return x.artist === this.$store.state.user.username;
            });
         numOfAvailableArt = availableForArtist.length;
        }
        if (this.$store.state.user.authorities[0].name == 'ROLE_DEALER'){
            const availableForDealer = availableArt.filter((x) => {
            return x.dealer === this.$store.state.user.username;
            });
         numOfAvailableArt = availableForDealer.length;
        }
        return numOfAvailableArt;
    },
    totalUnavailable() {
         let numOfUnavailableArt = 0;
        if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
            const unavailableArt = this.$store.state.artPieceData.filter((x) => {
                return !x.available;
         });
            numOfUnavailableArt = unavailableArt.length;
        }
        let unavailableArt = [];
        unavailableArt = this.$store.state.artPieceData.filter((x) => {
            return !x.available;
        });
        if ((this.$store.state.user.authorities[0].name == 'ROLE_ARTIST') || (this.$store.state.user.authorities[0].name == 'ROLE_SELLER')){
            const unavailableForArtist = unavailableArt.filter((x) => {
            return x.artist === this.$store.state.user.username;
            });
         numOfUnavailableArt = unavailableForArtist.length;
        }
        if (this.$store.state.user.authorities[0].name == 'ROLE_DEALER'){
            const unavailableForDealer = unavailableArt.filter((x) => {
            return x.dealer === this.$store.state.user.username;
            });
         numOfUnavailableArt = unavailableForDealer.length;
        }
        return numOfUnavailableArt;
    }
},

  created() {
    artPieceService
      .getAllListings()
      .then((response) => {
        this.$store.commit("SET_ART_DATA", response.data);
      })
      .catch((err) => console.error(err));
    transactionService
        .getTransactions7Days()
        .then((response) => {
        this.$store.commit("SET_TRANSACTION_DATA_7", response.data);
                    // this.getArtistTransactions();
                    // this.getDealerTransactions();
                })
      .catch((err) => console.error(err));
    
  }
}
</script>
<style scoped>
</style>