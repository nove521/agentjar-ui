<template>
    <div>
        <el-card class="box-card">
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        prop="date"
                        label="信息"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="值"
                        width="180">
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "JvmInfo",
        data() {
            return {
                tableData: [{
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                }, {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                }, {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1516 弄'
                }]
            }
        },
        created() {
            this.initData()
        },
        methods: {
            initData() {

            }
        }
    }
</script>

<style scoped>

</style>