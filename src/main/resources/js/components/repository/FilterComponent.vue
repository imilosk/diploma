<template>
    <v-select
            class="filter-component"
            :items="items"
            filled
            label="Filled style"
    ></v-select>
</template>

<script>
    export default {
        props: {
            'items': Array
        },
    }
</script>

<style scoped>
    .filter-component{
        width: 100px;
        padding-left: 10px;
        padding-right: 10px;
    }
</style>