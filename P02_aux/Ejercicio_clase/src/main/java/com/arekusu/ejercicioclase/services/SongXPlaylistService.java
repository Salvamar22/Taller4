package com.arekusu.ejercicioclase.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.entities.SongXPlaylist;

@Service
public interface SongXPlaylistService {
    List<SongXPlaylist> findAll();
    
    void save(Timestamp timestamp, Song song, Playlist playlist);

	List<SongXPlaylist> findByPlaylist(Playlist playlist);
	
}
