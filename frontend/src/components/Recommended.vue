<template>
    <div class="hello">
        <div class="container">
            <h2 class="text-center text-white">Gry polecane przez Gameweb</h2>
            <hr class="mt-4" style="border-color: mediumaquamarine" />
            <hr>
            <h3 class="my-4 text-center text-white"> Do polecanych trafiają gry, na które oddaliście największą liczbę głosów w konkursach</h3>
            <div class="row">
                <div class="col-12">
                    <div class="w-100 d-flex justify-content-end align-center mb-3">
                        <div class="text-white mr-2">Ilość na stronie:</div>
                        <b-form-select style="max-width: 200px" v-model="params.per_page" :options="options"></b-form-select>
                    </div>
                </div>

                <div class="col-md-3 my-2"  :key="item.id" v-for="item in allGames">

                    <b-card
                            class="mb-2 h-100"
                            :title="item.title"
                            :img-src="item.icon"
                            img-top
                    >
                        <div class="font-weight-bold text-muted my-2 text-center">
                            {{item.createdDate}}
                        </div>
                        <b-button class="games__button mt-3" @click="handleDetails(item)"> Zobacz więcej </b-button>
                    </b-card>
                </div>
            </div>
            <div class="w-100 d-flex justify-content-center">
            <b-pagination
                    @change="scrollToTop()"
                    v-model="params.page"
                    :total-rows="params.total_rows"
                    :per-page="params.per_page" first-text="First" prev-text="Prev" next-text="Next" last-text="Last">
            </b-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import Request from '../request';
    import axios from 'axios';
    //import {paginate} from "../helpers";
    export default {
        name: 'Home',
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data() {
            return {
                data: [],
                options: [
                    { value: 16, text: 16 },
                    { value: 32, text: 32 },
                ],
                params:{
                    page: 1,
                    per_page: 16,
                    total_rows: 1
                },
                allGames: [],
            }
        },
        computed:{
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
        },
        methods: {
            handleDetails(item) {
                this.$router.push({path:'/game?game2='+item.appId});
            },
        },

        mounted() {
            this.$store.commit('app/SET_BREADCRUMBS', [
                {
                    text: 'Polecane',
                    to: '/recommended'
                }
            ])/*
            axios.get('https://gameweb.projektstudencki.pl/apigames/api/apps/?category=GAME')
                .then((result) => {
                    this.allGames = result.data.results;
                });*/
            Request({
                url: 'games/recommended',
                method: 'get'
            }).then(res => {
                this.data = res;
                this.data.map(item => {
                    axios.get('https://gameweb.projektstudencki.pl/apigames/api/apps/' + item.appId)
                    .then(result => {
                        let merged = {...item, ...result.data}
                        this.allGames.push(merged)
                        return merged
                    })
                })
            })
        },
    }

</script>

<style lang="scss">
    .games__button {
        background-color: mediumaquamarine !important;
        margin-top: auto;
        border: none;

        &:focus {
            outline: none;
            border: none;
            box-shadow: none !important;
        }
    }
</style>
