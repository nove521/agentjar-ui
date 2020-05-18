import vuex from 'vuex'
import Vue from 'vue'
import System from './modules/system'
import Project from './modules/project'
import HotUpdate from './modules/hotUpdate'

Vue.use(vuex)

export default new vuex.Store({
    state: {
        JVM_LIST_URL: '/get-projects',
        JVM_OUT_URL: '/stop-project',
        JVM_JOIN_URL: '/join-project',
        JVM_INFO_URL: '/info-project',
        CLASS_ALL_URL: '/get-all-class',
        CLASS_INFO_URL: '/get-class-info',
        CLASS_CODE_URL: '/getClassCodeSource',
        CLASS_HOT_UPDATE_URL: '/updateClass'
    },
    modules: {
        System,
        Project,
        HotUpdate
    },
    mutations: {}
})