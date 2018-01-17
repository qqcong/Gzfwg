<template>
    <div class="login-div">
        <el-form :model="form" status-icon :rules="rules" ref="form" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" :label-width="formLabelWidth" prop="userName">
                <el-input v-model="form.userName" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
            </el-form-item>
            <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                <el-input type="password" v-model="form.password" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm()">登 录</el-button>
                <el-button @click="resetForm('form')">重 置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import api from "@/api/"
    import md5 from 'js-md5'
    import {
        mapActions,
        mapGetters
    } from "vuex"
    export default {
        name: "Login",
        mounted() {
            this.checkLogin()
            document.removeEventListener("keypress", this.keyPressEvent, false);
            document.addEventListener("keypress", this.keyPressEvent, false);
        },
        beforeDestroy() {
            document.removeEventListener("keypress", this.keyPressEvent, false);
        },
        data() {
            return {
                form: {
                    userName: '',
                    password: '',
                },
                formLabelWidth: '80px',
                rules: {
                    userName: [{
                            required: true,
                            message: '请输入用户名',
                            trigger: 'blur'
                        },
                        {
                            min: 1,
                            max: 50,
                            message: '长度在 1 到 50 个字符',
                            trigger: 'blur'
                        }
                    ],
                    password: [{
                            required: true,
                            message: '请输入密码',
                            trigger: 'blur'
                        },
                        {
                            min: 1,
                            max: 50,
                            message: '长度在 1 到 50 个字符',
                            trigger: 'blur'
                        }
                    ],
                },
            }
        },
        methods: {
            ...mapActions({
                setUserName: "setUserName"
            }),
            keyPressEvent(e) {
                console.log(e.keyCode)
                switch (e.keyCode) {
                    case 13:
                        this.submitForm()
                        break;
                }
            },
            async submitForm() {
                try {
                    var valid = await this.$refs.form.validate()
                    if (valid) {
                        var para = {}
                        para.userName = this.form.userName
                        para.password = md5(md5(this.form.password))
                        let data = await api.ajaxPost("system/login", para)
                        if (data.code == 0) {
                            this.setUserName(this.form.userName)
                            document.removeEventListener("keydown", this.keyPressEvent, false)
                            this.$router.replace({
                                name: "main"
                            })
                        } else {
                            this.$message.error('登录失败：' + data.msg)
                        }
                    } else {
                        return false
                    }
                } catch (e) {
                    console.log(e)
                    return false
                }
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            async checkLogin() {
                let data = await api.ajaxPost("system/checkLogin", {});
                if (data && data.code == '0') {
                    this.$router.push({
                        name: "main"
                    })
                }
            },
        }
    };
</script>

<style scoped>
    .login-div {
        margin-top: 230px;
        width: 400px;
        display: inline-block;
    }
</style>
