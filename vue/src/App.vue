<template>
  <div id="app">
    <img id="logo" src="./assets/Infinity ART (2) main.png" alt="logo" />
    <div>
      <div id="nav" class="sidenav">
        <div id="menuTab">Menu</div>
        <router-link class="tab" id="home" v-bind:to="{ name: 'home' }"
          >Home</router-link
        >
        <router-link
          class="tab"
          id="login"
          v-bind:to="{ name: 'login' }"
          v-if="$store.state.token == ''"
        >
          Login
        </router-link>

        <div id="authListings" v-if="$store.state.token != ''">
          <router-link
            class="tab"
            id="addart"
            v-bind:to="{ name: 'AddArt' }"
            v-if="$store.state.user.authorities[0].name != 'ROLE_USER'"
          >
            Add Listing</router-link
          >
        </div>

        <div id="authMyAccount" v-if="$store.state.token != ''">
          <router-link
            class="tab"
            id="myAccount"
            v-bind:to="{ name: 'MyAccount' }"
            v-if="$store.state.user.authorities[0].name != 'ROLE_USER'"
          >
            My Account</router-link
          >
        </div>

        <div id="authSettings" v-if="$store.state.token != ''">
          <router-link
            class="tab"
            id="settings"
            v-bind:to="{ name: 'Settings' }"
            v-if="
              $store.state.user.authorities[0].name == ('ROLE_ADMIN' ||
              'ROLE_DEALER')
            "
            >Settings</router-link
          >
        </div>
        <router-link
          class="tab"
          id="logout"
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link
        >
      </div>
      <router-view />
    </div>
  </div>
</template>


<style scoped>
#logo {
  display: block;
  margin: auto;
  width: 180px;
  padding: 20px;
}

.sidenav {
  display: flex;
  flex-direction: column;
  position: fixed;
  color: #fff;
  font-family: "Quicksand", sans-serif;
  text-align: right;
  top: 5px;
  width: 410px;
  font-size: 1.3rem;
  left: -380px;
}

#menuTab {
  background-color: #ab3f29;
  padding: 20px;
  margin: 5px;
  width: 530px;
  border-radius: 5px;
  font-weight: bolder;
  text-align: right;
  text-decoration: underline;
}

/* Style the links inside the sidenav */
.tab {
  color: #fff;
  font-family: "Quicksand", sans-serif;
  transition: 0.2s;
  padding: 20px;
  margin: 5px;
  text-align: right;
  border-radius: 5px;
  background-color: #ab3f29d0;
  display: flex;
  justify-content: flex-end;
  border-right: 15px solid #ab3f29;
  text-decoration: none;
}

.tab:hover {
  margin-right: -125px;
}
.router-link-active {
  margin-right: -125px;
  background-color: #ab3f29;
}

@media (max-width: 700px) {
  .sidenav {
    position: absolute;
  }
  .tab {
    padding: 10px;
    font-size: 1rem;
  }
  .tab:hover {
    margin-right: -95px;
  }
  #menuTab {
    padding: 10px;
    padding-right: 25px;
    margin: 5px;
    width: 500px;
    font-size: 1rem;
  }
  .router-link-active {
    margin-right: 5px;
  }
}
</style>