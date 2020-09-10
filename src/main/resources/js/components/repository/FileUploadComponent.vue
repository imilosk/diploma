<template>
    <vue-dropzone id="dropzone" :options="dropzoneOptions" :useCustomSlot=true>
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
    }
</script>

