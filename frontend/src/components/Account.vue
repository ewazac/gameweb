<template>
  <div id="v-for-object">
    <div class="container" v-for="(user) in currentUser" v-bind:key="user.id">
      <span> id: {{ user }} </span>
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