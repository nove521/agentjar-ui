import axios from "../../utils/axios";

export default {
    state: {
        dirList: []
    },
    mutations: {
        setDirList(state, {dirList}) {
            state.dirList = dirList
        }
    },
    actions: {
        getResourceDir({rootState, commit}) {
            axios.post(rootState.RESOURCE_DIR_GET_URL).then((res) => {
                commit("setDirList", {dirList: res.data})
            })
        },
        getFileCode({rootState}, {fileName}) {
            return axios.post(rootState.RESOURCE_FILE_CODE_GET_URL, {fileName})
        },
        saveFileCode({rootState}, {fileName, code}) {
            return axios.post(rootState.RESOURCE_FILE_SAVE_CODE_URL, {fileName, code})
        }
    }
}