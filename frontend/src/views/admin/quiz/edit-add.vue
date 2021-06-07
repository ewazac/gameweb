<template>
    <div>
        <div class="container">
            <div class="account">
                <div class="row">
                    <div class="col-md-3" v-if="isAdmin">
                        <!-- Sidebar -->
                        <admin-sidebar></admin-sidebar>
                        <!-- /#sidebar-wrapper -->
                    </div>
                    <div class="col-md-9" :class="{'col-md-12': !isAdmin}">
                        <div class="w-100">
                            <b-card
                                    title="Quiz"
                                    tag="article"
                                    class="mb-2 w-100">
                                <b-card-text>
                                    <b-form-textarea
                                            id="textarea"
                                            v-model="data.name"
                                            placeholder="Wpisz tytuł"
                                            rows="3"
                                            max-rows="6"
                                            class="my-2"
                                    ></b-form-textarea>
                                    <b-form-textarea
                                            id="textarea"
                                            v-model="data.description"
                                            placeholder="Wpisz krótki opis"
                                            rows="3"
                                            class="my-2"
                                            max-rows="6"
                                    ></b-form-textarea>
                                    <b-form-checkbox
                                            class="my-4"
                                            v-model="data.sendMail"
                                    >
                                        <span class="text-white">Newsletter</span>
                                    </b-form-checkbox>
                                    <ckeditor :editor="editor" v-model="data.body" :config="editorConfig"></ckeditor>
                                    <b-button class="mt-3" @click="data.answerList.push({question: '', option:[]})" variant="outline-success">Dodaj pytanie</b-button>
                                    <div :key="index_question" class="w-100" v-for="(item,index_question) in data.answerList">
                                        <b-card class="mb-2 h-100">
                                            <b-form-input v-model="item.question" placeholder="Wpisz treść pytania"></b-form-input>
                                            <div v-for="(answer, index) in item.option" :key="index">
                                                <div class="row my-3">
                                                    <div class="col-md-1">
                                                        <b-form-checkbox @change="($event)? unselectAnswers(index) : null" v-model="answer.proper"></b-form-checkbox>
                                                    </div>
                                                    <div class="col-md-10">
                                                        <b-form-input v-model="answer.answer" placeholder="Wpisz treść odpowiedzi"></b-form-input>
                                                    </div>
                                                    <div class="col-md-1 d-flex justify-center align-center">
                                                        <b-icon @click="item.option.splice(index, 1)" style="font-size: 20px; cursor: pointer" icon="trash-2" variant="danger"></b-icon>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="my-3">
                                                <b-button @click="item.option.push({value: '', proper: false})" variant="outline-success">Dodaj odpowiedź</b-button>
                                                <b-button class="ml-3" @click="data.answerList.splice(index_question, 1)" variant="outline-danger">usuń pytanie</b-button>
                                            </div>
                                            <!--<b-button class="games__button" @click="handleDetails(item.appId)"> Zobacz więcej </b-button>-->
                                        </b-card>
                                    </div>
                                    <div class="w-100 d-flex justify-content-end mt-5">
                                        <b-button @click="saveElement()" variant="outline-success">Zapisz</b-button>
                                    </div>
                                </b-card-text>
                            </b-card>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Quiz from '../../../models/Quiz';
    import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

    export default {
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data:() => {
            return{
                data: new Quiz(),
                editor: ClassicEditor,
                editorData: '<p>Content of the editor.</p>',
                editorConfig: {
                    height: 500
                    // The configuration of the editor.
                }
            }
        },
        components:{

        },
        mounted(){
            if(this.$route.params.id && this.$route.params.id != 'create'){
                this.data.getElementById(this.$route.params.id).then(res => {
                    this.data = new Quiz(res);
                });
            }
        },
        methods:{
            unselectAnswers(seledted_id){
                this.data.answerList.forEach((item, index) => {
                    if(index != seledted_id){
                        this.data.answerList[index].proper = false;
                    }
                })
            },
            saveElement(){
                this.data.save().then(() => {
                    this.$router.go(-1);
                })
            },
        },
        computed:{

            isAdmin(){
                return this.$store.getters['auth/isAdmin'];
            }
        },
    }
</script>
<style lang="scss">
    .ck-editor__editable {
        min-height: 500px;
    }
</style>
