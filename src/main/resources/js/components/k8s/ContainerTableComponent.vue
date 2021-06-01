<template>
  <div class="container-table">
    <div class="tbl-header">
      <table border="0" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
          <th>Name</th>
          <th>Ip address</th>
          <th>Created</th>
          <th>Location</th>
          <th>Replicas</th>
          <th></th>
        </tr>
        </thead>
      </table>
    </div>
    <div class="tbl-content">
      <table border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
          <td>ubuntu-s-1vcpu-1gb-slo1-01</td>
          <td>83.2.3.95:8000</td>
          <td>Yesterday</td>
          <td>Node 1 (Slovenia)</td>
          <td>3</td>
          <td>
            <start-stop-container-button-component></start-stop-container-button-component>
          </td>
        </tr>
        <div class="space-under"></div>
        <tr>
          <td>django-s-2vcpu-4gb-ny1-01</td>
          <td>83.2.3.95:1000</td>
          <td>3 days ago</td>
          <td>Node 3 (New York)</td>
          <td>3</td>
          <td>
            <start-stop-container-button-component></start-stop-container-button-component>
          </td>
        </tr>
        <div class="space-under"></div>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
</script>

<style scoped>

</style>