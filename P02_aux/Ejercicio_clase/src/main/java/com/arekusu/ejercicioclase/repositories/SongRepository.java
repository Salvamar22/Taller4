package com.arekusu.ejercicioclase.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.arekusu.ejercicioclase.models.entities.Song;

public interface SongRepository extends JpaRepository<Song, UUID> {
	List<Song> findByTitle(String title);
    List<Song> findByTitleContainingIgnoreCase(String title);
    Song findOneByCode(UUID code);
}
