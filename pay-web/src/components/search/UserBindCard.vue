<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true" :model="formInline" class="bingcard-form-inline">
                <el-form-item label="支付卡ID">
                    <el-input v-model="formInline.id" placeholder="支付卡ID" :clearable="true" :maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="开户人姓名">
                    <el-input v-model="formInline.userName" placeholder="开户人姓名" :clearable="true" :maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="银行预留手机号">
                    <el-input v-model="formInline.mobile" placeholder="银行预留手机号" :clearable="true" :maxlength="11"></el-input>
                </el-form-item>
                <el-form-item label="银行卡号" class="bingcard-cardNo">
                    <el-input v-model="formInline.cardNo" placeholder="银行卡号" :clearable="true" :maxlength="25"></el-input>
                </el-form-item>
                <el-form-item label="绑卡日期范围">
                    <el-date-picker v-model="formInline.createTime" type="daterange" :unlink-panels="true" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click.stop="handleCurrentChange">查 询</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table class="t-a-l" :data="dataList" :current-page="currentPage" :page-size="pageSize" style="width: 100%" :row-class-name="tableRowClassName">
                <el-table-column prop="id" label="支付卡ID" width="120">
                </el-table-column>
                <el-table-column prop="userName" label="开户人姓名" width="120">
                </el-table-column>
                <el-table-column prop="mobile" label="银行预留手机号" width="120">
                </el-table-column>
                <el-table-column prop="bank.bankName" label="开户银行" width="150">
                </el-table-column>
                <el-table-column prop="cardNo" label="银行卡号" width="180">
                </el-table-column>
                <el-table-column prop="idCardNo" label="身份证号" width="180">
                </el-table-column>
                <el-table-column prop="usedCount" label="使用次数" width="120">
                </el-table-column>
                <el-table-column :formatter.sync="formatDefaultCard" prop="defaultCard" label="是否是默认支付卡" width="150">
                </el-table-column>
                <el-table-column :formatter.sync="formatStatus" prop="status" label="状态" width="120">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" width="200">
                </el-table-column>
                <el-table-column prop="lastUsed" label="最后使用时间" :formatter="formatDate" width="200">
                </el-table-column>
            </el-table>
            <el-pagination background layout="total, prev, pager, next" @current-change="handleCurrentChange" :current-page.sync="currentPage" :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import api from "@/api/"
    export default {
        name: "UserBindCard",
        mounted() {
            this.handleCurrentChange()
        },
        data() {
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                formInline: {
                    id: '',
                    userName: '',
                    mobile: '',
                    cardNo: '',
                    createTime: [],
                },
                dataList: [],
            }
        },
        methods: {
            async handleCurrentChange() {
                let para = {}
                para.currentPage = this.currentPage
                para.pageSize = this.pageSize
                para.userName = this.formInline.userName
                para.mobile = this.formInline.mobile
                para.cardNo = this.formInline.cardNo
                para.id = this.formInline.id
                para.startTime = this.formInline.createTime ? this.formInline.createTime[0] || '' : ''
                para.endTime = this.formInline.createTime ? this.formInline.createTime[1] || '' : ''
                let data = await api.ajaxPost("userBindCard/listPage", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            formatStatus(row, column, cellValue) {
                if (cellValue == "0") {
                    return "已删除"
                } else if (cellValue == "1") {
                    return "正常"
                } else if (cellValue == "2") {
                    return "已解绑"
                }
            },
            formatDefaultCard(row, column, cellValue) {
                if (cellValue == true) {
                    return "是"
                }
                return "否"
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
