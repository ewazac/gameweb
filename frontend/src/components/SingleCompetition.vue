<template>
    <div class="container">
        <div class="w-100" v-if="data">
            <div v-if="!voted" class="row">
                <div class="col-md-3 my-2" :key="item.id" v-for="item in items">
                    <b-card class="mb-2 h-100 text-white" @click="(!voteItems[item.id])? $set(voteItems, item.id, true) : voteItems[item.id] = false">
                        <div style="position: absolute; right: 0px; top: 0px">
                            <b-form-checkbox size="lg" checked v-model="voteItems[item.id]"></b-form-checkbox>
                        </div>
                        <img :src="item.icon" class="w-100">
                        <h3 class="text-white my-3">{{item.title}}</h3>
                    </b-card>
                </div>
                <div class="col-12">
                    <div class="give-me-space">
                        <button class="btn btn-primary" @click="vote()">Zagłosuj na {{votedItemsProcesed.length}} gier</button>
                    </div>
                </div>
            </div>
            <div v-else>
                <div class="font-weight-bold text-center text-white">
                    Dziękujemy za oddanie głosu.
                    <br>
                    <router-link to="/competitions"><button class="btn btn-primary mt-5">Wróć do konkursów</button></router-link>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Request from '../request'
    import axios from 'axios'
    // import {content} from '../assets/example_html_content'
    export default {
        data:() => {
            return {
                data:null,
                allGames: null,
                voted:false,
                voteItems:{}
            }
        },
        computed:{
            items(){
                return this.data.map(item => {
                    if(!this.allGames) return {id: null}
                    var finded = this.allGames.find(x => x.appId == item.appId);
                    if(finded){
                        return {
                            ...item,
                            ...finded
                        }
                    }else{
                        return {
                            id: null
                        }
                    }
                })
            },
            votedItemsProcesed(){
                var arr = [];
                for(var i in this.voteItems){
                    if(this.voteItems[i]){
                        arr.push(i);
                    }
                }
                return arr;
            }
        },
        created(){
            this.getData();
        },
        methods:{
            vote(){
                this.votedItemsProcesed.forEach(gameId => {
                    var promiseJar = [];
                    promiseJar.push(Request({
                        url: '/games/' + gameId + '?groupId=' + this.items[0].groupId,
                        method: 'patch'
                    }).catch(() => {
                        this.$store.commit('app/ADD_MESSAGE', {text: 'Nie udało sie zagłosować na element: '+gameId+'. Prawdopodobnie oddałeś już głos na ten element.', type: 'danger'});
                    }));
                    Promise.all(promiseJar).then(() => {
                        this.voted = true;
                    }).catch(() => {
                    })
                });
            },
            getData(){
                axios.get('https://gameweb.projektstudencki.pl/apigames/api/apps/?category=GAME')
                    .then((result) => {
                        this.allGames = result.data.results;
                    });
                Request({
                    url:'/games/'+this.$route.params.id,
                    method:'get',
                }).then(res => {
                    this.data = res;
                    this.$store.commit('app/SET_BREADCRUMBS', [
                        {
                            text: 'Konkursy',
                            to: '/competitions'
                        },
                        {
                            text: 'Konkurs',
                        }
                    ])
                })
            }
        }
    }
</script>
