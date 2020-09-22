import axios from 'axios'

const API_URL = 'http://localhost:8086/users';
//const API_URL = 'https://gameweb21.herokuapp.com/'

class AuthService {
  login(user) {
    return axios.get(API_URL, {
        withCredentials: false,
        auth: {
          username: user.username,
          password: user.password
        }
      })
      .then(response => {
        var data;
        for (let i=0; i<response.data.length ; ++i) {
          let value = response.data[i]
          if (value.email == user.username) {
            data = value
            break;
          }
        }
        if (data) {
          localStorage.setItem('user', JSON.stringify(data));
        }
        return data;
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