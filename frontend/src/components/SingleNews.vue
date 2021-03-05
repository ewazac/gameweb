<template>
    <div class="container">
        <div class="w-100" v-if="news">
            <img class="w-100" v-if="news.image" :src="'data:image/jpeg;base64,'+news.image.data" style="max-height: 300px; object-fit: cover">
            <img class="w-100" v-else src="../assets/default.png" style="max-height: 300px; object-fit: cover">
            <div class="row text-left">
                <div class="col-md-12">
                    <h1 class="text-left">{{news.title}}</h1>
                    <div class="d-flex justify-content-between flex-wrap w-100 align-center">
                        <h2 class="my-0">{{news.description}}</h2>
                        <div class="text-muted font-weight-bold">{{news.createdDate | moment("YYYY-MM-DD")}}</div>
                    </div>
                </div>
            </div>
            <div class="my-5" v-html="news.body" style="color: black"></div>
        </div>
    </div>
</template>
<script>
    import Request from '../request'
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
                    this.news = res.filter(x => x.id == this.$route.params.id)[0];
                })
            }
        }
    }
</script>
