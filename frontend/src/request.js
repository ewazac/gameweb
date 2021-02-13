import axios from 'axios';
import store from './store';
const service = axios.create({
    baseURL: 'https://gameweb21.herokuapp.com/',
    timeout: 30000, // Request timeout
    withCredentials: true
});

// Request intercepter
service.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        Promise.reject(error);
    }
);

// response pre-processing
service.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        store.dispatch('app/add_message', {text: error.response.data.error, type: 'danger'});
        return Promise.reject(error);
    },
);

export default service;
