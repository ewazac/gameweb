<template>
    <div class="container">
        <b-row class="game" align-h="around" align-v="center">
            <div class="col-6 text-center">
                <b-img
                        class="gameImage"
                        :src="getImage(game.gameImage)"
                        :alt='game.name'>
                </b-img>
            </div>
            <div class="col-6 text-center">
                <h1>{{ game.name }} </h1>
            </div>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="8" offset-sm="2">
                <p class="game__description">{{ game.description }}</p>
            </b-col>
        </b-row>

        <hr class="mt-4" style="border-color: #fa0b0b" />

        <b-row class="mt-5">
            <b-col sm="8" offset-sm="2">
                <h2> Recenzje </h2>
                <p>Średnia ocena tej gry to {{ stars }}</p>
            </b-col>
        </b-row>
        <div class="mt-4" v-if="success">
            <b-row v-for="review in reviews" :key="review.id">
                <b-col sm="8" offset-sm="2">
                    <b-row>
                        <b-col cols="12">
                            <h5>{{ review.title.toUpperCase() }}</h5>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12">
                            <b-form-rating
                                    id="rating"
                                    :value="review.stars"
                                    inline
                                    disabled>
                            </b-form-rating>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12">
                            <p class="game__review">{{ review.description }}</p>
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </div>
    </div>
</template>>

<script>
    import {AXIOS} from '../http-commons'
    import Game from "../models/game"
    import Review from '../models/review'

    export default {
        name: "game-detail",
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data() {
            return {
                success: false,
                game: new Game('','',''),
                reviews: new Review('','','',null),
                stars: null,
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
            AXIOS.get('reviews/ranking')
                .then((response) => {
                    var name = this.$router.history.current.query.game
                    for (const object of response.data) {
                        if (object.game == name) {
                            this.stars = object.stars;
                            break;
                        }
                    }
                    console.log(response.data, this.stars);
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }
</script>

<style lang="scss" scoped>
    .container {
        background-color: #fff;
    }

    .game {
        margin-top: 40px;
        background-color: #fa0b0b;
        padding-bottom: 20px;
        padding-top: 20px;
    }

    .disabled {
        background-color: white;
        color: indianred;
        border-color: indianred;
    }

    .game__image {
        min-width: 200px;
        min-height: 200px;
        max-width: 250px;
        max-height: 250px;
        background-color: white;
        border: 1px solid black;
    }

    h1 {
        color: #fff;
    }
    .game__description {
        padding: 1rem;
        font-size: 1.2rem;
        font-weight: 350;
        line-height: 1.6;
        text-align: left;
    }

    .game__review {
        padding: 1rem 0;
        font-size: 1.2rem;
        font-weight: 300;
        line-height: 1.6;
        text-align: left;
    }

</style>