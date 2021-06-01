<template>
  <div class="container">
    <div class="account">
      <div class="row" style="justify-content: space-around;">
        <div class="col-md-9">
          <div class="w-100">
            <b-card
                    title="Dane użytkownika"
                    tag="article"
                    class="mb-2 w-100"
            >
              <div class="badge-holder" style="padding: 5px;">
                <img v-if="user.point > 4 && user.point < 10" src="../assets/normal.png" width="50px">
                <img v-if="user.point >= 10 && user.point <= 14" src="../assets/active.png" width="50px">
                <img v-if="user.point >= 15" src="../assets/s_active.png" width="50px">
              </div>
            <b-card-text class="card-text-dark">
                <h3 class="h5">Dane osobowe</h3>
                <div class="my-4">
                  <div class="row">
                    <div class="col-md-10" style="padding-top:0.25rem;">
                      <div class="avatar-holder"><img :src="'https://gameweb.s3.eu-central-1.amazonaws.com/'+user.id+'.png'"></div>
                    </div>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Imię i nazwisko:</div>
                  <div class="col-md-10">{{user.firstName}} {{user.lastName}} </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Ilość punktów:</div>
                  <div class="col-md-10">
                    <span class="font-weight-bold">{{user.point}}</span>
                    <b-icon id="question-target" class="ml-3" icon="question-circle"></b-icon>
                    <b-tooltip target="question-target" triggers="hover">
                      Punkty przyznawane za recenzje, 1recenzja = 1punkt
                    </b-tooltip>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Nick:</div>
                  <div class="col-md-10"><span v-if="user.nick">{{user.nick}}</span><span v-else class="text-muted">Brak nick'u</span></div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center" v-if="user.news.length > 0">
                    <b-link class="newsModal" v-b-modal.modal-scrollable-news>Ulubione newsy</b-link>
                    <b-modal id="modal-scrollable-news" ok-only ok-title="zamknij" title="Ulubione newsy"> 
                      <router-link class="news" v-for="item in user.news" :key="item.id" :to="'/news/'+item.id"> {{ item.title }} - {{ item.description }} <b-icon scale="1.3" class="mr-2" icon="eye-fill" color="#007bff"></b-icon></router-link>
                    </b-modal>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center" v-if="games.length > 0">
                    <b-link  class="newsModal" v-b-modal.modal-scrollable-games>Ulubione gry</b-link>
                    <b-modal id="modal-scrollable-games" ok-only ok-title="zamknij" title="Ulubione gry"> 
                      <router-link class="news" v-for="item in games" :key="item.appId" :to="'/game/'+item.appId"> {{ item.title }} <b-icon scale="1.3" class="mr-2" icon="eye-fill" color="#007bff"></b-icon></router-link>
                    </b-modal>
                  </div>
                </div>
            </b-card-text>
            </b-card>
            </div>
            </div>                  
        </div>
    </div>
</div>
</template>

<script>
import axios from "axios";

export default {
  name: "user-detail",
  beforeCreate: function () {
    document.body.className = "app__body";
  },
  data() {
    return {
      user: "",
      games: []
    };
  },
  methods: {
    GameName() {
     for(var item of this.user.gamesIds) {
       axios.get("https://gameweb.projektstudencki.pl/apigames/api/apps/" + item)
        .then(result => {
          this.games.push(result.data)
        })
        .catch()
      }
      console.log(this.games)
    }
  },
  mounted() {
    axios
      .get("https://gameweb.projektstudencki.pl/api/users/details/" +this.$router.history.current.query.user)
      .then((result) => {
        this.user = result.data;
        this.user.id = this.$router.history.current.query.user;
        this.GameName();
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style lang="scss" scoped>
  .newsModal {
    color: #9de0c8;
  }
  .news {
    display: block;
  }
  .news:hover, .news:active, .newsModal:hover, .newsModal:active {
    color: #59b493;
  }
  .account {
  /*width: 70%;*/
  /*background-color: black;*/
  /*padding: 20px 30px 30px;*!*/
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 0;
  -webkit-border-radius: 0;
  border-radius: 0;
  transition: 0.2s;
  }
  .form-control {
    background-color: black;
  }

  .input{
    width: 100%;
    border-width: 0px 0px 2px 0px;
    border-radius: 0;
    border: 1px solid #111;
    color: mediumaquamarine;
    display: block !important;

    &:active {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
    }

    &:focus {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
    }
  }

  .card-body {
      display: flex;
      flex-direction: column;
      padding: 10px;
  }
  .card {
      padding: 10px;
      min-height: 15rem;
      max-height: 25rem;
      border-width: 0 0 2px 0;
      -moz-border-radius: 2px;
      -webkit-border-radius: 2px;
      border-radius: 2px;
      -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
      -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
      box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
  }
  .rev {
    margin: 0.5rem 0;
  }
  .reviews {
    color: whitesmoke;
  }
  .reviews:hover, .reviews:active, .reviews:link {
    color: #7bd5b4;
  }
  @media (max-width: 991px) {
    .account {
      width: 100%;
    }
  }
</style>