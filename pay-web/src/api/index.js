/* global $ */
import Vue from 'vue'
import VueResource from 'vue-resource'
import router from '../router'
import { Message } from 'element-ui'

var hostdoamin = "http://localhost:8090"
if(process.env.NODE_ENV === 'production'){
    hostdoamin = ''
}

Vue.use(VueResource)

Vue.http.options.root = ""
Vue.http.options.emulateJSON = true
Vue.http.options.timeout = 10000
Vue.http.options.xhr = { withCredentials: true }
//Vue.http.headers.common['Authorization'] = 'Basic YXBpOnBhc3N3b3Jk'
Vue.http.headers.common['Content-Type'] = 'application/x-www-form-urlencoded'
Vue.http.options.before = function(){
    console.log('Vue.http.options.before')
}

export default {
    ajaxPost(url, param) {
        return new Promise((resolve, reject) => {
            Vue.http['post'](`/paybss/${url}`, param).then(data => {
                var data = data.body
                if(data.code == 5000 && data.msg == 'timeout') {
                    router.push({name: "login" })
                    Message.warning({message:"会话超时，请重新登录"})
                } else {
                    resolve(data)
                }
            }, error => {
                var msg = error ? error.statusText || "请联系管理员" :"请联系管理员"
                msg = "系统错误："+ msg
                Message.error({message: msg})
                reject(error)
            })
        })
    }
}
