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
          <div v-if="occupied" class="alert-danger">{{ occupied }}</div>
          <div v-if="submitted && errors.has('username')" class="alert-danger">{{ 'Niepoprawny email' }}</div>
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
          <div v-if="submitted && errors.has('password')" class="alert-danger">{{ 'Hasło musi zawierać conajmniej 4 znaki' }}</div>
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
          /*console.log(this.user); // dodane */
          this.$store.dispatch("auth/register", this.user).then(
            (data) => {
              this.message = data.message;
              this.successful = true;
              /*console.log(this.message) // dodane */
              console.log(data) // before
              this.$router.push("/login");
            },
            (error) => {
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
              this.successful = false;
              this.occupied = 'Ten email jest zajęty, proszę podać inny';
            }
          );
        }
      });
    },
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
  background-color: rgba(247, 247, 247, 0.9);
  padding: 20px 25px 30px;
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
.register__header {
  margin-bottom: 1rem;
  color: #fa0b0b;
  text-transform: uppercase;
  font-size: 30px;
  font-weight: bold;
}
.register__link {
  color: #fa0b0b;
  text-decoration: underline;
  display: inline-block;
  width: 100%;
  text-align: center;
}
.register__button {
  background-color: #fa0b0b;
  width: 100%;
  border: none;
  border-radius: 0;
  padding: 10px 20px;
  text-transform: uppercase;
  font-weight: bold;
  letter-spacing: 1px;
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

.error {
  color: #ff0000;
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