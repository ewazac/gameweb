import axios from 'axios'

const API_URL = 'http://localhost:8086/users';

class AuthService {
  login(user) {
    return axios.get(API_URL, {
        withCredentials: true,
        auth: {
          username: user.username,
          password: user.password
        }
      })
      .then(response => {
        console.log(response.data)
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
    return axios.post(API_URL, {
      username: user.username,
      password: user.password
    });
  }
}

export default new AuthService();