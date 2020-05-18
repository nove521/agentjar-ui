<template>
    <div class="content">
        <div>
            <el-input class="serche" v-model="name" placeholder="请输入类名"></el-input>
            <el-button class="update" type="primary">反编译</el-button>
        </div>
        <div>
            <textarea ref="mycode" class="codesql" v-model="code" ></textarea>
        </div>
        <el-button class="update" type="primary" @click="iHotUpdate">更新</el-button>
    </div>
</template>

<script>
    import "codemirror/theme/ambiance.css";
    import "codemirror/lib/codemirror.css";
    import "codemirror/addon/hint/show-hint.css";

    let CodeMirror = require("codemirror/lib/codemirror");
    require("codemirror/addon/edit/matchbrackets");
    require("codemirror/addon/selection/active-line");
    require("codemirror/addon/hint/show-hint");
    require("codemirror/mode/sql/sql");
    require("codemirror/addon/selection/active-line.js");

    import {mapActions, mapMutations} from 'vuex'
    import store from "../../vuex/store";

    export default {
        name: "HotUpdate",
        data() {
            return {
                cm: undefined
            }
        },
        mounted() {
            this.initData()
        },
        methods: {
            ...mapActions([
                'hotUpdate',
            ]),
            ...mapMutations([
                'setJavaCode',
                'setJavaCode'
            ]),
            iHotUpdate() {
                this.setJavaCode({code:this.cm.getValue()})
                this.hotUpdate()
            },
            initData(){
                let mime = 'text/x-mariadb'
                // let theme = 'ambiance'//设置主题，不设置的会使用默认主题
                let cm = CodeMirror.fromTextArea(this.$refs.mycode, {
                    mode: mime,//选择对应代码编辑器的语言，我这边选的是数据库，根据个人情况自行设置即可
                    indentWithTabs: true,
                    smartIndent: true,
                    lineNumbers: true,
                    matchBrackets: true,
                    // theme: theme,
                    autofocus: true,
                    extraKeys: {'Ctrl': 'autocomplete'}//自定义快捷键
                })
                this.cm = cm
            }
        },
        computed: {
            code(){
                return store.state.HotUpdate.code
            },
            name() {
                return store.state.HotUpdate.name
            }
        },
        components: {
        }
    }
</script>

<style >
    .update {
        margin-top: 20px;
    }

    .serche {
        width: 350px;
        margin-bottom: 20px;
        margin-right: 10px;
    }

    .codesql {
        font-size: 11pt;
        font-family: Consolas, Menlo, Monaco, Lucida Console, Liberation Mono, DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace, serif;
    }
    .CodeMirror{
        height: 700px !important;
    }

</style>