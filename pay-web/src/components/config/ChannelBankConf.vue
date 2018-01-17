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
                <el-table-column prop="payChannel.name" label="渠道名称" width="180">
                </el-table-column>
                <el-table-column prop="bank.bankCode" label="银行编号" width="150">
                </el-table-column>
                <el-table-column prop="bank.bankName" label="银行名称" width="180">
                </el-table-column>
                <el-table-column :formatter.sync="formatStatus" prop="status" label="状态" width="150">
                </el-table-column>
                <el-table-column prop="min" label="最小金额" width="150">
                </el-table-column>
                <el-table-column prop="max" label="最大金额" width="150">
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
                    <el-form-item label="银行" :label-width="formLabelWidth" prop="bankCode">
                        <el-select v-model="form.bankCode" placeholder="请选择银行" :disabled="disableSelect">
                            <el-option v-for="item in banks" :key="item.bankCode" :label="item.bankName" :value="item.bankCode"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="状态" :label-width="formLabelWidth">
                        <el-select v-model="form.status" placeholder="请选择渠道状态">
                            <el-option label="未开通" value="0"></el-option>
                            <el-option label="正常" value="1"></el-option>
                            <el-option label="关闭" value="2"></el-option>
                            <el-option label="异常" value="3"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="最小金额" :label-width="formLabelWidth" prop="min">
                        <el-input v-model.number="form.min" auto-complete="off" :maxlength="10" :minlength="1"></el-input>
                    </el-form-item>
                    <el-form-item label="最大金额" :label-width="formLabelWidth" prop="max">
                        <el-input v-model.number="form.max" auto-complete="off" :maxlength="10" :minlength="1"></el-input>
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
        name: "ChannelBankConf",
        mounted() {
            this.handleCurrentChange()
            this.getAllChannel()
        },
        data() {
            var validateMin = (rule, value, callback) => {
                if (!value && value != 0) {
                    callback(new Error('最小金额不能为空'));
                } else if (!Number.isInteger(value)) {
                    callback(new Error('最小金额必须是数字'));
                } else {
                    callback();
                }
            };
            var validateMax = (rule, value, callback) => {
                if (!value && value != 0) {
                    callback(new Error('最大金额不能为空'));
                }
                if (!Number.isInteger(value)) {
                    callback(new Error('最大金额必须是数字'));
                } else if (value <= this.form.min) {
                    callback(new Error('最大金额要大于最小金额'));
                } else {
                    callback();
                }
            };
            return {
                channels: [],
                banks: [],
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
                    bankCode: '',
                    status: 1,
                    max: 1,
                    min: 0,
                },
                formLabelWidth: '100px',
                rules: {
                    channelCode: [{
                        required: true,
                        message: '请选择渠道',
                        trigger: 'blur'
                    }, ],
                    bankCode: [{
                        required: true,
                        message: '请选择银行',
                        trigger: 'blur'
                    }, ],
                    min: [{
                        validator: validateMin,
                        trigger: 'blur'
                    }],
                    max: [{
                        validator: validateMax,
                        trigger: 'blur'
                    }],
                },
            }
        },
        methods: {
            async getAllBank() {
                let data = await api.ajaxPost("bank/list", {})
                if (data) {
                    this.banks = data
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
                        let data = await api.ajaxPost("channelBank/save", this.form)
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
                this.getAllBank()
                this.getAllChannel()
                this.form = {
                    channelCode: '',
                    bankCode: '',
                    status: '1',
                    max: 1,
                    min: 0
                }
                this.disableSelect = false
                this.dialogFormVisible = true
            },
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                para.channelCode = this.formInline.channelCode
                let data = await api.ajaxPost("channelBank/list", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            formatStatus(row, column, cellValue) {
                if (cellValue == "0") {
                    return "未开通"
                } else if (cellValue == "1") {
                    return "正常"
                } else if (cellValue == "2") {
                    return "关闭"
                } else if (cellValue == "3") {
                    return "异常"
                }
            },
            formatEnable(row, column, cellValue) {
                if (cellValue == false) {
                    return "未开启"
                } else if (cellValue == true) {
                    return "开启"
                }
            },
            async handleEdit(index) {
                console.log(index)
                this.getAllBank()
                this.getAllChannel()
                let d = this.dataList[index]
                this.form.channelCode = d.payChannel.code
                this.form.bankCode = d.bank.bankCode
                this.form.status = "" + d.status
                this.form.max = d.max
                this.form.min = d.min
                this.form.id = d.id
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
