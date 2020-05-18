<template>
    <div class="head">
        <div>
            {{title}}
            <i v-if="showF5" @click="shuaFun" class="shuaxin el-icon-refresh"></i>
        </div>
        <div class="xian">
        </div>
    </div>
</template>

<script>
    export default {
        name: "Head",
        props: {
            title: {
                type: String,
                default: 'Title',
            },
            showF5: {
                type: Boolean,
                default: false
            }
        },
        methods: {
            shuaFun() {
                this.$emit("f5")
            }
        }
    }
</script>

<style scoped>
    .head {
        height: 70px;
        width: 100%;
        font-size: 23px;
        color: #777777;
    }

    .xian {
        height: 1px;
        width: 100%;
        margin: 15px 0;
        background-color: #dddddd;
    }

    .shuaxin {
        margin-left: 5px;
        color: #aaaaaa;
        cursor: pointer;
    }
</style>