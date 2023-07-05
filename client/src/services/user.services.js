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
services.getSongs = async (page) => {
    try {
      const size=10;
      const response = await axios.get(`${BASE_URL}songs/` , {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
        params: {
             page,
             size },
      });
      console.log(response.data)
      return response.data;
    } catch (error) {
      console.log(error);
      return null;
    }
  };


  services.addSongToPlaylist = async (playlistCode, songCode) => {
    try {
        const formData = new URLSearchParams();
        formData.append('songCode', songCode);

        const response = await fetch(`${BASE_URL}api/playlist/${playlistCode}`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('token')}`,
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData,
        });
        if (response.status === 201) {
            const data = await response;
            return data;
        }
        return {};
    } catch (error) {
        console.log(error)
    }

    /* try {
        const params = new URLSearchParams();
        params.append('songCode', songCode);

      const response = await axios.post(`${BASE_URL}api/playlist/${playlistCode}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'content-type': 'application/x-www-form-urlencoded'
      },
        body: {
            params
        },
    }
      );
      console.log(response.data); 
    } catch (error) {
      console.error(error);
    } */
  };

  services.getPlaylistSongs = async (playlistCode) => {
    try {
        const response = await axios.get(`${BASE_URL}api/playlist/${playlistCode}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        });
        return response.data;
    } catch (error) {
        console.log(error);
        throw new Error('Error al obtener las playlists');
    }
};

export default services;