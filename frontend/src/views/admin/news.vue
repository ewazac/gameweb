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
                                title="Newsy"
                                tag="article"
                                class="mb-2 w-100"
                        >
                            <b-card-text>
                                <b-table striped hover :items="items">
                                    <template #cell(id)="{ rowSelected, item}">
                                        <div v-chunk class="text--primary">
                                            {{item.id}}
                                        </div>
                                    </template>
                                    <template #cell(image)="{ rowSelected, item}">
                                        <div>
                                            <img style="max-height: 70px; max-width: 70px" v-if="item.image" :src="'data:image/jpeg;base64,'+item.image.data">
                                            <img style="max-height: 70px; max-width: 70px" v-else src="../../assets/default.png">
                                        </div>
                                    </template>
                                    <template #cell(actions)="{ item }">
                                        <div>
                                            <b-button @click="deleteElement(item.id)" class="mr-2" variant="danger">Usuń</b-button>
                                            <b-button :to="'/admin/news/'+item.id" variant="outline-primary">Edytuj</b-button>
                                        </div>
                                    </template>
                                </b-table>
                                <b-button :to="'/admin/news/create'" variant="light">Dodaj nowy</b-button>

                            </b-card-text>
                        </b-card>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Request from '../../request';
    export default {
        computed:{
            isAdmin(){
                return this.$store.getters['auth/isAdmin'];
            },
            items(){
                return this.data.map((item) => {
                    delete item.body;
                    item.actions = '';
                    return item;
                });
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
                        url: '/news/'+id
                    }).then(() => {
                        this.getData();
                    })
                })
            },
            getData(){
                Request({
                    method: 'get',
                    url: '/news',
                }).then(res => {
                    this.data = res;
                })
            }
        },
    };
</script>

