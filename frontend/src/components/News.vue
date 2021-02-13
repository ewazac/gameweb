<template>
    <div>
        <div class="hello">
            <div class="container">
                <h2 class="text-center">News za newsem ze świata gier</h2>
                <hr>
                <h3 class="my-4 text-center">Ciekawostki a czasem spekulacje. Zapowiedzi, a po nich premiery. Pierwsze opinie oraz wrażenia. Bądź na bieżąco w świecie newsów!</h3>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-md-3" :key="item.id"
                             v-for="item in news">
                            <b-card
                                    class="mb-2"
                            >
                                <img v-if="item.image" :src="'data:image/jpeg;base64,'+item.image.data">
                                <img v-else src="../assets/default.png">
                                <div class="p-3">
                                    <div class="font-weight-bold d-flex justify-content-between">
                                        <div>{{item.title}}</div>
                                        <div class="text-muted">{{item.createdDate | convertDate('Y-m-d')}}</div>
                                    </div>
                                    <div class="font-weight-bold">{{item.description}}</div>
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
    export default {
        data:() => {
            return {
                news:null
            }
        },
        created(){
            this.getData();
        },
        methods:{
            getData(){
                Request({
                    url:'/news',
                    method:'get',
                }).then(res => {
                    this.news = res;
                })
            }
        }
    }
</script>
<style lang="scss">
    .card{
        .card-body{
            padding: 0px;
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
    }
</style>