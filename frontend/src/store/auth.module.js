import AuthService from '../services/auth.header';
import Request from '../request';
const user = (typeof localStorage.getItem('user') == 'string')? JSON.parse(localStorage.getItem('user')) : localStorage.getItem('user');
var isAdmin = () => {
  return user.roles.find(x => x == 'ADMIN') != null;
}
const initialState = user
    ? { status: { loggedIn: true }, user, isAdmin: isAdmin(user) }
    : { status: { loggedIn: false }, user: null, isAdmin: false };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user).then(
          user => {
            commit('loginSuccess', user);
            return Promise.resolve(user);
          })
          .catch(error => {
            commit('loginFailure');
            return Promise.reject(error);
          });
    },
    logout({ commit }) {
      AuthService.logout();
      commit('logout');
    },
    register({ commit }, user) {
      if (!user.firstName) {
        user.firstName = null
      }
      if (!user.lastName) {
        user.lastName = null
      }
      return AuthService.register(user).then(
          response => {
            commit('registerSuccess');
            return Promise.resolve(response.data);
          },
          error => {
            commit('registerFailure');
            return Promise.reject(error);
          }
      );
    },
    sendResetLink(payload, data){
      return new Promise((resolve) => {
        Request({
          url:'/users/restart?email='+data.email,
          method: 'POST'
        })
        resolve();
      })
    }
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
      state.isAdmin = isAdmin(user);
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    }
  },
  getters:{
    isAdmin(state){
      return state.isAdmin;
    }
  }
};