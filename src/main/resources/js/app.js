import Vue from 'vue';
import VueRouter from 'vue-router';
import VueApexCharts from 'vue-apexcharts';
import VueToastr from '@deveodk/vue-toastr';
import '@deveodk/vue-toastr/dist/@deveodk/vue-toastr.css';
import _ from 'lodash';

Vue.component('apexchart', VueApexCharts)
Vue.use(VueRouter);
Vue.use(VueToastr);

window.axios = require('axios');
window.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

// import all components
const files = require.context('./', true, /\.vue$/i);
files.keys().map(key => Vue.component(key.split('/').pop().split('.')[0], files(key).default));

// Define route components.
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

// Define routes
const routes = [
    {path: '/dashboard', component: DashboardPageComponent},
    {path: '/repository', component: RepositoryPageComponent},
    {path: '/k8s', component: K8sPageComponent},
    {path: '/uploadImage', component: UploadContainerPageComponent},
    {path: '/settings', component: Foo},
];

// Create the router instance and pass the `routes` option
const router = new VueRouter({
    routes
})

// Create and mount the root instance.
const app = new Vue({
    router,
}).$mount('#app');

// Now the app has started!
