package com.arekusu.ejercicioclase.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.dtos.SavePlaylistDTO;
import com.arekusu.ejercicioclase.models.entities.User;

@Service
public interface PlaylistService {
	void save (SavePlaylistDTO playlist , User user) throws Exception;
	void deleteByTitle(String title) throws Exception;
	Playlist findOneById(String id);
	List<Playlist> findAll();
	List<Playlist> findPlaylistByUser(User user);
    List<Playlist> findPlaylistsByUserAndTitle(User user, String title);
	
}