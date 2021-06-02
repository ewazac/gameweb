<template>
    <dir class="Listofreviews">
        <dir class="reviews" style="background-color: #111111; color: #7bd5b4;">
            <h1> Lista recenzji </h1>
        </dir>
        <dir class="reviews" v-for="Review in ListOfReviews" :key="Review.id">
            <dir class="review" v-if="Review.title">
                <p> Tytuł: {{ Review.title }} </p>
                <span> Dodano: {{ Review.createdDate }} <p>Oceniono na: {{ Review.stars }}</p> </span>
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
    background-color: #494949;
    color: #c6c6c6;
    width: 75%;
    margin: auto;
    padding: 0;
}
.review {
    padding: 1rem;
}
</style>