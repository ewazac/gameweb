<template>
  <div class="wrapper">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav left>
              <router-link to="/Games" class="nav-link">
              <b-icon icon="house-door-fill"></b-icon>Gry</router-link>
            </b-navbar-nav>
            <b-navbar-nav left> 
              <router-link to="/Review" v-if="currentLoggedIn === true" class="nav-link">
              <b-icon icon="filter-square" aria-hidden="review"></b-icon>Dodaj recenzję</router-link>
            </b-navbar-nav>
            <b-navbar-nav left>
              <router-link to="/NewGame" v-if="currentLoggedIn === true" class="nav-link">
              <b-icon icon="controller" aria-hidden="game"></b-icon>Dodaj grę</router-link>
            </b-navbar-nav>
            <b-navbar-nav class="m-auto" center>
                <img class="GameWeb" src="./assets/logo.png" alt="GameWeb" />
            </b-navbar-nav>
              <b-navbar-nav right>
                <router-link to="/login" v-if="currentLoggedIn === false" class="nav-link">
                <b-icon icon="person" aria-hidden="login"></b-icon>Zaloguj się</router-link>
              </b-navbar-nav>
              <b-navbar-nav right>
                <router-link to="/account" v-if="currentLoggedIn === true" class="nav-link">
                <b-icon icon="gear-fill" aria-hidden="settings"></b-icon>Ustawienia konta</router-link>
              </b-navbar-nav>
              <b-navbar-nav right>
                <router-link to="/" @click.native.prevent="logOut" v-if="currentLoggedIn === true" class="nav-link">
                <b-icon icon="power" aria-hidden="true"></b-icon>Wyloguj się</router-link>
              </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <div class="rest">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: "app",

  computed: {
    currentLoggedIn () {
      return this.$store.state.auth.status.loggedIn;
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="scss" scoped>
body {
  margin: 0;
  padding: 0;
}
.navbar {
    height: 108px;
    background-color: #fa0b0b !important;
}

.GameWeb {
  height: 100%;
  width: 150px;
}

.navbar-dark .navbar-nav .nav-link {
  color: #fff;
  font-weight: 550;
  font-size: 1.3rem;

  &:hover{
      color: #fff;
      opacity: 0.9;
  }
}
</style>