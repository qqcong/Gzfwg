<template>
    <el-container>
        <el-header>
            <img class="image-logo" src="static/img/icons/logo.png">
            <span class="logout-cls"><el-button @click.stop="logout" type="text">退出</el-button></span>
        </el-header>
        <el-container class="main-container">
            <el-aside width="220px">
                <LeftNav></LeftNav>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import api from "@/api/"
    import LeftNav from "./LeftNav"
    import {
        mapActions,
        mapGetters
    } from "vuex"
    export default {
        name: "MainCom",
        components: {
            LeftNav
        },
        mounted() {
            this.checkLogin()
        },
        computed: {
            username() {
                return this.$store.state.pay.userName
            }
        },
        data() {
            return {
                msg: ""
            };
        },
        methods: {
            ...mapActions({
                assignMessageText: "assignMessageText",
                setUserName: "setUserName"
            }),
            async checkLogin() {
                let data = await api.ajaxPost("system/checkLogin", {});
                if (!data || data.code != '0') {
                    this.$router.push({
                        name: "login"
                    })
                }
            },
            async logout() {
                let data = await api.ajaxPost("system/logout", {});
                if (data.code == '0') {
                    this.setUserName("")
                    this.$router.push({
                        name: "login"
                    })
                }
            },
        }
    };
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .el-header,
    .el-footer {
        background-color: #333;
        color: white;
        text-align: left;
        line-height: 60px;
    }
    .el-aside {
        background-color: #404040;
        color: white;
        text-align: center;
        line-height: 200px;
    }
    .el-main {
        background-color: #e9eef3;
        color: #333;
        text-align: center;
    }
    .main-container {
        position: absolute;
        width: 100%;
        top: 60px;
        bottom: 0px;
    }
    body>.el-container {
        margin-bottom: 40px;
    }
    .image-logo {
        padding-top: 8px;
    }
    .logout-cls {
        float: right;
        margin-right: 20px;
    }
</style>
