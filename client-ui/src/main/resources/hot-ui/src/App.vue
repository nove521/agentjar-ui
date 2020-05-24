<template>
    <router-view/>
</template>

<script>
    import cookies from "./utils/cookies";
    import {mapActions} from 'vuex'

    export default {
        name: 'App',
        created() {
          this.isLogin()
        },
        methods: {
            ...mapActions([
                'setLogin'
            ]),
            isLogin() {
                if (cookies.isLogin()){
                    this.setLogin()
                    this.$router.replace('/')
                }
            }
        }
    }
</script>

<style>
    body, html {
        margin: 0;
        padding: 0;
    }
</style>
