<template>
    <el-menu
            :router="true"
            @select="selectItem"
            :default-active="currentAsideIndex"
            class="el-menu-vertical-demo">
        <el-menu-item index="1" :route="{path:'/'}" :disabled="isCheckJvm === true">
            <span slot="title">选择应用</span>
        </el-menu-item>

        <el-submenu index="2" :disabled="isCheckJvm === false">
            <template slot="title">
                <span>应用操作</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="2-1" :route="{ path: '/JvmInfo' }" :disabled="isCheckJvm === false">JVM信息
                </el-menu-item>
                <el-menu-item index="2-2" :route="{ path: '/FindClass' }" :disabled="isCheckJvm === false">查找类
                </el-menu-item>
                <el-menu-item index="2-3" :route="{ path: '/HotUpdate' }" :disabled="isCheckJvm === false">热更新
                </el-menu-item>
            </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="3" :route="{ path: '' }" :disabled="isCheckJvm === false">
            <span slot="title">退出应用</span>
        </el-menu-item>

        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="20%">
            <span>确认退出应用？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="affirm">确 定</el-button>
            </span>
        </el-dialog>

    </el-menu>
</template>

<script>
    import {mapState, mapActions, mapMutations} from 'vuex'

    export default {
        name: "MyAside",
        data() {
            return {
                dialogVisible: false
            }
        },
        created() {
        },
        methods: {
            ...mapActions([
                'outJvm'
            ]),
            ...mapMutations([
                'setCurrentAsideIndex'
            ]),
            affirm() {
                this.outJvm();
                this.dialogVisible = false
            },
            selectItem(index) {
                this.setCurrentAsideIndex({currentAsideIndex:index})
                switch (index) {
                    case '3':
                        this.dialogVisible = true
                }
            }
        },
        computed: mapState({
            isCheckJvm: state => state.System.isCheckJvm,
            currentAsideIndex: status => status.System.currentAsideIndex
        })
    }
</script>

<style scoped>

</style>