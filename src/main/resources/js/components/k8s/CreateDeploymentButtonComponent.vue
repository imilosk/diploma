<template>
    <div>
        <button type="button" class="my-button createDeploymentButton" @click="openModal">Create new deployment</button>
    </div>
</template>

<script>
export default {
    data () {
        return {

        }
    },
    methods: {
        openModal: function () {
            this.$emit('openModal')
        }
    }
}
</script>

<style scoped>

</style>