<template>
  <div class="container">
    <b-form @submit.prevent="handleReview" class="review__form">
      <h1 class="review__header">Dodaj recenzję</h1>
      <b-row cols="12" class="mt-4">
         <b-col cols="2">
             <label>Podaj tytuł gry: </label>
         </b-col>
         <b-col cols="12">
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
         <b-col cols="12" class="mt-3">
             <label>Podaj tytuł recenzji: </label>
         </b-col>
         <b-col cols="12">
             <b-form-input
                 v-model="review.title"
                 v-validate="'required|max:50'"
                 type="text"
                 placeholder="Tytuł recenzji"
                 name="title"
             ></b-form-input>
         </b-col>
         <b-col cols="12" class="mt-3">
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
      <b-row cols="2" class="mt-3">
         <b-col sm="5">
             <label>Zaznacz ocenę końcową: </label>
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
             <b-button class="review__button mt-3" @click="handleReview">Dodaj recenzję</b-button>
         </b-col>
         <b-col sm="3"></b-col>
      </b-row>
      <b-alert v-if="dispatched === true" show variant="success" class="text-center">Recenzja została dodana pomyslnie</b-alert>
      <b-alert v-if="exist === true" show variant="danger" class="text-center">Ta recenzja została już dodana</b-alert>
    </b-form>>
  </div>
</template>

<script>
import { AXIOS } from "../http-commons";

export default {
  name: "NewReview",
    beforeCreate: function () {
        document.body.className = "app__body";
    },
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
.review__form {
   width: 60%;
   background-color: rgba(247, 247, 247, 0.9);
   padding: 20px 50px 30px;
   margin: 0 auto 25px;
   margin-top: 50px;
   -moz-border-radius: 10px;
   -webkit-border-radius: 10px;
   border-radius: 10px;
   transition: 0.2s;

    &:hover {
        -moz-box-shadow: 2px 2px 13px rgba(255, 255, 255, 0.5);
        -webkit-box-shadow: 2px 2px 13px rgba(255, 255, 255, 0.5);
        box-shadow: 2px 2px 13px rgba(255, 255, 255, 0.5);
    }
}

.review__header {
    color: #fa0b0b;
    text-transform: uppercase;
    font-size: 30px;
    font-weight: bold;
    text-align: center;
}

input,
textarea {
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

.review__button {
    background-color: #fa0b0b;
    width: 100%;
    border: none;
    border-radius: 0;
    padding: 10px 20px;
    text-transform: uppercase;
    font-weight: bold;
    letter-spacing: 1px;
    transition: 0.2s;

    &:active {
        background-color: #fa0b0b !important;
        outline: none !important;
        border: none !important;
        box-shadow: none;
    }

    &:focus {
        background-color: #fa0b0b !important;
        outline: none !important;
        border: none !important;
        box-shadow: none !important;
    }

    &:hover {
        background-color: #fa0b0b;
        opacity: 0.9;
    }
}

@media (max-width: 991px) {
    .review__form {
        width: 75%;
    }
}

@media (max-width: 767px) {
    .review__form {
        width: 100%;
    }
}

</style>