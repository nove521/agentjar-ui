import vuex from 'vuex'
import Vue from 'vue'
import System from './modules/system'
import Project from './modules/project'
import HotUpdate from './modules/hotUpdate'
import ResourceDir from './modules/resourceDir'

Vue.use(vuex)

export default new vuex.Store({
    state: {
        JVM_LIST_URL: '/rest/get-projects',
        JVM_OUT_URL: '/rest/stop-project',
        JVM_JOIN_URL: '/rest/join-project',
        JVM_INFO_URL: '/rest/info-project',
        CLASS_ALL_URL: '/rest/get-all-class',
        CLASS_INFO_URL: '/rest/get-class-info',
        CLASS_CODE_URL: '/rest/getClassCodeSource',
        CLASS_HOT_UPDATE_URL: '/rest/updateClass',
        CLASS_ALL_METHODS_URL: '/rest/get-class-all-methods',
        INVOKE_METHOD_URL: '/rest/invoke-method',
        ORNL_TEST_URL: '/rest/ognl-test',
        RESOURCE_DIR_GET_URL: '/rest/get-resource-dir',
        RESOURCE_FILE_CODE_GET_URL: '/rest/get-resource-file-code',
        RESOURCE_FILE_SAVE_CODE_URL: '/rest/get-resource-file-save-code',
        LOGIN_URL: '/login'
    },
    modules: {
        System,
        Project,
        HotUpdate,
        ResourceDir
    },
    mutations: {}
})