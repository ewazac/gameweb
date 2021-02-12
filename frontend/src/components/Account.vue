<template>
  <div class="container">
    <div class="account">
      <h1 class="account__header">Zmień ustawienia konta</h1>
      <b-row class="mt-5">
        <b-col cols="12" md="6">
          <h4 class="account__header account__header-sm text-left">
            Twój avatar:
          </h4>
          <img class="left account__avatar" alt="avatar" :src="avatar" />
          <input class="left" type="file" @change="changeImage" />
          <b-button class="account__button" @click="handleChange"
          >Zmień avatar</b-button
          >
        </b-col>
        <b-col cols="12" md="6">
          <h4 class="account__header account__header-sm text-left mt-4 mt-md-0">
            Twój obecny mail:
          </h4>
          <div class="text">{{ currentUser.email }}</div>
          <h5 class="account__header account__header-sm text-left mt-4">
            Zmień hasło:
          </h5>
          <form name="form" @submit.prevent="handleChangePass">
            <div>
              <h6 style="color: #fff;">Podaj stare hasło:</h6>
              <b-form-input
                      name="oldPassword"
                      class="input"
                      type="password"
                      v-model="oldPassword"
                      autocomplete="on"
                      required
              />
            </div>
            <div>
              <h6 style="color: #fff;">Podaj nowe hasło:</h6>
              <b-form-input
                      name="newPassword"
                      class="account__text-input"
                      type="password"
                      v-model="newPassword"
                      autocomplete="on"
                      required
              />
            </div>
            <b-button class="account__button mt-3" type="submit">
              Zmień hasło
            </b-button>
            <b-alert
                    class="left text-center"
                    v-if="dispatched === true"
                    show
                    variant="success"
            >Hasło zostało zmienione
            </b-alert>
            <b-alert
                    class="left text-center"
                    v-if="exist === true"
                    show
                    variant="danger"
            >Nieprawidłowe hasło
            </b-alert>
          </form>
        </b-col>
      </b-row>
    </div>
    <div class="account">
      <h1 class="account__header">Newsletter</h1>
      <p class="text-center mt-4" style="color: #fff;">
        Chcesz być na bieżąco, zapisz się do naszego newslettera:
      </p>
      <div class="row text-left">
        <div class="col-10 offset-1 col-sm-6 offset-sm-3">
          <form name="form-newsletter" @submit.prevent="subscribeToNewsletter">
            <b-form-group class="text">
              <template #label>
                <b class="mt-4" style="color: #fff;">Wybierz kategorie gier, które Cię interesują:</b><br />
                <b-form-checkbox
                        v-model="allSelected"
                        :indeterminate="indeterminate"
                        aria-describedby="Categories"
                        aria-controls="Categories"
                        @change="toggleAll"
                >
                  {{ allSelected ? "Odznacz wszystkie" : "Wybierz wszystkie" }}
                </b-form-checkbox>
              </template>

              <b-form-checkbox-group
                      id="Categories"
                      v-model="selected"
                      :options="categories"
                      name="Categories"
                      class="ml-4"
                      aria-label="Individual Categories"
                      stacked
              ></b-form-checkbox-group>
            </b-form-group>
            <b-button class="btn account__button mt-3" type="submit">
              Zapisz się
            </b-button>
          </form>
          <b-alert
                  class="left text-center"
                  v-if="newsletter === true"
                  show
                  variant="success"
          >Zostałeś zapisany do Newslettera
          </b-alert>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  const API_URL = "https://gameweb21.herokuapp.com/";

  export default {
    name: "Account",
    beforeCreate: function () {
      document.body.className = "app__body";
    },
    data() {
      return {
        currentUser: JSON.parse(localStorage.getItem("user")),
        avatar: "",
        dispatched: false,
        exist: false,
        selectedFile: null,
        email: "",
        oldPassword: "",
        newPassword: "",
        categories: [],
        selected: [],
        indeterminate: false,
        allSelected: false,
        newsletter: false
      };
    },
    methods: {
      handleChangePass() {
        const fd = new FormData();
        fd.append("newpassword", this.newPassword);
        fd.append("oldpassword", this.oldPassword);
        axios
                .put(API_URL + "users/changePassword", fd, {
                  withCredentials: true,
                })
                .then((response) => {
                  this.dispatched = true;
                  console.log(response);
                })
                .catch((error) => {
                  console.log(error);
                  this.exist = true;
                });
      },
      subscribeToNewsletter() {
        console.log(this.categories, this.selected);
        axios
                .post(API_URL + "newsletter", this.selected, {
                  withCredentials: true,
                  headers: {
                    "Content-Type": "application/json",
                  },
                })
                .then((response) => {
                  this.newsletter = true
                  console.log(response);
                })
                .catch((error) => {
                  console.log(error);
                });
      },
      changeImage(event) {
        console.log(event);
        this.selectedFile = event.target.files[0];
      },
      handleChange() {
        if (this.selectedFile === null) {
          this.$router.go();
        }
        const fd = new FormData();
        fd.append("avatar", this.selectedFile);
        axios
                .put(API_URL + "users/uploadAvatar", fd, {
                  withCredentials: true,
                  headers: {
                    "Content-Type": "multipart/form-data",
                  },
                })
                .then((response) => {
                  console.log(response);
                  this.$router.go();
                })
                .catch((error) => {
                  console.log(error);
                });
      },
      toggleAll(checked) {
        this.selected = checked ? this.categories.slice() : [];
      },
    },
    watch: {
      selected(newVal) {
        if (newVal.length === 0) {
          this.indeterminate = false;
          this.allSelected = false;
        } else if (newVal.length === this.categories.length) {
          this.indeterminate = false;
          this.allSelected = true;
        } else {
          this.indeterminate = true;
          this.allSelected = false;
        }
      },
    },
    mounted() {
      if (!this.currentUser) {
        this.$router.push("Login");
      }
      axios
              .get(API_URL + "users/getAvatar", {
                withCredentials: true,
                headers: {
                  "Content-Type": "multipart/form-data",
                },
              })
              .then((response) => {
                let base64 = response.data.data;
                let buffer = Uint8Array.from(atob(base64), (c) => c.charCodeAt(0));
                let blob = new Blob([buffer], { type: "image/gif" });
                this.avatar = URL.createObjectURL(blob);
              })
              .catch((error) => {
                console.log(error);
              });
      axios
              .get(API_URL + "getAllCategories", {
                withCredentials: true,
              })
              .then((response) => {
                this.categories = response.data;
              })
              .catch((error) => {
                console.log(error);
              });
    },
  };
</script>


<style lang="scss" scoped>
  img.left {
    height: 8rem;
    width: 8rem;
  }

  .account {
    width: 70%;
    background-color: #222;
    padding: 20px 30px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    -moz-border-radius: 0;
    -webkit-border-radius: 0;
    border-radius: 0;
    transition: 0.2s;
  }

  .account__header {
    color: mediumaquamarine;
    text-transform: uppercase;
    font-size: 30px;
    font-weight: bold;
    text-align: center;

    &-sm {
      font-size: 22px;
    }
  }

  input {
    width: 100%;
    border-width: 0px 0px 2px 0px;
    border-radius: 0;
    border: 1px solid #111;
    color: mediumaquamarine;
    background-color: #000;

    &:active {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
      background-color: #000;
    }

    &:focus {
      outline: none;
      box-shadow: none;
      border: 1px solid #111;
      color: mediumaquamarine;
      background-color: #000;
    }
  }

  .form-control {
    background-color: #000;
    color: mediumaquamarine;
  }

  input[type="file"] {
    border: none !important;
    cursor: pointer;

    width: 100%;
    border-width: 0px 0px 2px 0px;
    border-radius: 0;
    border: 1px solid #111;
    color: mediumaquamarine;
    margin-top: 20px;
    margin-bottom: 20px;
    background-color: #222;

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

    &:hover {
      cursor: pointer;
    }
  }
  .text{
    background-color: #222;
    color: white;
  }

  .account__text-input {
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

  .account__avatar {
    background-color: #fff;
  }

  .custom-control {
    color: #fff;

  }

  .account__button {
    background-color: mediumaquamarine !important;
    margin-top: auto;
    border: none;
    color: #fff;

    &:focus {
      outline: none;
      border: none;
      box-shadow: none !important;
    }
  }

  @media (max-width: 991px) {
    .account {
      width: 100%;
    }
  }
</style>