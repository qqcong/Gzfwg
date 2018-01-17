<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="应用名">
                    <el-input v-model="formInline.appName" placeholder="应用名" :clearable="true"></el-input>
                </el-form-item>
                <el-form-item label="应用ID">
                    <el-input v-model="formInline.appid" placeholder="应用ID" :clearable="true"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click.stop="handleCurrentChange">查 询</el-button>
                    <el-button @click.stop="onCreate" type="warning">创 建</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table class="t-a-l" :data="appDataList" :current-page="currentPage" :page-size="pageSize" style="width: 100%" :row-class-name="tableRowClassName">
                <el-table-column prop="appid" label="应用ID" width="300">
                </el-table-column>
                <el-table-column prop="appName" label="应用名称" width="180">
                </el-table-column>
                <el-table-column prop="secretKey" label="密钥" width="300">
                </el-table-column>
                <el-table-column prop="merchant.name" label="所属商户名称" width="180">
                </el-table-column>
                <el-table-column :formatter.sync="formatStatus" prop="status" label="状态" width="150">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" width="220">
                </el-table-column>
                <el-table-column prop="bindLimit" label="绑卡数量" width="150">
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
            <el-dialog title="创建应用" :visible.sync="dialogFormVisible" :center="true" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-form :model="form" :rules="rules" ref="form">
                    <el-form-item label="应用名称" :label-width="formLabelWidth" prop="appName">
                        <el-input v-model="form.appName" auto-complete="off" :maxlength="50" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="支付公钥" :label-width="formLabelWidth" prop="publicKey">
                        <el-input v-model="form.publicKey" auto-complete="off" type="textarea" :rows="10" class="dialog-input-width" ></el-input>
                    </el-form-item>
                    <el-form-item label="状态" :label-width="formLabelWidth">
                        <el-select v-model="form.status" placeholder="请选择状态">
                            <el-option label="未初始化" value="0"></el-option>
                            <el-option label="正常" value="1"></el-option>
                            <el-option label="关闭" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="绑卡数量" :label-width="formLabelWidth" prop="bindLimit">
                        <el-input-number v-model="form.bindLimit" :min="1" :max="10" label="描述文字"></el-input-number>
                    </el-form-item>
                    <el-form-item label="商户号" :label-width="formLabelWidth" prop="merchantId">
                        <el-select v-model="form.merchantId" placeholder="请选择商户号">
                        <el-option v-for="item in merchants" :key="item.id" :label="item.name" :value="item.id"></el-option>
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
    export default {
        name: "AppList",
        mounted() {
            this.handleCurrentChange()
        },
        data() {
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                formInline: {
                    appName: '',
                    appid: '',
                },
                appDataList: [],
                dialogFormVisible: false,
                form: {
                    appName: '',
                    status: 1,
                    publicKey: '',
                    bindLimit: 1,
                    merchantId: '',
                },
                formLabelWidth: '80px',
                rules: {
                    appName: [{
                            required: true,
                            message: '请输入应用名称',
                            trigger: 'blur'
                        },
                        {
                            min: 1,
                            max: 50,
                            message: '长度在 1 到 50 个字符',
                            trigger: 'blur'
                        }
                    ],
                    publicKey: [{
                        required: true,
                        message: '请输入支付公钥',
                        trigger: 'blur'
                    }, ],
                },
                merchants: []
            }
        },
        methods: {
            async onSubmit() {
                try {
                    var valid = await this.$refs.form.validate()
                    if (valid) {
                        console.log('submit!')
                        this.dialogFormVisible = false
                        let data = await api.ajaxPost("app/save", this.form)
                        if (data.code == "0") {
                            this.$message.success('操作成功')
                            if (!this.form.appid) {
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
                    appid: '',
                    appName: '',
                    status: '1',
                    publicKey: '',
                    bindLimit: 1,
                    merchantId: ''
                }
                let data = await api.ajaxPost("merchant/list", {})
                this.merchants = data.data
                this.dialogFormVisible = true
            },
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                para.appName = this.formInline.appName
                para.appid = this.formInline.appid
                let data = await api.ajaxPost("app/list", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.appDataList = data.data
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
                let d = this.appDataList[index]
                this.form.appName = d.appName
                this.form.status = "" + d.status
                this.form.publicKey = d.publicKey
                this.form.appid = d.appid
                this.form.bindLimit = d.bindLimit
                this.form.merchantId = d.merchant ? d.merchant.id : ''
                let data = await api.ajaxPost("merchant/list", {});
                this.merchants = data.data
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
