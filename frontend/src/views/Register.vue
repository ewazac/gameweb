<template>
  <div class="container reg">
    <form class="register" name="form" @submit.prevent="handleRegister">
      <h1 class="register__header">Rejestracja</h1>
      <div v-if="!successful">
        <div class="form-group">
          <b-form-input
                  v-model="user.username"
                  v-validate="'required|email|max:50'"
                  type="username"
                  class="form-control"
                  placeholder="Email"
                  name="username"
          />
          <div
                  v-if="occupied"
                  class="alert-danger"
          >{{ occupied }}</div>
          <div
                  v-if="submitted && errors.has('username')"
                  class="alert-danger"
          >{{ 'Niepoprawny email' }}</div>
        </div>
        <div class="form-group">
          <b-form-input
                  v-model="user.firstName"
                  v-validate="'max:50'"
                  type="firstName"
                  class="form-control"
                  placeholder="Imię"
                  name="firstName"
          />
          <b-icon v-on:click="showPassword" id="icon-show-password-id" class="show-password current-icon" icon="eye"></b-icon>
          <b-icon v-on:click="showPassword" id="icon-hide-password-id" class="show-password" icon="eye-slash-fill"></b-icon>
        </div>
        <div class="form-group">
          <b-form-input
                  v-model="user.lastName"
                  v-validate="'max:50'"
                  type="lastName"
                  class="form-control"
                  placeholder="Nazwisko"
                  name="lastName"
          />
        </div>
        <div class="form-group">
          <b-form-input
                  v-model="user.password"
                  v-validate="'required|min:4|max:40'"
                  type="password"
                  class="form-control"
                  name="password"
                  placeholder="Hasło"
                  autocomplete="on"
          />
          <div
                  v-if="submitted && errors.has('password')"
                  class="alert-danger"
          >{{ 'Hasło musi zawierać co najmniej 4 znaki' }}</div>
        </div>
        <hr />
        <b-link class="register__link" href="/Login"
        >Posiadasz już konto? Zaloguj się!</b-link>
        <hr />
        <div class="form-group">
          <b-button variant="info" class="register__button" type="submit">Zarejestruj się</b-button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
  import User from "../models/user";
  export default {
    name: "Register",
    data() {
      return {
        user: new User('','','',''),
        submitted: false,
        successful: false,
        message: "",
        occupied: '',
      };
    },
    computed: {
      loggedIn() {
        return this.$store.state.auth.status.loggedIn;
      },
    },
    mounted() {
      if (this.loggedIn) {
        this.$router.push("/account");
      }
    },
    beforeCreate: function () {
      document.body.className = "app__body-login";
    },
    methods: {
      handleRegister() {
        this.message = "";
        this.submitted = true;
        this.occupied = '';
        this.$validator.validate().then((isValid) => {
          if (isValid) {
            this.$store.dispatch("auth/register", this.user).then(
                    (data) => {
                      this.message = data.message;
                      this.successful = true;
                      this.$store.commit('app/ADD_MESSAGE', {text: 'Zarejestrowano poprawnie. Teraz możesz się zalogować', type: 'success'});
                      this.$router.push("/login");
                    },
                    (error) => {
                      this.message =
                              (error.response && error.response.data) ||
                              error.message ||
                              error.toString();
                      this.successful = false;
                      this.occupied = 'Ten email jest zajęty, prosze podać inny';
                    }
            );
          }
        });
      },
      showPassword() {
        var passwordInput = document.getElementById('password-input-id');
        var iconShowPassword = document.getElementById("icon-show-password-id");
        var iconHidePassword = document.getElementById("icon-hide-password-id");

        if (passwordInput.type === "password") {
          passwordInput.type = "text";
          iconShowPassword.classList.remove("current-icon");
          iconHidePassword.classList.add("current-icon");
        } else {
          passwordInput.type = "password";
          iconHidePassword.classList.remove("current-icon");
          iconShowPassword.classList.add("current-icon");
        }
      }
    },
  };
</script>

<style lang="scss" scoped>
  .reg {
    display: block;
    margin-top: 10px;
  }

  .register {
    width: 50%;
    background-color: #222;
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    -moz-border-radius: 10px;
    -webkit-border-radius: 10px;
    border-radius: 0;
    transition: 0.2s;
  }
  .register__header {
    margin-bottom: 1rem;
    color: mediumaquamarine;
    text-transform: uppercase;
    font-size: 30px;
    font-weight: bold;
  }
  .register__link {
    color: gray;
    text-decoration: underline;
    display: inline-block;
    width: 100%;
    text-align: center;
  }
  .register__button {
    background-color: mediumaquamarine;
    width: 100%;
    border: none;
    border-radius: 0;
    padding: 10px 20px;
    text-transform: uppercase;
    font-weight: bold;
    letter-spacing: 1px;
    transition: 0.2s;

    &:active {
      background-color: #66cdaa;
      filter: brightness(1.1);
      outline: none !important;
      border: none !important;
      box-shadow: none;
    }

    &:focus {
      background-color: #66cdaa;
      filter: brightness(1.1);
      outline: none !important;
      border: none !important;
      box-shadow: none !important;
    }

    &:hover {
      background-color: #66cdaa;
      filter: brightness(1.1);
    }
  }

  .form-group {
    margin-top: 10px;
    width: 100%;
    display: block;
  }

  input {
    width: 100%;
    border-width: 0px 0px 2px 0px;
    border-radius: 4px;
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

  .error {
    color: red;
  }

  h1 {
    text-align: center;
    margin-bottom: 10px;
  }

  @media (max-width: 991px) {
    .register {
      width: 75%;
    }
  }

  @media (max-width: 767px) {
    .register {
      width: 100%;
    }
  }
</style>
