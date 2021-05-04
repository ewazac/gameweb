<template>
  <div class="hello">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <div class="w-100 d-flex justify-content-end align-center mb-3">
              <div class="text-white mr-2">Ilość na stronie:</div>
              <b-form-select style="max-width: 200px" v-model="params.per_page" :options="options"></b-form-select>
          </div>
            <div class="w-100 d-flex justify-content-end align-center">
              <div class="text-white mr-2">Ranking gier:</div>
              <b-form-select style="max-width: 200px" v-model="ranking" :options="gameOptions"></b-form-select>
            </div>
        </div>
          <div class="col-md-3 my-2"  :key="item.appId" v-for="item in items">
           <b-card
                  class="mb-2 h-100"
                  :title="item.title"
                  :img-src="item.icon"
                  img-top
           >
<!--    <b-card-group class="cardGroup" v-for="i in Math.ceil(games.length/4)" :key="i" deck>-->
<!--      <b-card-->
<!--              class="mb-2"-->
<!--              v-for="item in games.slice((i-1)*4, (i-1)*4 +  4)"-->
<!--              :key="item.appId"-->
<!--              :title="item.title"-->
<!--              :img-src="item.icon"-->
<!--              img-top-->
<!--      >-->
<!--        <b-card-text v-if="item.summary">{{ item.summary.slice(0,150) }}...</b-card-text>-->
              <div style="position: absolute; top: 10px; right: 10px" v-if="currentLoggedIn">
                  <b-icon @click="item.toggleFavourite()" scale="2" style="cursor: pointer" icon="star-fill" variant="info"></b-icon>
              </div>
            <b-button class="games__button" @click="handleDetails(item.appId)"> Zobacz więcej </b-button>
           </b-card>
          </div>
      </div>
       <div class="w-100 d-flex justify-content-center">
           <b-pagination
                   @change="scrollToTop()"
                   v-model="params.page"
                   :total-rows="params.total_rows"
                   :per-page="params.per_page" first-text="First" prev-text="Prev" next-text="Next" last-text="Last">
           </b-pagination>
       </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import {paginate} from "../helpers";
  import Game from '../models/game';

  export default {
    name: 'Home',
    beforeCreate: function () {
      document.body.className = "app__body";
    },
    data() {
      return {
        games: '',
        options: [
          { value: 16, text: 16 },
          { value: 32, text: 32 },
        ],
        gameOptions: [
          { value: 'topselling_free', text: 'Najlepsze darmowe'},
          { value: 'topselling_paid', text: 'Najlepsze płatne'},
          { value: 'topgrossing', text: 'Najlepiej zarabiające'},
          { value: 'topselling_new_free', text: 'Najlepsze nowe darmowe'},
          { value: 'topselling_new_paid', text: 'Najlepsze nowe płatne'}
        ],
        ranking: 'topselling_free',
        params:{
          page: 1,
          per_page: 16,
          total_rows: 1
        }
      }
    },
    computed:{
      currentLoggedIn () {
        return this.$store.state.auth.status.loggedIn;
      },
      items(){
        return paginate(this.games, this.params.per_page, this.params.page);
      }
    },
    methods: {
      handleDetails(item) {
        this.$router.push({path:'/game', params:{game2:item}, query:{game2: item}});
      },
      handleNewOption() {
        console.log(this.ranking)
        axios.get('https://gameweb12.herokuapp.com/api/apps/?category=GAME&collection='+this.ranking)
          .then((result) => {
                  this.games = result.data.results.map(item => {
                    return new Game(item);
                  });
                  this.params.total_rows = this.games.length;
                }).catch((err) => {
          console.log(err)
        });
      }
    },
    watch: {
      ranking() {
        this.handleNewOption();
      }
    },
    mounted() {
      axios.get('https://gameweb12.herokuapp.com/api/apps/?collection=topselling_free&category=GAME')
              .then((result) => {
                this.games = result.data.results.map(item => {
                    return new Game(item);
                });
                console.log(this.games)
                this.params.total_rows =this.games.length;
              }).catch((err) => {
        console.log(err)
      });
    },
  }

</script>

<style lang="scss" scoped>
  .cardGroup {
    justify-content: center;
    text-align: left;
    margin: 0 auto;
  }
  @media screen and (max-width: 1199px) {
    .card-deck .card {
      flex: 1 0 33%;
    }
  }

  @media screen and (max-width: 991px) {
    .card-deck .card {
      flex: 1 0 30%;
    }
  }

  @media screen and (max-width: 991px) {
    .card-deck .card {
      flex: 1 0 30%;
    }
  }
  @media screen and (min-width: 1024px) {
    .card {
      max-width: 247px;
    }
  }

  .container {
    margin-top: 1%;
    text-align: center;
  }

  .search {
    display: inline-flex;
  }

  .search-wrapper {
    width: 20rem;
    margin: 0 auto;
    padding-right: 1%;
  }

  .search-advance {
    width: 20rem;
    margin: 0 auto;
  }

  .sort {
    padding-left: 1%;
    width: 20rem;
    margin: 0 auto;
  }
  .card-title {
    height: 35%;
  }

  .card-img-top {
    width: 100%;
    height: 45%;
  }

  .card-body {
    display: flex;
    flex-direction: column;
    padding: 10px;
  }

  .card-body > .card-title {
    text-align: center;
    margin-bottom: 5px;;
  }

  .card-body > .card-text {
    text-align: justify;
    padding: 0px;
  }

  .card {
    padding: 10px;
    min-height: 15rem;
    max-height: 25rem;
    border-width: 0 0 2px 0;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
  }
  .games__button {
    background-color: mediumaquamarine !important;
    margin-top: auto;
    border: none;

    &:focus {
      outline: none;
      border: none;
      box-shadow: none !important;
    }
  }

  select {
    border: 1px solid #111 !important;
    cursor: pointer;
    color: mediumaquamarine;

    &:active {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
    }

    &:focus {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
    }
  }

  input {
    width: 100%;
    border-width: 0px 0px 2px 0px;
    border-radius: 4px;
    border: 1px solid #111;
    color: mediumaquamarine;

    &:active {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
    }

    &:focus {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
    }
  }

  .btn-secondary {
    background-color: mediumaquamarine;
  }

  .btn-secondary:hover {
    opacity: 0.9;
  }
  label {
    color: white;
  }
  /*.gamesRanking {*/
  /*  margin-left: auto;*/
  /*}*/
  select {
    font-weight: 450;
    color: #0f865a;
  }
</style>

