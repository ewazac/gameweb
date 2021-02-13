import {makeid} from "../helpers";

var treshold = 5000;
export const app = {
    namespaced: true,
    state: {
        messages:[]
    },
    actions: {
        add_message({ commit }, data) {
            if(!data.id) data.id = makeid(50);
            commit('ADD_MESSAGE', data);
            setTimeout(() => {
                commit('REMOVE_MESSAGE', data.id);
            }, treshold)
        }
    },
    mutations: {
        ADD_MESSAGE(state, data) {
            state.messages.push(data);
        },
        REMOVE_MESSAGE(state, id) {
            var index = state.messages.findIndex(x => x.id == id);
            if(index != -1){
                state.messages.splice(index, 1);
            }
        }
    },
    getters:{
        messages: state => {
            return state.messages;
        }
    }
};