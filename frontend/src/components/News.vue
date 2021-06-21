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
                        <div class="col-12">
                            <div class="w-100 d-flex align-center mb-3 justify-content-end">
                                <div class="text-white mr-2">Pokaż na stronie:</div>
                                <b-form-select style="max-width: 200px" v-model="params.per_page" :options="options"></b-form-select>
                            </div>
                        </div>
                        <div class="col-md-3 py-3" :key="item.id"
                             v-for="item in news">
                            <b-card
                                    class="mb-2 h-100"
                            >
                              <div class="d-flex flex-column justify-space-between h-100" v-if="news">
                               <div>
                                 <div style="position: absolute; top: 10px; right: 10px" v-if="currentLoggedIn">
                                    <b-icon @click="item.toggleFavourite()" scale="2" style="cursor: pointer" icon="star-fill" variant="info"></b-icon>
                                 </div>
                                <img v-if="item.imageUrl" :src="'https://gwnews.s3.eu-central-1.amazonaws.com/'+item.imageUrl">
                                <img v-else src="../assets/default.png">
                                 <div class="p-3" style="color: whitesmoke">
                                    <div class="font-weight-bold d-flex justify-content-between flex-column">
                                        <div class="text-muted mb-1" style="font-size: 0.7rem">{{item.createdDate}}</div>
                                        <div>{{item.title}}</div>
                                    </div>
                                    <div class="font-weight-bold">{{item.description}}</div>
                                 </div>
                               </div>
                                  <div>
                                    <b-button class="news__button w-100 mt-3" :to="'/news/'+item.id"> Czytaj dalej </b-button>
                                  </div>
                                 </div>
                            </b-card>
                        </div>
                    </div>
                </div>
            </div>
            <div class="w-100 d-flex justify-content-center">
                <b-pagination
                        @change="scrollToTop()"
                        v-model="params.page"
                        :total-rows="params.total_rows"
                        :per-page="params.per_page" first-text="Pierwsza" prev-text="Poprzednia" next-text="Następna" last-text="Ostatnia">
                </b-pagination>
            </div>
        </div>
    </div>
</template>
<script>
    import Request from '../request';
    import News from '../models/news';
    import {paginate} from "../helpers";
    export default {
        name: 'Home',
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data:() => {
            return {
                news:null,
                options: [
                    { value: 8,  text: 8 },
                    { value: 16, text: 16 },
                    { value: 32, text: 32 },
                ],
                params:{
                    page: 1,
                    per_page: 16,
                    total_rows: 1
                }
            }
        },
        computed:{
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
            items(){
                return paginate(this.news, this.params.per_page, this.params.page);
            }
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
                    console.log(this.news);
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
        min-height: 30rem;
        max-height: 35rem;
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
    .btn-secondary {
        background-color: mediumaquamarine;
    }

    .btn-secondary:hover {
        opacity: 0.9;
    }
    label {
        color: white;
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
