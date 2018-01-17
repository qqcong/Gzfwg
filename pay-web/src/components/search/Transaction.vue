<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true" :model="formInline">
                <el-form-item label="订单编号">
                    <el-input v-model="formInline.orderNo" placeholder="订单编号" :clearable="true" :maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="订单创建日期范围">
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
                <el-table-column prop="transId" label="支付流水" width="200">
                </el-table-column>
                <el-table-column prop="orderNo" label="订单编号" width="180">
                </el-table-column>
                <el-table-column prop="amount" label="订单总金额" width="120">
                </el-table-column>
                <el-table-column prop="payAmount" label="实际支付金额" width="120">
                </el-table-column>
                <el-table-column prop="appid" label="应用ID" width="300">
                </el-table-column>
                <el-table-column prop="cardId" label="支付卡ID" width="300">
                </el-table-column>
                <el-table-column prop="openid" label="支付用户ID" width="300">
                </el-table-column>
                <el-table-column :formatter.sync="formatPaid" prop="paid" label="是否已支付" width="100">
                </el-table-column>
                <el-table-column :formatter.sync="formatClosed" prop="isClosed" label="订单是否已关闭" width="120">
                </el-table-column>
                <el-table-column prop="timePaid" label="支付完成时间" :formatter="formatDate" width="200">
                </el-table-column>
                <el-table-column prop="transactionNo" label="第三方支付订单编号" width="300">
                </el-table-column>
                <el-table-column prop="requestNo" label="客户请求单号" width="250">
                </el-table-column>
                <el-table-column prop="subject" label="商品标题" width="150">
                </el-table-column>
                <el-table-column prop="body" label="商品描述" width="200">
                </el-table-column>
                <el-table-column prop="tradeStatus" label="交易状态" width="120">
                </el-table-column>
                <el-table-column prop="callbackUrl" label="支付回调地址" width="300">
                </el-table-column>
                <el-table-column prop="failureCode" label="失败代码" width="150">
                </el-table-column>
                <el-table-column prop="failureMsg" label="失败描述" width="300">
                </el-table-column>
                <el-table-column :formatter.sync="formatNotified" prop="notified" label="是否已通知业务系统" width="150">
                </el-table-column>
                <el-table-column prop="created" label="创建时间" :formatter="formatDate" width="200">
                </el-table-column>
                <el-table-column prop="clientIp" label="客户端IP" width="200">
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
        name: "Transaction",
        mounted() {
            this.handleCurrentChange()
        },
        data() {
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                formInline: {
                    orderNo: '',
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
                para.orderNo = this.formInline.orderNo
                para.startTime = this.formInline.createTime ? this.formInline.createTime[0] || '' : ''
                para.endTime = this.formInline.createTime ? this.formInline.createTime[1] || '' : ''
                let data = await api.ajaxPost("transaction/listPage", para)
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
            formatClosed(row, column, cellValue) {
                if (cellValue == true) {
                    return "是"
                }
                return "否"
            },
            formatPaid(row, column, cellValue) {
                if (cellValue == true) {
                    return "是"
                }
                return "否"
            },
            formatNotified(row, column, cellValue) {
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
