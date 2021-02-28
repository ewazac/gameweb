import Request from '../request'
import store from '../store'
export default class News {
    constructor(data = null) {
        if(data){
            for(var i in data){
                this[i] = data[i];
            }
        }
    }
    getNewsById(id){
        return new Promise((resolve) => {
            Request({
                url:'/news',
                method:'get'
            }).then(res => {
                resolve(res.find(x => x.id == id));
            })
        })

    }
    save(){
        return new Promise((resolve, reject) => {
            const fd = new FormData();
            fd.append('title', this.title);
            fd.append('description', this.description);
            fd.append('body', this.body);
            var method = 'post';
            var url = '/news/addNews';
            var data = null;
            if(this.id){
                method = 'put'
                url = 'news/'+this.id
                data = this;
            }else{
                fd.append("image", this.image);
                data = fd;
            }
            Request({
                method: method,
                data: data,
                url:url
            }).then(res => {
                resolve(res);
            }).catch(e => {
                reject(e);
            })
        })
    }
    toggleFavourite(){
        Request({
            url: '/fav/'+this.id,
            method: 'post',
        }).then(() => {
            store.dispatch('app/getFavourites');
            store.commit('app/ADD_MESSAGE', {text: 'Udało się dodać do ulubionych'});
        })
    }
}