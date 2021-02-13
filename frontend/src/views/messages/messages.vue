<template>
    <div class="messages">
        <div>
            <b-alert :key="index" show :variant="message.type" v-for="(message, index) in messages">{{message.text}}</b-alert>
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