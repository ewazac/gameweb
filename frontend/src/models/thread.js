export default class Thread {
    constructor(data) {
        if(data){
            for(var i in data){
                this[i] = data[i];
            }
        }
    }
}