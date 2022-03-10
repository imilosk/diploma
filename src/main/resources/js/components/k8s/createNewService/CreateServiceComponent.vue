<template>
  <div>
    <div class="md:grid md:grid-cols-2 md:gap-6">
      <div class="mt-5 md:mt-0 md:col-span-1">
        <form action="#"
              method="POST">
          <div class="shadow sm:rounded-md sm:overflow-hidden">
            <div class="px-4 py-5 bg-white space-y-6 sm:p-6">
              <div class="grid grid-cols-3 gap-6">
                <div class="col-span-3 sm:col-span-2">
                  <label class="block text-sm font-medium text-gray-700"
                         for="image_id">
                    Image
                  </label>

                  <div>
                    <dropdown-arrow/>
                    <select class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md">
                      <option>Choose an image</option>
                      <option v-for="color in this.imageIds">{{ color }}</option>
                    </select>
                  </div>

                  <label class="block text-sm font-medium text-gray-700"
                         for="deployment_name_id">
                    Service name
                  </label>
                  <input
                      id="deployment_name_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="deployment_name"
                      placeholder='my_node_project'
                      type="text"
                      v-model="serviceName"
                  />

                  <label class="block text-sm font-medium text-gray-700"
                         for="replicas_id">
                    Replicas
                  </label>
                  <input
                      id="replicas_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="replicas"
                      placeholder='3'
                      type="number"
                      v-model="replicas"
                  />

                  <label class="block text-sm font-medium text-gray-700"
                         for="service_port_id">
                    Service port
                  </label>
                  <input
                      id="service_port_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="container_port"
                      placeholder='8181'
                      type="number"
                      v-model="servicePort"
                  />

                  <label class="block text-sm font-medium text-gray-700"
                         for="service_port_id">
                    Domain name
                  </label>
                  <input
                      id="domain"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="domain"
                      placeholder='www.test.org'
                      type="text"
                      v-model="domain"
                  />

                  <button
                      class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-white-600"
                      type="submit"
                      v-on:click="submitButtonClicked"
                  >
                    Submit
                  </button>
                  <!--                  <button-->
                  <!--                      class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-green-600"-->
                  <!--                      disabled-->
                  <!--                      type="submit"-->
                  <!--                  >-->
                  <!--                    <svg-->
                  <!--                        class="animate-spin h-4 w-4 rounded-full bg-transparent border-2 border-transparent border-opacity-50 mr-2"-->
                  <!--                        style="border-right-color: white; border-top-color: white;"-->
                  <!--                        viewBox="0 0 24 24"></svg>-->
                  <!--                    Loading-->
                  <!--                  </button>-->
                </div>
              </div>
            </div>
          </div>

        </form>
      </div>

    </div>
  </div>
</template>

<script>
import DropdownArrow from "../../svgs/DropdownArrow";

export default {

  components: DropdownArrow,
  data() {
    return {
      selectedImageId: '',
      serviceName: '',
      servicePort: '',
      replicas: '',
      domain: '',
      imageIds: [
        'node_js:v1',
        'php:v1',
        'ruby:latest',
        'elasticsearch:7.16.3',
        'mongodb:5.0.5-focal'
      ],
    }
  },
  methods: {
    submitButtonClicked: function (e) {
      e.preventDefault();

      let bodyFormData = new FormData();
      bodyFormData.append('serviceName', this.serviceName);
      bodyFormData.append('servicePort', this.servicePort);
      bodyFormData.append('replicas', this.replicas);

      let that = this;
      axios.post('/app/deployments', bodyFormData).then(function (response) {
        that.$toastr('add', {
          title: 'Message',
          msg: 'Your service was created successfully',
          timeout: 10000,
          type: 'success',
          position: 'toast-top-right',
          closeOnHover: false,
          clickClose: true
        });
      }).catch(function (error) {
        console.log(error.response.data.errors[0].defaultMessage);
        that.$toastr('add', {
          title: 'Message',
          msg: error.response.data.errors[0].defaultMessage,
          timeout: 10000,
          type: 'error',
          position: 'toast-top-right',
          closeOnHover: false,
          clickClose: true
        });
      });

    }
  }
}
</script>
