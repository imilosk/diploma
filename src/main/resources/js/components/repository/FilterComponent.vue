<template>
    <div class="dropdown-component" :data-value="value" :data-list="list">
        <div class="selector" @click="toggle()">
            <div class="label">
                <span>{{ value }}</span>
            </div>
            <div class="arrow" :class="{ expanded : visible }"></div>
            <div :class="{ hidden : !visible, visible }">
                <ul>
                    <li :class="{ current : item === value }" v-for="item in list" @click="select(item)">{{ item }}</li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {
            value: 'Select a Fruit',
            list: ["Orange","Apple","Kiwi", "Lemon", "Pineapple"],
            visible: false
        }
    },
    methods: {
        toggle() {
            this.visible = !this.visible;
        },
        select(option) {
            this.value = option;
        }
    }
}
</script>

<style scoped>

</style>