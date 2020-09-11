<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <h1> Welcome to Your Vue.js App </h1>
    <Home/>
  </div>
</template>

<script>
import Home from '@/components/Home.vue'

export default {
  name: 'Home2',
  components: {
    Home
  }
}
</script>

<style lang="scss" scoped>
.home {
  display: block;
  
}

</style>