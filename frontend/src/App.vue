<template>
    <div class="wrapper">
        <b-navbar toggleable="lg" type="dark" variant="dark" class="fixed-top">
            <img class="GameWeb-mobile" src="./assets/logo.png" alt="GameWeb" />
            <b-navbar-toggle class="app__toggle-menu ml-auto" target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav class="mr-auto align-items-baseline">
                    <b-nav-item href="/Games">
                        <img class="GameWeb" src="./assets/logo.png" alt="GameWeb" />
                    </b-nav-item>
                    <b-nav-item href="/News">
                        News
                    </b-nav-item>
                    <router-link to="/quizy">
                        <b-nav-item  to="/quizy">
                            Quiz
                        </b-nav-item>
                    </router-link>
                    <b-nav-item href="/competitions">
                        Konkurs
                    </b-nav-item>
                    <b-nav-item href="/Recommended">
                        Polecane
                    </b-nav-item>
                    <b-nav-item href="/forum">
                        Forum
                    </b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav>
                    <b-nav-item href="/favourites" id="fav" v-if="currentLoggedIn">
                        <div style="position: relative">
                            <b-icon icon="suit-heart-fill" aria-hidden="fav"></b-icon>
                            <div style="position: absolute; top: -8px; right: -8px; padding: 3px 7px; font-size: 11px; border-radius: 100%; background-color: red">{{favourites.length}}</div>
                        </div>

                    </b-nav-item>
                    <b-nav-item href="/search" id="search-icon-id">
                        <b-icon icon="search" aria-hidden="search"></b-icon>
                    </b-nav-item>
                    <b-nav-item v-if="currentLoggedIn === false" href="/login">
                        <b-icon icon="person" aria-hidden="login"></b-icon>
                    </b-nav-item>
                    <b-nav-item v-if="currentLoggedIn === true" href="/account">
                        <b-icon icon="gear-fill" aria-hidden="settings"></b-icon>
                    </b-nav-item>
                    <b-nav-item v-if="currentLoggedIn === true" href="javascript:void(0);">
                        <router-link to="/" @click.native.prevent="logOut" class="nav-link logout">
                            <b-icon icon="power" aria-hidden="true" class="logout"></b-icon>
                        </router-link>
                    </b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <messages></messages>
        <div class="rest">
            <my-breadcrumbs></my-breadcrumbs>

            <router-view />
        </div>
        <div class="loading-holder" v-if="loading">
            <div class="loading">
                <div class="loader"></div>
            </div>
        </div>
    </div>
</template>

<script>
    import MyBreadcrumbs from './components/breadcrumb';
    import Messages from './views/messages/messages';
    export default {
        name: "app",
        components:{
            MyBreadcrumbs,
            Messages
        },
        created(){
            if(this.currentLoggedIn){
                this.$store.dispatch('app/getFavourites');
            }
        },
        computed: {
            loading(){
                return this.$store.getters['app/loading'];
            },
            favourites(){
                return this.$store.getters['app/get_favourites'];
            },
            currentLoggedIn () {
                return this.$store.state.auth.status.loggedIn;
            },
            currentUser() {
                return this.$store.state.auth.user;
            },
        },
        methods: {
            logOut() {
                this.$store.dispatch("auth/logout");
                this.$router.push("/login");

            },
        },
    };
</script>

<style lang="scss" scoped>
    body {
        margin: 0;
        padding: 0;
    }

    .navbar {
        height: 75px;
        background-color: #272727 !important;
    }

    .wrapper {
        margin-top: 100px;
    }

    .GameWeb {
        height: 100%;
        width: 150px;
    }

    .navbar-dark .navbar-nav .nav-link {
        color: #fff !important;
        font-weight: 550;
        font-size: 1.3rem;

        &:hover{
            color: #fff;
            opacity: 0.9;
        }
    }

    .app__toggle-menu {
        background-color: #111;
        border: none;
    }

    .nav-collapse > .navbar-nav {
        &:hover {
            background-color: #272727;
        }
    }

    @media (max-width: 991px) {
        .navbar-collapse {
            text-align: center;
            background-color: #333;
            position: absolute;
            width: 100%;
            left: 0;
            top: 74px;
            z-index: 10;
            padding-bottom: 14px;
            box-shadow: #111 0px 18px 24px -19px;
            border-top: 1px solid #fff;
        }

        .GameWeb {
            display: none;
        }
    }

    .logout {
        padding: 0px !important;
    }
</style>