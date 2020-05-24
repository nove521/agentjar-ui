<template>
    <div>
        <Head title="查找类"
              :showF5="true"
              @f5="shua"
        ></Head>
        <div style="display: flex; width: 100%; padding: 5px 0 25px 0;color: #777777">
            <div style="width: 30%">简称</div>
            <div style="width: 30%;">完整路径</div>
            <div style="display: flex; width: 40%">
                <div class="input-i">
                    <i class="el-icon-aim" style="margin-right: 10px; font-size: 28px; cursor: pointer"
                       @click="check"
                    ></i>
                    <input class="search"
                           ref="inputSearch"
                           @keyup.enter="searchFun"
                           v-model="search"
                           placeholder="输入关键字搜索"/>
                </div>
                <el-button
                        size="mini"
                        type="danger"
                        @click="searchFun">搜索
                </el-button>
            </div>
        </div>
        <div  v-loading="loading">
            <el-collapse accordion>
                <el-collapse-item :name="index"
                                  v-for="(item,index) in list"
                                  :key="index">
                    <template slot="title">
                        <div style="display: flex; width: 100%; color: #333333">
                            <div style="width: 30%">{{item.SimpleName}}</div>
                            <div style="width: 65%;">{{item.name}}</div>
                            <div style="width: 5%">
                                <el-button
                                        size="mini"
                                        type="danger"
                                        @click.stop="handleDelete(item)">反编译
                                </el-button>
                            </div>
                        </div>
                    </template>
                    <class-info :info="item.info"/>
                    <method-info :data="item.methods" :className="item.name"></method-info>
                </el-collapse-item>
            </el-collapse>
        </div>
        <br/>
        <el-pagination
                :page-size="size"
                :pager-count="7"
                @current-change="pageChange"
                layout="prev, pager, next"
                :total="total">
        </el-pagination>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import ClassInfo from '@/components/ClassInfo'
    import MethodInfo from '@/components/MethodInfo'
    import Head from '../../components/Head'

    export default {
        data() {
            return {
                page: 1,
                size: 10,
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
                'getClassCodeSource',
                'getClassAllMethods'
            ]),
            find(index, row) {
                console.log(index, row);
            },
            handleEdit(item) {
                let className = item.name
                this.getClassInfo({className})
                this.getClassAllMethods({className})
            },
            handleDelete(item) {
                let className = item.name
                this.getClassCodeSource({className})
            },
            pageChange(page) {
                this.page = page
                this.loading = true
                this.getClassAll({page: this.page, size: this.size, pattenName: this.search}).then(() => {
                    this.loading = false
                }).catch(() => {
                    this.loading = false
                })
            },
            searchFun() {
                this.page = 1
                this.pageChange(this.page)
            },
            shua() {
                this.pageChange(this.page)
            },
            check() {
                this.$store.commit('initClassSearch')
                this.$refs.inputSearch.value = this.search
                this.searchFun()
            }
        },
        computed: {
            search: {
                get() {
                    return this.$store.state.Project.search
                },
                set(val) {
                    this.$store.commit('setClassSearch', {search: val})
                }
            },
            ...mapState({
                list: state => state.Project.classAll.list,
                total: state => state.Project.classAll.total,
                info: state => state.Project.classInfo,
            })
        },
        components: {
            ClassInfo,
            Head,
            MethodInfo
        }
    }
</script>
<style>
    .input-i {
        display: flex;
        align-items: center;
    }

    .search {
        width: 400px;
        margin-right: 10px;

        -webkit-appearance: none;
        color: #606266;
        height: 28px;
        line-height: 28px;
        background-color: #FFF;
        border-radius: 5px;
        padding: 0 15px;
        transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
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