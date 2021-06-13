<template>
  <div>
    <nav-bar-component/>
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
                    <span class="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500 text-sm">
                      username/
                    </span>
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
                  <input
                      id="image_tag_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="image_tag"
                      placeholder='v1'
                      type="text"
                      v-model="imageTag"
                  />
                  <label class="block text-sm font-medium text-gray-700"
                         for="additional_args_id">
                    Additional arguments
                  </label>
                  <input
                      id="additional_args_id"
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                      name="additional_args"
                      placeholder='--target dev-env'
                      type="text"
                      v-model="additionalArgs"
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

      <div class="mt-5 md:mt-0 md:col-span-1">
        <form action="#"
              method="POST">
          <div class="shadow sm:rounded-md sm:overflow-hidden">
            <div class="px-4 py-5 bg-white space-y-6 sm:p-6">
              <file-upload-component ref="fileUploadComponent"/>
            </div>
          </div>

        </form>
      </div>

    </div>
  </div>
</template>

<script>
import NavBarComponent from "../../dashboard/NavBarComponent";
import io from "socket.io-client";
let socket = io.connect('http://localhost:9092/chat')

export default {
  components: {
    NavBarComponent,
  },
  data() {
    return {
      imageName: '',
      imageTag: '',
      additionalArgs: '',
    }
  },
  methods: {
    submitButtonClicked: function (e) {
      e.preventDefault();

      let bodyFormData = new FormData();
      bodyFormData.append('imageName', this.imageName);
      bodyFormData.append('imageTag', this.imageTag);
      bodyFormData.append('additionalArgs', this.additionalArgs);
      if (this.$refs.fileUploadComponent.filelist[0]) {
        bodyFormData.append('file', this.$refs.fileUploadComponent.filelist[0]);
      }

      let that = this;
      axios.post('/app/uploadImage', bodyFormData).then(function (response) {
        that.$toastr('add', {
          title: 'Message',
          msg: 'Your image was built successfully',
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
    },
  },

  created() {
    console.log('mounted');

    socket.on('connect', function () {
      console.log('connected');
    });

    socket.on('chat', function (data) {
      console.log(data);
    });

  }

}
</script>
