<template>
  <div class="block">
    <div class="container">
      <div class="avatar">
        <img class="picture" alt="avatar" :src="avatar" />
      </div>
      <div class="data">
        <input class="inputPic" type="file" @change="changeImage" />
        <p class="profileData">id: {{ currentUser.id }}</p>
        <p class="profileData">email: {{ currentUser.email }}</p>
        <p class="profileData">password(tymczasowo): {{ currentUser.password }}</p>
      </div>
      <div class="button">
        <button @click="handleChange">Zmień</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Account",
  data() {
    return {
      avatar: '',
      selectedFile: null,
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    changeImage(event) {
      console.log(event);
      this.selectedFile = event.target.files[0];
    },
    handleChange() {
      const fd = new FormData();
      fd.append('avatar', this.selectedFile)
      fd.append('id', this.currentUser.id)
      axios.put('http://localhost:8086/users/' + this.currentUser.id + '/uploadAvatar', fd, {
        withCredentials: true,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      ).then(response => {
        console.log(response);
        this.$router.go()
      })
      .catch(error => {
        console.log(error);
      })
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("Login");
    }
    console.log(this.currentUser.id)
    axios.get("users/" + this.currentUser.id + "/getAvatar")
      .then((response) => {
        let base64 = response.data.data;
        let buffer = Uint8Array.from(atob(base64), (c) => c.charCodeAt(0));
        let blob = new Blob([buffer], { type: "image/gif" });
        this.avatar = URL.createObjectURL(blob);
      })
      .catch((error) => {
        console.log(error);
      })
  },
};
</script>


<style lang="scss" scoped>

.block {
  margin-top: 1%;
}

.container {
  display: grid;
  text-align: left;
  max-width: 70%;
}
.avatar {
  display: grid;
  justify-content: center;
}
.button {
  text-align: center;
  margin-top: 10px;
}
.profileData {
  margin-top: 10px;
}
</style>