<template>
  <div class="wrapper">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="/">
        <b-icon icon="house-door-fill" class="border rounded"></b-icon>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <router-link to="/Games" class="nav-link">Games</router-link>
      </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
          </b-nav-form>
          <b-nav-item-dropdown right>
            <template v-slot:button-content>
              <em>User</em>
            </template>
            <b-dropdown-item href="/account">Profile</b-dropdown-item>
            <b-dropdown-item href="/login">Sign In</b-dropdown-item>
            <b-dropdown-item href @click.prevent="logOut">logout</b-dropdown-item>
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
  name: 'app',

  computed: {
    currentUser() {
      console.log(this.$store.state.auth.user)
      return this.$store.state.auth.user;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
}

</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
}



</style>