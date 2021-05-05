import axios from 'axios';
import store from './store';
const service = axios.create({
    baseURL: 'https://gameweb.projektstudencki.pl/api',
    timeout: 30000, // Request timeout
    withCredentials: true
});

// Request intercepter
service.interceptors.request.use(
    config => {
        if(config.method == 'post' || config.method == 'put' || config.method == 'patch' || config.method == 'delete'){
            store.commit('app/START_LOADING');
        }
        return config;
    },
    error => {
        Promise.reject(error);
    }
);

// response pre-processing
service.interceptors.response.use(
    response => {
        store.commit('app/STOP_LOADING');
        return response.data;
    },
    error => {
        store.commit('app/STOP_LOADING');
        if(error.response && error.response.data && error.response.data.message){
            store.dispatch('app/add_message', {text: error.response.data.error+': '+error.response.data.message, type: 'danger'});
        }
        return Promise.reject(error);
    },
);

export default service;
