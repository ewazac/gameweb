<template>
    <div class="container">
        <div class="account">
            <div class="row" >
                <div class="col-md-3" v-if="isAdmin">
                    <!-- Sidebar -->
                    <admin-sidebar></admin-sidebar>
                    <!-- /#sidebar-wrapper -->
                </div>
            <div v-if="reviews" class="reviews">
                <h1 style="margin: 1rem;"> Recenzje do zmoderowania: {{ reviews.length }} </h1>
                <div style="padding: 3rem;">
                    <p> Gra: <router-link class="link" :to="'/game?game2='+reviews[currentReview].gameId"> {{ reviews[currentReview].gameName }} </router-link> </p>
                    <p> Tytuł: {{ reviews[currentReview].title }} </p>
                    <p> Stworzone przez: {{ reviews[currentReview].createdBy }} </p>
                    <p> Data dodania: {{ reviews[currentReview].createdDate }} </p>
                    <p> Ocenione na: <span style="color:#419c7b;">{{ reviews[currentReview].stars }}</span> </p>
                    <p> Opis: {{reviews[currentReview].description }} </p>
                    <div class="image" v-if="reviews[currentReview].imageUrl">
                        <p> Plik dodany do recenzji: </p>
                        <img :src="'https://gameweb.s3.eu-central-1.amazonaws.com/'+reviews[currentReview].imageUrl">
                    </div>
                </div>
                <div class="buttons">
                    <b-button v-if="currentReview==0" class="button" disabled>Poprzedni</b-button>
                    <b-button v-else class="button" @click="Previous">Poprzedni</b-button>
                    <div class="acceptButtons">
                        <b-button class="button" @click="handleReject">Odrzuć</b-button>
                        <b-button class="button" @click="handleAccept">Akceptuj</b-button>
                    </div>
                    <b-button v-if="currentReview < reviews.length && reviews.length>1" class="button" @click="Next">Następny</b-button>
                    <b-button v-else class="button" disabled>Następny</b-button>
                </div>
            </div>
            <div v-else class="reviews">
                <h1 style="margin: 1rem;">Nie ma żadnych recenzji do zmoderowania! :)</h1>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Review_accept',
    data() {
        return {
            currentUser: JSON.parse(localStorage.getItem("user")),
            reviews: null,
            currentReview: 0
        }
    },
    computed: {
        isAdmin(){
            return this.$store.getters['auth/isAdmin'];
        },
    },
    methods: {
        Previous() {
            this.currentReview -= 1;
        },
        Next() {
            this.currentReview += 1;
        },
        handleReject() {
            if(confirm("Czy na pewno chcesz usunąć tą recenzje!")) {
                axios.delete('https://gameweb.projektstudencki.pl/api/reviews/'+this.reviews[this.currentReview].id)
                .then(() => {
                    this.$delete(this.reviews, this.currentReview);
                    if (this.currentReview <  this.reviews.length) {
                        this.currentReview += 1;
                    }
                    else if (this.currentReview > 0) {
                        this.currentReview -= 1;
                    }
                })
                .catch(err => {
                    console.log(err)
                })
            }
        },
        handleAccept() {
            axios.patch('https://gameweb.projektstudencki.pl/api/reviews/accepted/'+this.reviews[this.currentReview].id)
            .then(() => {
                this.$delete(this.reviews, this.currentReview);
                if (this.currentReview < (this.reviews.length-1)) {
                    this.currentReview += 1;
                }
                else if (this.currentReview > 0) {
                    this.currentReview -= 1;
                }
            })
            .catch(err => {
                console.log(err)
            })
        }
    },
    created() {
        axios.get('https://gameweb.projektstudencki.pl/api/reviews/unaccepted')
        .then(result => {
            this.reviews = result.data
        })
        .catch(err => {
            console.log(err)
        })
    }
}
</script>

<style lang="scss" scoped>
.reviews {
    background-color: #222;
    color: white;
    width: 75%;
    text-align: left;
}
.row {
    justify-content: space-evenly;
    flex-direction: row;
    align-items: stretch;
}
.link {
    color: white;
}
.link:hover, .link:active{
    color: #419c7b;
}
.buttons {
    padding: 0 1rem;
    width: 100%;
    display: inline-flex;
    justify-content: space-between;
    align-items: baseline;
    flex-direction: row;
}
.acceptButtons button{
    margin: 0 5px;
}
.button {
    background-color: mediumaquamarine !important;
    margin-top: auto;
    border: none;
    color: #fff;
}
.button:hover, .button:active{
    background-color: #38866a !important;
}
</style>