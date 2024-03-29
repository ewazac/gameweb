import Vue from 'vue'
import VueRouter from 'vue-router'
import Forum from '../views/Forum.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Review_accept from '../views/admin/Review_accept.vue'
import Search from '../components/Search'
import Users from './users';
import AdminRoutes from './admin';
import NewsEditAdd from '../views/admin/news-edit-add';
import Quizes from '../router/quiz';
import Store from '../store/index'
import Favourites from '../views/users/favourites';
import Contest from '../router/contest';
import Reccomended from '../components/Recommended';
import ChangePassword from '../views/users/change-password'

Vue.use(VueRouter)

const routes = [
  {
    path: '/forum',
    name: 'Forum',
    component: Forum,
    meta:{auth: true}
  },
  {
    path: '/thread',
    name: 'Thread',
    component: () => import('../components/Thread.vue'),
    meta:{auth: true}
  },
  {
    path: '/',
    name: 'Home Page',
    component: () => import('../views/HomePage.vue'),
    meta:{auth:false}
  },
  {
    path: '/games',
    name: 'Games',
    component: () => import('../components/Games.vue'),
    meta:{auth: false}
  },
  {
    path: '/reviews',
    name: 'reviews', 
    component: () => import('../components/ReviewList.vue'),
    meta:{auth: true}
  },
  {
    path: '/recommended',
    name: 'Recommended',
    component: Reccomended,
    meta:{auth: false}
  },
  {
    path: '/admin/reviewstoaccept',
    name: 'Review To Accept',
    component: Review_accept,
    meta:{auth: true}
  },
  {
    path: '/game',
    name: 'game-detail',
    component: () => import('../components/Game.vue'),
    meta:{auth: false}
  },
  {
    path: '/user',
    name: 'user-detail',
    component: () => import('../components/UsersAccount.vue'),
    meta:{auth: false}
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('../components/Account.vue'),
    meta:{hideBreadcrumbs: true}
  },
  {
    path: '/news',
    name: 'News',
    component: () => import('../components/News.vue'),
    meta:{auth: false}
  },
  {
    path: '/news/:id',
    name: 'News',
    component: () => import('../components/SingleNews.vue'),
    meta:{auth: false}
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
  AdminRoutes,
  {
    path: '/admin/news/:id',
    name: 'news',
    component: NewsEditAdd,
    meta:{auth: true, hideBreadcrumbs: true}
  },
  {
    path: '/admin/news/create',
    name: 'news',
    component: NewsEditAdd,
    meta:{auth: true, hideBreadcrumbs: true}
  },
  {
    path: '/favourites',
    name: 'Ulubione',
    component: Favourites,
    meta:{auth: true}
  },
  {
    path: '/change-password',
    name: 'Zmiana hasła',
    component: ChangePassword,
    meta:{auth:false}
  },
  {
    path: '/search',
    name: 'Search',
    component: Search,
    meta:{auth: false}
  },
  {
    path: '*',
    redirect: '/games',
    meta:{auth: false}
  },
]


Quizes.forEach(item => {
  routes.push(item);
})

Contest.forEach(item => {
  routes.push(item);
})

const router = new VueRouter({
  mode: 'history',
  linkActiveClass: "active", // active class for non-exact links.
  linkExactActiveClass: "active",
  routes
})

router.beforeEach((to, from, next) => {
  /*const publicPages = ['/login', '/Register', '/games', '/game/!*', '/reset-password'];*/
  /*const authRequired = !publicPages.includes(to.path);*/
  Store.commit('app/SET_BREADCRUMBS', []);
  var authRequired = to.meta.auth != false;
  const loggedIn = localStorage.getItem('user');
  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    console.log('TEST');
    next();
  }
});

export default router

