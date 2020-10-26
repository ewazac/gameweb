<template>
  <div class="wraper">
    <div class="demo">
      <div id="v-for-object">
        <div class="container">
          <div class="search">
            <div class="search-wrapper">
              <label class="label">Wyszukaj tytuł:</label>
              <b-form-input type="text" v-model="search" placeholder="Wyszukaj tytuł.."></b-form-input>
            </div>
            <div class="search-advance">
              <label class="label">Wybierz kategorie:</label>
              <b-form-select
                v-model="category"
                :options="categories"
                class="mb-3"
                value-field="item"
                text-field="name"
                disabled-field="notEnabled"
              ></b-form-select>
            </div>
            <div class="sort">
              <label class="label">Sortuj:</label>
              <b-form-select
                v-model="sort"
                :options="options"
                class="mb-3"
                value-field="item"
                text-field="name"
                disabled-field="notEnabled"
              ></b-form-select>
            </div>
          </div>

          <b-card-group class="cardGroup" v-for="i in Math.ceil(games.length/4)" :key="i" deck>
            <b-card
              class="mb-2"
              v-for="item in sortedGames.slice((i-1)*4, (i-1)*4 +  4)"
              :key="item.id"
              :title="item.name"
              :img-src="getImage(item.gameImage)"
              img-top
            >
              <b-card-text v-if="item.description">{{ item.description.slice(0,150) }}...</b-card-text>
              <b-button class="games__button" @click="handleDetails(item.name)"> Zobacz więcej </b-button>
            </b-card>
          </b-card-group>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { AXIOS } from "../http-commons";
import Game from "../models/game";

export default {
  name: "games-list",
  beforeCreate: function () {
    document.body.className = "app__body";
  },
  data() {
    return {
      readMoreActivated: false,
      search: "",
      error: [],
      sort: "alfabetycznie",
      options: ["alfabetycznie", "od Z do A", "Najwyżej oceniane"],
      category: "wszystkie",
      categories: ["wszystkie"],
      games: new Game("", "", ""),
      sorting: -1,
    };
  },
  computed: {
    filteredList() {
      return this.games.filter((game) => {
        if (!this.categories.includes(game.category)) {
          this.categories.push(game.category);
        }
        return game.name.toLowerCase().includes(this.search.toLowerCase());
      });
    },
    filteredCategory() {
      return this.filteredList.filter((game) => {
        if (this.category === "wszystkie") {
          return game.category;
        }
        return game.category
          .toLowerCase()
          .includes(this.category.toLowerCase());
      });
    },
    sortedGames() {
      if (this.sort === "alfabetycznie") {
        return this.filteredCategory
          .slice(0)
          .sort((a, b) => (a.name < b.name ? this.sorting : -this.sorting));
      } else if (this.sort === "od Z do A") {
        return this.filteredCategory
          .slice(0)
          .sort((a, b) => (a.name < b.name ? -this.sorting : this.sorting));
      } else {
        return this.filteredCategory;
      }
    },
  },
  methods: {
    handleDetails(item) {
      console.log(item)
      this.$router.push({path:'/game', params:{game:item}, query:{game: item}});
    },
    getImage(image) {
      if (image === null) {
        return "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png";
      }
      let base64 = image.data;
      let buffer = Uint8Array.from(atob(base64), (c) => c.charCodeAt(0));
      let blob = new Blob([buffer], { type: "image/gif" });
      return URL.createObjectURL(blob);
    },
    activateReadMore() {
      this.readMoreActivated = true;
    },
  },
  mounted() {
    AXIOS.get("games")
      .then((response) => {
        this.games = response.data;
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
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
  height: 15%;
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
  margin-bottom: 20px;
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
/*.card {
  min-height: 30rem;
  border: solid darkgray;
  border-width: 0 0 2px 0;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
}*/
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
.btn-secondary {
  background-color: #fa0b0b;
}
.btn-secondary:hover {
  opacity: 0.9;
}
label {
  color: white;
}
</style>
