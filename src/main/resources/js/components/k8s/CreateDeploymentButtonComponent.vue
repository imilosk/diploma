<template>
  <div>
    <button class="my-button createDeploymentButton" type="button" @click="openModal">Create new deployment</button>
  </div>
</template>

<script>
export default {
  data() {
    return {}
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