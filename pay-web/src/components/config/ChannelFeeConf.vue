<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="渠道名称">
                    <el-select v-model="formInline.channelCode" placeholder="请选择渠道" :clearable="true">
                        <el-option v-for="item in channels" :key="item.code" :label="item.name" :value="item.code"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click.stop="handleCurrentChange">查 询</el-button>
                    <el-button @click.stop="onCreate" type="warning">创 建</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table class="t-a-l" :data="dataList" :current-page="currentPage" :page-size="pageSize" style="width: 100%" :row-class-name="tableRowClassName">
                <el-table-column prop="payChannel.code" label="渠道编号" width="150">
                </el-table-column>
                <el-table-column prop="payChannel.name" label="渠道名称" width="150">
                </el-table-column>
                <el-table-column prop="app.appName" label="APP名称" width="180">
                </el-table-column>
                <el-table-column :formatter.sync="formatEnable" prop="enable" label="是否启用" width="120">
                </el-table-column>
                <el-table-column :formatter.sync="formatStatus" prop="status" label="状态" width="120">
                </el-table-column>
                <el-table-column prop="payFeeRate" label="支付费率(%)" width="150">
                </el-table-column>
                <el-table-column prop="authFee" label="鉴权(绑卡)费用(元)" width="150">
                </el-table-column>
                <el-table-column prop="payFeeMin" label="支付费用保底值(元)" width="150">
                </el-table-column>
                <el-table-column prop="payFeeMax" label="支付费用封顶值(元)" width="150">
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
                    <el-form-item label="渠道" :label-width="formLabelWidth" prop="channelCode">
                        <el-select v-model="form.channelCode" placeholder="请选择渠道" :disabled="disableSelect">
                            <el-option v-for="item in channels" :key="item.code" :label="item.name" :value="item.code"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="APP" :label-width="formLabelWidth" prop="appid">
                        <el-select v-model="form.appid" placeholder="请选择APP" :disabled="disableSelect">
                            <el-option v-for="item in apps" :key="item.appid" :label="item.appName" :value="item.appid"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="是否启用" :label-width="formLabelWidth">
                        <el-select v-model="form.enable" placeholder="请选择是否启用">
                            <el-option label="未启用" value="false"></el-option>
                            <el-option label="启用" value="true"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="状态" :label-width="formLabelWidth">
                        <el-select v-model="form.status" placeholder="请选择渠道状态">
                            <el-option label="停用" value="0"></el-option>
                            <el-option label="正常" value="1"></el-option>
                            <el-option label="系统异常" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="支付费率(%)" :label-width="formLabelWidth" prop="payFeeRate">
                        <el-input v-model="form.payFeeRate" auto-complete="off" :maxlength="10" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="鉴权(绑卡)费用(元)" :label-width="formLabelWidth" prop="authFee">
                        <el-input v-model="form.authFee" auto-complete="off" :maxlength="10" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="支付费用保底值(元)" :label-width="formLabelWidth" prop="payFeeMin">
                        <el-input v-model="form.payFeeMin" auto-complete="off" :maxlength="10" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="支付费用封顶值(元)" :label-width="formLabelWidth" prop="payFeeMax">
                        <el-input v-model="form.payFeeMax" auto-complete="off" :maxlength="10" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="个性化配置项" :label-width="formLabelWidth" prop="payFeeMax">
                        <el-input v-model="form.config" auto-complete="off" type="textarea" :rows="10" class="dialog-input-width" ></el-input>
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
        name: "ChannelFeeConf",
        mounted() {
            this.handleCurrentChange()
            this.getAllChannel()
        },
        data() {
            return {
                channels: [],
                apps: [],
                total: 0,
                currentPage: 1,
                pageSize: 10,
                formInline: {
                    channelCode: '',
                },
                disableSelect: false,
                dataList: [],
                dialogFormVisible: false,
                form: {
                    id: '',
                    channelCode: '',
                    appid: '',
                    status: 1,
                    enable: true,
                    authFee: 0,
                    payFeeRate: 0,
                    payFeeMin: 0,
                    payFeeMax: 0,
                    config: ''
                },
                formLabelWidth: '140px',
                rules: {
                    channelCode: [{
                        required: true,
                        message: '请选择渠道',
                        trigger: 'blur'
                    }, ],
                    appid: [{
                        required: true,
                        message: '请选择APP',
                        trigger: 'blur'
                    }, ],
                },
            }
        },
        methods: {
            async getAllApp() {
                let data = await api.ajaxPost("app/listAll", {})
                if (data) {
                    this.apps = data
                }
            },
            async getAllChannel() {
                let data = await api.ajaxPost("channel/listAll", {})
                if (data && data.code == '0') {
                    this.channels = data.data
                }
            },
            async onSubmit() {
                try {
                    var valid = await this.$refs.form.validate()
                    if (valid) {
                        console.log('submit!')
                        this.dialogFormVisible = false
                        let data = await api.ajaxPost("channelFee/save", this.form)
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
                this.getAllApp()
                this.getAllChannel()
                this.form = {
                    id: '',
                    channelCode: '',
                    appid: '',
                    status: "1",
                    enable: "" + true,
                    authFee: 0,
                    payFeeRate: 0,
                    payFeeMin: 0,
                    payFeeMax: 0,
                    config: ''
                }
                this.disableSelect = false
                this.dialogFormVisible = true
            },
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                para.channelCode = this.formInline.channelCode
                let data = await api.ajaxPost("channelFee/list", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            formatStatus(row, column, cellValue) {
                if (cellValue == "0") {
                    return "停用"
                } else if (cellValue == "1") {
                    return "正常"
                } else if (cellValue == "2") {
                    return "系统异常"
                }
            },
            formatEnable(row, column, cellValue) {
                if (cellValue == false) {
                    return "未启用"
                } else if (cellValue == true) {
                    return "启用"
                }
            },
            formatConfig(row, column, cellValue) {
                if (row.config) {
                    return JSON.stringify(row.config)
                }
            },
            async handleEdit(index) {
                console.log(index)
                this.getAllApp()
                this.getAllChannel()
                let d = this.dataList[index]
                this.form.channelCode = d.payChannel.code
                this.form.appid = d.app.appid
                this.form.status = "" + d.status
                this.form.enable = "" + d.enable
                this.form.authFee = d.authFee
                this.form.payFeeRate = d.payFeeRate
                this.form.payFeeMin = d.payFeeMin
                this.form.payFeeMax = d.payFeeMax
                this.form.id = d.id
                this.form.config = JSON.stringify(d.config)
                this.disableSelect = true
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
