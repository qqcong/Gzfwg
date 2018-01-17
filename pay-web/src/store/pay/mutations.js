/**
 * 每个 mutation 都有一个字符串的 事件类型 (type),而这个事件类型可以用常量来代替,为什么用常量？
 * 当在action中触发事件类型时，都是字符传参，如果事件类型名称变了，那么所有调用的方法都要变一下。
 * 另外官网描述可以使 linter 之类的工具发挥作用，同时把这些常量放在单独的文件中可以让你的代码合作者对整个 app 
 * 包含的 mutation 一目了然。
 * 
 */
import * as types from './mutation-types'

export default {
    /**
     * 设置提示信息
     * @param {Object} state 
     * @param {string} text -提示内容
     */
    [types.ASSIGN_MESSAGE_TEXT](state,text){
        state.messageText = text;
    },

    [types.SET_LOGIN_USERNAME](state,userName){
        state.userName = userName;
    }
}