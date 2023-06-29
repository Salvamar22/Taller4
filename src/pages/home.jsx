import React from 'react'
import PlaylistsContainer from './components/PlaylistsContainer'
import AddButton from './components/AddButton'
import './home.css'

const home = () => {
    return (
        <div className='home place-items-center p-4'>
            <AddButton />
            <PlaylistsContainer />
        </div>
    )
}

export default home

