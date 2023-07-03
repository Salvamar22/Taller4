import React, { useState, useEffect } from 'react'
import PlaylistsContainer from '../components/PlaylistsContainer'
import AddButton from '../components/AddButton'
import { useUserContext } from '../context/userContext'
import './home.css'
import services from '../services/user.services'
import playlist from '../components/playlist'

const Home = () => {
    const [playlists, setPlaylists] = useState([]);
    const [playlistCount, setPlaylistCount] = useState(0);
    const { username } = useUserContext();
    const title = ""

    useEffect(() => {
        const fetchPlaylists = async () => {
            try {
                const response = await services.getPlaylists(username, title);
                setPlaylists(response);
                console.log(response)
            } catch (error) {
                console.log(error);
            }
        };

        fetchPlaylists();
    }, [username, playlistCount]);


    const handlePlaylistAdded = () => {
        setPlaylistCount(playlistCount + 1); // Incrementar el contador cuando se agrega una playlist
    };

    return (
        <div className='home place-items-center p-4'>
            <AddButton onPlaylistAdded={handlePlaylistAdded} />
            <PlaylistsContainer playlists={playlists} />
        </div>
    )
}

export default Home

