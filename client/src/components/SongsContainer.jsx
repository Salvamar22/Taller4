import React, { useState } from 'react';
import Playlist from './playlist';
import Song from './song';
import services from '../services/user.services';

const SongsContainer = ({ canciones, playlists }) => {


  return (
    <div className="bg-slate-300 w-3/4 h-auto rounded-xl overflow-auto">
      {canciones.map((song) => (
        <Song
          key={song.code}
          code={song.code}
          title={song.title}
          duration={song.songDuration}
          playlists={playlists}
        />
      ))}
    </div>
  );
};

export default SongsContainer;
