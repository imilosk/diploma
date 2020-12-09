<template>
    <vue-dropzone id="dropzone" :options="dropzoneOptions" :useCustomSlot=true
                  v-on:vdropzone-success="onSuccessUpload"
                  v-on:vdropzone-file-added="onBeforeUpload">

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
                    url: "/uploadDockerImage",
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
                timeout: 2000,
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
                timeout: 2000,
                type: 'success',
                position: 'toast-top-right',
                closeOnHover: true,
                clickClose: false
              });
            }
        }
    }
</script>

