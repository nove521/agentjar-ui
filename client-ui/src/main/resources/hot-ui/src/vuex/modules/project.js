import axios from "../../utils/axios";
import {SUCCEED} from '@/utils/constant'
import {Message, Loading} from 'element-ui'
import router from "../../router";

let changeClassInfo = (classInfo) => {
    let arr = []
    Object.keys(classInfo).forEach(key => {
        arr.push({
            key: key,
            val: classInfo[key]
        })
    })
    return arr
}

export default {
    state: {
        ROOT_PATH: '',
        search: '',
        jvmlist: [],
        jvmInfo: {},
        classAll: {
            total: 0,
            list: []
        },
        classInfo: []
    },
    mutations: {
        setROOTPATH(state, {search}) {
            state.ROOT_PATH = search
            state.search = search
        },
        initClassSearch(state){
            state.search = state.ROOT_PATH
        },
        setClassSearch(state, {search}) {
            state.search = search
        },
        setClassInfo(state, classInfo) {
            state.classInfo = classInfo
        },
        setClassAll(state, classAll) {
            state.classAll = classAll
        },
        setJvmInfo(state, jvmInfo) {
            state.jvmInfo = jvmInfo
        },
        setJvmlist(state, jvmlist) {
            state.jvmlist = jvmlist
        }
    },
    actions: {
        getClassAllMethods({rootState}, {className}) {
            axios.post(rootState.CLASS_ALL_METHODS_URL,
                {className: className}).then(response => {
                console.log(response)
            })
        },
        getClassCodeSource({rootState, commit}, {className}) {
            axios.post(rootState.CLASS_CODE_URL, {
                className: className
            }).then(response => {
                commit('setCurrentAsideIndex', {currentAsideIndex: '2-3'})
                console.log(rootState.System.currentAsideIndex)
                commit('setJavaCode', {code: response.data.code})
                commit('setJavaCodeName', {name: response.data.name})
                router.replace('/HotUpdate')
            })
        },
        getClassInfo({rootState, commit}, {className}) {
            axios.get(rootState.CLASS_INFO_URL + `?data={"className":"${className}"}`).then(response => {
                commit('setClassInfo', changeClassInfo(response.data))
            })
        },
        async getClassAll({rootState, commit}, {page, size, pattenName}) {
            await axios.post(rootState.CLASS_ALL_URL,
                {page: page, size: size, pattenName: pattenName}
            ).then(response => {
                commit('setClassAll', response.data)
            })
        },
        getJvmInfo({rootState, commit}) {
            axios.get(rootState.JVM_LIST_URL).then(response => {
                commit('setJvmInfo', response.data)
            })
        },
        getJvmlist({rootState, commit}) {
            axios.get(rootState.JVM_LIST_URL).then(response => {
                commit('setJvmlist', response.data)
            })
        },
        joinJvm({dispatch, rootState, commit}, {id, name}) {
            const loading = Loading.service({
                lock: true,
                text: '加载中',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });

            axios.get(rootState.JVM_JOIN_URL, {params: {data: {id}}}).then(() => {
                dispatch('changeInProject')
                let search = name.split(".")[0] + '.' + name.split(".")[1]
                commit('setROOTPATH', {search: search})
                Message(SUCCEED());
            }).finally(() => {
                loading.close()
                dispatch('getJvmlist')
            })
        },
        outJvm({dispatch, rootState}, {restore}) {
            axios.get(rootState.JVM_OUT_URL + `?data={"restore":"${restore}"}`).then(() => {
                dispatch('changeOutProject')
                Message(SUCCEED());
            })
        }
    }
}