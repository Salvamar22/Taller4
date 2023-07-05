import React, { useState, useEffect } from 'react'
import PlaylistsContainer from '../components/PlaylistsContainer'
import SongsContainer from '../components/SongsContainer'
import AddButton from '../components/AddButton'
import { useUserContext } from '../context/userContext'
import './home.css'
import services from '../services/user.services'
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import ToggleButton from 'react-bootstrap/ToggleButton';
import PageBtn from '../components/PageBtn'

const Home = () => {
    const [playlists, setPlaylists] = useState([]);
    const [radioValue, setRadioValue] = useState('1');
    const [songs, setSongs] = useState([]);
    const [page, setPage] = useState(0);
    const { username } = useUserContext();
    const title = ""
    const radios = [
        { name: 'Playlists', value: '1' },
        { name: 'Canciones', value: '2' },
    ];

    useEffect(() => {
        const fetchPlaylists = async () => {
            try {
                const response = await services.getPlaylists(username, title);
                const responseS = await services.getSongs(page)
                setPlaylists(response);
                setSongs(responseS);
                console.log(response)
            } catch (error) {
                console.log(error);
            }
        };

        fetchPlaylists();
    }, [radioValue, username, page]);


    const renderContainer = () => {
        if (radioValue === '1') {
            return <PlaylistsContainer playlists={playlists}/>;
        } else if (radioValue === '2') {
            return <SongsContainer canciones={songs} playlists={playlists} />;
        }
    };


    const handleNextPage = () => {
        setPage(page + 1);
    };

    const handlePrevPage = () => {
        setPage(page - 1);
    };

    return (
        <div className='home'>
            <AddButton />
            <ButtonGroup className="">
                {radios.map((radio, idx) => (
                    <ToggleButton
                        key={idx}
                        id={`radio-${idx}`}
                        type="radio"
                        variant="secondary"
                        name="radio"
                        value={radio.value}
                        checked={radioValue === radio.value}
                        onChange={(e) => setRadioValue(e.currentTarget.value)}
                    >
                        {radio.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>
            {renderContainer()}
            
            {radioValue === '2' && (
                <PageBtn onNext={handleNextPage} onPrev={handlePrevPage} />
            )}
        </div>
    )
}

export default Home
