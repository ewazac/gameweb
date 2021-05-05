<template>
    <div class="container">
        <div class="row text-white">
            <div class="col-md-6">
                <h3 class="text-white mb-4">Newsy </h3>
                <b-list-group>
                    <b-list-group-item style="background-color: #222" :key="item.id" v-for="item in news">
                        <div class="d-flex align-center" style="justify-content: space-between">
                            <div>{{item.title}}</div>
                            <div class="d-flex">
                                <router-link :to="'/news/'+item.id"><b-icon scale="1.3" class="mr-2" icon="eye-fill" color="#007bff"></b-icon></router-link>
                                <a v-if="item.fav_id" style="cursor: pointer" @click="deleteItem(item.fav_id, 'news')"> <b-icon scale="1.3" color="red" icon="trash-2-fill"></b-icon></a>

                            </div>
                        </div>

                    </b-list-group-item>
                </b-list-group>
                <div v-if="news.length == 0" class="text-center heading mt-4">
                    Nie masz żadnych newsów w ulubionych
                </div>
            </div>
            <div class="col-md-6">
                <h3 class="text-white mb-4">Gry</h3>
                <b-list-group>
                    <b-list-group-item style="background-color: #222" :key="item.appId" v-for="item in games">
                        <div class="d-flex align-center" style="justify-content: space-between">
                            <div>{{item.title}}</div>
                            <div class="d-flex">
                                <router-link :to="'/game?game2='+item.appId"><b-icon scale="1.3" class="mr-2" icon="eye-fill" color="#007bff"></b-icon></router-link>
                                <a v-if="item.fav_id" style="cursor: pointer" @click="deleteItem(item.fav_id, 'game')"> <b-icon scale="1.3" color="red" icon="trash-2-fill"></b-icon></a>

                            </div>
                        </div>

                    </b-list-group-item>
                </b-list-group>
                <div v-if="games.length == 0" class="text-center heading mt-4">
                    Nie masz żadnej gry w ulubionych
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Request from '../../request';
    import axios from 'axios'
    export default {
        data:() => {
            return{
                allGames: null,
                fetchedFavs: null
            }
        },
        computed:{
            favourites(){
                return this.$store.getters['app/get_favourites'];
            },
            news(){
                return this.favourites.filter(x => x.type == 'news').map(item => {
                    if(!this.fetchedFavs) return item;
                    var finded = this.fetchedFavs.find(x => x.newsId == item.id);
                    if(finded) item.fav_id = finded.id;
                    return item;
                });
            },
            games(){
                var games = this.favourites.filter(x => x.type != 'news').map(id => {
                    if(this.allGames){
                        var finded = this.allGames.find(x => x.appId == id);
                        if(finded){
                            return finded;
                        }else{
                            return {};
                        }
                    }
                    return {appId: id, title: id};
                });
                return games.map(item => {
                    if(!this.fetchedFavs) return item;
                    var finded = this.fetchedFavs.find(x => x.gameId == item.appId);
                    if(finded) item.fav_id = finded.id;
                    return item;
                }).filter(x => x.title != null);
            }
        },
        mounted(){
            this.getAllGames();
            Request({
                url: '/fav/news/allFav',
                method:'get'
            }).then((res) =>{
                this.fetchedFavs = res;
            })
        },
        methods:{
            getAllGames(){
                axios.get('https://gameweb.projektstudencki.pl/apigames/api/apps/?category=GAME')
                    .then(({data}) => {
                        this.allGames = data.results;
                    })
            },
            deleteItem(id){
                Request({
                    url: '/fav/'+id,
                    method:'delete'
                }).then(() =>{
                    this.$store.dispatch('app/getFavourites');
                })
            }
        }
    }
</script>
