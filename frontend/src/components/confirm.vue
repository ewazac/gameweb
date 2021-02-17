<template>
    <div>
        <b-modal
                cancel-title="Nie"
                cancel-variant="info"
                ok-title="Tak"
                ok-variant="danger"
                @hidden="cancel"
                @ok="ok" id="modal-1" :title="title">
            <p class="my-4">{{message}}</p>
        </b-modal>    </div>
</template>
<script>
    export default {
        props:['title', 'message', 'buttons'],
        mounted() {
            this.$bvModal.show('modal-1')
        },
        methods:{
            cancel(){
                this.$emit('cancel');
                this.$bvModal.hide('modal-1');
            },

            ok(){
                this.$emit('confirm');
                this.$bvModal.hide('modal-1');
            }
        }
    }
</script>