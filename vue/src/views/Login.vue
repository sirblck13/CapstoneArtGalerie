<template>
  <div id="login-form" class="text-center">
    <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
    <form class="form-signin" @submit.prevent="login">
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >
        Thank you for registering, please sign in.
      </div>
      <div>
        <label for="username" class="sr-only">Username: </label>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder=" Username"
          v-model="user.username"
          required
          autofocus
        />
      </div>
      <div>
        <label for="password" class="sr-only">Password: </label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder=" Password"
          v-model="user.password"
          required
        />
      </div>
      <router-link :to="{ name: 'register' }"> Need an account? </router-link>
      <button type="submit">Sign in</button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push({name: 'home'});
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
form.form-signin {
  background-color: #ab3f294b;
  border-radius: 20px;
  top: 80px;
  width: fit-content;
  min-width: 300px;
  padding: 30px;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #f4f4f4eb; 
  font-family: 'Quicksand', sans-serif;
  margin-bottom: 20px;
}

a {
  color: #f4f4f4eb; 
}

form.form-signin > div {
  padding: 10px;
}

button {
  margin-top: 7px;
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
}
</style>
