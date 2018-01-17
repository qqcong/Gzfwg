/**
 * 过渡状态控制
 */
import mutations from './mutations'
import actions from './actions'

/**
 * 状态
 */
const state = {
  /**
   * 提示信息
   */
  messageText:"",
  userName:"",
};

export default{
  state,
  mutations,
  actions
}