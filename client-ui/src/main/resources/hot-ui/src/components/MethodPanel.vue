<template>
    <div style="width: 100%;">
        <el-card class="method-card">
            <div style="display: flex;align-items: center;">
                <div style="width: 50px;">
                    入参：
                </div>
                <div style="display: flex;align-items: center;flex-direction: column;">
                    <div v-for="(item, index) in info.paramsType"
                         :key="index">
                        <el-input
                                class="prams-input"
                                v-model="forms[index]"
                                placeholder="请输入内容"
                                clearable>
                            <template slot="prepend">{{item}}</template>
                        </el-input>
                        <el-button
                                @click="newObject(index,item)"
                                size="mini"
                                type="primary">new 该对象
                        </el-button>
                    </div>
                    <span v-if="info.paramsType.length === 0">无</span>
                </div>
            </div>
            <div style="width: 100%; display: flex;justify-content: center;margin-top: 10px;">
                <el-button
                        @click="invoke"
                        size="mini"
                        type="primary">执行
                </el-button>
            </div>
            <div>
                结果：
                <el-input
                        disabled
                        type="textarea"
                        resize="none"
                        :rows="10"
                        placeholder="结果输出"
                        v-model="textarea">
                </el-input>
            </div>
        </el-card>
    </div>
</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "MethodPanel",
        props: {
            className: {
                type: String,
                default: ""
            },
            info: {
                type: Object,
                default: () => {
                }
            }
        },
        data() {
            return {
                textarea: '',
                forms: {}
            }
        },
        created() {
        },
        watch: {
            info(val) {
                this.params(val)
            }
        },
        methods: {
            ...mapActions([
                'invokeMethod'
            ]),
            newObject(index, type) {
                let val = `#{new ${type.toString().substring(type.toString().lastIndexOf(".") + 1)}()}`
                this.$set(this.forms, index, val)
            },
            invoke() {
                let parseFroms1 = this.parseFroms()
                let paramsJson = JSON.stringify(parseFroms1)
                let {name} = this.info
                let className = this.className
                this.invokeMethod({className, methodName: name, paramsJson}).then(res => {
                    let data = res.data
                    this.textarea = JSON.stringify(data)
                })
            },
            params(val) {
                let paramsType = val.paramsType
                let len = paramsType.length
                for (let i = 0; i < len; i++) {
                    this.$set(this.forms, i, '')
                }
            },
            parseFroms() {
                let json = []
                Object.keys(this.forms).forEach(key => {
                    json.push(this.forms[key])
                })
                return json
            }
        },
        computed: {}
    }
</script>

<style scoped>
    .method-card {
        width: 90%;
        margin-left: 5%;
    }

    .prams-input {
        margin: 5px 5px 5px 0;
        width: 600px;
    }
</style>