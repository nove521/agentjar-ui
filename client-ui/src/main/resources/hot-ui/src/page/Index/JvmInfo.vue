<template>
    <el-row>
        <el-col :span="24">
            <el-row>
                <el-col :span="18" :offset="3">
                    <el-card style="width: 100%;color: #333333;">
                        <div>
                            <div v-for="(item,index) in infoArr" :key="index">
                                {{item.name + "：" + item.val}}
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </el-col>
    </el-row>
</template>

<script>
    const MAP = {
        'name': '虚拟机名称',
        'specName': '规范名称',
        'managementSpecVersion': '规范版本',
        'specVendor': '供应商',
        'startTime': '启动时间',
        'uptime': '运行时间',
    }

    import {mapActions} from 'vuex'

    export default {
        name: "JvmInfo",
        data() {
            return {}
        },
        created() {
            this.initData()
        },
        methods: {
            ...mapActions([
                'initJvmInfo'
            ]),
            initData() {
                this.initJvmInfo()
            },
        },
        computed: {
            infoArr() {
                let res = []
                let info = this.$store.state.JvmInfo.info;
                console.log(info)
                Object.keys(info).forEach(key => {

                    if (MAP[key] !== undefined) {
                        res.push({
                            name: MAP[key],
                            val: info[key]
                        })
                    }

                })
                return res;
            }
        }
    }
</script>

<style scoped>

</style>