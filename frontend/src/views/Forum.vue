<template>
  <div class="main">
      <div class="search-wrapper">
            <input type="text" v-model="search" class="form-control w-100 py-2" placeholder="Wyszukaj wątek.."/>
        </div>  
    <div class="forum">
      <div class="newThread">
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
              v-model="title"
              type="title"
              class="form-control"
              placeholder="Tytuł"
              name="title"
              required
            />
          </div>
          <div class="idk">
            <p>Opis</p>
            <b-form-textarea
              v-model="answer"
              type="answer"
              class="form-control"
              placeholder="Opis"
              name="answer"
              required
            />
          </div>
          <button class="button" type="submit">Dodaj</button>
          <b-alert v-if="message" show variant="success">
            {{ message }}
          </b-alert>
        </form>
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
        <div class="thread" v-for="thread in displayedPosts" :key="thread.id">
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
        <div class="thread" v-for="thread in displayedPosts" :key="thread.id">
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
    <nav>
        <ul class="pagination">
          <li class="page-item">
            <button
              type="button"
              class="page-link"
              v-if="page != 1"
              @click="page--"
            >
              Previous
            </button>
          </li>
          <li class="page-item">
            <button
              type="button"
              class="page-link"
              v-for="pageNumber in pages.slice(page - 1, page + 5)"
              :key="pageNumber"
              @click="page = pageNumber"
            >
              {{ pageNumber }}
            </button>
          </li>
          <li class="page-item">
            <button
              type="button"
              @click="page++"
              v-if="page < pages.length"
              class="page-link"
            >
              Next
            </button>
          </li>
        </ul>
      </nav>
  </div>
</template>

<script>
import axios from 'axios';

export default{
    name: "Forum",
    data() {
        return {
            currentUser: JSON.parse(localStorage.getItem("user")),
            message: null,
            threads: '',
            title: '',
            answer: '',
            show: false,
            id: '',
            page: 1,
            perPage: 10,
            pages: [],
            search: '',
        }
    },
    beforeCreate: function () {
        document.body.className = "app__body";
    },
    computed: {
        filteredList() {
            return Object.values(this.threads).filter(post => {
                return post.name.toLowerCase().includes(this.search.toLowerCase())
            })
        },
        isAdmin(){
          return this.$store.getters['auth/isAdmin'];
        },
        displayedPosts () {
            return this.paginate(this.filteredList);
        }
    },
    methods: {
        Show: function() {
            if (this.show) {
                this.show = false
            }
            else {
                this.show = true
            }
        },
        handleDelete(item) {
            console.log(item)
            if(confirm("Czy na pewno chcesz usunąć wątek o nazwie: "+item.name)) {
                axios.delete("https://gameweb21.herokuapp.com/api/forums/"+item)
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
            axios.post("https://gameweb21.herokuapp.com/api/forums", {
                answers: [
                {
                    message: this.answer,
                    username: this.currentUser.nick,
                },
                ],
                name: this.title
            }).then((result) => {
                this.message = "Dodano nowy wątek!"
                console.log(result)
            }).catch((err) => {
                console.log(err)
            });
        },
        handleDetails(item) {
            console.log(item)
            this.$router.push({path:'/thread', params:{thread:item}, query:{thread:item}});
        },
        setPages () {
            let numberOfPages = Math.ceil(this.threads.length / this.perPage);
            for (let index = 1; index <= numberOfPages; index++) {
                this.pages.push(index);
            }
        },
        paginate (posts) {
            let page = this.page;
            let perPage = this.perPage;
            let from = (page * perPage) - perPage;
            let to = (page * perPage);
            return posts.slice(from, to);
        }
    },
    watch: {
        threads () {
            this.setPages();
        }
    },
    mounted() {
      this.$store.commit('app/SET_BREADCRUMBS', [
        {
          text: 'Forum',
          to: '/forum'
        }
      ])
        axios.get("https://gameweb21.herokuapp.com/api/forums")
        .then((result) => {
            this.threads = result.data;
            console.log(result.data)
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
.search-wrapper input{
    margin-top: 10px;
  margin-bottom: 10px;
    width: 30%;
}
  .main{
    div,h1,h2,h3,h4,h5,label,button{
      font-size: 12px !important;
    }
  }
</style>
