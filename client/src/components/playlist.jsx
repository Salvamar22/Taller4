import React, { useState, useEffect } from 'react';
import Song from './song';
import services from '../services/user.services';

const Playlist = ({ playlistId, title, description }) => {
  const [selectedPlaylist, setSelectedPlaylist] = useState(false);
  const [songs, setSongs] = useState([]);

  const handlePlaylistClick = () => {
    setSelectedPlaylist(!selectedPlaylist);
  };

  useEffect(() => {
    if (selectedPlaylist) {
      fetchPlSongs(playlistId);
    }
  }, [selectedPlaylist, playlistId]);

  const fetchPlSongs = async (playlistId) => {
    try {
      const response = await services.getPlaylistSongs(playlistId);
      setSongs(response.songs);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div>
      <button
        className={`song-header flex between h-10 font-bold text-white bg-slate-800 m-1 rounded-md ${
          description ? 'w-full' : ''
        } justify-between p-2 m-2`}
        onClick={handlePlaylistClick}
      >
        <h2 className="p-1">Titulo: {title}</h2>
        {description && <h3 className="p-1">Descripción: {description}</h3>}
      </button>
      {selectedPlaylist && (
        <div className="song-list ml-4 w-1/2">
          {songs.map((song) => (
            <Song key={song.code} title={song.title} duration={song.songDuration} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Playlist;
