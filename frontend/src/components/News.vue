<template>
    <div>
        <div class="hello">
            <div class="container">
                <h2 class="text-center">News za newsem ze świata gier</h2>
                <hr class="mt-4" style="border-color: mediumaquamarine" />
                <hr>
                <h3 class="my-4 text-center">Ciekawostki a czasem spekulacje. Zapowiedzi, a po nich premiery. Pierwsze opinie oraz wrażenia. Bądź na bieżąco w świecie newsów!</h3>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-md-3" :key="item.id"
                             v-for="item in news">
                            <b-card
                                    class="mb-2"
                            >
                                <div style="position: absolute; top: 10px; right: 10px" v-if="currentLoggedIn">
                                    <b-icon @click="item.toggleFavourite()" scale="2" style="cursor: pointer" icon="star-fill" variant="info"></b-icon>
                                </div>
                                <img v-if="item.image" :src="'data:image/jpeg;base64,'+item.image.data">
                                <img v-else src="../assets/default.png">
                                <div class="p-3" style="color: whitesmoke">
                                    <div class="font-weight-bold d-flex justify-content-between flex-column">
                                        <div class="text-muted mb-1" style="font-size: 0.7rem">{{item.createdDate}}</div>
                                        <div>{{item.title}}</div>
                                    </div>
                                    <div class="font-weight-bold">{{item.description}}</div>
                                    <b-button class="news__button w-100 mt-3" :to="'/news/'+item.id"> Czytaj dalej </b-button>
                                </div>
                            </b-card>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Request from '../request';
    import News from '../models/news'
    export default {
        name: 'Home',
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data:() => {
            return {
                news:null
            }
        },
        computed:{
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
        },
        created(){
            this.getData();
            this.$store.commit('app/SET_BREADCRUMBS', [
                {
                    text: 'Newsy',
                    to: '/news'
                }
            ])
        },
        methods:{
            getData(){
                Request({
                    url:'/news',
                    method:'get',
                }).then(res => {
                    this.news = res.reverse().map(item => {
                        return new News(item);
                    });
                })
                Request({
                    url:'/fav',
                    method:'get'
                })
            },
        },
    }
</script>
<style lang="scss">
    .card{
        justify-content: center;
        min-height: 20rem;
        border-width: 0 0 2px 0;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;

    .card-body{
       padding: 10px;
            img{
                width: 100%;
                height: 200px;
                object-fit: cover;
            }
       }
       transition: all 200ms;
        &:hover{
            -webkit-box-shadow: 0px 8px 24px 0px rgba(0,0,0,0.15);
            -moz-box-shadow: 0px 8px 24px 0px rgba(0,0,0,0.15);
            box-shadow: 0px 8px 24px 0px rgba(0,0,0,0.15);
        }
        &:focus {
            outline: none;
            border: none;
        }
    }
    .news__button{
        background-color: mediumaquamarine !important;
        margin-top: auto;
        border: none;

        &:focus {
            outline: none;
            border: none;
            box-shadow: none !important;
        }
    }

    label {
        color: white;
    }
    .text-center{
        color: #fff;
        font-weight: 550;
        font-size: 1.3rem;

        &:hover{
            color: #fff;
            opacity: 0.9;
        }
    }
</style>
