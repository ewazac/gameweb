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

        <b-navbar-nav class="ml-auto">
          <b-navbar-brand class="navbar-brand">
              <img class="w-25" src="./assets/logo.png" alt="GameWeb" />
          </b-navbar-brand>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">

          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">Wyszukaj</b-button>
          </b-nav-form>
          <b-nav-item-dropdown right>
            <template v-slot:button-content>
              <em>Użytkownik</em>
            </template>
            <b-dropdown-item
              href="/Register"
              v-if="currentLoggedIn === false"
            >Zarejestruj się</b-dropdown-item>
            <b-dropdown-item
              href="/account"
              v-if="currentLoggedIn === true"
            >Ustawienia konta</b-dropdown-item>
            <b-dropdown-item
              href="/login"
              v-if="currentLoggedIn === false"
            >Zaloguj się</b-dropdown-item>
            <b-dropdown-item
              href
              v-if=" currentLoggedIn === true"
              @click.prevent="logOut"
            >Wyloguj się</b-dropdown-item>
          </b-nav-item-dropdown>
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
  background: repeat fixed center;
}
</style>