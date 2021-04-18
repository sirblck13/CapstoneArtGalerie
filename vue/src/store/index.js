import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'


Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    customerId: 1,
    artPieceData: [],
    transactionData: [],
    transactionData7: [],
    activeArtPiece: 0,
    fee: [],
    commission: []

  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_ART_DATA(state, data){
      this.state.artPieceData = data;
    },
    SET_TRANSACTION_DATA(state, data){
      this.state.transactionData = data;
    },
    SET_CUSTOMER_ID(state, customerId){
      this.state.customerId = customerId;
    },
    SET_FEE(state, fee){
      this.state.fee = fee;
    },
    SET_COMMISSION(state, commission){
      this.state.commission = commission;
    },
    SET_TRANSACTION_DATA_7(state, data){
      this.state.transactionData7 = data;
    },
  }

})
