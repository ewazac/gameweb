<template>
    <div class="thread">
        <h3>{{thread.name}}</h3>
        <div class="FirstAnswers">
            <p class="Created">Użytkownik: {{ firstAnswer.username }} </p>
            <p class="Created">Dodano: {{ firstAnswer.createdDate }}</p>
            <h4 class="ThreadMessage"> {{ firstAnswer.message }} </h4>
        </div>
        <button class="button" v-if="Show==false" v-on:click="show">Dodaj wiadomość!</button>
        <form class="addAnswer" name="newAnswer" v-if="Show" @submit.prevent="handleAnswer">
            <p>Napisz nową wiadomość:</p>
            <b-form-textarea
                v-model="answer"
                type="answer"
                class="form-control"
                name="answer"
                required
            />
            <button class="button" type="submit">Dodaj</button>
        </form>
        <div v-if="admin">
            <div class="answers" v-for="answers in thread" :key="answers.username">
                <div class="details">
                    <p> Użytkownik: {{ answers.username }} Dodano: {{ answers.createdDate }} </p>
                    <span @click='handleDelete(answers.message)'>Usuń</span>
                </div>
                <div class="message">
                    <h5> {{ answers.message }}</h5>
                </div>
                
            </div>
        </div>
        <div v-else>
            <div class="answers" v-for="answers in thread" :key="answers.username">
                <div class="details">
                    <p> Użytkownik: {{ answers.username }} Dodano: {{ answers.createdDate }} </p>
                </div>
                <div class="message">
                    <h5> {{ answers.message }}</h5>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'Thread',
    data() {
        return {
            currentUser: JSON.parse(localStorage.getItem("user")),
            answer: '',
            thread: '',
            Show: false,
            firstAnswer: '',
            id: this.$router.history.current.query.thread,
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
        show: function() {
            if (this.Show) {
                this.Show = false
            }
            else {
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
                axios.delete("https://gameweb21.herokuapp.com/api/forums/"+this.id+"/answer/"+(i+1))
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
        handleAnswer() {/*
            const today = new Date();
            const date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
            const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
            const dateTime = date +'T'+ time;*/
            axios.patch("https://gameweb21.herokuapp.com/api/forums/"+this.id, {message: this.answer}, {withCredentials:true}).then((result) => {
                this.$router.go(0);
                console.log(result)
            }).catch((err) => {
                console.log(err)
            });
        },
    },
    mounted() {
        axios.get("https://gameweb21.herokuapp.com/api/forums")
        .then((result) => {
            let threads = result.data;
            for (const thread of threads) {
                if (thread.id == this.id){
                    this.firstAnswer = thread.answers[0]
                    this.thread = thread.answers.slice(1,thread.answers.length)
                    break
                }
            }
            console.log(this.thread, result.data)
        }).catch((err) => {
            console.log(err)
        });
    },
}
</script>
<style lang="scss" scoped>
.thread {
    margin: 3rem;
    color: white;
}
h3 {
    text-align: center;
    color:mediumaquamarine;
}
.answers {
    background-color: #222;
    border: 1px solid;
}
span {
    margin-left: auto;
    text-align: right;
    font-size: 0.9rem;
}
.details {
    font-size: 0.8rem;
    display: flex;
    padding: 0.5rem;
}
.message {
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
    padding: 1rem;
    width: 100%;
}
</style>