import axios from 'axios';

import { getCookie } from './cookieUtil.js';
import { httpStatusCode } from '@util/status.js';

const { VITE_SERVICE_BASE_URL } = import.meta.env;

// local vue api axios instance

function serverAxios() {
    const instance = axios.create({
        baseURL: VITE_SERVICE_BASE_URL,
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
    });

    instance.interceptors.request.use((config) => {
        const access_token = getCookie('access_token');
        // console.log('access : ', access_token);
        config.headers['authorization'] = `Bearer ${access_token}`;
        return config;
    });

    return instance;
}

export { serverAxios };
