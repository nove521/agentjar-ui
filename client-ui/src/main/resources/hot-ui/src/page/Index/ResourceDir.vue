<template>
    <el-container style="height: 100%;">
        <el-aside width="320px" class="dir-class">
            <p>目录</p>
            <el-tree :data="dirList" @node-click="checkFile"/>
        </el-aside>
        <el-container>
            <el-main>
                <textarea ref="filepanel" class="codesql" v-model="code"/>
                <div style="display: flex;justify-content: center;">
                    <el-button class="update" type="primary" @click="save">保存</el-button>
                </div>
            </el-main>
        </el-container>
    </el-container>
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

    import {mapActions, mapState} from 'vuex'
    import {Message} from "element-ui";
    import {SUCCEED, REQUEST_ERROR} from '@/utils/constant'

    export default {
        name: "ResourceDir",
        data() {
            return {
                currentFilePath: '',
                cm: undefined,
                code: ''
            }
        },
        created() {
            this.getResourceDir()
        },
        mounted() {
            this.initData()
        },
        methods: {
            ...mapActions([
                'getResourceDir',
                'getFileCode',
                'saveFileCode'
            ]),
            initData() {
                let mime = 'text/x-mariadb'
                this.cm = CodeMirror.fromTextArea(this.$refs.filepanel, {
                    mode: mime,//选择对应代码编辑器的语言，我这边选的是数据库，根据个人情况自行设置即可
                    indentWithTabs: true,
                    smartIndent: true,
                    lineNumbers: true,
                    matchBrackets: true,
                    autofocus: true,
                    extraKeys: {'Ctrl': 'autocomplete'}//自定义快捷键
                })
            },
            checkFile(data) {
                let {isFile, path} = data
                if (isFile) {
                    this.currentFilePath = path
                    this.getFileCode({fileName: path}).then(res => {
                        this.code = res.data
                        this.cm.setValue(res.data)
                        this.cm.refresh()
                    })
                } else {
                    this.currentFilePath = ''
                }
            },
            save() {
                this.code = this.cm.getValue()
                if (this.currentFilePath !== '' && this.currentFilePath !== undefined) {
                    this.saveFileCode({fileName: this.currentFilePath, code: this.code}).then(res => {
                        if (res.data >= 0) {
                            Message(SUCCEED("保存成功"));
                        } else {
                            Message(REQUEST_ERROR("保存失败"));
                        }
                    })
                }
            }
        },
        computed: {
            ...mapState({
                dirList: state => state.ResourceDir.dirList
            })
        }
    }
</script>

<style>

    .dir-class {
        border-right: #cccccc 3px solid;
    }

    .el-aside {
        color: #333;
        text-align: center;
        height: 100%;
    }

    .codesql {
        font-size: 11pt;
        font-family: Consolas, Menlo, Monaco, Lucida Console, Liberation Mono, DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace, serif;
    }

    .CodeMirror {
        height: 85vh !important;
    }
</style>