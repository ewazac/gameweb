<template>
    <div class="messages">
        <div @click="removeMessage(message.id)" v-for="(message, index) in messages" :key="index">
            <b-alert show :variant="message.type" >
                <div class="d-flex justify-content-between w-100">
                    {{message.text}}
                    <div class="ml-2" style="cursor: pointer">
                        <p class="h4 mb-0"><b-icon icon="x"></b-icon></p>
                    </div>
                </div>

            </b-alert>
        </div>
    </div>
</template>
<script>
    export default {
        data:() => {
            return{
                test: '',
                test_2: ''
            }
        },
        computed:{
            messages(){
                return this.$store.getters['app/messages'];
            },
        },
        methods:{
            removeMessage(id){
               this.$store.commit('app/REMOVE_MESSAGE', id);
            }
        }
    }
</script>
<style lang="scss">
    .messages{
        z-index: 999999;
        position: fixed;
        bottom: 10vh;
        width: 50%;
        left: 25%;
    }
</style>