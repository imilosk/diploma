<template>
  <div class="py-12 align-middle inline-block min-w-full sm:px-6 lg:px-24">
    <router-link to="/createService">
      <button
          type="submit"
          class="inline-flex py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-white-600"
      >
        Create a new deployment
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
                  Image name
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Image tag
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Container port
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                    scope="col">
                  Service port
                </th>
              </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="deployment in filteredList" :key="deployment.name">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ deployment.name }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ deployment.imageName }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ deployment.imageTag }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ deployment.containerPort }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ deployment.servicePort }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <div class="text-blue-600 hover:text-blue-900 cursor-pointer" :id="deployment.name">Edit</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <div class="text-red-600 hover:text-red-900 cursor-pointer" :id="deployment.name"
                       v-on:click="deleteDeployment">DELETE</div>
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

export default {
  data() {
    return {
      deployments: [],
      inputText: ''
    }
  },
  computed: {
    filteredList: function () {
      return this.deployments.filter(deployment => {
        return deployment.name.toLowerCase().includes(this.inputText.toLowerCase())
      });
    }
  },
  mounted() {
    let that = this;
    axios.get('/app/deployments').then(function (response) {
      that.deployments = response.data;
    }).catch(function (error) {
      console.log(error.response.data.errors[0].defaultMessage);
    });
  },
  methods: {
    deleteDeployment: function (e) {
      const that = this;

      const deploymentName = e.target.id;
      axios.delete('/app/deployments/' + deploymentName).then(function (response) {
        that.deployments = that.deployments.filter(function (element) {
          return element.name !== deploymentName;
        });

        that.$toastr('add', {
          title: 'Message',
          msg: 'The deployment was deleted successfully',
          timeout: 10000,
          type: 'success',
          position: 'toast-top-right',
          closeOnHover: false,
          clickClose: true
        });
      });
    }
  }
}
</script>