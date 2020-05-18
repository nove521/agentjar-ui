import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueResource from 'vue-resource'
import App from './App.vue'
import router from "./router";
import store from './vuex/store'

Vue.config.productionTip = false
Vue.use(VueResource)
Vue.use(ElementUI)

new Vue({
    store,
    router,
    render: h => h(App),
}).$mount('#app')
