import Vue from 'vue'
import VueRouter from 'vue-router'
//import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

import Users from './users';

Vue.use(VueRouter)

const routes = [/*
  {
    path: '/',
    name: 'Home',
    component: Home
  },*/
  {
    path: '/games',
    name: 'Games',
    component: () => import('../components/Games.vue'),
    meta:{auth: false}
  },
  {
    path: '/game',
    name: 'game-detail',
    component: () => import('../components/Game.vue'),
  },
  {
    path: '/Newgame',
    name: 'NewGame',
    component: () => import('../components/NewGame.vue'),
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('../components/Account.vue')
  },
  {
    path: '/news',
    name: 'News',
    component: () => import('../components/News.vue'),
    meta:{auth: false}
  },
  {
    path: '/review',
    name: 'Review',
    component: () => import('../components/Review.vue'),
    meta:{auth:true}
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta:{auth: false}
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register,
    meta:{auth: false}
  },
  Users,

  {
    path: '*',
    redirect: '/games',
    meta:{auth: false}
  },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
  /*const publicPages = ['/login', '/Register', '/games', '/game/!*', '/reset-password'];*/
  /*const authRequired = !publicPages.includes(to.path);*/
  var authRequired = to.meta.auth != false;
  const loggedIn = localStorage.getItem('user');
  console.log(authRequired, 'AUTH');
  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    console.log('TEST');
    next();
  }
});

export default router

