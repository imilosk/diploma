<template>
        <div id="--page-k8s">
                <p class="page-title">Kubernetes</p>
                <div class="button-container">
                        <create-deployment-button-component @openModal="openModal"></create-deployment-button-component>
                </div>
                <container-table-component></container-table-component>
                <create-deployment-modal-component ref="modal"></create-deployment-modal-component>
        </div>
</template>

<script>
export default {
        data () {
            return {

            }
        },
        methods: {
            openModal() {
                this.$refs.modal.openModal();
            }
        }
}
</script>