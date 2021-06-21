<template>
    <dir class="Listofreviews">
        <dir class="reviews" style="background-color: #111111; color: #7bd5b4;">
            <h1> Lista recenzji </h1>
        </dir>
        <dir class="reviews" v-for="Review in ListOfReviews" :key="Review.id">
            <dir class="review" v-if="Review.title">
                <p> Tytuł: <span style="text-transform: uppercase;"> {{ Review.title }} </span> </p>
                <p v-if="Review.gameName">Gra: <router-link class="link" :to="'/game?game2='+Review.gameId"> {{ Review.gameName }} </router-link></p>
                <p v-else><router-link class="link" :to="'/game?game2='+Review.gameId"> Przejdz do gry</router-link></p>
                <span> Dodano: {{ Review.createdDate }} <p>Oceniono na: <span style="color:#38866a">{{ Review.stars }} </span></p> </span>
                <p> Opis: {{ Review.description }} </p>
            </dir>
        </dir>
    </dir>

</template>

<script>
import axios from 'axios'

export default {
    name: "ReviewList",
    beforeCreate: function () {
      document.body.className = "app__body";
    },
    data() {
      return {
          ListOfReviews: '',


      }
    },
    mounted() {
      axios.get("https://gameweb.projektstudencki.pl/api/users/reviews", {
          withCredentials: true
      })
      .then(response => {
          this.ListOfReviews = response.data;
          console.log(response.data)
      })
      .catch(err => {
          console.log(err);
      })
    }

}
</script>

<style lang="scss" scoped>
.Listofreviews {
    padding-left: 0;
}
.reviews {
    background-color: #222;
    color: white;
    width: 75%;
    margin: auto;
    padding: 0;
}
.review {
    padding: 1rem;
}
.link {
    color: white;
}
.link:hover, .link:active{
    color: #419c7b;
}
</style>