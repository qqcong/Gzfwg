import Vue from 'vue'
import Vuex from 'vuex'

import pay from './pay'

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        pay
    }
});
