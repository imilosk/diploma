<template>
    <div class="container-card">
        <div class="image">
            <img :src=imgPath />
        </div>
        <div class="container-info">
            <p>{{ name }}</p>
            <p>{{ description }}</p>
            <p>{{ dockerImageTags }}</p>
        </div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                imgPath: 'https://d1q6f0aelx0por.cloudfront.net/product-logos/library-nginx-logo.png',
                name: 'nginx',
                description: 'Official build of Nginx.',
                dockerImageTags: 'Tags: 1.3.4, 1.3.5, latest',
                searchTags: ['Container', 'ARM', 'Application Infrastracture'],
            }
        },
        methods: {

        }
    }
</script>

<style scoped>

</style>