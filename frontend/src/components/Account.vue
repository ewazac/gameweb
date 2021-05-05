<template>
  <div class="container">
    <div class="account">
      <div class="row">
        <div class="col-md-3" v-if="isAdmin">
          <!-- Sidebar -->
          <admin-sidebar></admin-sidebar>
          <!-- /#sidebar-wrapper -->
        </div>
        <div class="col-md-9" :class="{'col-md-12': !isAdmin}">
          <div class="w-100">
            <b-card
                    title="Ustawienia konta"
                    tag="article"
                    class="mb-2 w-100"
            >
              <div class="badge-holder">
                <img v-if="currentUser.point > 5 && currentUser.point < 10" src="../assets/normal.png" width="50px">
                <img v-if="currentUser.point >= 10 && currentUser.point <= 14" src="../assets/active.png" width="50px">
                <img v-if="currentUser.point >= 15" src="../assets/s_active.png" width="50px">
              </div>

              <b-card-text class="card-text-dark">
                <h3 class="h5">Dane osobowe</h3>
                <div class="my-4">
                  <div class="row">
                    <div class="col-md-2 align-center">Zmień avatar</div>
                    <input class="left d-none" type="file" ref="file_input" @change="handleChangeAvatar($event)" />
                    <div class="col-md-10" v-if="!avatar">
                      <div @click="$refs.file_input.click()" class="w-100 text-center" style="cursor: pointer; border: 1px dashed #c8c1c1; padding: 40px">
                        <span class="text-muted">Kliknij aby zmienić avatar</span>
                      </div>
                    </div>
                    <div class="col-md-10" v-if="avatar">
                      <div class="avatar-holder" style="cursor: pointer" @click="$refs.file_input.click()"><img :src="avatar"></div>
                    </div>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Imię i nazwisko:</div>
                  <div class="col-md-10" v-if="edited_field != 'name'">{{currentUser.firstName}} {{currentUser.lastName}} <i @click="edited_field = 'name'" class="bi bi-pencil-square"></i></div>
                  <div v-else class="d-flex flex-wrap col-md-10 transition">
                    <b-form-input
                            class="input mx-1"
                            type="text"
                            v-model="currentUser.firstName"
                            autocomplete="on"
                            required
                    />
                    <b-form-input
                            class="input mx-1"
                            type="text"
                            v-model="currentUser.lastName"
                            autocomplete="on"
                            required
                    />
                    <b-button class="account__button" @click="handleChange">Zapisz</b-button>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Ilość punktów:</div>
                  <div class="col-md-10">
                    <span class="font-weight-bold">{{currentUser.point}}</span>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Nick:</div>
                  <div class="col-md-10" v-if="edited_field != 'nick'"><span v-if="currentUser.nick">{{currentUser.nick}}</span><span v-else class="text-muted">Brak nick'u</span><i @click="edited_field = 'nick'" class="bi bi-pencil-square ml-2"></i></div>
                  <div v-else class="d-flex flex-wrap col-md-10 transition">
                    <b-form-input
                            placeholder="Nick"
                            class="input mx-1"
                            type="text"
                            v-model="currentUser.nick"
                            autocomplete="on"
                            required
                    />
                    <b-button class="account__button" @click="handleChange">Zapisz</b-button>
                  </div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Email:</div>
                  <div class="col-md-10" v-if="edited_field != 'email'">{{currentUser.email}}</div>
                </div>
                <div class="row align-center mt-2">
                  <div class="col-md-2 align-center">Hasło:</div>
                  <div class="col-md-10" v-if="edited_field != 'password'">.....<i @click="edited_field = 'password'" class="bi bi-pencil-square ml-2"></i></div>
                  <div v-else class="d-flex flex-wrap col-md-10 transition">
                    <div :style="{'margin-right': (isMobile())? '0px' : '20px', 'margin-top': (isMobile())? '20px' : '0px'}">
                      <b-form-input
                              style="max-width: 150px"
                              placeholder="Stare hasło"
                              class="input"
                              type="password"
                              v-model="currentUser.oldpassword"
                              :state="validatePasswords()"
                              autocomplete="on"
                              required
                              aria-describedby="old_password"
                      />
<!--                      <b-form-invalid-feedback id="old_password">This is a required field and must be at least 4 characters.</b-form-invalid-feedback>-->
                    </div>

                    <div style="min-width: 200px" >
                      <b-form-input
                              placeholder="Nowe hasło"
                              class="input"
                              type="password"
                              :state="validatePasswords()"
                              v-model="currentUser.newpassword"
                              autocomplete="on"
                              required
                              aria-describedby="new_password"
                      />
                      <b-form-invalid-feedback id="new_password">Hasło powinno mieć conajmniej 4 litery. Hasła powinny do siebie pasować.</b-form-invalid-feedback>
                      <b-form-input
                              placeholder="Powtórz nowe hasło"
                              class="input  mt-2"
                              type="password"
                              :state="validatePasswords()"
                              v-model="currentUser.newpassword_confirmation"
                              autocomplete="on"
                              required
                              aria-describedby="old_password_confirmation"
                      />
                    </div>
                    <div class="align-center ml-auto">
                      <b-button class="ml-2 account__button" @click="handleChangePass()">Zapisz</b-button>
                    </div>
                  </div>                  
                </div>
                <div class="rev">
                    <a href="/reviews" class="reviews"> Zobacz swoje recenzje! </a>
                  </div>
              </b-card-text>
            </b-card>
          </div>
          <div class="w-100 mt-5">
            <b-card
                    title="Newsletter"
                    tag="article"
                    class="mb-2 w-100"
            >
              <b-card-text class="card-text-dark">
                <b-form-checkbox
                        id="checkbox-1"
                        @change="handleChange(); changeNewsletter()"
                        v-model="currentUser.newsletter"
                        name="checkbox-1"
                        :unchecked-value="false"
                        :checked-value="true"
                >
                  Wyrażam zgodę na wysyłanie newslettera.
                </b-form-checkbox>
                <b-alert class="my-4" variant="success" v-if="currentUser.newsletter" show>Jesteś zapisany na newsletter</b-alert>
              </b-card-text>
            </b-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  const API_URL = "https:/gameweb.projektstudencki.pl/api";
  import User from '../models/user'
  import Request from '../request'
  export default {
    name: "Account",
    beforeCreate: function () {
      document.body.className = "app__body";
    },
    computed:{
      isAdmin(){
        return this.currentUser && this.currentUser.roles.findIndex(x => x == 'ADMIN') != -1;
      }
    },
    data() {
      return {
        currentUser: new User(JSON.parse(localStorage.getItem("user"))),
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
        newsletter: false,
        edited_field:null,
        sending: false,
        invalid_pass_message: ''
      };
    },
    methods: {
      handleChangePass() {
        this.sending = true;
        if(this.validatePasswords()){
          const fd = new FormData();
          fd.append("newpassword", this.currentUser.newpassword);
          fd.append("oldpassword", this.currentUser.oldpassword);
          axios
                  .put(API_URL + "users/changePassword", fd, {
                    withCredentials: true,
                  })
                  .then(() => {
                    this.dispatched = true;
                    this.$store.dispatch('app/add_message', {text: 'Hasło zostało zmienione', type: 'success'});
                    this.sending = false
                    this.edited_field = null;
                  })
                  .catch(() => {
                    this.$store.dispatch('app/add_message', {text: 'Niepoprawne stare hasło', type: 'danger'});
                    this.sending = false
                  });
        }
      },
      subscribeToNewsletter() {
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
      changeNewsletter(){
          Request({
            url:'/users/newsletter',
            method:'patch'
          }).then(() => {

          })
      },
      handleChange(){
        this.$nextTick(() => {
          this.currentUser.updateUser();
          this.edited_field = null;
        })
      },
      validatePasswords(){
        if(!this.sending) return null;
        if(!this.currentUser.newpassword || !this.currentUser.newpassword_confirmation || !this.currentUser.oldpassword){
          return false;
        }
        if(this.currentUser.newpassword.length < 4 || this.currentUser.newpassword_confirmation.length < 4 || this.currentUser.oldpassword.length < 4){
          return false;
        }
        if(this.currentUser.newpassword != this.currentUser.newpassword_confirmation){
          return false;
        }
        return true;
      },
      handleChangeAvatar(event) {
        var selected_file = event.target.files[0]
        const fd = new FormData();
        fd.append("avatar", selected_file);
        axios
                .put(API_URL + "users/uploadAvatar", fd, {
                  withCredentials: true,
                  headers: {
                    "Content-Type": "multipart/form-data",
                  },
                })
                .then(() => {
                  /*this.$router.go();*/
                  this.getAvatar();
                })
                .catch(() => {
                });
      },
      getAvatar(){
        axios.get(API_URL + "users/getAvatar", {
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
      this.getAvatar();
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

  .account {
  /*width: 70%;*/
  /*background-color: black;*/
  /*padding: 20px 30px 30px;*!*/
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 0;
  -webkit-border-radius: 0;
  border-radius: 0;
  transition: 0.2s;
  }
  .form-control {
    background-color: black;
  }

  .input{
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

  .card-body {
      display: flex;
      flex-direction: column;
      padding: 10px;
  }
  .card {
      padding: 10px;
      min-height: 15rem;
      max-height: 25rem;
      border-width: 0 0 2px 0;
      -moz-border-radius: 2px;
      -webkit-border-radius: 2px;
      border-radius: 2px;
      -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
      -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
      box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);
  }
  .rev {
    margin: 0.5rem 0;
  }
  .reviews {
    color: whitesmoke;
  }
  .reviews:hover, .reviews:active, .reviews:link {
    color: #7bd5b4;
  }
  /*.form-control {*/
  /*  background-color: #000;*/
  /*  color: mediumaquamarine;*/
  /*}*/

  /*input[type="file"] {*/
  /*  border: none !important;*/
  /*  cursor: pointer;*/

  /*  width: 100%;*/
  /*  border-width: 0px 0px 2px 0px;*/
  /*  border-radius: 0;*/
  /*  border: 1px solid #111;*/
  /*  color: mediumaquamarine;*/
  /*  margin-top: 20px;*/
  /*  margin-bottom: 20px;*/
  /*  background-color: #222;*/

  /*  &:active {*/
  /*    outline: none;*/
  /*    box-shadow: none;*/
  /*    border: 1px solid #111;*/
  /*    color: mediumaquamarine;*/
  /*  }*/

  /*  &:focus {*/
  /*    outline: none;*/
  /*    box-shadow: none;*/
  /*    border: 1px solid #111;*/
  /*    color: mediumaquamarine;*/
  /*  }*/

  /*  &:hover {*/
  /*    cursor: pointer;*/
  /*  }*/
  /*}*/
  /*.text{*/
  /*  background-color: #222;*/
  /*  color: white;*/
  /*}*/

  /*.account__edited_field {*/
  /*  width: 100%;*/
  /*  border-width: 0px 0px 2px 0px;*/
  /*  border-radius: 0;*/
  /*  border: 1px solid #111;*/
  /*  color: mediumaquamarine;*/
  /*  display: block !important;*/

  /*  &:active {*/
  /*    outline: none;*/
  /*    box-shadow: none;*/
  /*    border: 1px solid #111;*/
  /*    color: mediumaquamarine;*/
  /*  }*/

  /*  &:focus {*/
  /*    outline: none;*/
  /*    box-shadow: none;*/
  /*    border: 1px solid #111;*/
  /*    color: mediumaquamarine;*/
  /*  }*/
  /*}*/

  /*.custom-control {*/
  /*  color: #fff;*/

  /*  &:focus {*/
  /*    outline: none;*/
  /*    border: none;*/
  /*    box-shadow: none !important;*/
  /*  }*/
  /*}*/

  @media (max-width: 991px) {
    .account {
      width: 100%;
    }
  }
</style>
