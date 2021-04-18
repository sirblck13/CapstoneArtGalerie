<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h2>Register New User Account</h2>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder=" Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder=" Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder=" Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <label for="email">Email</label>
      <input
        type="email"
        id="email"
        placeholder=" Email Address"
        v-model="user.email"
        required
      />
      <div id="dropdownMenu">
        <label for="role">Choose a role: </label>
        <select name="role" id="role" v-model="user.role">
          <option value="admin">Manager</option>
          <option value="dealer">Dealer</option>
          <option value="artist">Artist</option>
          <option value="seller">Seller</option>
        </select>
      </div>

      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "registerOthers",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
h2 {
  color: #ffffff;
  font-family: "Quicksand", sans-serif;
  font-weight: 700;
  line-height: 25px;
  margin-bottom: 1em;
  text-align: center;
  font-style: italic;
}

form.form-register {
  background-color: #ab3f294b;
  border-radius: 20px;
  top: 80px;
  width: fit-content;
  min-width: 300px;
  padding: 40px;
  margin: auto;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #fff;
  font-family: "Quicksand", sans-serif;
}

form.form-register > input {
  margin-bottom: 10px;
}

button {
  margin-top: 25px;
  padding: 5px 15px;
  background-color: #ab3f29;
  color: #fff;
  border-radius: 5px;
  border: none;
  box-shadow: 1.5px 1.5px 1.5px 1.5px #310f08b7;
  cursor: pointer;
}
</style>
