<template>
    <div class="container log">
        <form class="login" @submit.prevent="handleReset()">
            <div class="form">
                <h1 class="login__header" style="margin-bottom:1rem;"><span v-if="!token">Resetuj hasło</span><span v-else>Wprowadź nowe hasło</span></h1>
                <div class="form-group" v-if="!token">
                    <b-form-input
                            required
                            v-model="user.username"
                            name="username"
                            type="email"
                            placeholder="Podaj adres e-mail"
                    />
                </div>
                <div class="form-group" v-else>
                    <b-form-input
                            v-model="user.password"
                            v-validate="'required|min:4|max:40'"
                            type="password"
                            class="form-control"
                            name="password"
                            placeholder="Nowe hasło"
                            autocomplete="on"
                    />
                    <div
                            v-if="submitted && errors.has('password')"
                            class="alert-danger"
                    >{{ 'Hasło musi zawierać co najmniej 4 znaki' }}</div>
                </div>
                <b-link class="login__link" href="/Register">Nie masz konta? Zarejestruj się!</b-link>
                <hr />
                <div>
                    <b-button class="login__button" type="submit"><span v-if="token">Resetuj hasło</span><span v-else>Wyślij link resetowania hasła</span></b-button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
    import User from "../../models/user";

    export default {
        name: "Login",
        data() {
            return {
                user: new User("", ""),
                loading: false,
                message: "",
                errorMessage: "",
                token: null
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
            this.token = this.$route.query.token;
        },
        beforeCreate: function () {
            document.body.className = "app__body-login";
        },
        methods: {
            handleReset() {
                this.loading = true;
                this.$validator.validateAll().then((isValid) => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }
                    this.$store.dispatch('auth/sendResetLink', {email: this.user.username}).then(res => {
                        console.log(res);
                    })
                });
            }
        }
    };
    //         handleLogin() {
    //             this.loading = true;
    //             this.$validator.validateAll().then((isValid) => {
    //                 if (!isValid) {
    //                     this.loading = false;
    //                     return;
    //                 }
    //                 if (this.user.username && this.user.password) {
    //                     this.$store.dispatch("auth/login", this.user).then(
    //                         () => {
    //                             this.$router.push("/account");
    //                         },
    //                         (error) => {
    //                             this.errorMessage = "Niepoprawne hasło lub login";
    //                             this.loading = false;
    //                             this.message =
    //                                 (error.response && error.response.data) ||
    //                                 error.message ||
    //                                 error.toString();
    //                         }
    //                     );
    //                 }
    //             });
    //         },
    //         showPassword() {
    //             var passwordInput = document.getElementById('password-input-id');
    //             var iconShowPassword = document.getElementById("icon-show-password-id");
    //             var iconHidePassword = document.getElementById("icon-hide-password-id");
    //
    //             if (passwordInput.type === "password") {
    //                 passwordInput.type = "text";
    //                 iconShowPassword.classList.remove("current-icon");
    //                 iconHidePassword.classList.add("current-icon");
    //             } else {
    //                 passwordInput.type = "password";
    //                 iconHidePassword.classList.remove("current-icon");
    //                 iconShowPassword.classList.add("current-icon");
    //             }
    //         }
    //     },
    // };
</script>
