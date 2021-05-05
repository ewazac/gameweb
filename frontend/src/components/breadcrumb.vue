<template>
    <div class="container">
        <b-breadcrumb class="my-breadcrumbs text-muted" :items="breadcrumbs"></b-breadcrumb>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                items: [
                    {
                        text: 'Strona główna',
                        href: '/'
                    }
                ]
            }
        },
        computed:{
            breadcrumbs(){
                return this.$store.getters['app/get_breadcrumbs'];
            }
        }
    }
</script>
<style lang="scss">
    .my-breadcrumbs{
        opacity: 0.5;
        background-color: transparent !important;
        padding: 20px 0px !important;
        color: white !important;
        a, span{
            font-size: 14px !important;
            color: white !important;
        }
        li{
            &:before{
                font-size: 14px !important;
                color: white !important;
            }
        }
    }
</style>