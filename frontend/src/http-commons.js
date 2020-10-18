import axios from 'axios'

export const AXIOS = axios.create({
  url: `https://gameweb21.herokuapp.com/`,
  withCredentials: true,
})
