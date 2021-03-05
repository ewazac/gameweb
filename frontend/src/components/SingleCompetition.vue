<template>
    <div class="container">
        <div class="w-100" v-if="data">
            <div v-if="!voted" class="row">
                <div class="col-md-3 my-2" :key="item.id" v-for="item in items">
                    <b-card class="mb-2 h-100 text-white">
                        <div class="font-weight-bold mb-2">{{item.id}}</div>
                        <b-button class="games__button" @click="vote(item)"> Zagłosuj </b-button>
                    </b-card>
                </div>
            </div>
            <div v-else>
                <div class="font-weight-bold text-center text-white">Dziękujemy za oddanie głosu na grę: {{voted.id}}</div>
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
                voted:false
            }
        },
        computed:{
            items(){
                return this.data;
               /* return this.data.map(item => {
                    if(!this.allGames) return {id: null}
                    var finded = this.allGames.find(x => x.appId == item.id);
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
                }).filter(x => x.id != null)*/
            }
        },
        created(){
            this.getData();
        },
        methods:{
            vote(item){
                Request({
                    url: '/games/'+item.id,
                    method: 'patch'
                }).then(() => {
                    this.voted = item;
                }).catch(() => {
                    this.$store.commit('app/ADD_MESSAGE', {text: 'Nie udało sie zagłosować', type: 'danger'});
                })

            },
            getData(){
                axios.get('https://gameweb12.herokuapp.com/api/apps/?category=GAME')
                    .then((result) => {
                        this.allGames = result.data.results;
                    });
                Request({
                    url:'/games/'+this.$route.params.id,
                    method:'get',
                }).then(res => {
                    this.data = res;
                })
            }
        }
    }
</script>
