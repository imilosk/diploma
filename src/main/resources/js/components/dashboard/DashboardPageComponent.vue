<template>
  <div id="--page-dashboard">
    <p class="page-title">Dashboard</p>
    <template>
      <div>
        <apexchart :options="options" :series="series" type="line" width="500"></apexchart>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      options: {
        chart: {
          id: 'vuechart-example'
        },
        xaxis: {
          categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998]
        }
      },
      series: [{
        name: 'series-1',
        data: [30, 40, 45, 50, 49, 60, 70, 91]
      }]
    }
  }
}
</script>