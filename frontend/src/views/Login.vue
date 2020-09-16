<template>
  <div class="log">
   <form class="login" @submit.prevent="handleLogin">
    <div class="form">
     <h1>Logowanie</h1>
     <div class="form-group">
        <input required v-model="user.username" name="username" type="text" placeholder="Username">
     </div>
     <div class="form-group">
        <input required v-model="user.password" name="password" type="password" placeholder="Password" autocomplete="on">
     </div>
     <p class="error" v-if="errorMessage != ''"> {{ errorMessage }} </p>
     <hr/>
     <div class="button">
        <button class="btn btn-primary btn-block" type="submit">Login</button>
     </div>
    </div>
   </form>
  </div>
</template>

<script>
import User from '../models/user';

export default {
  name: 'Login',
  data() {
    return {
      user: new User('', ''),
      loading: false,
      message: '',
      errorMessage: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  created() {
    if (this.loggedIn) {
      this.$router.push('/account');
    }
  },
  methods: {
    handleLogin() {
      this.loading = true;
      this.$validator.validateAll().then(isValid => {
        if (!isValid) {
          this.loading = false;
          return;
        }

        if (this.user.username && this.user.password) {
          this.$store.dispatch('auth/login',  this.user ).then(
            () => {
              this.$router.push('/account');
            },
            error => {
              this.errorMessage = 'Niepoprawne hasło lub login'
              this.loading = false;
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
            }

          );
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.log {
    display: block;
    margin-top: 10px;
}

.login {
    max-width: 450px !important;
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
/*.button {
    display: flex;
    justify-content: center;
    align-items: center;
}*/

.form-group {
    margin-top:10px;
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

.error{
  color: red;
}

h1 {
    text-align: center;
}


</style>