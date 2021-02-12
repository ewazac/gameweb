<template>
<div>
    <form name="form" @submit.prevent="handleSearch">
                <h6 class="tytul">Wyszukaj tytuł:</h6>
                <div class="sss">
                    <b-form-input
                        name="search"
                        class="input"
                        type="search"
                        v-model="search"
                        required
                    />
                    <b-button class="account__button" type="submit">
                    Wyszukaj
                    </b-button>
                </div>
    </form>
    <div class="hello" v-if="games">
        <div v-for="items in games" :key="items">
            <b-card-group class="cardGroup" v-for="i in Math.ceil(items.length/4)" :key="i" deck>
                <b-card
                class="mb-2"
                v-for="item in items.slice((i-1)*4, (i-1)*4 +  4)"
                :key="item.appId"
                :title="item.title"
                :img-src="item.icon"
                img-top
                >
                <b-card-text v-if="item.summary">{{ item.summary.slice(0,150) }}...</b-card-text>
                <b-button class="games__button" @click="handleDetails(item.appId)"> Zobacz więcej </b-button>
                </b-card>
            </b-card-group>
        </div>
    </div>
</div>
</template>


<script>
import axios from "axios";

export default {
  name: 'Search',
  beforeCreate: function () {
    document.body.className = "app__body";
  },
  data() {
    return {
        search: '',
        games: '',
    }
  },
  methods: {
    handleDetails(item) {
      console.log(item)
      this.$router.push({path:'/game', params:{game2:item}, query:{game2: item}});
    },
    handleSearch() {
        console.log(this.search)
        axios.get('https://gameweb12.herokuapp.com/api/apps/?q='+this.search)
        .then((result) => {
            console.log(result.data)
            this.games=result.data
        }).catch((err) => {
            console.log(err)
        });
    }
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
  height: 35%;
}

.card-body {
  display: flex;
  flex-direction: column;
  padding: 10px;
}

.card-body > .card-title {
  text-align: center;
  margin-bottom: 20px;;
}

.card-body > .card-text {
  text-align: justify;
  padding: 0 10px;
}

.card {
  min-height: 30rem;
  border: solid darkgray;
  border-width: 0 0 2px 0;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
}
.games__button {
  background-color: #fa0b0b !important;
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
  color: #fa0b0b;

  &:active {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }

  &:focus {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }
}

input {
  width: 100%;
  border-width: 0px 0px 2px 0px;
  border-radius: 4px;
  border: 1px solid #111;
  color: #fa0b0b;

  &:active {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }

  &:focus {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }
}

.btn-secondary {
  background-color: #fa0b0b;
}

.btn-secondary:hover {
  opacity: 0.9;
}
label {
  color: white;
}
.sss {
    width: 100%;
    display: inline-flex;
}
.tytul {
    color: white;
}
</style>

