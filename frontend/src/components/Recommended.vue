<template>
    <div class="hello">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="w-100 d-flex align-center mb-3">
                        <div class="text-white mr-2">Ilość na stronie:</div>
                        <b-form-select style="max-width: 200px" v-model="params.per_page" :options="options"></b-form-select>
                    </div>
                </div>
                <div class="col-md-3 my-2"  :key="item.appId" v-for="item in items">
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
            <b-pagination
                    v-model="params.page"
                    :total-rows="params.total_rows"
                    :per-page="params.per_page" first-text="First" prev-text="Prev" next-text="Next" last-text="Last">

            </b-pagination>
        </div>
    </div>
</template>

<script>
    import Request from '../request';
    import axios from 'axios'

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
                allGames:null
            }
        },
        computed:{
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
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
            }
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
            ])
            axios.get('https://gameweb12.herokuapp.com/api/apps/?category=GAME')
                .then((result) => {
                    this.allGames = result.data.results;
                });
            Request({
                url: '/games/recommended',
                method: 'get'
            }).then(res => {
                this.data = res;
            })
        },
    }

</script>


