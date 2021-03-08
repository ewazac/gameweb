<template>
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
                                title="Quizy"
                                tag="article"
                                class="mb-2 w-100 text-white"
                        >
                            <b-card-text>
                                <div class="table-responsive">
                                    <b-table striped hover :items="items" class="table-white-text">
                                        <template #cell(id)="{ rowSelected, item}">
                                            <div v-chunk class="text--primary">
                                                {{item.id}}
                                            </div>
                                        </template>
                                        <template #cell(answerList)="{ item}">
                                            <div>
                                                Ilość odpowiedzi <span v-if="item.answerList">{{item.answerList.length}}</span><span v-else>0</span>
                                            </div>
                                        </template>
                                        <template #cell(actions)="{ item }">
                                            <div>
                                                <b-button @click="deleteElement(item.id)" class="mr-2" variant="outline-danger">Usuń</b-button>
                                                <b-button :to="'/admin/quiz/'+item.id" variant="outline-primary">Edytuj</b-button>
                                            </div>
                                        </template>
                                    </b-table>
                                </div>

                                <div class="w-100 d-flex justify-content-end">
                                    <b-button class="ma-auto" :to="'/admin/quiz/create'" variant="outline-light">Dodaj nowy</b-button>
                                </div>


                            </b-card-text>
                        </b-card>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Request from '../../../request';
    export default {
        computed:{
            isAdmin(){
                return this.$store.getters['auth/isAdmin'];
            },
            items(){
                return this.data.map((item) => {
                    delete item.body;
                    delete item.description;
                    item.actions = '';
                    return item;
                }).reverse();
            }
        },
        data() {
            return {
                currentUser: JSON.parse(localStorage.getItem("user")),
                data: []
            };
        },
        beforeCreate: function () {
            document.body.className = "app__body";
        },
        mounted(){
            this.getData();
        },
        methods: {
            deleteElement(id){
                this.$confirm('Usuwanie elementu', 'Czy na pewno chcesz usunąć ten element', null).then(() => {
                    Request({
                        method: 'DELETE',
                        url: '/api/quizy/'+id
                    }).then(() => {
                        this.getData();
                    })
                })
            },
            getData(){
                Request({
                    method: 'get',
                    url: '/api/quizy',
                }).then(res => {
                    this.data = res;
                })
            }
        },
    };
</script>

<style lang="scss">
    .table-white-text{
        td{
            color: white !important;
        }
        color: white !important;
        a{
            color: white !important;
        }
    }
</style>
