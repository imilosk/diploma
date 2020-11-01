<template>
    <div class="createDeploymentModal" v-if="isVisible" @close="closeModal">
        <div class="modal">
            <div class="modal-content">
                <h1>
                    Hi!
                </h1>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                isVisible: false
            }
        },
        methods: {
            openModal() {
                this.isVisible = true;
            },
            closeModal() {
                this.isVisible = false;
            }
        },
        mounted() {
            let self = this;
            window.addEventListener('keyup', function(event) {
                // If  ESC key was pressed...
                if (event.code === 'Escape') {
                    // try close your dialog
                    self.closeModal();
                }
            });
        },
    }
</script>

<style scoped>

</style>