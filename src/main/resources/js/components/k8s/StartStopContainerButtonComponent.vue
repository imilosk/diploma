<template>
  <div>
    <button v-model="buttonText" class="my-button" type="button" v-bind:class="buttonBackgroundClass"
            @click="toggleOnOff">{{ buttonText }}
    </button>
  </div>
</template>

<script>
export default {
  data() {
    const startContainerText = 'Start container';
    const buttonStartBackgroundClass = 'start-container-button';
    return {
      startContainerText: startContainerText,
      stopContainerText: 'Stop container',
      isStarted: false,
      buttonText: startContainerText,
      buttonBackgroundClass: buttonStartBackgroundClass,
      buttonStartBackgroundClass: buttonStartBackgroundClass,
      buttonStopBackgroundClass: 'stop-container-button',
    }
  },
  methods: {
    setButtonText: function () {
      this.buttonText = this.isStarted ? this.stopContainerText : this.startContainerText;
    },
    setButtonBackgroundColor: function () {
      this.buttonBackgroundClass = this.isStarted ? this.buttonStopBackgroundClass : this.buttonStartBackgroundClass;
    },
    toggleOnOff: function () {
      this.isStarted = !this.isStarted;
      this.setButtonText();
      this.setButtonBackgroundColor();
    }
  }
}
</script>

<style scoped>

</style>