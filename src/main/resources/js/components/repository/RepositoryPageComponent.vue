<template>
  <div class="py-12 align-middle inline-block min-w-full sm:px-6 lg:px-24">
    <router-link to="/uploadImage">
      <button
          type="submit"
          class="inline-flex py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-green-600"
      >
        Upload a new image
      </button>
    </router-link>
    <div class="flex justify-between mt-1 mb-10">
      <div class="search-box">
        <label class=" block text-sm font-medium text-gray-700">
          Search
        </label>
        <input
            id="exp"
            class="focus:ring-indigo-500 focus:border-indigo-500 block shadow-sm sm:text-sm border-gray-300 rounded-md"
            name="exp"
            placeholder='Enter name'
            type="text"
            v-model="inputText"
        />
      </div>
      <div class="filters flex space-x-4">
        <dropdown-menu-component/>
        <dropdown-menu-component/>
        <dropdown-menu-component/>
      </div>
    </div>

    <div class="flex flex-col">
      <div class="-my-4 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="">
          <div class="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Name
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Tag
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Image id
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Size
                </th>
                <th class="relative px-6 py-3"
                    scope="col">
                  <span class="sr-only">Edit</span>
                </th>
              </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="dockerImage in filteredList" :key="dockerImage.imageId">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ dockerImage.name }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ dockerImage.tag }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ dockerImage.imageId }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ dockerImage.size }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <div class="text-red-600 hover:text-red-900 cursor-pointer" :id="dockerImage.imageId"
                       v-on:click="deleteImage">DELETE
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DropdownMenu from "./DropdownMenuComponent";
import DropdownMenuComponent from "./DropdownMenuComponent";

export default {
  components: {DropdownMenuComponent, DropdownMenu},
  data() {
    return {
      dockerImages: [],
      inputText: ''
    }
  },
  methods: {
    deleteImage: function (e) {
      const that = this;

      const imageId = e.target.id;
      axios.delete('/app/images/' + imageId).then(function (response) {
        that.dockerImages = that.dockerImages.filter(function (element) {
          return element.imageId !== imageId;
        });

        that.$toastr('add', {
          title: 'Message',
          msg: 'The image was deleted successfully',
          timeout: 10000,
          type: 'success',
          position: 'toast-top-right',
          closeOnHover: false,
          clickClose: true
        });
      });
    }
  },
  computed: {
    filteredList: function () {
      return this.dockerImages.filter(image => {
        return image.name.toLowerCase().includes(this.inputText.toLowerCase())
      });
    }
  },
  mounted() {
    let that = this;
    axios.get('/app/images').then(function (response) {
      that.dockerImages = response.data;
    }).catch(function (error) {
      console.log(error.response.data.errors[0].defaultMessage);
    });
  }
}
</script>