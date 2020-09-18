<template>
<div class="newgame">
      <div class="form">
        <h1 class="h1">
            Dodaj gre
        </h1>
        <form class="game" @submit.prevent="handleGame">
            <div class="form-group">
                <label>
                    Podaj nazwę gry
                </label>
                <input
                v-model="game.name"
                v-validate="'required|max:50'"
                type="name"
                class="form-control"
                placeholder="Nazwa gry"
                name="name"
                />
                <div
                v-if="submitted && errors.has('name')"
                class="alert-danger"
                >{{ 'Brak nazwy' }}</div>
            </div>
            <div class="form-group">
                <label>
                    Podaj kategorie
                </label>
                <input
                v-model="game.category"
                v-validate="'required|max:50'"
                type="category"
                class="form-control"
                placeholder="Kategoria"
                name="category"
                />
                <div
                v-if="submitted && errors.has('category')"
                class="alert-danger"
                >{{ 'Brak kategorii' }}</div>
            </div>
            <div class="form-group">
                <label>
                    Podaj opis gry
                </label>
                <textarea
                rows="5"
                v-model="game.description"
                v-validate="'required|min:50|max:1000'"
                type="description"
                class="form-control"
                placeholder="Krótki opis"
                name="description"
                ></textarea>
                <div
                v-if="submitted && errors.has('description')"
                class="alert-danger"
                >{{ 'Brak opisu lub opis jest zbyt krótki' }}</div>
            </div>
            <div class="form-group">
                <button class="btn btn-primary btn-block">Dodaj gre</button>
            </div>
            <div v-if="exist === true" class="alert-danger text-center">
                <h2> Ta gra została już dodana </h2>
            </div>
        </form>
      </div>
</div>
</template>

<script>
import {AXIOS} from '../http-commons';

export default {
  name: "AddGame",
  data() {
    return {
      error: [],
      submitted: false,
      successful: false,
      message: '',
      exist: false,
      game: {
        name: '',
        category: '',
        description: ''
      },
    };
  },
  methods: {
    handleGame() {
      
      this.$validator.validate().then((isValid) => {
        if (isValid) {
            this.submitted = true;
            AXIOS.post('games', {
                'category': this.game.category,
                'name': this.game.name,
                'description': this.game.description,
            })
            .catch(error => {
                this.message =
                    (error.response && error.response.data) ||
                    error.message ||
                    error.toString();
                if (error.response.status === 400)
                    this.exist = true;
            });
        }
        else {
            console.log("Nie działa")
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>

.h1 {
  text-align: center;
}
.newgame {
    display: grid;
    justify-content: center;
}
.form {
    width: 400px;
}

</style>
