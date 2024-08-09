import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json', 
    }
});

instance.interceptors.request.use(
    config => {
        const token = localStorage.getItem("JWT");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);


instance.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 403) {
            localStorage.removeItem("JWT");
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);


export default instance;
