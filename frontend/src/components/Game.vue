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
                <p v-if="!readMore" class="game__description">{{ game.description.substring(0, 500) + "..." }}</p>
                <div v-if="descLength & readMore" class="readMore" @click="showMore()">Pokaż mniej!</div>
                <div v-if="!readMore" class="readMore" @click="showLess()">Pokaż więcej!</div>
            </b-col>
        </b-row>

        <hr class="mt-4" style="border-color: mediumaquamarine" />

        <b-row class="mt-5">
            <b-col sm="8" offset-sm="2">
                <h2>Recenzje</h2>
                <h5 v-if="fav.length==0">Ta gra jest polecana przez: {{ fav.length }} użytkowników </h5>
                <h5 v-if="fav.length!=0 && !showFav" class="fav" style="cursor: pointer;" @click="showFavUsers()">Ta gra jest polecana przez: {{ fav.length }} użytkowników <b-icon-arrow-down></b-icon-arrow-down></h5>
                <h5 v-if="fav.length!=0 && showFav" class="fav" style="cursor: pointer;" @click="showNoFavUsers()">Ta gra jest polecana przez: {{ fav.length }} użytkowników <b-icon-arrow-up></b-icon-arrow-up></h5>
                <div v-if="showFav"><ul style="list-style-type:none;padding:0;" v-for="user in fav" :key="user.id">
                    <li class="fav" style="cursor: pointer;" @click="handleDetails(user.id)"> <b-icon scale="1.3" class="mr-2" icon="eye-fill" color="#007bff"></b-icon> {{ user.nick }} </li>
                </ul></div>
                <b-alert show>Jeśli dodasz recenzję, przyznamy Ci 1 punkt.</b-alert>
                <div class="d-flex mean">
                    <span class="rating">Średnia ocena tej gry    </span>
                    <b-form-rating id="rating" :value="game.score" inline disabled>
                    </b-form-rating>
                    <button class="button" v-on:click="Show">Dodaj recenzje!</button>
                </div>
            </b-col>
        </b-row>
        <div>
            <form class="addReview" name="addReview" v-if="show & currentLoggedIn" @submit="setSubmitting" @submit.prevent="handleReview">
                <div class="formTitle">
                    <p>Tytuł:</p>
                    <b-form-input
                        v-model="review.title"
                        type="text"
                        class="form-control"
                        placeholder="Podaj tytuł"
                        name="title"
                        required
                    />
                </div>
                <p class="rate">Oceń grę:</p>
                <div class="starRating">
                    <p>Grafika:</p>
                    <b-form-rating id="rating" v-model="stars.starsGraphics" required inline></b-form-rating>
                </div><div class="starRating">
                    <p>Sterowanie:</p>
                    <b-form-rating id="rating" v-model="stars.starsControl" required inline></b-form-rating>
                </div><div class="starRating">
                    <p>Grywalność:</p>
                    <b-form-rating id="rating" v-model="stars.starsGameplay" required inline></b-form-rating>
                </div><div class="starRating">
                    <p>Optymalizajca i jakość rozgrywki:</p>
                    <b-form-rating id="rating" v-model="stars.starsOptErr" required inline></b-form-rating>
                </div>
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
                <div class="col-md-10" style="margin:5px auto;" v-if="!file">
                    <div @click="$refs.file_input.click()" class="w-100 text-center" style="cursor: pointer; border: 1px dashed #c8c1c1; padding: 15px">
                    <span class="text-muted">Kliknij aby dodać plik</span>
                    </div>
                </div>
                <div class="col-md-10" v-if="file">
                    <div class="avatar-holder" style="cursor: pointer" @click="$refs.file_input.click()"><img :src="file"></div>
                </div>
                <button class="button" type="submit" :disabled="submitting">Dodaj</button>
                <div
                  v-if="submitting"
                  class="alert-danger"
                >{{ "Recenzja została wysłana" }}</div>
                <div class="w-100 d-flex justify-content-center" v-if="review_created_succesfully">
                    <div class="pa-6 bg-success text-white">
                        <div style="font-size: 1.5rem; font-weight: bold">Przyznano Ci 1 punkt</div>
                    </div>
                </div>
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
                                <img class="review-avatar" v-bind:src="'https://gwreviews.s3.eu-central-1.amazonaws.com/'+r.id+'png'" />
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
    import axios from "axios"
    import {paginate} from "../helpers"
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
                stars: {
                    starsGraphics: 0,
                    starsControl: 0,
                    starsGameplay: 0,
                    starsOptErr: 0,
                },
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
                maxLength: 100,
                areaMessage: '',
                gamewebReviewsT: '',
                nick: '',
                submitting: false,
                fav: '',
                showFav: false,
                review_created_succesfully: false,
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
                return this.game.description.length > 500;
            },
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
            filterdReviews(){
                return paginate(this.reviews, this.paramsReviews.per_page, this.paramsReviews.page);
            },
            reverseGamewebReviews() {
                console.log(this.gamewebReviews)
                return [...this.gamewebReviews].reverse();
            },
            findNick() {
                return this.gamewebReviews.filter((item) => {
                    return (item.userId == this.currentUser.userId)
                });
            }
        },
        methods: {
            setSubmitting() {
                this.submitting = true
            },
            getNick(userId) {
                axios.get("https://gameweb.projektstudencki.pl/api/users/"+userId)
                .then((result) => {
                    return result.data.nick;
                }).catch((err) => {
                    console.log(err)
                });
            },
            maxAreaLength: function() {
                if (this.review.description.length >= this.maxLength*5) {
                    console.log('tak',this.review.description)
                    this.review.description = this.review.description.substring(0,this.maxLength*5);
                    this.areaMessage = 'Przekroczono limit znaków'
                }
            },
            showMore() {
                this.readMore = false;
            },
            showLess() {
                this.readMore = true;
            },
            showFavUsers() {
                this.showFav = true;
            },
            showNoFavUsers() {
                this.showFav = false;
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
            handleDetails(item) {
                this.$router.push({path:'/User/', params:{user:item}, query:{user: item}});
             },
            handleReview() {
                if(this.findNick.length>0) {
                    this.message = "Już dodałeś recenzję do tej gry!"
                    this.variant = 'warning'
                }
                else {
                    this.$nextTick(() => {
                        const fd = new FormData();
                        this.review.stars = (this.stars.starsGraphics + this.stars.starsControl + this.stars.starsGameplay + this.stars.starsOptErr) / 4
                        fd.append("description",this.review.description)
                        fd.append("gameId",this.review.gameId)
                        fd.append("stars",this.review.stars)
                        fd.append("title",this.review.title)
                        fd.append("gameName",this.game.title)
                        axios.post("https://gameweb.projektstudencki.pl/api/reviews/", fd, {withCredentials:true})
                            .then((result) => {
                                if (this.file == '') {
                                    console.log(result)
                                    this.gamewebReviews = this.gamewebReviews.concat(result.data)
                                    this.review.description = ''
                                    this.review.stars = ''
                                    this.review.stars = 0
                                    this.review.title = ''
                                    this.show = false
                                    this.review_created_succesfully = true
                                    this.message = "Dodano nową recenzje!"
                                    this.variant = 'success'
                                }
                                else {
                                    const fdd = new FormData();
                                    fdd.append('id', result.data.id)
                                    fdd.append('multipartFile', this.file)
                                    axios.patch('https://gameweb.projektstudencki.pl/api/reviews/addImage/'+result.data.id, fdd, {withCredentials:true})
                                    .then((result) => {
                                        this.file = ''
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
                                    this.review_created_succesfully = true
                                    this.message = "Dodano nową recenzje! Żeby wszyscy mogli ją zobaczyć, poczekaj, aż moderatorzy strony ją zaakceptują!"
                                    this.variant = 'success'
                                    }  
                            })
                            .catch((err) => {
                                console.log(err)
                            });
                    });
            }
            }
        },
        mounted() {
            axios.get("https://gameweb.projektstudencki.pl/apigames/api/apps/" + this.$router.history.current.query.game2 + "/?lang=pl")
            .then((response) => {
                this.game = response.data;
                this.$store.commit('app/SET_BREADCRUMBS', [
                    {
                        text: 'Gry',
                        to: '/'
                    },
                    {
                        text: this.game.title,
                    }
                ])
                console.log(response.data);
                axios.get("https://gameweb.projektstudencki.pl/api/reviews/game/" + this.game.appId)
                .then((response) => {
                    this.gamewebReviews = response.data
                })
                .catch((error) => {
                    console.log(error);
                });
            })
            .catch((error) => {
                console.log(error);
            });
            axios.get("https://gameweb.projektstudencki.pl/apigames/api/apps/" + this.$router.history.current.query.game2 + "/reviews")
            .then((response) => {
                this.reviews = response.data.results.data
                this.paramsReviews.total = this.reviews.length;
                console.log(this.reviews)
            })
            .catch((error) => {
                console.log(error);
            });
            axios.get("https://gameweb.projektstudencki.pl/api/fav/users?gameId=" + this.$router.history.current.query.game2)
            .then(result => {
                this.fav = result.data
                console.log("fav ",result.data)
            })
            .catch(err => {
                console.log(err)
            })
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
        margin: auto 0;
        background-color: white;
        color: mediumaquamarine;
        border-color: mediumaquamarine;
    }
    textarea ,input {
        background-color: white;
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
        margin-left: auto;
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
        margin: 1rem 0;
        padding: 1rem 1rem;
        border-style: outset;
        text-align: left;
    }
    .formTitle {
        margin: 1rem 0;
        display: inline-flex;
        width: 100%;
        justify-content: space-between;
        @media screen and(max-width: 1200px){
            display: block;
        }
    }
    .formTitle input {
        max-width: 75%;
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
    .rating {
        margin: auto 0.5rem auto;
    }
    p.rate {
        margin: 0;
    }
    .starRating {
        width: 100%;
        display: inline-flex;
        justify-content: space-between;
        @media screen and(max-width: 1200px){
            display: block;
        }
    }
    .fav:hover, .fav:active, .fav:link{
        color: #419c7b;
    }
    .mean {
        @media screen and(max-width: 1200px){
            display: block !important;
        }
    }
</style>