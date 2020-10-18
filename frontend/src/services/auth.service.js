export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));
    console.log('a',user.authdata)
    if (user && user.authdata) {
      return { Authorization: 'Basic ' + user.authdata };
    } else {
      return {};
    }
  }