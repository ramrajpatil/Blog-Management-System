import axios from "axios";
import { getToken } from "../auth/index-auth";

export const BASE_URL = "http://localhost:8080";

export const myAxios = axios.create({
  baseURL: BASE_URL,
});

export const privateAxios = axios.create({
  baseURL: BASE_URL,
});

// to add header.
privateAxios.interceptors.request.use(
    (config) => {
      let token = getToken();
      
      // Check if config and headers are defined
      if (config && config.headers) {
        // If token exists, set Authorization header
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        
        // console.log(config);
      } else {
        // Handle edge case where config or headers are undefined
        console.error('Invalid config structure:', config);
      }
      
      return config;
    },
    (error) => Promise.reject(error)
  );
