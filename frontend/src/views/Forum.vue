<template>
  <div class="main">
      <div class="search-wrapper">
            <input type="text" v-model="search" class="form-control w-100 py-2" placeholder="Wyszukaj wątek.."/>
        </div>  
    <div class="forum">
      <div class="newThread">
        <div style="width:75%;">
          <button class="button" v-on:click="Show">Utwórz nowy wątek!</button>
          <form
            class="addThread"
            name="newThread"
            v-if="show"
            @submit.prevent="handleThread"
          >
            <div class="idk">
              <p>Podaj tytuł</p>
              <b-form-input
                style="margin:0"
                v-model="title"
                type="text"
                class="form-control"
                placeholder="Tytuł"
                name="title"
                required
              />
            </div>
            <div class="idk">
              <p>Opis</p>
              <b-form-textarea
                style="margin:0"
                v-model="answer"
                type="text"
                class="form-control"
                placeholder="Opis"
                name="answer"
                required
              />
            </div>
            <div class="idk">
              <p>Kategoria</p>
              <b-form-select style="width:50%" v-model="newOption" :options="newOptions"></b-form-select>
            </div>
            <button class="button" type="submit">Dodaj</button>
            <b-alert v-if="alertMessage" show variant="success">
              {{ alertMessage }}
            </b-alert>
          </form>
        </div>
        <div v-if="show == false" style="width:25%;margin:auto;text-align:center;">
          <b-form-select style="max-width: 200px; text-align:right;" v-model="option" :options="Options"></b-form-select>
        </div>
      </div>
      <div v-if="isAdmin" class="T">
        <div class="Description">
          <h5 class="title">Nazwa</h5>
          <h5 class="AL">Liczba odpowiedzi</h5>
        </div>
        <div class="ADD">
          <h5 class="ADM">Usuń Wątek</h5>
        </div>
      </div>
      <div v-else class="T">
        <div class="Descriptionu">
          <h5 class="title">Nazwa</h5>
          <h5 class="AL">Liczba odpowiedzi</h5>
        </div>
      </div>
      <div v-if="isAdmin">
        <div class="thread" v-for="thread in List" :key="thread.id">
          <div class="thr">
            <div class="Description" @click="handleDetails(thread.id)">
                <h5 class="title">{{ thread.name }}</h5>
                <h5 class="AL" v-if="thread.answers">
                {{ thread.answers.length - 1 }}
                </h5>
                <h5 class="AL" v-else>0</h5>
            </div>
                <div class="ADD"  @click="handleDelete(thread.id)">
                 <h5 class="ADM">Usuń</h5>
                </div>
          </div>
        </div>
      </div>
      <div v-else>
        <div class="thread" v-for="thread in List" :key="thread.id">
          <div class="thr">
            <div class="Descriptionu" @click="handleDetails(thread.id)">
                <h5 class="title">{{ thread.name }}</h5>
                <h5 class="AL" v-if="thread.answers">
                {{ thread.answers.length - 1 }}
                </h5>
                <h5 class="AL" v-else>0</h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import User from '../models/user'
import {paginate} from "../helpers";

export default{
    name: "Forum",
    data() {
        return {
            currentUser: new User(JSON.parse(localStorage.getItem("user"))),
            message: null,
            threads: '',
            title: '',
            answer: '',
            show: false,
            id: '',
            search: '',
            paramsThreads:{
              per_page: 10,
              page: 1,
              total: 1
            },
            alertMessage: null,
            variant: null,
            List: '',
            option: "Wszystkie",
            Options: [
              { value: 'Wszystkie', text: 'Wszystkie'},
              { value: 'Ogólne', text: 'Ogólne'},
              { value: 'Eventy', text: 'Eventy'},
              { value: 'Problemy', text: 'Problemy'},
            ],
            newOption: 'Ogólne',
            newOptions: [
              { value: 'Ogólne', text: 'Ogólne'},
              { value: 'Eventy', text: 'Eventy'},
              { value: 'Problemy', text: 'Problemy'},
            ],
        }
    },
    beforeCreate: function () {
        document.body.className = "app__body";
    },
    computed: {
        categoriesList() {
          return Object.values(this.filteredList).filter(post => {
            return post.category.includes(this.option)
          })
        },
        filteredList() {
            return Object.values(this.threads).filter(post => {
                return post.name.toLowerCase().includes(this.search.toLowerCase())
            })
        },
        isAdmin(){
          return this.$store.getters['auth/isAdmin'];
        },
        filterdThreads(){
          return paginate(this.filteredList, this.paramsThreads.per_page, this.paramsThreads.page);
        },
    },
    watch: {
      option() {
        if(this.option == "Wszystkie") {
          this.List = this.filteredList
        }
        else {
          this.List = this.categoriesList
        }
      }
    },
    methods: {
        Show: function() {
            if (this.show) {
                this.show = false
            }
            else {
                this.alertMessage = null
                this.show = true
            }
        },
        handleDelete(item) {
            console.log(item)
            if(confirm("Czy na pewno chcesz usunąć wątek o nazwie: "+item.name)) {
                axios.delete("https://gameweb.projektstudencki.pl/api/api/forums/"+item)
                .then((result) => {
                    this.$router.go(0);
                    console.log(result.data)
                }).catch((err) => {
                    console.log(err)
                });
            }
            else {
                console.log("nic sie nie stało")
            }
        },
        handleThread() {
            //let datetime = new Date().toJSON().slice(0,19).replace(/T/g,' ');
            let datetime = new Date().toJSON().slice(0,19);
            axios.post("https://gameweb.projektstudencki.pl/api/api/forums", {
                answers: [{
                    createdDate: datetime,
                    message: this.answer,
                    userId: this.currentUser.id,
                }],
                category: this.newOption,
                name: this.title
                })
                .then((result) => {
                  this.alertMessage = "Dodano nowy wątek!"
                  this.threads = this.threads.concat(result.data)
                  this.answer = ''
                  this.title = ''
                  this.paramsThreads.total += 1;
                  this.$router.go();
                }).catch((err) => {
                    console.log(err)
                });
        },
        handleDetails(item) {
            this.$router.push({path:'/thread', params:{thread:item}, query:{thread:item}});
        },
    },
    mounted() {
        this.$store.commit('app/SET_BREADCRUMBS', [
          {
            text: 'Forum',
            to: '/forum'
          }
        ])
        axios.get("https://gameweb.projektstudencki.pl/api/api/forums")
        .then((result) => {
            this.threads = result.data;
            this.paramsThreads.total = this.threads.length;
            this.List = this.filteredList
        }).catch((err) => {
            console.log(err)
        });
    },
}


</script>

<style lang="scss" scoped>
.main {
  color: white;
  @media screen and (min-width: 1200px) {
    padding: 0 10rem 10rem 10rem;
  }
}
.forum {
  background-color: #222;
  display: grid;
  border: 1px solid;
}
.thread:hover {
  cursor: pointer;
  color: #58c79f;
}
.thread {
  display: flex;
}
.thr {
  display: inline-flex;
  margin: auto;
  border-bottom: 1px solid;
  width: 100%;
  height: 3rem;
}
.T {
  display: flex;
  border-top: 1px solid;
  border-bottom: 1px solid;
}
.title {
  margin: auto;
  margin-left: 0;
  text-align: left;
}
.AL {
  margin: auto;
  margin-right: 0.5rem;
  text-align: right;
}
.ADM {
  margin: auto;
}
.Description {
  min-width: 80%;
  display: flex;
  padding: 1rem;
  @media screen and (max-width: 1200px) {
    padding: 10px;
  }
}
.Descriptionu {
  width: 100%;
  display: flex;
  padding: 1rem;
  @media screen and (max-width: 1200px) {
    padding: 10px;
  }
}
.ADD {
  padding: 1rem;
  display: flex;
  min-width: 20%;
  border-left: 1px solid;
}
.idk {
    padding: 1rem;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-between;
}
.button {
  min-width: 5rem;
  margin: 1rem 2rem 1rem 2rem;
  padding: 0.5rem;
  background-color: #58c79f;
  color: #fff;
  outline: none;
  border: none;
  border-radius: 15px;
}
.button:hover {
  background-color: #389774;
}
.button:active {
  background-color: #58c79f;
  box-shadow: 0 2.5px #666;
  transform: translateY(4px);
}
input,
textarea {
  margin: auto;
  max-width: 80%;
}
button.page-link {
  display: inline-block;
}
button.page-link {
    font-size: 20px;
    background-color: #58c79f;
    color: #fff;
    font-weight: 500;
}
.pagination {
    margin: 1rem 2rem 1rem 2rem;
}
.search-wrapper {
    text-align: right;
  @media screen and (min-width: 1200px) {
    margin: 1rem 2rem 1rem 2rem;
  }

}
.newThread {
  display: flex;
}
input {
  background-color: white;
}
.search-wrapper input{
    margin-top: 10px;
  margin-bottom: 10px;
    width: 30%;
}
  .main{
    div,h1,h2,h3,h4,h5,label,button{
      font-size: 15px !important;
      @media screen and (max-width: 600px) {
        font-size: 13px !important;
      }
    }
  }
</style>