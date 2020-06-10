<template>
    <el-row>
        <el-col :span="24">
            <Head title="执行Ognl表达式"/>
            <el-row>
                <el-col :span="5">
                    <div style="width: 100%; display: flex;flex-wrap: wrap;">
                        <div style="color: #999;width: 100%;font-size: 0.9rem;margin-bottom: 5px;">
                            快捷操作：
                        </div>
                        <el-button class="shortcuts" type="info" size="mini" plain
                                   @click="setShortcutOgnlText(item.command)"
                                   v-for="(item,index) in shortcuts" :key="index">{{item.label}}
                        </el-button>
                    </div>
                </el-col>
                <el-col :span="14">
                    <el-input
                            type="textarea"
                            :rows="6"
                            @keyup.enter.native="runOgnl"
                            placeholder="请输入Ognl表达式"
                            v-model="ognlText">
                    </el-input>
                </el-col>
            </el-row>
            <el-row style="margin-top: 20px;">
                <el-col :span="16" :offset="4">
                    <div style="display: flex;justify-content: center;">
                        <el-button type="primary" @click="runOgnl">执行</el-button>
                    </div>
                </el-col>
            </el-row>
            <el-row style="margin-top: 30px;">
                <el-col :span="20" :offset="2">
                    <el-input
                            type="textarea"
                            :rows="25"
                            disabled
                            placeholder="结果区"
                            v-model="resText">
                    </el-input>
                </el-col>
            </el-row>
        </el-col>
    </el-row>

</template>

<script>
    import Head from '../../components/Head'
    import {mapActions, mapMutations} from "vuex";

    export default {
        name: "Ognl",
        data() {
            return {
                resText: '',
                shortcuts: [
                    {
                        label: '系统时间',
                        command: '@java.util.Calendar@getInstance().getTime()'
                    }
                ]
            }
        },
        methods: {
            ...mapActions([
                'ognlTest'
            ]),
            ...mapMutations([
                'setOgnlText'
            ]),
            setShortcutOgnlText(val){
                this.setOgnlText({ognlText: val})
            },
            runOgnl() {
                this.ognlTest({ognlText: this.ognlText}).then(res => {
                    this.resText = res.data
                })
            }
        },
        computed: {
            ognlText: {
                set(val) {
                    this.setOgnlText({ognlText: val})
                },
                get() {
                    return this.$store.state.Ognl.ognlText
                }
            }
        },
        components: {
            Head
        }
    }
</script>

<style>
    .el-textarea.is-disabled .el-textarea__inner {
        background-color: white !important;
        color: #555 !important;
    }

    .shortcuts {
        margin: 4px 0;
    }
</style>