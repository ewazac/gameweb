import Request from '../request';
import store from '../store/index';
export default class User {
    constructor(obj) {
        if(obj){
            for(var i in obj){
                this[i] = obj[i];
            }
        }
    }
    saveNick(){
        /*   Request({
               url:'/users/addNick',
               method:'post',
               data: {nick: this.nick}
           }).then(() =>{

           })*/
        Request({
            url:'/users/changeNick?newNick='+this.nick,
            method:'put',
            data: {newNick: this.nick}
        }).then(() =>{

        })
    }
    updateUser(){
        var clone = Object.assign({}, this);
        delete clone.avatar;
        delete clone.roles;
        delete clone.password;
        (clone.newsletter == 1 || clone.newsletter == true)? clone.newsletter = true : clone.newsletter = false;
        Request({
            url:'/users/updateUser/'+this.id,
            method:'patch',
            data: clone
        }).then((res) =>{
            store.commit('auth/loginSuccess', res);
            localStorage.setItem('user', JSON.stringify(res));
        })
    }
    changeNewsletter(){
        Request({
            url:'/users/newsletter',
            method:'patch',
        }).then(() =>{

        })
    }
}