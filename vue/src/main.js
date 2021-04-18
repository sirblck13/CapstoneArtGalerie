import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import "@/assets/global.css"
import firebase from 'firebase'

Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  store,
  render: h => h(App),
  created() {
    var firebaseConfig = {
      apiKey: "AIzaSyB2OuALYE_tTi9yzDIQnPtPIexwpQihMTM",
      authDomain: "artsales-cd212.firebaseapp.com",
      databaseURL: "https://artsales-cd212-default-rtdb.firebaseio.com",
      projectId: "artsales-cd212",
      storageBucket: "artsales-cd212.appspot.com",
      messagingSenderId: "1030614952064",
      appId: "1:1030614952064:web:16d788bae0087fc2e31e65"
    };
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();
  }
}).$mount('#app')
