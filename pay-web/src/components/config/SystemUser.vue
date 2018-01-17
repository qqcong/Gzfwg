<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click.stop="handleCurrentChange">查 询</el-button>
                    <el-button @click.stop="onCreate" type="warning">创 建</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table class="t-a-l" :data="dataList" :current-page="currentPage" :page-size="pageSize" style="width: 100%" :row-class-name="tableRowClassName">
                <el-table-column prop="userName" label="用户名" width="180">
                </el-table-column>
                <el-table-column :formatter="formatDate" prop="createDate" label="创建日期" width="180">
                </el-table-column>
                <el-table-column :formatter="formatDate" prop="lastLogin" label="最后登录日期" width="300">
                </el-table-column>
                <el-table-column :formatter.sync="formatStatus" prop="status" label="状态" width="150">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                                <el-button
                                size="mini"
                                type="danger"
                                @click.stop="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                background
                layout="total, prev, pager, next"
                @current-change="handleCurrentChange"
                :current-page.sync = "currentPage"
                :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog title="创建用户" :visible.sync="dialogFormVisible" :center="true" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-form :model="form" :rules="rules" ref="form">
                    <el-form-item label="用户名" :label-width="formLabelWidth" prop="userName">
                        <el-input v-model="form.userName" auto-complete="off" :maxlength="50" :minlength="1" :disabled="disableSelect" width="100px"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                        <el-input type="password" v-model="form.password" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" :label-width="formLabelWidth" prop="passwordc">
                        <el-input type="password" v-model="form.passwordc" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="状态" :label-width="formLabelWidth">
                        <el-select v-model="form.status" placeholder="请选择状态">
                            <el-option label="禁用" value="N"></el-option>
                            <el-option label="正常" value="Y"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.stop="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click.stop="onSubmit()">保 存</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import api from "@/api/"
    import md5 from 'js-md5'
    export default {
        name: "SystemUser",
        mounted() {
            this.handleCurrentChange()
        },
        data() {
            var validatePassword = (rule, value, callback) => {
                if (!this.disableSelect && !value) {
                    callback(new Error('密码不能为空'))
                } else if (value && (value.length < 6 || value.length > 30)) {
                    callback(new Error('密码长度在 6 到 30 个字符'))
                } else {
                    callback()
                }
            };
            var validatePasswordc = (rule, value, callback) => {
                if (!this.disableSelect && !value) {
                    callback(new Error('密码不能为空'))
                } else if (value && (value.length < 6 || value.length > 30)) {
                    callback(new Error('密码长度在 6 到 30 个字符'))
                } else if (value != this.form.password) {
                    callback(new Error('两次输入的密码不一致'))
                } else {
                    callback()
                }
            };
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                dataList: [],
                dialogFormVisible: false,
                disableSelect: false,
                form: {
                    id: '',
                    userName: '',
                    password: '',
                    passwordc: '',
                    status: "Y",
                },
                formLabelWidth: '80px',
                rules: {
                    userName: [{
                            required: true,
                            message: '请输入用户名',
                            trigger: 'blur'
                        },
                        {
                            min: 4,
                            max: 30,
                            message: '长度在 4 到 30 个字符',
                            trigger: 'blur'
                        }
                    ],
                    password: [{
                        validator: validatePassword,
                        trigger: 'blur'
                    }],
                    passwordc: [{
                        validator: validatePasswordc,
                        trigger: 'blur'
                    }]
                },
            }
        },
        methods: {
            async onSubmit() {
                try {
                    var valid = await this.$refs.form.validate()
                    if (valid) {
                        console.log('submit!')
                        var para = {
                            id: '',
                            userName: '',
                            password: '',
                            passwordc: '',
                            status: '',
                        }
                        
                        para.id = this.form.id
                        para.userName = this.form.userName
                        para.status = this.form.status
                        para.password = this.form.password ? md5(md5(this.form.password)) : ''
                        let data = await api.ajaxPost("systemUser/save", para)
                        if (data.code == "0") {
                            this.dialogFormVisible = false
                            this.$message.success('操作成功')
                            if (!this.disableSelect) {
                                this.currentPage = 1
                            }
                            this.handleCurrentChange()
                        } else {
                            this.$message.error('操作失败：' + data.msg)
                        }
                    } else {
                        return false
                    }
                } catch (e) {
                    console.log(e)
                    return false
                }
            },
            async onCreate() {
                console.log('create!')
                this.form = {
                    id: '',
                    userName: '',
                    password: '',
                    passwordc: '',
                    status: "Y",
                }
                this.disableSelect = false
                this.dialogFormVisible = true
            },
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                let data = await api.ajaxPost("systemUser/listPage", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            formatStatus(row, column, cellValue) {
                if (cellValue == "Y") {
                    return "正常"
                } else if (cellValue == "N") {
                    return "禁用"
                }
            },
            formatDate(row, column, cellValue) {
                if (cellValue) {
                    return new Date(cellValue).toLocaleString()
                }
            },
            async handleEdit(index) {
                console.log(index)
                let d = this.dataList[index]
                this.form.userName = d.userName
                this.form.password = ''
                this.form.passwordc = ''
                this.form.status = d.status
                this.form.id = d.id
                this.disableSelect = true
                this.dialogFormVisible = true
            },
            tableRowClassName({
                row,
                rowIndex
            }) {
                if (rowIndex % 2 == 0) {
                    return 'odd-row'
                } else {
                    //return 'even-row'
                }
            }
        }
    }
</script>
<style scoped>
    .query-bar {
        text-align: left;
    }
</style>
