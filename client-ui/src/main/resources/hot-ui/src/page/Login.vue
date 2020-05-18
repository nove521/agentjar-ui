<template>
    <div class="page">
        <div class="card">
            <el-input v-model="input"
                      ref="inputToken"
                      @keyup.enter.native="subInput"></el-input>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                input: ''
            }
        },
        mounted() {
            this.$refs.inputToken.focus()
        },
        methods: {
            subInput() {
                console.log(this.input)
                this.$store.commit('setIsLogin', {isLogin: true})
                this.$router.replace('/')
            }
        }
    }
</script>

<style scoped>
    .page {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .card {
        width: 500px;
        height: 200px;
    }
</style>