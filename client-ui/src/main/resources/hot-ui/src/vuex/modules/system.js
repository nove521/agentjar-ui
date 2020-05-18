import router from "../../router/index";

export default {
    state: {
        isLogin: true,
        isCheckJvm: false,
        currentAsideIndex: '1'
    },
    mutations: {
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
        }
    }
}