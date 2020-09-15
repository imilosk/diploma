<template>
    <v-container fluid class="ma-0 pa-0">
        <v-row>
            <file-upload-component></file-upload-component>
        </v-row>
        <v-row align="center" class="d-flex" cols="12" sm="6">
            <v-col>
                <auto-complete-component></auto-complete-component>
            </v-col>
            <v-col>
                <v-row class="d-flex" cols="12">
                    <filter-component v-bind:items="['Most popular', 'Latest']"></filter-component>
                    <filter-component v-bind:items="['Monitoring', 'Databases', 'Operating Systems']"></filter-component>
                    <filter-component v-bind:items="['Your images', 'Public images']"></filter-component>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
</script>