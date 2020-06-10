import Vue from 'vue'
import Router from 'vue-router'
import store from '../vuex/store'

import Index from '@/page/Index'
import Login from '@/page/Login'
import SelectProject from '@/page/Index/SelectProject'
import JvmInfo from '@/page/Index/JvmInfo'
import FindClass from '@/page/Index/FindClass'
import HotUpdate from '@/page/Index/HotUpdate'
import ResourceDir from '@/page/Index/ResourceDir'
import Ognl from '@/page/Index/Ognl'

Vue.use(Router)

const router = new Router({
    routes: [
        {
            path: '/',
            name: 'Index',
            component: Index,
            children: [
                {
                    path: '',
                    name: 'SelectProject',
                    component: SelectProject
                },{
                    path: '/JvmInfo',
                    name: 'JvmInfo',
                    component: JvmInfo
                },{
                    path: '/Ognl',
                    name: 'Ognl',
                    component: Ognl
                },{
                    path: '/FindClass',
                    name: 'FindClass',
                    component: FindClass
                },{
                    path: '/HotUpdate',
                    name: 'HotUpdate',
                    component: HotUpdate
                },{
                    path: '/ResourceDir',
                    name: 'ResourceDir',
                    component: ResourceDir
                }
            ]
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        }
    ]
})

function hasCookiesUser(){
    return store.state.System.isLogin
}

router.beforeEach((to, from, next) => {
    let paths = ['/login']
    if (!hasCookiesUser() && paths.indexOf(to.path) < 0) { // 如果没有cookies
        next({path: '/login'})
        return
    }
    next()
})

export default router