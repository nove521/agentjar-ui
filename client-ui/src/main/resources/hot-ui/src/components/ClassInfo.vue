<template>
    <div class="cards">
        <div class="content">
            <div style="width: 40%" v-for="(item,index) in data" :key="index">
               <span style="font-weight: 700"> {{item.name}}</span> : {{item.val}}
            </div>
        </div>
    </div>
</template>

<script>


    let NAME_MAP = {
        'name': '类名称',
        'SimpleName': '类简称',
        'isInterface': '接口类',
        'isAnnotation': '注解类',
        'isEnum': '枚举类',
        'isAnonymousClass': '匿名类',
        'isArray': '是数组',
        'isLocalClass': '本地类',
        'superClass': '父类是',
        'ClassLoad': '加载器',
        'classLoaderHash': '哈希值',
    }

    export default {
        name: "ClassInfo",
        props: {
            info: {
                type: Object,
                default: () => {
                }
            }
        },
        methods: {},
        computed: {
            data() {
                let result = []
                Object.keys(this.info).forEach(key => {
                    result.push({
                        name: NAME_MAP[key],
                        val: this.info[key] === false ? '不是': this.info[key] === true ? '是' : this.info[key]
                    })
                })
                return result;
            }
        }
    }
</script>

<style scoped>
    .box-card {
        background-color: white;
    }

    .cards {
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .content {
        width: 100%;
        padding: 20px 0;
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        color: #777777;
    }

    .item {
        display: flex;
        line-height: 40px;
    }

    .close {
        margin: 10px 0;
    }
</style>