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
                <el-table-column prop="bankCode" label="银行编号" width="180">
                </el-table-column>
                <el-table-column prop="bankName" label="银行名称" width="180">
                </el-table-column>
                <el-table-column prop="logo" label="银行logo" width="300">
                </el-table-column>
                <el-table-column prop="sort" label="排序" width="180">
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
            <el-dialog title="创建商户" :visible.sync="dialogFormVisible" :center="true" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-form :model="form" :rules="rules" ref="form">
                    <el-form-item label="银行编号" :label-width="formLabelWidth" prop="bankCode">
                        <el-input v-model="form.bankCode" auto-complete="off" :maxlength="50" :minlength="1" :disabled="disableSelect" width="100px"></el-input>
                    </el-form-item>
                    <el-form-item label="银行名称" :label-width="formLabelWidth" prop="bankName">
                        <el-input v-model="form.bankName" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="银行logo" :label-width="formLabelWidth" prop="logo">
                        <el-input v-model="form.logo" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="状态" :label-width="formLabelWidth">
                        <el-select v-model="form.status" placeholder="请选择状态">
                            <el-option label="未支持" value="0"></el-option>
                            <el-option label="正常" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="排序" :label-width="formLabelWidth" prop="sort">
                        <el-input-number v-model="form.sort" :min="1" :max="1000"></el-input-number>
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
        name: "Bank",
        mounted() {
            this.handleCurrentChange()
        },
        data() {
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                dataList: [],
                dialogFormVisible: false,
                disableSelect: false,
                form: {
                    bankCode: '',
                    bankName: '',
                    status: "1",
                    logo: '',
                    sort: '',
                },
                formLabelWidth: '80px',
                rules: {
                    bankCode: [{
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
                    bankName: [{
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
            async onSubmit() {
                try {
                    var valid = await this.$refs.form.validate()
                    if (valid) {
                        console.log('submit!')
                        this.dialogFormVisible = false
                        let data = await api.ajaxPost("bank/save", this.form)
                        if (data.code == "0") {
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
                    bankCode: '',
                    bankName: '',
                    status: "1",
                    logo: '',
                    sort: '',
                }
                this.disableSelect = false
                this.dialogFormVisible = true
            },
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                let data = await api.ajaxPost("bank/listPage", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            formatStatus(row, column, cellValue) {
                if (cellValue == "0") {
                    return "未支持"
                } else if (cellValue == "1") {
                    return "正常"
                }
            },
            async handleEdit(index) {
                console.log(index)
                let d = this.dataList[index]
                this.form.bankCode = d.bankCode
                this.form.bankName = d.bankName
                this.form.status = "" + d.status
                this.form.logo = d.logo
                this.form.sort = d.sort
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
