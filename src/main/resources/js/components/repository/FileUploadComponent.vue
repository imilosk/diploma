<template>
    <vue-dropzone id="dropzone" :options="dropzoneOptions" :useCustomSlot=true
                  v-on:vdropzone-success="onSuccessUpload"
                  v-on:vdropzone-file-added="onBeforeUpload"
                  v-on:vdropzone-error="onFailUpload"
    >

        <div class="dropzone-custom-content">
            <h3 class="dropzone-custom-title">Drag and drop your Dockerfile here!</h3>
            <div class="subtitle">...or click to select one from your computer</div>
        </div>
    </vue-dropzone>
</template>

<script>
    export default {
        data () {
            return {
                dropzoneOptions: {
                    url: "/app/uploadDockerImage",
                    createImageThumbnails: false,
                    uploadMultiple: false,
                    thumbnailWidth: 60,
                    thumbnailHeight: 60
                },
            }
        },
        methods: {
            onBeforeUpload(file) {
              this.$toastr('add', {
                title: 'Message',
                msg: 'Your image is being build',
                timeout: 5000,
                type: 'info',
                position: 'toast-top-right',
                closeOnHover: true,
                clickClose: false
              });
            },
            onSuccessUpload(file, response) {
              this.$toastr('add', {
                title: 'Message',
                msg: 'Your image was build successfully',
                timeout: 5000,
                type: 'success',
                position: 'toast-top-right',
                closeOnHover: true,
                clickClose: false
              });
            },
          onFailUpload(file, message, xhr) {
            this.$toastr('add', {
              title: 'Message',
              msg: xhr.responseText,
              timeout: 5000,
              type: 'error',
              position: 'toast-top-right',
              closeOnHover: true,
              clickClose: false
            });
          }

        }
    }
</script>

