<template>
    <el-card class="box-card" shadow="hover">
        <div slot="header" class="clearfix">
            <span>JVM ID: {{item.id}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="checkInJvm">选择应用</el-button>
        </div>
        <div class="crad-content">
            <div class="left-img">
                java
            </div>
            <div class="right-text">
                {{item.name}}
            </div>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: "ListCard",
        props: {
            item: {
                type: Object,
                default: () => {
                }
            }
        },
        methods: {
            checkInJvm() {
                this.$emit('checkInJvm', this.item)
            }
        }
    }
</script>

<style>
    .box-card {
        width: 32%;
        margin: 0 1% 1% 0;
        color: #777;
        background-color: #eee;
    }

    .crad-content {
        display: flex;
        height: 100%;
        width: 100%;
    }

    .left-img {
        width: 80px;
        height: 80px;
        line-height: 80px;
        text-align: center;
        font-size: 1.5rem;
        font-weight: bold;
        background-color: aliceblue;
        color: red;
        border-radius: 10px;
    }

    .el-card__body {
        padding: 5px !important;
    }

    .right-text {
        padding: 0.9rem;
        display: flex;
        align-items: center;
    }
</style>