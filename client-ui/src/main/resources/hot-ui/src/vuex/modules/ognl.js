import axios from "../../utils/axios";

export default {
    state: {
        ognlText: '',
    },
    mutations: {
        setOgnlText(state, {ognlText}) {
            state.ognlText = ognlText
        }
    },
    actions: {
        ognlTest({rootState}, {ognlText}) {
            return axios.post(rootState.ORNL_TEST_URL, {
                ognlText
            })
        }
    }
}