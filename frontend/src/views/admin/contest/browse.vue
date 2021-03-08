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
                                title="Konkursy"
                                tag="article"
                                class="mb-2 w-100 text-white"
                        >
                            <b-card-text>
                                <b-table :fields="fields" striped hover :items="data" class="table-white-text">
                                    <template #cell(actions)="{ item }">
                                        <div>
                                            <b-button @click="deleteElement(item.group_id)" class="mr-2" variant="outline-danger">Usuń</b-button>
                                            <b-button class="ma-auto" @click="addToPromotion(item)" variant="outline-success">Dodaj do polecanych</b-button>

                                        </div>
                                    </template>
                                </b-table>
                                <div class="w-100 d-flex justify-content-end">
                                    <b-button class="ma-auto" :to="'/admin/contest/create'" variant="outline-light">Dodaj nowy</b-button>
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
            }
        },
        data() {
            return {
                fields:[{
                    key: 'group_id',
                    label: 'ID grupy'
                },{
                    key: 'count',
                    label: 'Ilość gier'
                },
                    {
                        key: 'actions',
                        label: 'Akcje'
                    }],
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
            addToPromotion(item){
                Request({
                    url:'/games/recommended?groupId='+item.group_id,
                    method:'patch'
                }).then(() => {
                })
            },
            deleteElement(id){
                this.$confirm('Usuwanie elementu', 'Czy na pewno chcesz usunąć ten element', null).then(() => {
                    Request({
                        method: 'DELETE',
                        url: '/games/'+id
                    }).then(() => {
                        this.getData();
                    })
                })
            },
            getData(){
                Request({
                    url: '/games',
                    method: 'get'
                }).then(res => {
                    this.data = [];
                    for(var i in res){
                        this.data.push({
                            group_id: i,
                            count: res[i].length
                        })
                    }
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
