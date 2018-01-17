<template>
    <div class="hello">
        <div class="query-bar">
            <el-form :inline="true" :model="formInline" class="bingcard-form-inline">
                <el-form-item label="应用">
                    <el-select v-model="formInline.appid" placeholder="请选择应用" :clearable="true">
                        <el-option v-for="item in apps" :key="item.appid" :label="item.appName" :value="item.appid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户ID" class="appuser-form-inline">
                    <el-input v-model="formInline.openid" placeholder="用户ID" :clearable="true" :maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="用户手机号">
                    <el-input v-model="formInline.phone" placeholder="用户手机号" :clearable="true" :maxlength="11"></el-input>
                </el-form-item>
                <el-form-item label="创建日期范围">
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
                <el-table-column prop="openid" label="用户ID" width="300">
                </el-table-column>
                <el-table-column prop="user.name" label="用户姓名" width="120">
                </el-table-column>
                <el-table-column prop="app.appName" label="应用名称" width="180">
                </el-table-column>
                <el-table-column prop="user.phone" label="用户电话" width="150">
                </el-table-column>
                <el-table-column prop="user.identityCode" label="身份证号" width="180">
                </el-table-column>
                <el-table-column :formatter.sync="formatSubscribed" prop="subscribed" label="是否关注状态" width="150">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" width="200">
                </el-table-column>
                <el-table-column>
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
        name: "AppUser",
        mounted() {
            this.handleCurrentChange()
            this.getAllApp()
        },
        data() {
            return {
                total: 0,
                currentPage: 1,
                pageSize: 10,
                apps: [],
                formInline: {
                    openid: '',
                    appid: '',
                    phone: '',
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
                para.openid = this.formInline.openid
                para.appid = this.formInline.appid
                para.phone = this.formInline.phone
                para.startTime = this.formInline.createTime ? this.formInline.createTime[0] || '' : ''
                para.endTime = this.formInline.createTime ? this.formInline.createTime[1] || '' : ''
                let data = await api.ajaxPost("appUser/listPage", para)
                this.total = data.total
                console.log(JSON.stringify(data))
                this.dataList = data.data
            },
            async getAllApp() {
                let data = await api.ajaxPost("app/listAll", {})
                this.apps = data || []
            },
            formatSubscribed(row, column, cellValue) {
                if (cellValue == true) {
                    return "正常"
                }
                return "已注销"
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
