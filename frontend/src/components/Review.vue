<template>
  <div class="container">
    <b-form @submit.prevent="handleReview">
        <h2 style="margin-top:1rem;">Dodaj recenzje</h2>
        <b-container style="max-width:60%" fluid>
            <b-row cols="2">
                <b-col sm="3">
                    <label>Podaj tytuł gry: </label>
                </b-col>
                <b-col sm="9">
                    <b-form-input 
                    v-model="review.game"
                    list="game-list"
                    v-validate="'required|max:50'"
                    type="text"
                    placeholder="Nazwa gry"
                    name="title"
                    ></b-form-input>
                    <datalist id="game-list">
                        <option v-for="game in games" :key="game.id" > {{ game.name }}  </option>
                    </datalist>
                </b-col>
                <b-col sm="3">
                    <label>Podaj tytuł: </label>
                </b-col>
                <b-col sm="9">
                    <b-form-input 
                    v-model="review.title"
                    v-validate="'required|max:50'"
                    type="text"
                    placeholder="Tytuł recenzji"
                    name="title"
                    ></b-form-input>
                </b-col>
                <b-col sm="4">
                    <label>Opisz swoje wrażenia: </label>
                </b-col>
            </b-row>
            <b-row cols="1">
                <b-col>
                    <b-form-textarea 
                    v-model="review.description"
                    v-validate="'required|min:10'"
                    type="text"
                    placeholder="Opisz swoje wrażenia z grą"
                    name="title"
                    rows="5"
                    ></b-form-textarea>
                </b-col>
            </b-row>
            <b-row cols="2">
                <b-col sm="5">
                    <label>Zaznacz końcową ocene: </label>
                </b-col>
                <b-col sm="7">
                    <b-form-rating 
                    v-model="review.stars"
                    v-validate="'required'"
                    name="start"
                    icon-empty="heart"
                    icon-half="heart-half"
                    icon-full="heart-fill"
                    icon-clear="slash-circle"
                    show-clear
                    variant="danger"
                    ></b-form-rating>
                </b-col>
            </b-row>
            <b-row>
                <b-col sm="3"></b-col>
                <b-col sm="6">
                    <b-button class="w-100" variant="warning" @click="handleReview">Dodaj recenzje</b-button>
                </b-col>
                <b-col sm="3"></b-col>
            </b-row>
            <b-alert v-if="dispatched === true" show variant="success" class="text-center">Recenzja została dodana pomyslnie</b-alert>
            <b-alert v-if="exist === true" show variant="danger" class="text-center">Ta recenzja została już dodana</b-alert>
        </b-container>
    </b-form>
  </div>
</template>

<script>
import { AXIOS } from "../http-commons";

export default {
  name: "NewReview",
  data() {
    return {
      success: false,
      dispatched: false,
      exist: false,
      games: '',
      review: {
          game: '',
          title: '',
          description: '',
          stars: null,
      },
    };
  },
  methods: {
      handleReview() {
        this.$validator
          .validate()
          .then((isValid) => {
            if (isValid) {
                console.log(this.review.title, this.review.description, this.review.game, this.review.stars)
                AXIOS.post('reviews', {
                    title: this.review.title,
                    description: this.review.description,
                    game: this.review.game,
                    stars: this.review.stars
                })
                .then((response) => {
                    console.log(response)
                    this.dispatched = true;
                }).catch((error) => {
                    this.message =
                        (error.response && error.response.data) ||
                        error.message ||
                        error.toString();
                    if (error.response.status === 400) {
                        this.exist = true;
                    }
                });
            }
        })
    },
  },
  mounted() {
      AXIOS.get('games')
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
.container {
    text-align: center;
}

.container-fluid {
    border: solid gainsboro;
    border-width: 0 0 2px 1px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
}

.col-sm-6 {
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.col-sm-3, .col-sm-9, .col-sm-4, .col-sm-5, .col-sm-7 {
    text-align: left;
    margin-top: 1rem;
}

</style>