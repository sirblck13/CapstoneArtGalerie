<template>
  <div id="feeSettings" v-if="$store.state.user.authorities[0].name == 'ROLE_ADMIN'">
    <h2>Set Fees and Commission</h2>
    <form>
      <div>
        <label>Set Default Fee Percent (%): </label>
      </div>
      <input
        type="number"
        step="0.01"
        min="0.01"
        max="99.99"
        v-model.trim="currentDefaultFees.fee"
      />

      <div>
        <label>Set Default Commission Percent (%): </label>
      </div>
      <input
        type="number"
        step="0.01"
        min="0.01"
        max="99.99"
        v-model.trim="currentDefaultFees.commission"
      />

      <div>
        <button id="setChanges" @click.prevent="setFee()">
          Set Default Fees
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import transactionService from "@/services/TransactionService.js";
export default {
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
  margin-left: 0em;
  margin-bottom: 1em;
  text-align: center;
}

#feeSettings {
  background-color: #ab3f294b;
  border-radius: 20px;
  color: #fff;
  font-family: "Quicksand", sans-serif;
  width: fit-content;
  min-width: 300px;
  padding: 40px;
  margin: auto;
  display: flex;
  text-align: center;
  flex-direction: column;
  align-items: center;
}

#setChanges {
  margin-top: 10px;
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #f4f4f4eb;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
  width: 60%;
}
</style>