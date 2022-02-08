<template>
  <div>
    <dropdown-arrow/>
    <select class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md">
      <option>Choose an image</option>
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