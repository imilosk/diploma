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
                         for="image_name_id">
                    Image name
                  </label>
                  <div class="mt-1 flex rounded-md shadow-sm">
                    <input id="image_name_id"
                           class="focus:ring-indigo-500 focus:border-indigo-500 flex-1 block w-full rounded-none rounded-r-md sm:text-sm border-gray-300"
                           name="image_name"
                           placeholder="node_js"
                           type="text"
                           v-model="imageName"
                    />
                  </div>
                  <label class="block text-sm font-medium text-gray-700"
                         for="image_tag_id">
                    Image tag
                  </label>
                  <div class="mt-1 flex rounded-md shadow-sm">
                    <input id="image_tag_id"
                           class="focus:ring-indigo-500 focus:border-indigo-500 flex-1 block w-full rounded-none rounded-r-md sm:text-sm border-gray-300"
                           name="image_tag"
                           placeholder="v1"
                           type="text"
                           v-model="imageTag"
                    />
                  </div>
                  <label class="block text-sm font-medium text-gray-700"
                         for="deployment_name_id">
                    Deployment name
                  </label>
                  <input
                      id="deployment_name_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="deployment_name"
                      placeholder='my_node_project'
                      type="text"
                      v-model="deploymentName"
                  />
                  <label class="block text-sm font-medium text-gray-700"
                         for="container_port_id">
                    Container port
                  </label>
                  <input
                      id="container_port_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="container_port"
                      placeholder='1337'
                      type="number"
                      v-model="containerPort"
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
                  <button
                      class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-green-600"
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
export default {
  data() {
    return {
      imageName: '',
      imageTag: '',
      deploymentName: '',
      containerPort: '',
      servicePort: '',
    }
  },
  methods: {
    submitButtonClicked: function (e) {
      e.preventDefault();

      let bodyFormData = new FormData();
      bodyFormData.append('imageName', this.imageName);
      bodyFormData.append('imageTag', this.imageTag);
      bodyFormData.append('deploymentName', this.deploymentName);
      bodyFormData.append('containerPort', this.containerPort);
      bodyFormData.append('servicePort', this.servicePort);

      let that = this;
      axios.post('/app/services', bodyFormData).then(function (response) {
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
