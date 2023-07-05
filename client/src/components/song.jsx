import React, { useState, useEffect } from 'react';
import Playlist from './playlist';
import services from '../services/user.services';

const Song = ({ code, title, duration, playlists }) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const [selectedPlaylist, setSelectedPlaylist] = useState(null);
  const [selectedSong, setSelectedSong] = useState(null);
  const [isRequestSent, setIsRequestSent] = useState(false); // Variable de estado para controlar la petición

  const handleTogglePlaylists = () => {
    setIsExpanded(!isExpanded);
  };

  const handlePlaylistClick = (playlistId, codeSong) => {
    setSelectedPlaylist(playlistId);
    setSelectedSong(codeSong);
    setIsRequestSent(false); // Reiniciar la variable de estado al seleccionar una nueva playlist
  };

  useEffect(() => {
    if (selectedSong && selectedPlaylist && !isRequestSent) {
      saveSongToPlaylist(selectedPlaylist, selectedSong);
      setIsRequestSent(true); // Establecer la variable de estado para evitar peticiones adicionales
    }
  }, [selectedSong, selectedPlaylist, isRequestSent]);

  const saveSongToPlaylist = async (playlistId, songId) => {
    try {
      console.log(selectedSong, selectedPlaylist);
      const response = await services.addSongToPlaylist(playlistId, songId);

      if (response.status === 201) {
        // La petición fue exitosa, muestra el alert
        alert('¡La canción se agregó exitosamente!');
      } else {
        // La petición no fue exitosa, muestra un mensaje de error
        alert('Hubo un error al agregar una canción a la playlist.');
      }
      console.log(response);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className={`song-item w-auto`}>
      <button
        className="song-header flex between h-10 font-bold text-white bg-slate-800 m-1 rounded-md w-full justify-between p-2"
        onClick={handleTogglePlaylists}
      >
        <h2>{title}</h2>
        <h2>{duration}</h2>
      </button>
      {isExpanded && (
        <div className="playlists-list ml-4 w-1/2">
          <p>Playlist existentes:</p>
          {playlists.map((playlist) => (
            <Playlist
              key={playlist.code}
              title={playlist.title}
              onClick={() => handlePlaylistClick(playlist.code, code)}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default Song;
