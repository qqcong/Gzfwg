import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Main from '@/components/Main'
import Login from '@/components/Login'
import AppList from '@/components/config/AppList'
import Merchant from '@/components/config/Merchant'
import Channel from '@/components/config/Channel'
import ChannelBankConf from '@/components/config/ChannelBankConf'
import ChannelFeeConf from '@/components/config/ChannelFeeConf'
import Bank from '@/components/config/Bank'
import SystemUser from '@/components/config/SystemUser'
import UserBindCard from '@/components/search/UserBindCard'
import AppUser from '@/components/search/AppUser'
import Transaction from '@/components/search/Transaction'

Vue.use(Router)

export default new Router({
  routes: [
    {
        path: '/main',
        component: Main,
        children: [
            { name: 'main', path: '', component: Hello },
            { name: 'appConf', path: 'appConf', component: AppList },
            { name: 'merchantConf', path: 'merchantConf', component: Merchant },
            { name: 'channelConf', path: 'channelConf', component: Channel },
            { name: 'channelBankConf', path: 'channelBankConf', component: ChannelBankConf },
            { name: 'channelFeeConf', path: 'channelFeeConf', component: ChannelFeeConf },
            { name: 'bankConf', path: 'bankConf', component: Bank },
            { name: 'systemUserConf', path: 'systemUserConf', component: SystemUser },
            { name: 'userBindCardQuery', path: 'userBindCardQuery', component: UserBindCard },
            { name: 'appUserQuery', path: 'appUserQuery', component: AppUser },
            { name: 'transactionQuery', path: 'transactionQuery', component: Transaction },
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
    {
        path: '',
        name: 'login',
        component: Login,
    },
]
})
