<template>
    <div class="container">
        <b-row class="game" align-h="around" align-v="center">
            <div class="col-4 text-center">
                <b-img
                        class="gameImage"
                        :src="game.icon"
                        :alt="game.title"
                >
                </b-img>
            </div>
            <div class="col-6 text-center">
                <h1>{{ game.title }}</h1>
            </div>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="8" offset-sm="2">
                <p class="game__description">{{ game.description }}</p>
            </b-col>
        </b-row>

        <hr class="mt-4" style="border-color: mediumaquamarine" />

        <b-row class="mt-5">
            <b-col sm="8" offset-sm="2">
                <h2>Recenzje</h2>
                <span>Średnia ocena tej gry    </span>
                <b-form-rating id="rating" :value="game.score" inline disabled>
                </b-form-rating>
                <button class="button" v-on:click="Show">Dodaj recenzje!</button>
            </b-col>
        </b-row>
        <div>
            <form class="addReview" name="addReview" v-if="show" @submit.prevent="handleReview">
                <p>Tytuł:</p>
                <b-form-input
                    v-model="review.title"
                    type="text"
                    class="form-control"
                    placeholder="Podaj tytuł"
                    name="title"
                    required
                />
                <p>Oceń:</p>
                <b-form-rating id="rating" v-model="review.stars" required inline></b-form-rating>
                <p>Opis:</p>
                <b-form-textarea
                    v-model="review.description"
                    type="text"
                    class="form-control"
                    name="description"
                    required
                />
                <button class="button" type="submit">Dodaj</button>
            </form>
        </div>
        <div>
            <div class="mt-4">
                <h3>Recenzje Gameweb:</h3>
                <b-row v-for="r in gamewebReviews" :key="r.id">
                    <b-col sm="8" offset-sm="2">
                        <b-row>
                            <b-col cols="12">
                                <h5>{{ r.title }}</h5>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12">
                                <b-form-rating id="rating" :value="r.stars" inline disabled>
                                </b-form-rating>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12">
                                <p class="game__review">{{ r.description }}</p>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </div>
        </div>

        <hr class="mt-4" style="border-color: mediumaquamarine" />

        <div class="mt-4">
            <h3>Recenzje z Google Play:</h3>
            <b-row v-for="r in filterdReviews" :key="r.id">
                <b-col sm="8" offset-sm="2">
                    <b-row>
                        <b-col cols="12">
                            <h5>{{ r.userName }}</h5>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12">
                            <b-form-rating id="rating" :value="r.score" inline disabled>
                            </b-form-rating>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12">
                            <p class="game__review">{{ r.text }}</p>
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </div>
        <div class="py-5 d-flex justify-content-center">
            <b-pagination
                    v-model="paramsReviews.page"
                    :total-rows="paramsReviews.total"
                    :per-page="paramsReviews.per_page" first-text="First" prev-text="Prev" next-text="Next" last-text="Last"></b-pagination>
        </div>
    </div>
</template>>

<script>
    import axios from "axios";
    import {paginate} from "../helpers";

    export default {
        name: "game-detail",
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data() {
            return {
                review: {
                    description: '',
                    gameId: '',
                    stars: 0,
                    title: '',
                },
                game: '',
                reviews: '',
                gamewebReviews: '',
                show: false,
                paramsReviews:{
                    per_page: 10,
                    page: 1,
                    total: 1
                }
            };
        },
        watch: {
            game () {
                this.review.gameId = this.game.appId
                console.log(this.review)
            }
        },
        computed:{
            filterdReviews(){
                return paginate(this.reviews, this.paramsReviews.per_page, this.paramsReviews.page);
            }
        },
        methods: {
            Show: function() {
                if (this.show) {
                    this.show = false
                }
                else {
                    this.show = true
                }
            },
            handleReview() {
                console.log(this.review)
                axios.post("https://gameweb21.herokuapp.com/reviews/", {
                    description: this.review.description,
                    gameId: this.review.gameId,
                    stars: this.review.stars,
                    title: this.review.title
                    }, {withCredentials:true})
                .then((result) => {
                    console.log(result)
                    //this.$router.go(0);
                }).catch((err) => {
                    console.log(err)
                });
            },
        },
        created() {
            axios.get("https://gameweb12.herokuapp.com/api/apps/" + this.$router.history.current.query.game2 + "/?lang=pl")
            .then((response) => {
                this.game = response.data;
                console.log(response.data);
                axios.get("https://gameweb21.herokuapp.com/reviews/game/" + this.game.appId)
                .then((response) => {
                    this.gamewebReviews = response.data;
                    console.log(response.data)
                })
                .catch((error) => {
                    console.log(error);
                });
            })
            .catch((error) => {
                console.log(error);
            });
            axios.get("https://gameweb12.herokuapp.com/api/apps/" + this.$router.history.current.query.game2 + "/reviews")
            .then((response) => {
                this.reviews = response.data.results.data
                console.log(this.reviews)
            })
            .catch((error) => {
                console.log(error);
            });
        },
    }
</script>

<style lang="scss" scoped>
    .container {
        max-width:950px;
        background-color: #fff;
    }
    .game {
        margin-top: 20px;
        min-height: 250px;
        max-height: 300px;
        background-color: #222;
        padding-bottom: 10px;
        padding-top: 10px;
    }
    #rating {
        background-color: white;
        color: mediumaquamarine;
        border-color: mediumaquamarine;
        margin: 0rem 0rem 1rem 0rem;
    }
    .gameImage {
        min-width: 200px;
        min-height: 200px;
        max-width: 200px;
        max-height: 200px;
        background-color: mediumaquamarine;
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
    .button {
        min-width: 5rem;
        margin: 1rem 2rem 1rem 2rem;
        padding: 0.5rem;
        background-color: #58c79f;
        color: #fff;
        outline: none;
        border: none;
        border-radius: 15px;
    }
    .button:hover {
        background-color: #389774
    }
    .button:active {
        background-color: #58c79f;
        box-shadow: 0 2.5px #666;
        transform: translateY(4px);
    }
    .addReview {
        padding: 0rem 2rem 0 2rem
    }
</style>