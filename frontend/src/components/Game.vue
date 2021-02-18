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
            </b-col>
        </b-row>
        <div class="mt-4" v-if="success">
            <b-row v-for="r in reviews" :key="r.id">
                <b-col sm="8" offset-sm="2">
                    <b-row>
                        <b-col cols="12">
                            <h5>{{ r.title }}</h5>
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
    </div>
</template>>

<script>
    import axios from "axios";

    export default {
        name: "game-detail",
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data() {
            return {
                success: false,
                game: '',
                reviews: '',
            };
        },
        created() {
            axios.get("https://gameweb12.herokuapp.com/api/apps/" + this.$router.history.current.query.game2 + "/?lang=pl")
                .then((response) => {
                    this.game = response.data;
                    console.log(response.data);
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        mounted() {
            axios.get("https://gameweb12.herokuapp.com/api/apps/" + this.$router.history.current.query.game2 + "/reviews")
                .then((response) => {
                    this.success=true
                    this.reviews = response.data.results.data
                    console.log(response.data)
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

    .disabled {
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

    .game__review {
        padding: 1rem 0;
        font-size: 1.2rem;
        font-weight: 300;
        line-height: 1.6;
        text-align: left;
    }
</style>