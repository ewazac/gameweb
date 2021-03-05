import Request from "../request";
import store from "../store";

export default class Game {
    constructor(name, category, description, star, data) {
      this.name = name;
      this.category = category;
      this.description = description;
      this.star = star;
        if(data){
            for(var i in data){
                this[i] = data[i];
            }
        }
    }
    toggleFavourite(){
        Request({
            url: '/fav/game/'+this.appId,
            method: 'post',
        }).then(() => {
            store.dispatch('app/getFavourites');
            store.commit('app/ADD_MESSAGE', {text: 'Udało się dodać do ulubionych'});
        })
    }
}
