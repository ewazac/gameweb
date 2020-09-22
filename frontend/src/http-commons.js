import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `http://localhost:4200`,
  headers: {
    'Access-Control-Allow-Origin': 'https://gameweb21.herokuapp.com/',
    'Access-Control-Allow-Credentials': true,
  }
})
