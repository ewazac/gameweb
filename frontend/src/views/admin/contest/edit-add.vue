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
                                    title="Konkurs"
                                    tag="article"
                                    class="mb-2 w-100">
                                <b-card-text>
                                    <b-form-input v-model="filters.term" placeholder="Wpisz nazwę gry"></b-form-input>

                                    <div class="table-responsive">
                                        <b-table :fields="fields" striped hover :items="paginated" class="table-white-text">
                                            <template #cell(akcje)="{ item }">
                                                <div>
                                                    <b-button @click="addGame(item)" class="mr-2" variant="outline-danger">Dodaj</b-button>
                                                </div>
                                            </template>
                                        </b-table>
                                    </div>
                                    <b-form-checkbox
                                            class="my-4"
                                            v-model="sendMail"
                                    >
                                        <span class="text-white">Newsletter</span>
                                    </b-form-checkbox>
                                    <b-pagination
                                            v-model="filters.page"
                                            :total-rows="filters.total_rows"
                                            :per-page="filters.max" first-text="First" prev-text="Prev" next-text="Next" last-text="Last">
                                    </b-pagination>
                                    <hr>
                                    <div class="font-weight-bold text-white mt-5 mb-5">Gry w tym konkursie</div>
                                    <div class="table-responsive">
                                        <b-table :fields="fields" striped hover :items="data" class="table-white-text">
                                            <template #cell(akcje)="{ item }">
                                                <div>
                                                    <b-button @click="deleteGame(item)" class="mr-2" variant="outline-danger">Usuń</b-button>
                                                </div>
                                            </template>
                                        </b-table>
                                    </div>
                                    <b-button @click="save()" class="mt-5 mb-5" variant="outline-success">Zapisz konkurs</b-button>
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
    import axios from 'axios'
    import {paginate} from "../../../helpers";
    import Request from '../../../request';
    export default {
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        data:() => {
            return{
                sendMail: false,
                filters:{
                    term:'',
                    max: 10,
                    page: 1,
                    total_rows: 10
                },
                allGames:null,
                data:[],
                fields: [
                    { key: 'appId', sortable: true },
                    { key: 'title', sortable: true },
                    { key: 'akcje', sortable: false}
                ],
            }
        },
        computed:{
            filteredGames(){
                if(!this.allGames) return [];
                var filterd = this.allGames.filter(x => {
                    var check = false
                    if(this.filters.term){
                        if( x.title.toLowerCase().includes(this.filters.term.toLowerCase())) check = true;
                    }else{
                        check = true;
                    }
                    if(this.data.includes(x)) check = false;
                    return check;
                })
                return filterd;
            },
            paginated(){
                return paginate(this.filteredGames, this.filters.max, this.filters.page);
            },
            isAdmin(){
                return this.$store.getters['auth/isAdmin'];
            }
        },
        watch:{
            filteredGames:{
                deep: true,
                handler: function () {
                    this.$set(this.filters, 'total_rows', this.filteredGames.length);
                }
            }
        },
        mounted(){
           this.getAllGames();
        },
        methods:{
            deleteGame(item){
                var index = this.data.findIndex(x => x.appId == item.appId);
                if(index != -1) this.data.splice(index, 1);
            },
            save(){
                if(this.data.length < 2) this.$store.commit('app/ADD_MESSAGE', {text: 'Konkurs musi mieć przynajmniej 2 gry', type: 'danger'})
                var to_send = Object.assign([], this.data);
                this.data.forEach(item => {
                    if(this.sendMail){
                        item.sendMail = true;
                    }
                })
                Request({
                    url:'/games',
                    method: 'post',
                    data: this.data
                }).then(() => {
                   this.$router.push('/admin/contests');
                })
            },
            addGame(game){
                this.data.push(game);
            },
            getAllGames(){
                axios.get('https://gameweb.projektstudencki.pl/apigames/api/apps/?category=GAME')
                    .then(({data}) => {
                        this.allGames = data.results;
                    })
            }
        },
    }
</script>

