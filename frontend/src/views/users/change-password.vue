<template>
    <div></div>
</template>
<script>
    export default {
        mounted(){
            this.$router.push('/reset-password?token='+this.$route.query.code);
        }
    }
</script>