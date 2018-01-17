<template>
    <el-menu :default-active="defaultActive" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" @select="handleSelect" background-color="#404040" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="main">
            <i class="el-icon-message"></i>
            <span slot="title" class="f15">欢迎页</span>
        </el-menu-item>
        <el-submenu index="2">
            <template slot="title">
                   <i class="el-icon-setting"></i>
                   <span class="f15">配置</span>
            </template>
        <el-menu-item index="appConf">应用配置</el-menu-item>
        <el-menu-item index="merchantConf">商户配置</el-menu-item>
        <el-menu-item index="channelConf">渠道配置</el-menu-item>
        <el-menu-item index="channelBankConf">渠道银行限额配置</el-menu-item>
        <el-menu-item index="channelFeeConf">渠道费用配置</el-menu-item>
        <el-menu-item index="bankConf">银行配置</el-menu-item>
        <el-menu-item v-if="showSystemUserConf" index="systemUserConf">系统用户配置</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
            <i class="el-icon-search"></i>
            <span class="f15">查询</span>
        </template>
        <el-menu-item index="transactionQuery">交易查询</el-menu-item>
        <el-menu-item index="userBindCardQuery">用户绑卡查询</el-menu-item>
        <el-menu-item index="appUserQuery">应用用户查询</el-menu-item>
      </el-submenu>
    </el-menu>
</template>


<script>
    export default {
        name: "LeftNav",
        data() {
            return {
                defaultActive: 'main'
            }
        },
        beforeDestroy() {
            window.removeEventListener("hashchange", this.hashChange, false)
        },
        mounted() {
            window.addEventListener("hashchange", this.hashChange, false)
        },
        computed: {
            showSystemUserConf() {
                console.log(this.$store.state.pay.userName)
                return 'root' == this.$store.state.pay.userName
            },
            userName() {
                return this.$store.state.pay.userName
            }
        },
        watch: {
            /* userName: function(val, oldVal) {
                if (!val) {
                    this.$router.push({
                        name: "login"
                    })
                }
            } */
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            handleSelect(indexPath) {
                console.log(indexPath);
                this.$router.push({
                    name: indexPath
                })
            },
            hashChange() {
                this.defaultActive = location.hash.substr(7) || 'main'
            }
        }
    };
</script>

<style scoped>
    .el-menu {
        border-right: solid 0px #e6e6e6;
        text-align: left;
    }
</style>