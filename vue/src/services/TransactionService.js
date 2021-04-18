import axios from 'axios';

export default {

    postTransaction(transactions) {
      return axios.post('/orders', transactions)
    },

    setDefaultFee(fee){
      return axios.put('/fees', fee)
    },

    getCurrentDefaultFees(){
      return axios.get('/fees')
    },
    
    getAllTransactions(){
      return axios.get('/transactions')
    },

    getTransactions30Days(){
      return axios.get('/transactions/30')
    },

    getTransactions7Days(){
      return axios.get('/transactions/7')
    },

    getEmail(username){
      return axios.get(`/transaction/${username}`)
    }
}