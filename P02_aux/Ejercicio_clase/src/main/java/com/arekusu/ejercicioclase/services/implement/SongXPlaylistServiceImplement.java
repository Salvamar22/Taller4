package com.arekusu.ejercicioclase.services.implement;

import java.security.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.entities.SongXPlaylist;
import com.arekusu.ejercicioclase.models.entities.User;
import com.arekusu.ejercicioclase.repositories.SongRepository;
import com.arekusu.ejercicioclase.repositories.SongXPlaylistRepository;
import com.arekusu.ejercicioclase.services.SongXPlaylistService;

import jakarta.transaction.Transactional;

@Service
public class SongXPlaylistServiceImplement implements SongXPlaylistService{
	
	private final SongXPlaylistRepository songXPlaylistRepository;
	
	@Autowired
	public SongXPlaylistServiceImplement(SongXPlaylistRepository songXPlaylistRepository) {
        this.songXPlaylistRepository = songXPlaylistRepository;
    }


	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(java.sql.Timestamp time, Song song, Playlist playlist) {
		
		SongXPlaylist existingSXP = songXPlaylistRepository.findOneSongXRepositoryBySongAndPlaylist(song, playlist);
        if (existingSXP != null) {
            throw new IllegalArgumentException("Esta cancion ya esta en la playlist.");
        }
        SongXPlaylist SXP = new SongXPlaylist(
        		time,
        		song,
        		playlist
        		);
        		songXPlaylistRepository.save(SXP);
	}

	@Override
	public List<SongXPlaylist> findAll() {
		return songXPlaylistRepository.findAll();
	}


	@Override
	public  List<SongXPlaylist> findByPlaylist(Playlist playlist) {
		
		return songXPlaylistRepository.findByPlaylist(playlist);
	}
	
	
}
