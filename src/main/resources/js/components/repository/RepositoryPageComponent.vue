<template>
  <div id="--page-repository">
    <p class="page-title">Repository</p>

    <button class="py-2 px-4  bg-indigo-600 hover:bg-indigo-700 focus:ring-indigo-500 focus:ring-offset-indigo-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg "
            type="button">
      Valider
    </button>

    <div class="filters-row">
      <search-component></search-component>
      <div class="filters">
        <filter-component></filter-component>
        <filter-component></filter-component>
        <filter-component></filter-component>
      </div>
    </div>
    <div class="containers-row">
      <container-card-component></container-card-component>
      <container-card-component></container-card-component>
      <container-card-component></container-card-component>
      <container-card-component></container-card-component>
    </div>
  </div>
</template>

<script>
</script>