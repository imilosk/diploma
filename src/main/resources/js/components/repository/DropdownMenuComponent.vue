<template>
  <div>
    <dropdown-arrow/>
    <select class="border border-gray-300 rounded-full text-gray-600 h-10 pl-5 pr-10 bg-white hover:border-gray-400 focus:outline-none appearance-none">
      <option>Choose a color</option>
      <option v-for="color in colors">{{ color }}</option>
    </select>
  </div>
</template>

<script>
import DropdownArrow from "../svgs/DropdownArrow";
const colors = [
  'Red',
  'Blue',
  'Orange',
]
export default {
  components: {DropdownArrow},
  data() {
    return {
      colors
    };
  }
}
</script>