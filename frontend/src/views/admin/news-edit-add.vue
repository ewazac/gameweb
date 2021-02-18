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
                                    title="Newsy"
                                    tag="article"
                                    class="mb-2 w-100">
                                <b-card-text>
                                    <b-form-textarea
                                            id="textarea"
                                            v-model="data.title"
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
                                    <b-form-file
                                            @change="fileInput($event)"
                                            class="my-2"
                                            placeholder="Dodaj obrazek"
                                            drop-placeholder="Przeciągnij plik tutaj"
                                    ></b-form-file>
                                    <b-form-textarea
                                            id="textarea"
                                            v-model="data.body"
                                            placeholder="Wpisz treść"
                                            rows="3"
                                            class="my-2"
                                            max-rows="6"
                                    ></b-form-textarea>
                                    <editor-content :editor="editor" />

                                    <b-button @click="saveElement()" variant="outline-success">Zapisz</b-button>

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
    import News from '../../models/news';
    import { Editor, EditorContent } from 'tiptap'

    export default {
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data:() => {
            return{
                data: new News(),
                editor: null
            }
        },
        components:{
            EditorContent,

        },
        mounted(){
            this.editor = new Editor({
                content: '<p>This is just a boring paragraph</p>',
            })
            if(this.$route.params.id){
                this.data.getNewsById(this.$route.params.id).then(res => {
                    this.data = new News(res);
                });
            }
        },
        methods:{
            fileInput(event){
                this.data.image = event.target.files[0]
            },
            saveElement(){
                console.log(this.data);
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
