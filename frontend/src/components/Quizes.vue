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
                            :title="item.name"
                    >
                        <!--    <b-card-group class="cardGroup" v-for="i in Math.ceil(games.length/4)" :key="i" deck>-->
                        <!--      <b-card-->
                        <!--              class="mb-2"-->
                        <!--              v-for="item in games.slice((i-1)*4, (i-1)*4 +  4)"-->
                        <!--              :key="item.appId"-->
                        <!--              :title="item.title"-->
                        <!--              :img-src="item.icon"-->
                        <!--              img-top-->
                        <!--      >-->
                        <!--        <b-card-text v-if="item.summary">{{ item.summary.slice(0,150) }}...</b-card-text>-->
                        <div class="my-3 text-white">{{item.description}}</div>
                        <b-button class="quiz__button mt-3" @click="handleDetails(item)"> Zobacz więcej </b-button>
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
    import {paginate} from "../helpers";

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
                }
            }
        },
        computed:{
            items(){
                return paginate(this.data, this.params.per_page, this.params.page);
            }
        },
        methods: {
            handleDetails(item) {
                this.$router.push({path:'/quizy/'+item.id});
            },
        },

        mounted() {
            this.$store.commit('app/SET_BREADCRUMBS', [
                {
                    text: 'Quizy',
                    to: '/quizy'
                }
            ])
           Request({
               url: '/api/quizy',
               method: 'get'
           }).then(res => {
               this.data = res.reverse(

               );
           })
        },
    }

</script>

<style lang="scss">
    .quiz__button {
        background-color: mediumaquamarine !important;
        margin-top: auto;
        border: none;

    &:focus {
         outline: none;
         border: none;
         box-shadow: none !important;
     }
    }

    .card-body {
        display: flex;
        flex-direction: column;
        padding: 10px;
    }

    .card-body > .card-title {
        text-align: center;
        margin-bottom: 5px;;
    }

    .card-body > .card-text {
        text-align: justify;
        padding: 0px;
    }

    .card {
        padding: 10px;
        min-height: 10rem;
        max-height: 15rem;
        border-width: 0 0 2px 0;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
    }

</style>
