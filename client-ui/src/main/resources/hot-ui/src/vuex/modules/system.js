import router from "../../router/index";
import axios from "../../utils/axios";
import {Message} from "element-ui";
import {REQUEST_ERROR} from '@/utils/constant'
import cookies from "../../utils/cookies";

export default {
    state: {
        isLogin: false,
        isCheckJvm: false,
        currentAsideIndex: '1',
        authKey: ''
    },
    mutations: {
        setAuthKey(state, {authKey}) {
            state.authKey = authKey
        },
        setCurrentAsideIndex(state, {currentAsideIndex}) {
            state.currentAsideIndex = currentAsideIndex
        },
        setIsLogin(state, {isLogin}) {
            state.isLogin = isLogin
        },
        setIsCheckJvm(state, {isCheckJvm}) {
            state.isCheckJvm = isCheckJvm
        }
    },
    actions: {
        changeInProject({commit}) {
            commit('setIsCheckJvm', {isCheckJvm: true})
            commit('setCurrentAsideIndex', {currentAsideIndex: '2-1'})
            router.replace('/JvmInfo')
        },
        changeOutProject({commit}) {
            commit('setIsCheckJvm', {isCheckJvm: false})
            commit('setCurrentAsideIndex', {currentAsideIndex: '1'})
            router.replace('/')
        },
        setLogin({commit}) {
            commit('setAuthKey', {authKey: ''})
            commit('setIsLogin', {isLogin: true})
        },
        login({commit, rootState}, {token}) {
            axios.get(rootState.LOGIN_URL + `?key=${token}`).then((response) => {
                commit('setIsLogin', {isLogin: true})
                commit('setAuthKey', {authKey: response.data})
                router.replace('/')
            }).catch(err => {
                let response = err.response;
                Message(REQUEST_ERROR(response.data.message))
            })
        },
        outLoginLocal({commit}){
            commit('setIsLogin', {isLogin: false})
            commit('setAuthKey', {authKey: ''})
            cookies.clearLogin()
        }
    }
}