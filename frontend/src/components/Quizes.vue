<template>
    <div class="hello">
        <div class="container">
            <h2 class="text-center text-white">Quizy</h2>
            <hr class="mt-4" style="border-color: mediumaquamarine" />
            <hr>
            <h3 class="my-4 text-center text-white">Sprawdź swoją wiedzę o grach. Rozwiąż quiz nieskończoną liczbę razy i baw się razem z nami!</h3>
            <div class="row">
                <div class="col-12">
                    <div class="w-100 d-flex align-center mb-3 justify-content-end">
                        <div class="text-white mr-2">Pokaż na stronie:</div>
                        <b-form-select style="max-width: 200px" v-model="params.per_page" :options="options"></b-form-select>
                    </div>
                </div>
                <div class="col-md-3 py-3"  :key="item.appId" v-for="item in items">
                    <b-card
                            class="mb-2 h-100"
                            :title="item.name"
                    >

                        <div class="d-flex flex-column justify-space-between h-100" v-if="items">

                                <div class="font-weight-bold d-flex justify-content-between flex-column" style="color: whitesmoke">{{item.description}}</div>

                            <div>
                                <b-button class="game__button align-card-bottom w-100 mb-3" @click="handleDetails(item)"> Zobacz więcej </b-button>
                            </div>
                        </div>
                    </b-card>

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
                url: 'api/quizy',
                method: 'get'
            }).then(res => {
                this.data = res.reverse(

                );
            })
        },
    }

</script>
<style lang="scss">
    .quiz__button{
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
</style>