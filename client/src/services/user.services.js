const BASE_URL = 'http://localhost:8080/';
const services = {};
import axios from 'axios';

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

services.createPlaylist = async (titleP, descriptionP) => {
    try {
        const formData = new URLSearchParams();
        formData.append('title', titleP);
        formData.append('description', descriptionP);

        const response = await fetch(`${BASE_URL}api/playlist`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('token')}`,
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData,
        });
        if (response.ok) {
            const data = await response;
            return data;
        }
        return {};
    } catch (error) {
        console.log(error)
    }
};


services.getPlaylists = async (username, title) => {

    /*    try {
           const response = await axios.get(`${BASE_URL}api/user/playlist?User=${username}&title=${title}`, {
               headers: {
                   Authorization: `Bearer ${localStorage.getItem('token')}`,
               },
               params: {
                   username,
                   title
               },
           });
           return response.data;
       } catch (error) {
           console.log(error);
           throw new Error('Error al obtener las playlists');
       }
   }; */

    try {
        const response = await fetch(`${BASE_URL}api/user/playlist?User=${username}&title=${title}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('token')}`,
            },

        });
        if (response.ok) {
            const data = await response.json();
            console.log(data)
            return data;
        }


    } catch (error) {
        console.error({ error });

    }
};



export default services;