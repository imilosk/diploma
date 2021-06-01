<template>
  <div :data-list="list" :data-value="value" class="dropdown-component">
    <div class="selector" @click="toggle()">
      <div class="label">
        <span>{{ value }}</span>
      </div>
      <div :class="{ expanded : visible }" class="arrow"></div>
      <div :class="{ hidden : !visible, visible }">
        <ul>
          <li v-for="item in list" :class="{ current : item === value }" @click="select(item)">{{ item }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: 'Select a Fruit',
      list: ["Orange", "Apple", "Kiwi", "Lemon", "Pineapple"],
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