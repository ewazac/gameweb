<template>
    <div class="homepage-header">
        <div class="w-100 py-2 primary">
            <div class="text black-text">{{text}}</div>
        </div>
    </div>
</template>
<script>
    export default {
        props:['text']
    }
</script>
<style lang="scss">
    .homepage-header{
        .primary{
            background-color: #5E9883;
        }
        .text{
            margin-left: 30px;
            letter-spacing: 2px;
            font-weight: bold;
            font-size: 50px;
            text-transform: uppercase;
        }
    }
</style>