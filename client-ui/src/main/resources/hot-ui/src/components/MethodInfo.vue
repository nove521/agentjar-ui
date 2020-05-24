<template>
    <div style="font-size: 15px;">
        <p>方法列表：</p>
        <div v-for="(item,index) in data" :key="index"
             style="display: flex; padding: 5px 0; justify-content: space-between; padding-right: 40px;border-bottom: 1px solid #dddddd;">
            <div>{{item.returnType}} <span style="font-weight: 700;">{{item.name}}</span>（{{item.paramsType.join(",")}}）
            </div>
            <el-button
                    @click="invoke(item)"
                    size="mini"
                    type="primary">执行
            </el-button>
        </div>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        name: "MethodInfo",
        props: {
            className: {
                type: String,
                default: ""
            },
            data: {
                type: Array,
                default: () => []
            }
        },
        methods: {
            ...mapActions([
                'invokeMethod'
            ]),
            invoke(item) {
                let {name} = item
                let className = this.className
                this.invokeMethod({className, methodName: name})
            }
        }
    }
</script>

<style scoped>

</style>