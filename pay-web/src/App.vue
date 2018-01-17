<template>
    <div id="app">
        <main>
            <router-view></router-view>
        </main>
    </div>
</template>

<script>
    import api from "@/api/"
    import {
        mapActions,
        mapGetters
    } from "vuex"
    export default {
        name: 'App',
        mounted() {
            this.checkLogin()
        },
        methods: {
            ...mapActions({
                setUserName: "setUserName"
            }),
            async checkLogin() {
                let data = await api.ajaxPost("system/checkLogin", {})
                if (data.code == '0') {
                    this.setUserName(data.data)
                    this.$router.replace({
                        name: "main"
                    })
                } else {
                    this.$router.push({
                        name: "login"
                    })
                }
            }
        }
    }
</script>

<style scoped>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }
    main {
        text-align: center;
    }
    header {
        margin: 0;
        height: 56px;
        padding: 0 16px 0 24px;
        background-color: #35495E;
        color: #ffffff;
    }
    header span {
        display: block;
        position: relative;
        font-size: 20px;
        line-height: 1;
        letter-spacing: .02em;
        font-weight: 400;
        box-sizing: border-box;
        padding-top: 16px;
    }
</style>
