<template>
  <div class="container">
    <b-container fluid>
      <h1 style="text-align:center;">Zmień ustawienia konta</h1>
      <b-row cols="2">
        <b-col style="display:grid;">
          <h4 class="left">Twój avatar:</h4>
          <img class="left" alt="avatar" :src="avatar" />
          <input class="left" type="file" @change="changeImage" />
          <b-button class="button" @click="handleChange">Zmień avatar</b-button>
        </b-col>
        <b-col>
          <h4 class="left">Twój obecny mail: {{ currentUser.email }}</h4>
          <h5 class="left">Zmień hasło:</h5>
          <form name='form' @submit.prevent='handleChangePass'>
            <div class="left" style="display:inline-flex;">
              <h6 style="min-width:10rem;">Podaj stare hasło:</h6>
              <b-form-input name="oldPassword" class="input" type="password" v-model="oldPassword" required/>
            </div>
            <div class="left" style="display:inline-flex;">
              <h6 style="min-width:10rem;">Podaj nowe hasło:</h6>
              <b-form-input name="newPassword" class="input" type="password" v-model="newPassword" required/>
            </div>
            <b-button class="button" type="submit">Zmień hasło</b-button>
            <b-alert class="left text-center" v-if="dispatched === true" show variant="success" >Hasło zostało zmienione</b-alert>
            <b-alert class="left text-center" v-if="exist === true" show variant="danger" >Nieprawidłowe hasło</b-alert>
          </form>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";

const API_URL = 'https://gameweb21.herokuapp.com/'
//const API_URL = 'http://localhost:8086/'


export default {
  name: "Account",
  data() {
    return {
      avatar: "",
      dispatched: false,
      exist: false,
      selectedFile: null,
      email: "",
      oldPassword: "",
      newPassword: "",
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    handleChangePass() {
      const fd = new FormData();
      fd.append('id',this.currentUser.id)
      fd.append('newpassword',this.newPassword)
      fd.append('oldpassword',this.oldPassword)
      axios.put(
        API_URL + "users/" + this.currentUser.id + "/changePassword", fd, {withCredentials: true}
      ).then((response)=>{
        this.dispatched = true
        console.log(response)
      }).catch((error)=>{
        console.log(error)
        this.exist = true
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
      fd.append("id", this.currentUser.id);
      axios
        .put(
          API_URL + "users/" +
            this.currentUser.id +
            "/uploadAvatar",
          fd,
          {
            withCredentials: true,
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        )
        .then((response) => {
          console.log(response);
          this.$router.go();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("Login");
    }
    axios
      .get(
        API_URL + "users/" + this.currentUser.id + "/getAvatar",
        {
          withCredentials: true,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      )
      .then((response) => {
        let base64 = response.data.data;
        let buffer = Uint8Array.from(atob(base64), (c) => c.charCodeAt(0));
        let blob = new Blob([buffer], { type: "image/gif" });
        this.avatar = URL.createObjectURL(blob);
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>


<style lang="scss" scoped>
h5 h6 {
  margin-top: auto;
}
.left {
  margin: 1rem;
}
img.left {
  height: 8rem;
  width: 8rem;
}
.btn {
  width: 5rem;
}
.input {
  margin-left: 1rem;
  max-width: 15rem;
}
.col {
  padding: 1rem;
}
.button {
  margin: 1rem;
  width: 15rem;
}
</style>