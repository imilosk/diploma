<template>
  <div class="terminal">
    <div class=fakeMenu>
      <div class="fakeButtons fakeClose"></div>
      <div class="fakeButtons fakeMinimize"></div>
      <div class="fakeButtons fakeZoom"></div>
    </div>
    <div class="fakeScreen">
      <p class="line3" v-for="line in lines">{{ line }}</p>
      <p class="line4">><span class="cursor4"></span>{{ progress }}</p>
    </div>

  </div>
</template>

<script>
  export default {
    props: {
      lines: [],
      progress: '',
    },
  }
</script>

