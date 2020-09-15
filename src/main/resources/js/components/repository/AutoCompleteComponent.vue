<template>
    <v-autocomplete
            class="auto-complete"
            v-model="model"
            :items="items"
            :loading="isLoading"
            :search-input.sync="search"
            color="white"
            hide-no-data
            hide-selected
            item-text="Description"
            item-value="API"
            placeholder="Start typing to Search"
            prepend-icon="mdi-database-search"
            return-object
            filled
    ></v-autocomplete>
</template>

<script>
    export default {
        data: () => ({
            descriptionLimit: 60,
            entries: [],
            isLoading: false,
            model: null,
            search: null,
        }),

        computed: {
            fields () {
                if (!this.model) return []

                return Object.keys(this.model).map(key => {
                    return {
                        key,
                        value: this.model[key] || 'n/a',
                    }
                })
            },
            items () {
                return this.entries.map(entry => {
                    const Description = entry.Description.length > this.descriptionLimit
                        ? entry.Description.slice(0, this.descriptionLimit) + '...'
                        : entry.Description

                    return Object.assign({}, entry, { Description })
                })
            },
        },

        watch: {
            search (val) {
                // Items have already been loaded
                if (this.items.length > 0) return

                // Items have already been requested
                if (this.isLoading) return

                this.isLoading = true

                // Lazily load input items
                fetch('https://api.publicapis.org/entries')
                    .then(res => res.json())
                    .then(res => {
                        const { count, entries } = res
                        this.count = count
                        this.entries = entries
                    })
                    .catch(err => {
                        console.log(err)
                    })
                    .finally(() => (this.isLoading = false))
            },
        },
    }
</script>

<style scoped>
    .auto-complete {
        width: 250px;
    }
</style>