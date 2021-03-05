import {makeid} from "../helpers";
import Request from '../request';
var treshold = 5000;
export const app = {
    namespaced: true,
    state: {
        messages:[],
        breadcrumbs: [],
        favourites: [],
        app_loading: false
    },
    actions: {
        getFavourites({commit}){
            var jar = [];
              jar.push(Request({
                  url:'/fav',
                  method:'get'
              }))
            jar.push(Request({
                url: '/fav/game',
                method: 'get'
            }))
            Promise.all(jar).then(res => {
                var array = [];
                var newses = res[0].map(item => {
                    item.type = 'news'
                    return item;
                })
                array = newses;
                var games = res[1].map(item => {
                    if(typeof item == 'object')
                    item.type = 'game';
                    return item;
                })
                games.forEach(item => {
                    array.push(item)
                })
                commit('SET_FAVOURITES', array);

            })
        },
        add_message({ commit }, data) {
            if(!data.id) data.id = makeid(50);
            commit('ADD_MESSAGE', data);
            setTimeout(() => {
                commit('REMOVE_MESSAGE', data.id);
            }, treshold)
        }
    },
    mutations: {
        SET_FAVOURITES(state, data){
            state.favourites = data;
        },
        ADD_MESSAGE(state, data) {
            if(!data.id) data.id = makeid(40);
            state.messages.push(data);
        },
        SET_BREADCRUMBS(state, data) {
            var first_item = {
                text: 'Strona główna',
                to: '/'
            }
            data.unshift(first_item);
            state.breadcrumbs = data;
        },
        REMOVE_MESSAGE(state, id) {
            console.log('ID', id);
            var index = state.messages.findIndex(x => x.id == id);
            if(index != -1){
                state.messages.splice(index, 1);
            }
        },
        START_LOADING(state){
            state.app_loading = true
        },
        STOP_LOADING(state){
            state.app_loading = false
        }
    },
    getters:{
        loading: state => {
            return state.app_loading;
        },
        messages: state => {
            return state.messages;
        },
        get_breadcrumbs: state => {
            return state.breadcrumbs;
        },

        get_favourites: state => {
            return state.favourites;
        }
    }
};
