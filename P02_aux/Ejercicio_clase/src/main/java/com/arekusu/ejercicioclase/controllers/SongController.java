	package com.arekusu.ejercicioclase.controllers;
	
	import java.util.List;
import java.util.stream.Collectors;
import java.io.UnsupportedEncodingException;
	import java.net.URLDecoder;
	import java.util.ArrayList;
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

import com.arekusu.ejercicioclase.models.dtos.SongDTO;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.entities.SongXPlaylist;
import com.arekusu.ejercicioclase.services.SongService;
import com.arekusu.ejercicioclase.services.SongXPlaylistService;
	
	@RestController
	@RequestMapping("/songs")
	public class SongController {
	
	    private final SongService songService;
	    private final SongXPlaylistService songXplaylistService;
	
	    @Autowired
	    public SongController(SongService songService) {
	        this.songService = songService;
			this.songXplaylistService = null;
	    }
	    
	    @GetMapping("/")
	    public ResponseEntity<?> getAllSongs(@RequestParam(defaultValue = "0") int page,
	                                         @RequestParam(defaultValue = "10") int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Song> songPage = songService.getAllSongs(pageable);

	        List<SongDTO> songDTOs = songPage.getContent().stream()
	                .map(song -> {
	                    SongDTO songDTO = new SongDTO();
	                    songDTO.setCode(song.getCode());
	                    songDTO.setTitle(song.getTitle());
	                    songDTO.setTotalDurationInSeconds(song.getDuration());
	                    return songDTO;
	                })
	                .collect(Collectors.toList());

	        return ResponseEntity.ok().body(songDTOs);
	    }

	
	    @GetMapping("/search/{title}")
	    public ResponseEntity<?> searchSongsByTitle(@PathVariable String title) {
	        try {
	            String decodedTitle = URLDecoder.decode(title, "UTF-8");
	            List<Song> songs = songService.searchSongsByTitle(decodedTitle);
	            
	            List<SongDTO> songDTOs = new ArrayList<>();
	            for (Song song : songs) {
	                SongDTO songDTO = new SongDTO();
	                songDTO.setTitle(song.getTitle());
	                songDTO.setTotalDurationInSeconds(song.getDuration());
	                songDTOs.add(songDTO);
	            }
	            
	            return new ResponseEntity<>(songDTOs, HttpStatus.OK);
	        } catch (UnsupportedEncodingException e) {
	            return new ResponseEntity<>("Error decoding title", HttpStatus.BAD_REQUEST);
	        }
	    }
		
		@DeleteMapping("/deleteByTitle/{title}")
		public ResponseEntity<?> deleteSongByTitle(@PathVariable String title) {
		    try {
		        String decodedTitle = URLDecoder.decode(title, "UTF-8");
		        boolean deleted = songService.deleteSongByTitle(decodedTitle);
		        if (deleted) {
		            return new ResponseEntity<>("Canción borrada.", HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>("La canción no existe.", HttpStatus.NOT_FOUND);
		        }
		    } catch (UnsupportedEncodingException e) {
		        return new ResponseEntity<>("Error decoding title", HttpStatus.BAD_REQUEST);
		    }
		}
		
		@GetMapping("/all")
		public ResponseEntity<?> findAllSongXPlaylist(){
			List<SongXPlaylist> songXplaylist = songXplaylistService.findAll();

			return new ResponseEntity<>(songXplaylist,HttpStatus.OK);
		}
		  
		
}