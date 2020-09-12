import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/games',
    name: 'Games',
    component: () => import('../components/Games.vue')
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
    path: '*',
    redirect: '/',
  },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/Games'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  if (authRequired && !loggedIn) {
    return next({ 
      path: '/login', 
      query: { returnUrl: to.path } 
    });
  }

  next();
})