import Request from '../request';
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
    updateFirstLastName(){
        Request({
            url:'/users/changeFirstName?newFirstName='+this.firstName,
            method:'put',
            data: {newNick: this.firstName}
        }).then(() =>{

        })
        Request({
            url:'/users/changeLastName?newFirstName='+this.lastName,
            method:'put',
            data: {newNick: this.lastName}
        }).then(() =>{

        })
    }
}