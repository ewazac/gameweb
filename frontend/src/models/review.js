export default class Review {
  constructor(data) {
      if(data){
          for(var i in data){
              this[i] = data[i];
          }
      }
  }
}