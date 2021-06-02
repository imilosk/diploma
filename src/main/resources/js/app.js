import Vue from 'vue';
import VueRouter from 'vue-router';
import vue2Dropzone from 'vue2-dropzone';
import 'vue2-dropzone/dist/vue2Dropzone.min.css';
import VueApexCharts from 'vue-apexcharts';
import VueToastr from '@deveodk/vue-toastr';
import '@deveodk/vue-toastr/dist/@deveodk/vue-toastr.css';

Vue.component('vue-dropzone', vue2Dropzone);
Vue.component('apexchart', VueApexCharts)
Vue.use(VueRouter);
Vue.use(VueToastr);

window.axios = require('axios');
window.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

// import all components
const files = require.context('./', true, /\.vue$/i);
files.keys().map(key => Vue.component(key.split('/').pop().split('.')[0], files(key).default));

// 1. Define route components.
const Foo = {template: '<div>foo</div>'}
const RepositoryPageComponent = resolve => {
    require.ensure([], require => resolve(require('./components/repository/RepositoryPageComponent.vue')));
};
const K8sPageComponent = resolve => {
    require.ensure([], require => resolve(require('./components/k8s/K8sPageComponent.vue')));
};
const DashboardPageComponent = resolve => {
    require.ensure([], require => resolve(require('./components/dashboard/DashboardPageComponent.vue')));
};
const UploadContainerPageComponent = resolve => {
    require.ensure([], require => resolve(require('./components/repository/uploadNewImage/UploadContainerPageComponent.vue')));
};

// 2. Define routes
const routes = [
    {path: '/dashboard', component: DashboardPageComponent},
    {path: '/repository', component: RepositoryPageComponent},
    {path: '/k8s', component: K8sPageComponent},
    {path: '/uploadContainer', component: UploadContainerPageComponent},
    {path: '/settings', component: Foo},
];

// 3. Create the router instance and pass the `routes` option
const router = new VueRouter({
    routes
})

// 4. Create and mount the root instance.
const app = new Vue({
    router,
}).$mount('#app');
// Now the app has started!
