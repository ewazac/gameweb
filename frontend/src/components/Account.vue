<template>
  <div id="v-for-object">
    <div class="container" >
      <p> id: {{ currentUser.id }} </p>
      <p> email: {{ currentUser.email }} </p>
      <p> password(tymczasowo): {{ currentUser.password }} </p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Account',
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('Login');
    }
  }
};
</script>