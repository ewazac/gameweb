import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `https://gameweb.projektstudencki.pl/api`,
  withCredentials: true,
})
