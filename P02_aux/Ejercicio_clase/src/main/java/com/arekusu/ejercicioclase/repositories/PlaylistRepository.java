package com.arekusu.ejercicioclase.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.entities.User;

@Repository
public interface PlaylistRepository extends ListCrudRepository<Playlist, UUID> {
    List<Playlist> findByTitle(String title);
    List<Playlist> findByUser(User user);
    List<Playlist> findByUserAndTitleContaining(User user, String titleFragment);
    void deleteByTitle(String title);;
	
}