<template>
    <div style="font-size: 15px;">
        <p>方法列表：</p>
        <el-collapse>
            <el-collapse-item :name="index"
                              :disabled="!haveBean"
                              v-for="(item,index) in data"
                              :key="index">
                <template slot="title">
                    <div class="han">
                        <div>{{item.returnType}} <span style="font-weight: 700;">{{item.name}}</span>（{{item.paramsType.join(",")}}）
                        </div>
                        <div style="margin-right: 10px;" v-if="haveBean">
                            <el-button
                                    size="mini"
                                    type="primary">准备调用
                            </el-button>
                        </div>
                    </div>
                </template>
                <method-panel :info="item" :className="className"/>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
    import MethodPanel from './MethodPanel'

    export default {
        name: "MethodInfo",
        props: {
            haveBean: {
                type: Boolean,
                default: false
            },
            className: {
                type: String,
                default: ""
            },
            data: {
                type: Array,
                default: () => []
            }
        },
        methods: {},
        components: {
            MethodPanel
        }
    }
</script>

<style scoped>
    .han {
        width: 100%;
        display: flex;
        justify-content: space-between;
    }
</style>