<template>
    <div class="container">
        <div class="w-100 text-white" v-if="item">
            <div class="row text-left">
                <div class="col-md-12">
                    <h1 class="text-left font-weight-bold display-1">{{item.name}}</h1>
                    <div class="d-flex justify-content-between flex-wrap w-100 align-center">
                        <h2 class="my-0">{{item.description}}</h2>
                        <div class="text-muted font-weight-bold">{{item.createdDate | moment("YYYY-MM-DD")}}</div>
                    </div>
                </div>
            </div>
            <b-card
                    v-for="(item,index) in data.answerList"
                    :key="index"
                    style="box-shadow: none !important;"
                    class="mt-5"
            >
                <div class="d-flex w-100" style="font-size: 24px">
                    <div class="mr-4 text-muted">
                        {{index + 1}} / {{data.answerList.length}}
                    </div>
                    <div>{{stripHtml(item.question)}}</div>
                </div>

                <b-form-group class="my-radio-group" label="Wybierz jedną odpowiedź" v-if="!showAnswer">
                    <b-form-radio-group
                            v-model="userAnswers[index]"
                            :options="item.option"
                            :state="userAnswers[index] != null"
                            stacked
                    >
                        <b-form-invalid-feedback>Proszę wybrać odpowiedź</b-form-invalid-feedback>
                    </b-form-radio-group>
                </b-form-group>
                <div v-if="showAnswer" class="my-4">
                    <div class="font-weight-bold">Twoja odpowiedź: {{userAnswers[index]}}</div>
                    <div class="font-weight-bold" :class="{'text-danger': item.option.find(x => x.proper == true).value != userAnswers[index], 'text-success': item.option.find(x => x.proper == true).value == userAnswers[index]}">Poprawna odpowiedź {{item.option.find(x => x.proper == true).value}}</div>
                </div>
            </b-card>
            <div class="w-100 mt-5" v-if="showAnswer">
                <b-alert show><span class="font-weight-bold">{{correctAnswers}} / {{data.answerList.length}}</span> poprawnych odpowiedzi</b-alert>
            </div>
            <b-button variant="success" class="w-100 py-3 my-5" @click="submit()">Poznaj swój wynik</b-button>
        </div>
    </div>
</template>
<script>
    import {stripHtml} from "../helpers";
    import Request from '../request'
    export default {
        data:() => {
            return {
                item:null,
                items:null,
                submitted:false,
                selected: null,
                showAnswer: false,
                userAnswers: {}
            }
        },
        created(){
            this.getData();
        },
        computed:{
            correctAnswers(){
                var count = 0;
                this.data.answerList.forEach((item,index) => {
                    if(item.option.find(x => x.proper).value == this.userAnswers[index]) count = count +1 ;
                })
                return count;
            },
            data() {
                var item = Object.assign({}, this.item);
                if(item.answerList){
                    item.answerList = item.answerList.map(question_item => {
                        question_item.option = question_item.option.map(answer => {
                            return{
                                text: answer.answer,
                                value: answer.answer,
                                proper: answer.proper
                            }
                        })
                        return question_item
                    })
                }else{
                    item.answerList = [];
                }


                return item;
            }
        },
        methods:{
            stripHtml(data){
                return stripHtml(data);
            },
            submit(){
                this.submitted = true;
                if(Object.keys(this.userAnswers).length == this.data.answerList.length){
                    this.showAnswer = true;
                }else{
                    this.$store.commit('app/ADD_MESSAGE', {text: 'Zaznacz odpowiedzi', type:'info'});
                }
            },
            getData(){
                Request({
                    url:'/api/quizy',
                    method:'get',
                }).then(res => {
                    this.item = res.filter(x => x.id == this.$route.params.id)[0];
                    this.$store.commit('app/SET_BREADCRUMBS', [
                        {
                            text: 'Quizy',
                            to: '/quizy'
                        },
                        {
                            text: this.item.name,
                            to: '/quizy/'+this.item.id
                        }
                    ])
                })
            }
        }
    }
</script>
