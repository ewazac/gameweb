import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `http://localhost:4200`,
  headers: {
    'Access-Control-Allow-Origin': 'http://localhost:8086/users/',
    'Access-Control-Allow-Credentials': true,
  }
})
