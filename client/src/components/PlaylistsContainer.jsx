import React, { useState } from 'react';
import Playlist from './playlist';

import Song from './song';
import services from '../services/user.services';

const PlaylistsContainer = ({ playlists }) => {

/* 
  const fetchPlSongs = async (playlistId) => {
    try {
        const response = await services.getPlaylistSongs(playlistId);
        
        setSongs(response.songs);
        console.log(songs);
        return response.songs;
    } catch (error) {
        console.log(error);
    }
};
 */

  return (
    <div className="bg-slate-300 w-3/4 h-auto rounded-xl overflow-y-auto overflow-x-hidden p-2 ">
      {playlists.map((playlist) => (
        <Playlist
          key={playlist.code}
          title={playlist.title}
          description={playlist.description}
          plDuration={playlist.playlistDuration}
          playlistId={playlist.code}
        />
      ))}
{/*       {selectedPlaylist && (
        <div className="song-list ml-4 w-1/2 ">
          {songs.map((song) => (
            <Song key={song.code} title={song.title} duration={song.songDuration} />
          ))}
        </div>
      )} */}
    </div>
  );
};

export default PlaylistsContainer;
