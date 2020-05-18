let API_OK = "200"

let RESPONSE_NO_DATA = (message = '请求返回结果没有数据') => {
    return {
        message,
        type: 'warning'
    }
}

let REQUEST_ERROR = (message = '请求结果异常') => {
    return {
        message,
        type: 'error'
    }
}

let NONE_ERROR = (message = '未知异常') => {
    return {
        message,
        type: 'error'
    }
}

let SUCCEED = (message = '请求成功') => {
    return {
        message,
        type: 'success'
    }
}

export {API_OK,RESPONSE_NO_DATA,REQUEST_ERROR,NONE_ERROR,SUCCEED}