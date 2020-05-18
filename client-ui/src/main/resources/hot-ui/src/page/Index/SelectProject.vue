<template>
    <div class="cards">
        <Head title="选择应用"
              :showF5="true"
              @f5="shua"
        ></Head>
        <list-card v-for="(item,index) in jvmlist"
                   :key="index"
                   :item="item"
                   @checkInJvm="checkInJvm"
        ></list-card>
    </div>
</template>

<script>
    import ListCard from "../../components/ListCard";
    import Head from '../../components/Head'
    import {mapActions, mapState} from 'vuex'

    export default {
        name: "SelectProject",
        created() {
            this.getJvmlist()
        },
        methods: {
            ...mapActions([
                'getJvmlist',
                'joinJvm'
            ]),
            checkInJvm(item) {
                this.joinJvm(item)
            },
            shua(){
                this.getJvmlist()
            }
        },
        computed: mapState({
            jvmlist: state => state.Project.jvmlist
        }),
        components: {
            ListCard,
            Head
        }
    }
</script>

<style scoped>
    .cards {
        display: flex;
        flex-wrap: wrap;
    }
</style>