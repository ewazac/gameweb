<template>
  <div class="newgame">
    <div class="form">
      <h1 class="h1">Dodaj gre</h1>
      <form class="game" @submit.prevent="handleGame">
        <div class="form-group">
          <label>Podaj nazwę gry</label>
          <input
            v-model="game.name"
            v-validate="'required|max:50'"
            type="name"
            class="form-control"
            placeholder="Nazwa gry"
            name="name"
          />
          <div v-if="submitted && errors.has('name')" class="alert-danger">{{ 'Brak nazwy' }}</div>
        </div>
        <div class="form-group">
          <label>Wybierz kategorie</label>
          <b-form-select
            v-validate="'required'"
            v-model="game.category"
            :options="options"
            size="sm"
            class="mt-3"
            name="category"
          ></b-form-select>
          <div
            v-if="submitted && errors.has('category')"
            class="alert-danger"
          >{{ 'Brak kategorii' }}</div>
        </div>
        <div class="form-group">
          <label>Podaj opis gry</label>
          <textarea
            rows="5"
            v-model="game.description"
            v-validate="'required|min:50'"
            type="description"
            class="form-control"
            placeholder="Krótki opis"
            name="description"
          ></textarea>
          <div
            v-if="submitted && errors.has('description')"
            class="alert-danger"
          >Brak opisu lub opis jest zbyt krótki</div>
        </div>
        <div class="form-group">
          <input class="inputPic" type="file" @change="addImage" />
        </div>
        <div class="form-group">
          <button class="btn btn-primary btn-block" @click="handleAdding">Dodaj gre</button>
        </div>
        <b-alert v-if="dispatched === true" show variant="success" class="text-center">Gra została dodana pomyślnie</b-alert>
        <b-alert v-if="exist === true" show variant="danger" class="text-center">Ta gra została już dodana</b-alert>
      </form>
    </div>
  </div>
</template>

<script>
import { AXIOS } from "../http-commons";

export default {
  name: "AddGame",
  data() {
    return {
      error: [],
      submitted: false,
      successful: false,
      message: "",
      exist: false,
      dispatched: false,
      options: ["Zręcznościowa", "Wyścigowa", "Strategiczna", 'Akcja','Sportowa','Przygodowa','Karciana','Łamigłówka'],
      game: {
        name: "",
        category: "",
        description: "",
      },
    };
  },
  methods: {
    handleGame() {
      this.$validator
        .validate()
        .then((isValid) => {
          this.submitted = true;
          if (isValid) {
            AXIOS.post("games", {
              category: this.game.category,
              name: this.game.name,
              description: this.game.description,
            }).then((response) => {
              console.log(response)
              this.dispatched = true;
            }).catch((error) => {
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
              if (error.response.status === 400) this.exist = true;
            });
          }
        })
        .catch((error) => {
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
          console.log(this.message)
        });
    },
    addImage(event) {
      this.selectedFile = event.target.files[0];
    },
    handleAdding() {
      const fd = new FormData();
      fd.append('gameimage', this.selectedFile)
      fd.append('name', this.game.name)
      AXIOS.post('games/uploadGameImage', fd
      ).then(response => {
        console.log(response);
        this.$router.go()
      })
      .catch(error => {
        console.log(error);
      })
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
