<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="商户名">
                    <el-input v-model="formInline.name" placeholder="商户名" :clearable="true"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click.stop="handleCurrentChange">查 询</el-button>
                    <el-button @click.stop="onCreate" type="warning">创 建</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table class="t-a-l" :data="dataList" :current-page="currentPage" :page-size="pageSize" style="width: 100%" :row-class-name="tableRowClassName">
                <el-table-column prop="id" label="商户ID" width="180">
                </el-table-column>
                <el-table-column prop="userName" label="用户名" width="180">
                </el-table-column>
                <el-table-column prop="password" label="密码" width="180">
                </el-table-column>
                <el-table-column prop="name" label="商户名称" width="180">
                </el-table-column>
                <el-table-column :formatter.sync="formatStatus" prop="status" label="状态" width="150">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" width="220">
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
            <el-dialog title="创建商户" :visible.sync="dialogFormVisible" :center="true" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-form :model="form" :rules="rules" ref="form">
                    <el-form-item label="用户名" :label-width="formLabelWidth" prop="userName">
                        <el-input v-model="form.userName" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                        <el-input v-model="form.password" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="状态" :label-width="formLabelWidth">
                        <el-select v-model="form.status" placeholder="请选择状态">
                            <el-option label="未初始化" value="0"></el-option>
                            <el-option label="正常" value="1"></el-option>
                            <el-option label="关闭" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="商户名称" :label-width="formLabelWidth" prop="name">
                        <el-input v-model="form.name" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
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
    export default {
        name: "Merchant",
        mounted() {
            this.handleCurrentChange()
        },
        data() {
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                formInline: {
                    name: '',
                },
                dataList: [],
                dialogFormVisible: false,
                form: {
                    userName: '',
                    password: '',
                    status: 1,
                    name: '',
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
                    name: [{
                            required: true,
                            message: '请输入商户名称',
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
            async onSubmit() {
                try {
                    var valid = await this.$refs.form.validate()
                    if (valid) {
                        console.log('submit!')
                        this.dialogFormVisible = false
                        let data = await api.ajaxPost("merchant/save", this.form)
                        if (data.code == "0") {
                            this.$message.success('操作成功')
                            if (!this.form.id) {
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
                    status: '1',
                    password: '',
                    name: ''
                }
                this.dialogFormVisible = true
            },
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                para.name = this.formInline.name
                let data = await api.ajaxPost("merchant/list", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            formatStatus(row, column, cellValue) {
                if (cellValue == "0") {
                    return "未初始化"
                } else if (cellValue == "1") {
                    return "正常"
                } else if (cellValue == "2") {
                    return "关闭"
                }
            },
            async handleEdit(index) {
                console.log(index)
                let d = this.dataList[index]
                this.form.userName = d.userName
                this.form.status = "" + d.status
                this.form.password = d.password
                this.form.name = d.name
                this.form.id = d.id
                this.dialogFormVisible = true
            },
            formatDate(row, column, cellValue) {
                if (cellValue) {
                    return new Date(cellValue).toLocaleString()
                }
            },
            tableRowClassName({
                row,
                rowIndex
            }) {
                console.log("rowIndex:" + rowIndex)
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
