<template>
    <div class="thread">
        <div class="FirstAnswers">
            <h3>{{threadName}}</h3>
            <div class="FA">
                <div class="FADetails">
                    <p class="Created">Użytkownik: {{ firstAnswer.username }} </p>
                    <p class="Created">Dodano: {{ firstAnswer.createdDate }}</p>
                </div>
                <img class="avatar" v-bind:src="'https://gameweb.s3.eu-central-1.amazonaws.com/'+firstAnswer.userId+'.png'">
            </div>
            <h4 class="ThreadMessage"> {{ firstAnswer.message }} </h4>
        </div>
        <button class="button" v-if="Show==false" v-on:click="show">Dodaj wiadomość!</button>
        <form class="addAnswer" name="newAnswer" v-if="Show" @submit.prevent="handleAnswer">
            <p>Napisz nową wiadomość:</p>
            <b-form-textarea
                v-model="answer"
                type="text"
                class="form-control"
                name="answer"
                required
            />
            <button class="button" type="submit">Dodaj</button>
        </form>
        <b-alert v-if="alertMessage" show :variant='variant'>
            {{ alertMessage }}
        </b-alert>
        <div v-if="isAdmin">
            <div class="answers" v-for="answer in thread" :key="answer.createdDate">
                <div class="details">
                    <p style="margin-right:1rem;" @click="handleDetails(answer.userId)"> Użytkownik: {{ answer.username }}</p>
                    <p> Dodano: {{ answer.createdDate }}</p>
                    <img class="avatar" v-bind:src="'https://gameweb.s3.eu-central-1.amazonaws.com/'+answer.userId+'.png'">
                </div>
                <div class="message">
                    <span> {{ answer.message }}</span>
                </div>
                <span @click='handleDelete(answers.message)'>Usuń</span>
            </div>
        </div>
        <div v-else>
            <div class="answers" v-for="answer in thread" :key="answer.createdDate">
                <div class="details">
                    <p @click="handleDetails(answer.userId)"> Użytkownik:{{ answer.username }} Dodano: {{ answer.createdDate }} </p>
                </div>
                <div class="message">
                    <span> {{ answer.message }} </span>
                    <img class="avatar" v-bind:src="'https://gameweb.s3.eu-central-1.amazonaws.com/'+answer.userId+'.png'">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Thread from '../models/thread'

export default {
    name: 'Thread',
    data() {
        return {
            currentUser: JSON.parse(localStorage.getItem("user")),
            answers: '',
            thread: '',
            threadName: '',
            Show: false,
            firstAnswer: '',
            id: this.$router.history.current.query.thread,
            alertMessage: null,
            variant: null,
        }
    },
    beforeCreate: function () {
        document.body.className = "app__body";
    },
    computed: {
        isAdmin(){
            return this.$store.getters['auth/isAdmin'];
        }
    },
    methods: {
        handleDetails(item) {
            this.$router.push({path:'/User/', params:{user:item}, query:{user: item}});
        },
        show: function() {
            if (this.Show) {
                this.Show = false
            }
            else {
                this.alertMessage = null
                this.Show = true
            }
        },
        handleDelete(item) {
            let i = 0;
            console.log(item)
            for (const answer of this.thread) {
                console.log(answer.message)
                if (answer.message == item) {
                    break
                }
                i = i + 1
            }
            console.log(i)
            if(confirm("Czy na pewno chcesz usunąć tą wiadomość: "+item)) {
                axios.delete("https://gameweb.projektstudencki.pl/api/api/forums/"+this.id+"/answer/"+(i-1))
                .then((result) => {
                    this.$router.go(0);
                    console.log(result.data)
                }).catch((err) => {
                    console.log(err)
                });
            }
            else {
                console.log('nic się nie stało')
            }
        },
        handleAnswer() {
            let datetime = new Date().toJSON().slice(0,19);
            axios.patch("https://gameweb.projektstudencki.pl/api/api/forums/"+this.id, {
                createdDate: datetime,
                message: this.answer,
                userId: this.currentUser.id
            }, {withCredentials:true})
            .then((result) => {
                this.thread = this.thread.concat(result.data.answers[result.data.answers.length-1])
                this.answer = ''
                this.alertMessage = "Dodałeś nową odpowiedź!"
                this.variant = 'success'
            }).catch((err) => {
                console.log(err)
            });
        },
    },
    mounted() {
        axios.get("https://gameweb.projektstudencki.pl/api/api/forums")
        .then((result) => {
            let threads = result.data;
            console.log(result)
            for (const thread of threads) {
                if (thread.id == this.id){
                    this.firstAnswer = thread.answers[0]
                    this.threadName = thread.name
                    this.thread = thread.answers.slice(1,thread.answers.length)
                    break
                }
            }
            axios.get("https://gameweb.projektstudencki.pl/api/users/"+this.firstAnswer.userId)
            .then((result) => {
                this.firstAnswer.username = result.data.nick
                this.firstAnswer.createdDate = this.firstAnswer.createdDate.replace(/T/gm,' ')
            })
            .catch((err) => {
                console.log(err)
            })
            this.$store.commit('app/SET_BREADCRUMBS', [
                {
                    text: 'Forum',
                    to: '/forum'
                },
                {
                    text: this.firstAnswer.message
                }
            ])
            this.thread.map(item => {  
                axios.get("https://gameweb.projektstudencki.pl/api/users/"+item.userId)
                .then((result) => {
                    console.log(result)
                    item.username = result.data.nick;
                    return new Thread(item);
                })
                .catch((err) => {
                    console.log(err)
                })
            })
        }).catch((err) => {
            console.log(err)
        });
    },
}
</script>
<style lang="scss" scoped>
.thread {
    margin: auto !important;
    width: 75%;
    margin: 3rem;
    @media screen and(max-width: 1200px){
        margin: 10px;
        span{
            font-size: 12px !important;
        }
    }
    color: white;
}
h3 {
    font-size: 2.5rem;
    text-align: center;
    color:mediumaquamarine;
}
.answers {
    background-color: #222;
    border: 1px solid;
}
span {
    text-align: right;
    font-size: 0.9rem;
}
.details {
    font-size: 0.8rem;
    display: flex;
    padding: 0.2rem;
}
.details p {
    margin: 0;
}
.details span {
    margin-left: auto;
}
.message {
    display: flex;
    padding: 1rem;
}
.AD {
    padding: 1rem;
    max-width: 10%;
}
.addAnswer {
    border: 1px solid;
    padding: 1rem;
    background-color: #222;
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
    background-color: #389774
}
.button:active {
  background-color: #58c79f;
  box-shadow: 0 2.5px #666;
  transform: translateY(4px);
}
.FirstAnswers {
    background-color: #222;
    border: 1px solid;
    display: grid;
}
.Created {
    margin: 0;
    padding-left: 1rem;
    padding-right: 1rem;
    white-space: pre-line;
    font-size: 0.8rem;
}
.ThreadMessage {
    margin: auto;
    padding:1rem;
}
.message span {
    font-size: 1.5rem;
    font-weight: 450;
    white-space: pre-line;
    text-align: left;
}
.FA {
    margin: 0.5rem;
    display: flex;
}
.FA p {
    padding: 0;
}
.FADetails {
    display: block;
    margin-top: auto;
    margin-bottom: auto;
}
.avatar {
    margin-left: auto;
    max-width: 100px;
    max-height: 100px;
}
.message img {
    margin-left: auto;
}
input {
    background-color: white;
}
</style>