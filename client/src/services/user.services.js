const BASE_URL = 'http://localhost:8080/';
import axios from 'axios';
const services = {};

/* services.login = async (identifier, password) => {
    try {
        const response = await axios.post(`${BASE_URL}api/login`, {
            identifier,
            password
        });
        return response.data;
    } catch (error) {
        console.log(error.response.data)
        return error.response.data;
    }
} */

services.login = async (username, password) => {
    try {
        const formData = new URLSearchParams();
        formData.append('identifier', username);
        formData.append('password', password);

        const response = await fetch(`${BASE_URL}api/login`, {
            method: "POST",
            headers: {
                "Content-type": "application/x-www-form-urlencoded",
            },
            body: formData,
        });
        if (response.ok) {
            const data = await response.json();
            return data;
        }
        return {};
    } catch (error) {
        console.log(error)
    }
};
export default services;