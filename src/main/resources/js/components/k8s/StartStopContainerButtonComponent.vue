<template>
    <div>
        <button type="button" @click="toggleOnOff" v-model="buttonText">{{ buttonText }}</button>
    </div>
</template>

<script>
export default {
    data () {
        return {
            startContainerText: 'Start container',
            stopContainerText: 'Stop container',
            isStarted:  false,
            buttonText: 'Start container',
        }
    },
    methods: {
        setButtonText: function() {
            this.buttonText = this.isStarted ? this.stopContainerText : this.startContainerText;
        },
        toggleOnOff: function () {
            this.isStarted = !this.isStarted;
            this.setButtonText();
        }
    }
}
</script>

<style scoped>

</style>