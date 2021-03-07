<template>
    <div class="container">
        <b-row class="game" align-h="around" align-v="center">
            <div class="col-md-4 text-center">
                <b-img
                        class="gameImage w-100"
                        style="max-width: 100%"
                        :src="game.icon"
                        :alt="game.title"
                >
                </b-img>
            </div>
            <div class="col-md-6 text-center">
                <h1>{{ game.title }}</h1>
            </div>
        </b-row>
        <b-row class="mt-4">
            <b-col sm="8" offset-sm="2">
                <p v-if="readMore" class="game__description">{{ game.description }}</p>
                <p v-else-if="!readMore" class="game__description">{{ game.description.substring(0, 500) + "..." }}</p>
                <div v-if="descLength & readMore" class="readMore" @click="showMore()">Pokaż mniej!</div>
                <div class="readMore" @click="showLess()" v-if="!readMore">Pokaż więcej!</div>
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
            <form class="addReview" name="addReview" v-if="show & currentLoggedIn" @submit.prevent="handleReview">
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
                    @input="maxAreaLength()"
                    type="text"
                    class="form-control"
                    name="description"
                    required
                />
                <div
                  v-if="areaMessage"
                  class="alert-danger"
                >{{ areaMessage }}</div>
                <input class="left d-none" type="file" ref="file_input" v-on:change="handleFileUpload($event)">
                <div class="col-md-10" v-if="!file">
                    <div @click="$refs.file_input.click()" class="w-100 text-center" style="cursor: pointer; border: 1px dashed #c8c1c1; padding: 40px">
                    <span class="text-muted">Kliknij aby dodać plik</span>
                    </div>
                </div>
                <div class="col-md-10" v-if="file">
                    <div class="avatar-holder" style="cursor: pointer" @click="$refs.file_input.click()"><img :src="file"></div>
                </div>
                <button class="button" type="submit">Dodaj</button>
            </form>
            <b-alert v-else-if="!currentLoggedIn" show variant='info'>
                {{ 'Musisz być zalogowany, aby dodać recenzje!' }}
            </b-alert>
            <b-alert v-if="message" show :variant='variant'>
                {{ message }}
            </b-alert>
        </div>
        <hr class="mt-4" style="border-color: mediumaquamarine" />
        <div>
            <div class="mt-4">
                <h3 class="col-sm-8 offset-sm-2 reviewTitle">Recenzje Gameweb:</h3>
                <b-row v-for="r in reverseGamewebReviews" :key="r.id">
                    <b-col sm="8" offset-sm="2">
                        <b-row>
                            <b-col class="review_details" cols="12">
                                <h5>{{ r.nick }}</h5>
                                <h6 class="reviewDate">{{ r.createdDate }}</h6>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12 image">
                                <b-form-rating id="rating" :value="r.stars" inline disabled>
                                </b-form-rating>
                                <img v-if="r.image" class="review-avatar" v-bind:src="'data:image/jpeg;base64,'+r.image.data" />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12">
                                <h6 class="game_title"> {{ r.title }} </h6>
                                <p class="game__review">{{ r.description }}</p>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </div>
        </div>

        <hr class="mt-4" style="border-color: mediumaquamarine" />

        <div class="mt-4">
            <h3 class="col-sm-8 offset-sm-2 reviewTitle">Recenzje z Google Play:</h3>
            <b-row v-for="r in filterdReviews" :key="r.id">
                <b-col sm="8" offset-sm="2">
                    <b-row>
                        <b-col class="review_details" cols="12">
                                <h5>{{ r.userName }}</h5>
                                <h6 class="reviewDate">{{ r.date.slice(0,10) }}</h6>
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
                    :per-page="paramsReviews.per_page" first-text="First" prev-text="Prev" next-text="Next" last-text="Last">
            </b-pagination>
        </div>
    </div>
</template>>

<script>
    import axios from "axios";
    import {paginate} from "../helpers";
    import User from '../models/user'

    export default {
        name: "game-detail",
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data() {
            return {
                currentUser: new User(JSON.parse(localStorage.getItem("user"))),
                readMore: false,
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
                },
                message: null,
                variant: null,
                file: '',
                maxLength: 1000,
                areaMessage: ''
            };
        },
        watch: {
            game () {
                this.review.gameId = this.game.appId
                console.log(this.review)
            }
        },
        computed:{
            descLength() {
                return this.game.description.length>500;
            },
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
            filterdReviews(){
                return paginate(this.reviews, this.paramsReviews.per_page, this.paramsReviews.page);
            },
            reverseGamewebReviews() {
                return [...this.gamewebReviews].reverse();
            },
            findNick() {
                return this.gamewebReviews.filter((item) => {
                    return (item.nick === this.currentUser.nick)
                });
            }
        },
        methods: {
            maxAreaLength: function() {
                if (this.review.description.length >= this.maxLength) {
                    this.review.description = this.review.description.substring(0,this.maxLength);
                    this.areaMessage = 'Przekroczono limit znaków'
                }
            },
            showMore() {
                this.readMore = false;
            },
            showLess() {
                this.readMore = true;
            },
            Show: function() {
                if (this.show) {
                    this.show = false
                }
                else {
                    this.message = null
                    this.show = true
                }
            },
            handleFileUpload(event) {
                this.file = event.target.files[0];
            },
            handleReview() {
                if(this.findNick.length>0) {
                    this.message = "Już dodałeś recenzję do tej gry!"
                    this.variant = 'warning'
                }
                else {
                    this.$nextTick(() => {
                        const fd = new FormData();
                        fd.append("description",this.review.description)
                        fd.append("gameId",this.review.gameId)
                        fd.append("stars",this.review.stars)
                        fd.append("title",this.review.title)
                        axios.post("https://gameweb21.herokuapp.com/reviews/", fd, {withCredentials:true})
                            .then((result) => {
                                const fdd = new FormData();
                                fdd.append('id', result.data.id)
                                fdd.append('multipartFile', this.file)
                                axios.patch('https://gameweb21.herokuapp.com/reviews/addImage/'+result.data.id, fdd, {withCredentials:true})
                                .then((result) => {
                                    this.file = ''
                                    console.log(result)
                                    this.gamewebReviews = this.gamewebReviews.concat(result.data)
                                })
                                .catch((error) => {
                                    console.log(error)
                                })
                                this.review.description = ''
                                this.review.stars = ''
                                this.review.stars = 0
                                this.review.title = ''
                                this.show = false
                                this.message = "Dodano nową recenzje!"
                                this.variant = 'success'
                            })
                            .catch((err) => {
                                console.log(err)
                            });
                    });
            }
            }
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
                this.paramsReviews.total = this.reviews.length;
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
        background-color: #222;
        padding-bottom: 10px;
        padding-top: 10px;
    }
    #rating {
        margin: auto 0 auto;
        background-color: white;
        color: mediumaquamarine;
        border-color: mediumaquamarine;
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
    .game_title {
        font-size: 1.2rem;
        font-weight: 400;
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
    .review_details {
        display:flex;
        font-size: 1.2rem;
    }
    .reviewDate {
        margin: auto 0 auto auto;
        text-align: right;
    }
    .reviewTitle {
        text-align: center;
    }
    .review-avatar {
        max-width: 100px;
        max-height: 100px;
        margin-left: auto;
    }
    .image {
        display: flex;
    }
    .readMore {
        text-align: center;
        font-weight: 300;
        color: rgb(56, 55, 55);
    }
    .readMore:hover, .readMore:active, .readMore:link  {
        font-weight: 400;
        color: black;
        cursor: pointer;
    }
</style>