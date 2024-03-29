import axios from 'axios';

const corsProxyUrl = window.location.origin;
export const api = axios.create({
  baseURL: 'https://fit-me.site',
  headers: {
    'Content-Type': 'application/json',
    'cors-proxy-url': corsProxyUrl,
  },
  withCredentials: true,
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
// api.interceptors.request.use(
//   (config) => {
//     console.log('Request Headers:', config.headers);
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );
