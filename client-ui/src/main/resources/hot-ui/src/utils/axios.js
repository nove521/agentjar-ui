import axios from 'axios'
import {NONE_ERROR,API_OK,REQUEST_ERROR} from '@/utils/constant'
import { Message } from 'element-ui'

const ajax = axios.create({
    baseURL: '/rest',  // 配置请求路由前缀
    timeout: 5000
})

// 添加请求拦截器
ajax.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
});

// 添加响应拦截器
ajax.interceptors.response.use(function (response) {
    let data = response.data
    if (data.code === API_OK) {
        return data
    }
    Message(REQUEST_ERROR(data.msg));
    throw data.msg
}, function (error) {
    Message(NONE_ERROR())
    return Promise.reject(error);
});

export default ajax
