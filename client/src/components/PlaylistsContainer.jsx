import React from 'react'
import Playlist from './playlist'

const PlaylistsContainer = ({ playlists }) => {
    return (
        <div className='  bg-slate-300 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-3/4 h-auto rounded-xl overflow-auto'>
            {console.log(playlists)}
            {playlists.map((pl) => (
                <Playlist key={pl.code} title={pl.title} />
            ))}
        </div>
    )
}

export default PlaylistsContainer
