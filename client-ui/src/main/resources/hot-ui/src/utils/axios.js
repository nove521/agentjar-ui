import axios from 'axios'
import {NONE_ERROR, API_OK, REQUEST_ERROR} from '@/utils/constant'
import {Message} from 'element-ui'
import store from '../vuex/store'
import router from "../router";

const ajax = axios.create({
    timeout: 5000
})

let isLoginUrl = (obj) => {
    let url = obj.request.responseURL
    if (url.indexOf("/login") > 0) {
        return true
    }
    return false
}

// 添加请求拦截器
ajax.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
});

// 添加响应拦截器
ajax.interceptors.response.use(function (response) {
    if (isLoginUrl(response)) {
        return response
    }

    let data = response.data
    if (data.code === API_OK) {
        return data
    }
    Message(REQUEST_ERROR(data.msg));
    throw data.msg
}, function (error) {
    if (!isLoginUrl(error)) {
        if (error.response.status === 499) {
            let code = error.response.data.code
            if (code === 902 || code === 904) {
                Message(NONE_ERROR(error.response.data.message))
                store.dispatch('outLoginLocal')
                router.replace("/login")
            }else {
                Message(NONE_ERROR())
            }
        }

    }

    return Promise.reject(error);
});

export default ajax
