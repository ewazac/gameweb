import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `https://gameweb21.herokuapp.com/`,
  withCredentials: true,
})
