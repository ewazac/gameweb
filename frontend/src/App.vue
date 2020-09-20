<template>
  <div class="wrapper">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="/">
        <b-icon icon="house-door-fill" class="border rounded"></b-icon>
      </b-navbar-brand>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <router-link to="/Games" class="nav-link">Gry</router-link>
        </b-navbar-nav>
        <b-navbar-nav>
          <router-link to="/Review" v-if="currentLoggedIn === true" class="nav-link">Dodaj recenzje</router-link>
        </b-navbar-nav>
        <b-navbar-nav>
          <router-link to="/NewGame" v-if="currentLoggedIn === true" class="nav-link">Dodaj grę</router-link>
        </b-navbar-nav>
        <!--
        <b-navbar-nav class="ml-auto">
          <b-navbar-brand class="navbar-brand">
              <img class="w-25" src="./assets/logo.png" alt="GameWeb" />
          </b-navbar-brand>
        </b-navbar-nav>-->
        <b-navbar-nav class="ml-auto">
          <b-navbar-nav right>
            <router-link to="/login" v-if="currentLoggedIn === false" class="nav-link">Zaloguj się</router-link>
          </b-navbar-nav>
          <b-navbar-nav right>
            <router-link to="/account" v-if="currentLoggedIn === true" class="nav-link">Ustawienia konta</router-link>
          </b-navbar-nav>
          <b-navbar-nav right>
            <router-link to="/" @click.native.prevent="logOut" v-if="currentLoggedIn === true" class="nav-link">Wyloguj się</router-link>
          </b-navbar-nav>
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
</style>