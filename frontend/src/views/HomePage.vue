<template>
    <div class="container">
        <header-text text="Gry"></header-text>
        <div class="row" v-if="sortedGames && sortedGames.length > 0">
            <div class="col-md-6">
                <a :href="'/game?game2='+sortedGames[0].appId">
                    <div class="py-3 white-background mt-5" style="position: relative">
                        <img :src="sortedGames[0].icon">
                        <div class="position-absolute col-md-10" style="background-color: black; bottom: 0px; left: 0px">
                            <div class="pa-5 text-white">
                                <div class="row">
                                    <div class="col-md-6 text-left">
                                        <div class="font-weight-bold title">{{sortedGames[0].title}}</div>
                                    </div>
                                    <div class="col-md-6 d-flex justify-content-end">
                                        <div class="star-ratings-sprite"><span :style="{'width': calculateStarWidth(sortedGames[0].score)+'%'}" class="star-ratings-sprite-rating"></span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-md-6 pt-5">
                <div :key="item.appId" v-for="item in sortedGames.splice(1, 6)">
                    <a :href="'/game?game2='+item.appId">
                        <div class="row mb-4">
                            <div class="col-md-2">
                                <img class="w-100" :src="item.icon">
                            </div>
                            <div class="col-md-6 text-white font-weight-bold d-flex align-center text-left">
                                {{item.title}}
                            </div>
                            <div class="col-md-4 d-flex justify-content-end">
                                <div class="star-ratings-sprite"><span :style="{'width': calculateStarWidth(item.score)+'%'}" class="star-ratings-sprite-rating"></span></div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <header-text style="margin-top: 60px" text="Newsy"></header-text>
        <div class="row" v-if="data.news && data.news.length > 0">
            <div class="col-md-6 pt-5">
                <a :href="'/news/'+data.news[0].id" class="d-block h-100">
                    <div class="py-3 white-background h-100" style="position: relative">
                        <img v-if="data.news[0].id.image" :src="'data:image/jpeg;base64,'+data.news[0].image.data">
                        <img v-else src="../assets/default.png">
                        <div class="position-absolute col-md-10" style="background-color: black; bottom: 0px; left: 0px">
                            <div class="pa-5 text-white">
                                <div class="row">
                                    <div class="col-md-6 text-left">
                                        <div class="font-weight-bold title">{{data.news[0].title}}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-md-6 pt-5">
                <div :key="item.id" v-for="item in splicedNews">
                    <a :href="'/news/'+item.id">
                        <div class="row mb-4">
                            <div class="col-md-2">
                                <img class="w-100" v-if="item.image" :src="'data:image/jpeg;base64,'+item.image.data">
                                <img class="w-100" v-else src="../assets/default.png">
                            </div>
                            <div class="col-md-6 text-white font-weight-bold d-flex align-center text-left">
                                {{item.title}}
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="w-100" style="padding: 60px 0px;">
            <div style=" position: relative">
                <img class="w-100" src="../assets/banner_quiz.jpg">
                <div class="position-absolute w-100 h-100" style="left: 0px; top: 0px;">
                    <div class="row h-100 mx-5 align-center justify-content-end">
                        <div class="col-md-6 text-left">
                            <div class="text-white">
                                <div style="font-size: 3.2rem" class="font-weight-bold">Wez udzial w naszych quizach</div>
                                <div style="font-size: 1.5rem" class=" my-4 font-weight-bold">Aktualnie mamy {{data.quizes}} Quizów</div>
                                <a href="/quizy" class="btn btn-primary px-5 py-2" style="background-color: #5E9883; border-radius: 30px; font-size: 1.2rem; border: none; outline: none">Zobacz więcej</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mx-0" style="position: relative">
            <div class="white w-100" style="position: absolute; min-height: 5px; background-color: white; top: 25px; z-index: 0"></div>
            <div class="w-100 text-white font-weight-bold text-center d-flex justify-content-center" style="font-size: 1.5rem; z-index: 1"><div class="px-4 py-2" style="background-color: #111111">Tylko dla zalogowanych</div></div>
        </div>
        <div class="mt-5">
            <div class="row">
                <div class="col-md-6" style="position: relative">
                    <img src="../assets/tlo_forum.png" class="w-100">
                    <div style="position: absolute; width: 100%; height: 100%; top: 0px" class="d-flex justify-content-center align-center">
                        <a href="/forum" class="btn btn-primary px-5 py-2 font-weight-bold" style="border: 5px solid #5E9883; background-color: transparent; border-radius: 30px; color: #5E9883 !important; font-size: 1.4rem; outline: none">Forum</a>
                    </div>
                </div>
                <div class="col-md-6" style="position: relative">
                    <img src="../assets/konkursy_tlo.jpg" class="w-100">
                    <div style="position: absolute; width: 100%; height: 100%; top: 0px" class="d-flex justify-content-center align-center">
                        <a href="/competitions" class="btn btn-primary px-5 py-2 font-weight-bold" style="border: 5px solid #5E9883; background-color: transparent; border-radius: 30px; color: #5E9883 !important; font-size: 1.4rem; outline: none">Konkursy</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import HeaderText from './HomepageComponents/header'
    import axios from 'axios'
    import Game from '../models/game'
    import News from '../models/news';
    import Request from '../request';
    export default {
        components:{HeaderText},
        data:() => {
            return{
                data:{}
            }
        },
        computed:{
            splicedNews(){
                if(!this.data.news) return [];
                var arr = Object.assign([], this.data.news);
                return arr.splice(1, 6);
            },
            sortedGames(){
                if(!this.data) return [];
                var array = Object.assign([], this.data.games);
                return array.sort((a, b) => {
                    return b.score - a.score;
                });
            }
        },
        mounted(){
            this.getData();
        },
        methods:{
            getData(){
                axios.get('https://gameweb.projektstudencki.pl/apigames/api/apps/?category=GAME')
                    .then((result) => {
                        var results = result.data.results.map(item => {
                            return new Game(item);
                        });
                        this.$set(this.data, 'games',  results);
                    })
                Request({
                    url:'/news',
                    method:'get',
                }).then(res => {
                    var data = res.reverse().map(item => {
                        return new News(item);
                    });
                    this.$set(this.data, 'news', data);
                })
                Request({
                    url: '/api/quizy',
                    method: 'get'
                }).then(res => {
                    this.$set(this.data, 'quizes', res.length);
                })
            }
        }
    }
</script>