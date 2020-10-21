<template>
  <div class="log">
    <form class="container login" @submit.prevent="handleLogin">
      <div class="form">
        <h1 class="login__header" style="...">Logowanie</h1>
        <div class="form-group">
          <b-form-input
            required
            v-model="user.username"
            name="username"
            type="text"
            placeholder="Username"
          />
        </div>
        <div class="form-group">
          <b-form-input
            required
            v-model="user.password"
            name="password"
            type="password"
            placeholder="Password"
            autocomplete="on"
          />
        </div>
        <p class="error" v-if="errorMessage != ''">{{ errorMessage }}</p>
        <hr />
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
    background-color: #f7f7f7;
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 2px 2px 13px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 2px 2px 13px rgba(0, 0, 0, 0.3);
    box-shadow: 2px 2px 13px rgba(0, 0, 0, 0.3);
}

 /* -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);

  */
/*.button {
    display: flex;
    justify-content: center;
    align-items: center;
}*/
.login__button {
    background-color: #b42230;
    width: 100%;
    border: none;
    border-radius: 0;
    padding: 10px 20px;
    text-transform: uppercase;
    font-weight: bold;
    letter-spacing: 1px;
    transition: 0.2s;

    &:active {
        background-color: #b42230 !important;
        outline: none !important;
        border: none !important;
        box-shadow: none;
    }

    &:focus {
        background-color: #b42230 !important;
        outline: none !important;
        border: none !important;
        box-shadow: none !important;
    }

    &:hover {
        background-color: #b42230;
        opacity: 0.9;
    }
}

.login__link {
    color: #b42230;
    text-decoration: underline;
    display: inline-block;
    width: 100%;
    text-align: center;
}

.login__header {
    margin-bottom: 1rem;
    color: #b42230;
    text-transform: uppercase;
    font-size: 30px;
    font-weight: bold;
}

.form-group {
  margin-top: 10px;
  width: 100%;
  display: flex;
}

input {
  width: 100%;
  border-width: 0px 0px 2px 0px;
  border-radius: 4px;
}
input:focus {
  outline: none;
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