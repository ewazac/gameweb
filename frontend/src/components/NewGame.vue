<template>
  <div class="newgame">
    <div class="container new-game__form">
      <h1 class="new-game__header">Dodaj grę</h1>
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
          <div v-if="submitted && errors.has('name')" class="alert-danger">
            {{ 'Brak nazwy' }}
          </div>
        </div>
        <div class="form-group">
          <label>Wybierz kategorię</label>
          <b-form-select
            v-validate="'required'"
            v-model="game.category"
            :options="options"
            size="sm"
            class="mt-3"
            name="category"
          ></b-form-select>
          <div v-if="submitted && errors.has('category')" class="alert-danger">
            {{ 'Brak kategorii' }}
          </div>
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
          <button class="new-game__button" @click="handleAdding">
            Dodaj grę
          </button>
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
  beforeCreate: function () {
    document.body.className = "app__body";
  },
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
        //this.$router.go()
      })
      .catch(error => {
        console.log(error);
      })
    },
  },
};
</script>

<style lang="scss" scoped>

.new-game__form {
  width: 60%;
  background-color: rgba(247, 247, 247, 0.9);
  padding: 20px 50px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 10px;
  -webkit-border-radius: 10px;
  border-radius: 10px;
  transition: 0.2s;

  &:hover {
    -moz-box-shadow: 2px 2px 13px rgba(255, 255, 255, 0.5);
    -webkit-box-shadow: 2px 2px 13px rgba(255, 255, 255, 0.5);
    box-shadow: 2px 2px 13px rgba(255, 255, 255, 0.5);
  }
}

.new-game__header {
  color: #fa0b0b;
  text-transform: uppercase;
  font-size: 30px;
  font-weight: bold;
  text-align: center;
}

input[type="file"] {
  border: none !important;
  cursor: pointer;

  &:hover {
    cursor: pointer;
  }
}

select {
  border: 1px solid #111 !important;
  cursor: pointer;
  color: #fa0b0b;

  &:active {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }

  &:focus {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }
}

input,
textarea {
  width: 100%;
  border-width: 0px 0px 2px 0px;
  border-radius: 4px;
  border: 1px solid #111;
  color: #fa0b0b;

  &:active {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }

  &:focus {
    outline: none;
    box-shadow: none;
    border: 1px solid #111;
    color: #fa0b0b;
  }
}

.new-game__button {
  background-color: #fa0b0b;
  width: 100%;
  border: none;
  border-radius: 0;
  padding: 10px 20px;
  text-transform: uppercase;
  font-weight: bold;
  letter-spacing: 1px;
  color: #fff;
  transition: 0.2s;

  &:active {
    background-color: #fa0b0b !important;
    outline: none !important;
    border: none !important;
    box-shadow: none;
  }

  &:focus {
    background-color: #fa0b0b !important;
    outline: none !important;
    border: none !important;
    box-shadow: none !important;
  }

  &:hover {
    background-color: #fa0b0b;
    opacity: 0.9;
  }
}

@media (max-width: 991px) {
  .new-game__form {
    width: 75%;
  }
}

@media (max-width: 767px) {
  .new-game__form {
    width: 100%;
  }
}
</style>
