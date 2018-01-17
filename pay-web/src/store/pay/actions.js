import * as types from './mutation-types'

export default {
  assignMessageText(context,text) {
    context.commit(types.ASSIGN_MESSAGE_TEXT,text);
  },
  setUserName(context,userName) {
    context.commit(types.SET_LOGIN_USERNAME,userName);
  }
};