<template>
    <el-card class="box-card">
        <div class="cards">
            <div class="content">
                <div class="item" v-for="(item,index) in data"
                     :key="index">
                    <div>{{item.key}}</div>：
                    <div>{{item.val}}</div>
                </div>
            </div>
            <el-button class="close" type="info" @click="close">关闭</el-button>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: "ClassInfo",
        props: {
            data: {
                type: Array,
                default: () => []
            }
        },
        methods: {
            close() {
                this.$emit('close')
            }
        }
    }
</script>

<style scoped>
    .box-card{
        background-color: white;
    }
    .cards{
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .content{
        padding: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .item{
        display: flex;
        line-height: 40px;
    }
    .close{
        margin: 10px 0;
    }
</style>