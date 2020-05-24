import Cookies from 'js-cookie'

export default {
    getCookies(){
        return Cookies
    },
    isLogin(){
        return this.getAttribute("sid") !== undefined
    },
    getAttribute (key) {
        return Cookies.get(key)
    },
    remove (key) {
        return Cookies.remove(key)
    },
    clearLogin(){
        this.remove('sid')
    }
}