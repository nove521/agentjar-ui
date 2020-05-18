<template>
    <div>
        <Head title="查找类"
              :showF5="true"
              @f5="shua"
        ></Head>
        <el-table
                v-loading="loading"
                :data="list"
                style="width: 100%">
            <el-table-column
                    label="类名"
                    prop="SimpleName">
            </el-table-column>
            <el-table-column
                    label="完整类名"
                    prop="name">
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot="header">
                    <div style="display: flex">
                        <div class="input-i">
                            <input class="search" v-model="search" placeholder="输入关键字搜索" />
                        </div>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="searchFun">搜索
                        </el-button>
                    </div>
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">详情
                    </el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">反编译
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <br/>
        <el-pagination
                :page-size="size"
                :pager-count="7"
                @current-change="pageChange"
                layout="prev, pager, next"
                :total="total">
        </el-pagination>

        <div class="shade" v-if="showDetails">
            <class-info @close="close" :data="info"></class-info>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import ClassInfo from '@/components/ClassInfo'
    import Head from '../../components/Head'

    export default {
        data() {
            return {
                search: '',
                page: 1,
                size: 10,
                showDetails: false,
                loading: false
            }
        },
        created() {
            this.pageChange(this.page)
        },
        methods: {
            ...mapActions([
                'getClassAll',
                'getClassInfo',
                'getClassCodeSource'
            ]),
            find(index, row) {
                console.log(index, row);
            },
            handleEdit(index, row) {
                let className = row.name
                this.getClassInfo({className})
                this.showDetails = true
            },
            handleDelete(index, row) {
                let className = row.name
                this.getClassCodeSource({className})
            },
            close() {
                this.showDetails = false
            },
            pageChange(page){
                this.page = page
                this.loading = true
                this.getClassAll({page: this.page, size: this.size , pattenName: this.search}).then(()=>{
                    this.loading = false
                }).catch(()=>{
                    this.loading = false
                })
            },
            searchFun(){
                this.page = 1
                this.pageChange(this.page)
            },
            shua(){
                this.pageChange(this.page)
            }
        },
        computed: mapState({
            list: state => state.Project.classAll.list,
            total: state => state.Project.classAll.total,
            info: state => state.Project.classInfo
        }),
        components: {
            ClassInfo,
            Head
        }
    }
</script>
<style>
    .input-i{
    }
    .search {
        width: 425px;
        margin-right: 10px;

        -webkit-appearance: none;
        color: #606266;
        height: 28px;
        line-height: 28px;
        background-color: #FFF;
        border-radius: 5px;
        padding: 0 15px;
        transition: border-color .2s cubic-bezier(.645,.045,.355,1);
        border: 1px solid #DCDFE6;
        font-size: inherit;
        display: inline-block;
        text-rendering: auto;
        text-indent: 0px;
        text-shadow: none;
    }

    .shade {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(55, 55, 55, 0.5);
        z-index: 999;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>