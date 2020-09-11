<template>
<div class="wraper">
      <div class="demo">
        <h1 class="H1">Gry</h1>
        <div class="G1" id="v-for-object">
          <div class="games" v-for="(item) in games" v-bind:key="item.id">
            {{ item.id }} - {{ item.name }}
          </div>
        </div>
      </div>
</div>
</template>

<script>
import {AXIOS} from '../http-commons';

export default {
  name: "games-list",
  data() {
    return {
      error: [],
      games: {},
    };
  },
  methods: {
    /*getImageUrl(imageId) {
      return `https://picsum.photos/150/100/?image=${imageId}`
    }*/
  },
  mounted() {
    AXIOS.get('games')
    .then(response => {
        this.games = response.data;
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }
};
</script>

<style lang="scss" scoped>
.H1 {
  text-align: center;
}

.G1 {
  display: inline-block;
  min-width: 23%;
  text-align: center;
}

.games {
  margin-left: 2%;
  min-width: 25%;
  margin-bottom: 10px;
  padding: 2px;
  border: solid darkgray;
  border-width: 0 0 2px 0;
  -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}


</style>
