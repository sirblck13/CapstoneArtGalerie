<template>
    <div id="transactionStatus">
        <div v-if="$store.state.user.authorities[0].name == 'ROLE_ADMIN'">
            <table id="transactionStatus">
                <h1>Transaction Report</h1>
                <tr>
                    <td>Gross Revenue</td>
                    <td>${{getRevenue}}</td>
                </tr>
                <tr>
                    <td>Net Profit</td>
                    <td>${{getFees}}</td>
                </tr>
                <tr>
                    <td>Dealer Commission</td>
                    <td>${{getComission}}</td>
                </tr>
                <tr>
                    <td>Artist Commission</td>
                    <td>${{getArtistTotal}}</td>
                </tr>

            </table>
        </div>
        <div v-if="$store.state.user.authorities[0].name == 'ROLE_DEALER'">
            <h1>Commission Report</h1>
            <table>
                <tr>
                    <td>Commissions Received</td>
                    <td>{{getCommissionForDealer}}</td>
                </tr>
            </table>
        </div>
        
        <div v-if="$store.state.user.authorities[0].name == 'ROLE_ARTIST'">
            <h1>Payment Report</h1>
            <h3>Total Pay: ${{getCommissionForArtist}}</h3>
               
        </div>
        <div class="transaction-details">
        <transaction-details
        v-for="transaction in transactions"
        v-bind:key="transaction.artID"
        v-bind:transaction="transaction"
         />
        </div>

    </div>
</template>

<script>
import transactionService from "@/services/TransactionService.js";
import TransactionDetails from "./TransactionDetails.vue";

export default {
    name: "TransactionStatus",
    components: {
        TransactionDetails
    },
    data(){
        return{
            transactions: [],
            currentDealerTransaction: [],
            currentArtistTransaction: []
        
        }
    },
    created(){
        transactionService
            .getAllTransactions()
                .then((response) => {
                    this.transactions = response.data;
                    this.getArtistTransactions();
                    this.getDealerTransactions();
                })
                .catch((err) => console.error(err));
    },
    computed: {
        getRevenue(){
            let sum = 0;
            this.transactions.forEach((price) => {
               sum += price.totalPrice;
            })
            return parseFloat(sum).toFixed(2);
        },
        getArtistTotal(){
            let sum = 0;
            this.transactions.forEach((price) => {
                sum += price.price;
            })
            return parseFloat(sum).toFixed(2);
        },
        getComission(){
            let sum = 0;
            this.transactions.forEach((price) => {
                sum += price.commission;
            })
            return parseFloat(sum).toFixed(2);
        },
        getFees(){
            let sum = 0;
            this.transactions.forEach((price) => {
                sum += price.fee;
            })
            return parseFloat(sum).toFixed(2);
        },
        getCommissionForDealer(){
            const dealerTransactions = this.transactions.filter((x) => {
            return x.dealer === this.$store.state.user.username;
            });
            let sum = 0;
            dealerTransactions.forEach((price) => {
                sum += price.commission;
            })
            return parseFloat(sum).toFixed(2);
        },
        getCommissionForArtist(){
            const artistTransactions = this.transactions.filter((x) => {
            return x.artist === this.$store.state.user.username;
            });
            let sum = 0;
            artistTransactions.forEach((price) => {
                sum += price.price;

            });
            return parseFloat(sum).toFixed(2);
        },
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

}
</script>

<style>
#transactionStatus {
  background-color: #ab3f294b;
  border-radius: 20px;
  width: fit-content;
  padding: 40px;
  margin: auto;
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #fff;
  font-family: "Quicksand", sans-serif;
}
</style>