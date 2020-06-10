import axios from "../../utils/axios";

export default {
    state: {
        info: {}
    },
    mutations: {
        setJvmInfo(state, info) {
            state.info = info
        }
    },
    actions: {
        initJvmInfo({rootState, commit}) {
            axios.post(rootState.GET_JVM_INFO_URL).then(res => {
                commit('setJvmInfo', res.data)
            })
        }
    }
}