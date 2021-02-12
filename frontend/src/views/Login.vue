<template>
  <div class="container log">
    <form class="login" @submit.prevent="handleLogin">
      <div class="form">
        <h1 class="login__header" style="margin-bottom:1rem;">Logowanie</h1>
        <div class="form-group">
          <b-form-input
                  required
                  v-model="user.username"
                  name="username"
                  type="text"
                  placeholder="Username"
          />
        </div>
        <div class="password-group form-group">
          <b-form-input
                  required
                  v-model="user.password"
                  name="password"
                  type="password"
                  placeholder="Password"
                  autocomplete="on"
                  id="password-input-id"
          />
          <b-icon v-on:click="showPassword" id="icon-show-password-id" class="show-password current-icon" icon="eye"></b-icon>
          <b-icon v-on:click="showPassword" id="icon-hide-password-id" class="show-password" icon="eye-slash-fill"></b-icon>
        </div>
        <p class="error" v-if="errorMessage != ''">{{ errorMessage }}</p>
        <hr />
        <b-link class="login__link" href="#">Zapomniałeś hasła?</b-link>
        <b-link class="login__link" href="/Register">Nie masz konta? Zarejestruj się!</b-link>
        <hr />
        <div>
          <b-button class="login__button" type="submit">Login</b-button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
  import User from "../models/user";

  export default {
    name: "Login",
    data() {
      return {
        user: new User("", ""),
        loading: false,
        message: "",
        errorMessage: "",
      };
    },
    computed: {
      loggedIn() {
        return this.$store.state.auth.status.loggedIn;
      },
    },
    created() {
      if (this.loggedIn) {
        this.$router.push("/account");
      }
    },
    beforeCreate: function () {
      document.body.className = "app__body-login";
    },
    methods: {
      handleLogin() {
        this.loading = true;
        this.$validator.validateAll().then((isValid) => {
          if (!isValid) {
            this.loading = false;
            return;
          }
          if (this.user.username && this.user.password) {
            this.$store.dispatch("auth/login", this.user).then(
                    () => {
                      this.$router.push("/account");
                    },
                    (error) => {
                      this.errorMessage = "Niepoprawne hasło lub login";
                      this.loading = false;
                      this.message =
                              (error.response && error.response.data) ||
                              error.message ||
                              error.toString();
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
  .log {
    display: block;
    margin-top: 10px;
  }

  .login {
    width: 50%;
    background-color: #222;
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    -moz-border-radius: 10px;
    -webkit-border-radius: 10px;
    transition: 0.2s;
    border-radius: 0;
  }

  .login__button {
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
      background-color: mediumaquamarine;
      filter: brightness(1.1);
    }
  }

  .form-control {
    background-color: #000;
  }

  .login__link {
    color: gray;
    text-decoration: underline;
    display: inline-block;
    width: 100%;
    text-align: center;
  }

  .login__header {
    margin-bottom: 1rem;
    color: mediumaquamarine;
    text-transform: uppercase;
    font-size: 30px;
    font-weight: bold;
  }

  .form-group {
    margin-top: 10px;
    width: 100%;
    display: flex;
  }

  .password-group {
    position: relative;
  }

  .show-password {
    position: absolute;
    color: darkgrey;
    font-size: 26px;
    top: 6px;
    right: 8px;
    display: none;
    cursor: pointer;
  }

  .current-icon {
    display: inline !important;
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
  }

  @media (max-width: 991px) {
    .login {
      width: 75%;
    }
  }

  @media (max-width: 767px) {
    .login {
      width: 100%;
    }
  }
</style>