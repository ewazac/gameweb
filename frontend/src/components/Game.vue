<template>
    <div class="container">
        <b-container class="gameDetails">
            <b-row class="game" align-h="around" align-v="center">
                <h1> {{ game.name }} </h1>
                <b-img class="gameImage" :src="getImage(game.gameImage)" :alt='game.name'> </b-img>
            </b-row>
            <b-row>
                <b-col sm='1'></b-col>
                <b-col sm='9'> <p class="description"> {{ game.description }} </p> </b-col>
                <b-col sm='2'></b-col>
            </b-row>
        </b-container>
        <h2> Recenzje </h2>
        <h5> Średnia ocena tej gry to ...</h5>
        <b-container v-if="success">
            <b-row cols='1' v-for="review in reviews" :key="review.id">
                <b-col> <h3>{{ review.title.toUpperCase() }}</h3> </b-col>
                <b-col>
                    <b-form-rating id="rating" :value="review.stars" inline disabled></b-form-rating>
                </b-col>
                <b-col> <p class="description">{{ review.description }}</p> </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import {AXIOS} from '../http-commons'
import Game from "../models/game"
import Review from '../models/review'

export default {
    name: "game-detail",
    data() {
        return {
            success: false,
            game: new Game('','',''),
            reviews: new Review('','','',null),
        }
    },
    methods: {
        getImage(image) {
            if (image === null) {
                return "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png";
            }
            else {
                let base64 = image.data;
                let buffer = Uint8Array.from(atob(base64), (c) => c.charCodeAt(0));
                let blob = new Blob([buffer], { type: "image/gif" });
                return URL.createObjectURL(blob);
            }
        },
    },
    created(){
        AXIOS.get('games/'+this.$router.history.current.query.game)
        .then((response) => {
            this.game = response.data;
            console.log(response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    },
    mounted() {
        AXIOS.get('reviews/game/'+this.$router.history.current.query.game)
        .then((response) => {
            this.success = true;
            this.reviews = response.data;
            console.log(response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    }
}
</script>

<style lang="scss" scoped>
.container {
    background-color: #d6d6d6;
}
.gameDetails {
    padding: 1rem;
    min-height: 30rem;
}
.disabled {
    background-color: white;
    color: indianred;
    border-color: indianred;
}
.gameImage {
    min-width: 150px;
    min-height: 150px;
    max-width: 250px;
    max-height: 250px;
}
h1 {
    color: #d62b4c;
}
.description {
    padding: 1rem;
    font-size: 1.2rem;
    font-weight: 350;
    line-height: 1.6;
    text-align: left;
}
</style>