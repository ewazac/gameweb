import Request from '../request'
export default class Quiz {
    constructor(data = null) {
        if(data){
            for(var i in data){
                this[i] = data[i];
            }
        }
        if(!this.body) this.body = '';
        if(!this.answers) this.answers= [];
    }
    getElementById(id){
        return new Promise((resolve) => {
            Request({
                url:'/api/quizy',
                method:'get'
            }).then(res => {
                var item = res.find(x => x.id == id);
                resolve(item);
                Request({
                    url:'/api/quizy/getQuizBody/'+item.id,
                    method:'get',
                }).then(res => {
                    console.log(res);
                })
            })
        })

    }
    save(){
        return new Promise((resolve, reject) => {
            var method = 'post';
            var url = '/api/quizy';
            var data = this;
            if(this.id){
                method = 'put'
                url = '/api/quizy/'+this.id
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
}