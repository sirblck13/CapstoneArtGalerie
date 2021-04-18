import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import AddArt from '../views/AddArt.vue'
import ArtDetails from '../views/ArtDetails.vue'
import Transaction from '../views/Transaction.vue'
import EditArt from '../views/EditArt.vue'
import Settings from '../views/Settings.vue'
import FeeOverride from '../views/FeeOverride.vue'
import MyAccount from '../views/MyAccount.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/addListing",
      name: "AddArt",
      component: AddArt,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/artDetails/:artId",
      name: "ArtDetails",
      component: ArtDetails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/orderDetails/:artId",
      name: "Transaction",
      component: Transaction,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/editArt/:artId",
      name: "EditArt",
      component: EditArt,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/settings",
      name: "Settings",
      component: Settings,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/override/:artId",
      name: "FeeOverride",
      component: FeeOverride,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/myAccount",
      name: "MyAccount",
      component: MyAccount,
      meta: {
        requiresAuth: true
      }
    }

  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
