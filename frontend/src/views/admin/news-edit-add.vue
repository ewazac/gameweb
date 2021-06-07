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
                                            v-if="!data.id"
                                            @change="fileInput($event)"
                                            class="my-2"
                                            placeholder="Dodaj obrazek"
                                            drop-placeholder="Przeciągnij plik tutaj"
                                    ></b-form-file>
                                    <b-form-checkbox
                                            class="my-4"
                                            v-model="data.sendMail"
                                    >
                                        <span class="text-white">Newsletter</span>
                                    </b-form-checkbox>
                                    <ckeditor :editor="editor" v-model="data.body" :config="editorConfig"></ckeditor>
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
    import News from '../../models/news';
    import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

    export default {
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data:() => {
            return{
                data: new News(),
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
