package com.arekusu.ejercicioclase.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.entities.SongXPlaylist;

@Repository
public interface SongXPlaylistRepository extends ListCrudRepository<SongXPlaylist, UUID> {
	SongXPlaylist findOneSongXRepositoryBySongAndPlaylist(Song song, Playlist playlist);
	
	 List<SongXPlaylist> findByPlaylist(Playlist playlist);
}
