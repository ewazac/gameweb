import {AXIOS} from '../http-commons';

const API_URL = 'http://localhost:8086/users';

class AuthService {
  login(user) {
    return AXIOS.get(API_URL, {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return AXIOS.post(API_URL, {
      username: user.username,
      password: user.password
    });
  }
}

export default new AuthService();