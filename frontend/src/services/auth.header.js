import axios from 'axios'

const API_URL = 'http://localhost:8086/users';
//const API_URL = 'https://gameweb21.herokuapp.com/'

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
        if (response) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response;
      })
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL, {
      email: user.username,
      firstName: user.firstName,
      lastName: user.lastName,
      password: user.password
    });
  }
}

export default new AuthService();