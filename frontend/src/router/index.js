import Vue from 'vue'
import VueRouter from 'vue-router'
//import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

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
    component: () => import('../components/Games.vue')
  },
  {
    path: '/game',
    name: 'game-detail',
    component: () => import('../components/Game.vue')
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('../components/Account.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/Search',
    name: 'Search',
    component: () => import('../components/Search.vue')
  },
  { 
    path: '*',
    redirect: '/games',
  },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/Register', '/games', '/game/*','/search'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');
  console.log(to.matched.some(route => route.meta.requiresSession))

  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    next();
  }
});

export default router

