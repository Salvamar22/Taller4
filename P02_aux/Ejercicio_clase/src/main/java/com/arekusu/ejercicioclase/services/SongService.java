package com.arekusu.ejercicioclase.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arekusu.ejercicioclase.models.dtos.SongDTO;
import com.arekusu.ejercicioclase.models.entities.Song;

public interface SongService {
    Page<Song> getAllSongs(Pageable pageable);
    
    List<Song> searchSongsByTitle(String title);
    boolean deleteSongByTitle(String title);
    Song createSong(Song song);
    Song createSong(SongDTO songDTO);
    Song searchSongByCode(String code);
}