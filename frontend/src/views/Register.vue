<template>
  <div class="reg">
    <form class="register" name="form" @submit.prevent="handleRegister">
      <h1 style="margin-bottom:1rem;">Rejestracja</h1>
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
          >{{ 'Brak hasła' }}</div>
        </div>
        <div class="form-group">
          <b-button variant="info" style="width:100%;">Zarejestruj się</b-button>
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
              console.log(this.message);
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
  },
};
</script>

<style lang="scss" scoped>
.reg {
  display: block;
  margin-top: 10px;
}

.register {
  width: 30%;
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
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
}
input:focus {
  outline: none;
}

.error {
  color: red;
}

h1 {
  text-align: center;
  margin-bottom: 10px;
}
</style>