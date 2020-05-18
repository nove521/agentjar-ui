<template>
    <el-container>
        <el-aside width="200px">
            <my-aside/>
        </el-aside>
        <el-container>
            <el-main>
                <router-view/>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import MyAside from '../components/MyAside'

    export default {
        name: "Index",
        components: {
            MyAside
        }
    }
</script>

<style scoped>

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        height: 100vh;
    }

    .el-main {
        color: #333;
    }

</style>