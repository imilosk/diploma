import Vue from 'vue';
import VueRouter from 'vue-router';

import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'

Vue.component('vue-dropzone', vue2Dropzone);
Vue.use(VueRouter);

window.axios = require('axios');
window.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

// import all components
const files = require.context('./', true, /\.vue$/i);
files.keys().map(key => Vue.component(key.split('/').pop().split('.')[0], files(key).default));

// 1. Define route components.
const Foo = { template: '<div>foo</div>' }
const RepositoryComponent = resolve => {
    require.ensure([], require => resolve(require('./components/RepositoryComponent.vue')), 'foo');
};

// 2. Define routes
const routes = [
    { path: '/dashboard', component: Foo },
    { path: '/repository', component: RepositoryComponent },
    { path: '/k8s', component: Foo },
    { path: '/settings', component: Foo }
]

// 3. Create the router instance and pass the `routes` option
const router = new VueRouter({
    routes
})

// 4. Create and mount the root instance.
const app = new Vue({
    router,
}).$mount('#app');

// Now the app has started!
