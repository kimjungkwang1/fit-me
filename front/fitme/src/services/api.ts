import axios from 'axios';

export const api = axios.create({
  baseURL: 'https://fit-me.site',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('accessToken');
    // const refreshToken = localStorage.getItem('refreshToken ');
    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`;
    }
    // if (refreshToken) {
    //   config.headers['AuthorizationRefresh'] = `Bearer ${refreshToken}`;
    // }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
