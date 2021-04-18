<template>
  <div>
    <h1>My Account</h1>
    <report-status />
    <div id="reportStatus">
      <transaction-status />
    </div>
  </div>
</template>


<script>
import ReportStatus from "../components/ReportStatus.vue";
import TransactionStatus from "../components/TransactionStatus.vue";
//import transactionService from "@/services/TransactionService.js";

export default {
  name: "MyAccount",
  data() {
    return {
      transactions: [],
    };
  },
  components: {
    ReportStatus,
    TransactionStatus,
  },
  // computed: {
  //   getAll() {
  //     transactionService
  //     .getAllTransactions()
  //       .then((response) => {
  //         this.transactions = response.data;
  //         this.getArtistTransactions();
  //         this.getDealerTransactions();
  //       })
  //       .catch((err) => console.error(err));

  //   },
  //   get30days() {
  //     transactionService
  //       .getTransactions30Days()
  //       .then((response) => {
  //         this.transactions = response.data;
  //         this.getArtistTransactions();
  //         this.getDealerTransactions();
  //       })
  //       .catch((err) => console.error(err));
  //   },
  //   get7days() {
  //     transactionService
  //       .getTransactions7Days()
  //       .then((response) => {
  //         this.transactions = response.data;
  //         this.getArtistTransactions();
  //         this.getDealerTransactions();
  //       })
  //       .catch((err) => console.error(err));
  //   }
  //}
};
</script>


<style scoped>

</style>
