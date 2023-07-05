package com.arekusu.ejercicioclase.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arekusu.ejercicioclase.models.dtos.MessageDTO;
import com.arekusu.ejercicioclase.models.dtos.PlaylistDTO;
import com.arekusu.ejercicioclase.models.dtos.SavePlaylistDTO;

import com.arekusu.ejercicioclase.models.dtos.SongxPlaylistDTO;
import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Song;
import com.arekusu.ejercicioclase.models.entities.SongXPlaylist;
import com.arekusu.ejercicioclase.models.entities.User;
import com.arekusu.ejercicioclase.repositories.UserRepository;
import com.arekusu.ejercicioclase.services.PlaylistService;
import com.arekusu.ejercicioclase.services.SongService;
import com.arekusu.ejercicioclase.services.SongXPlaylistService;	
import com.arekusu.ejercicioclase.services.UserService;
import com.arekusu.ejercicioclase.utils.JWTTools;
import com.arekusu.ejercicioclase.utils.RequestErrorHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")

public class PlaylistController {
	
	@Autowired
    private final PlaylistService playlistService;
	@Autowired
    private final SongService songService;
	@Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserRepository userRepository;
	@Autowired
    private final SongXPlaylistService songXPlaylistService;
	@Autowired
	private RequestErrorHandler errorHandler;
	
    @Autowired
    public PlaylistController(PlaylistService playlistService, SongService songService, JWTTools jwtTools, UserRepository userRepository, SongXPlaylistService songXPlaylistService) {
        this.playlistService = playlistService;
        this.songService = songService;
        this.jwtTools = jwtTools;
        this.userRepository = userRepository;
        this.songXPlaylistService = songXPlaylistService;
    }
    
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/playlist")
    public ResponseEntity<?> savePlaylist(@RequestParam("title") String title, @RequestParam("description") String description, HttpServletRequest request) {
    	String tokenHeader = request.getHeader("Authorization");
    	String token = tokenHeader.substring(7);
    	String username = jwtTools.getUsernameFrom(token);
		User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        if (title.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ingrese un nombre para la playlist");
        }
        if (description.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ingrese una descripción para la playlist");
        }
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No tienes permisos para esta acción");
        }
        
        SavePlaylistDTO newPlaylist = new SavePlaylistDTO(title, description, user);

        try {
            playlistService.save(newPlaylist, user);
            return ResponseEntity.ok("Playlist guardada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al guardar la playlist");
        }
    }

    @DeleteMapping("/deleteByTitle/{title}")
    public ResponseEntity<String> deleteByTitle(@PathVariable String title) {
        try {
            playlistService.deleteByTitle(title);
            return ResponseEntity.ok("Playlist eliminada con éxito");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al eliminar la playlist");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Playlist>> findAllPlaylist() {
        List<Playlist> playlists = playlistService.findAll();

        if (playlists == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(playlists);
    }
    
    @PostMapping("/playlist/{playlistCode}")
    public ResponseEntity<?> addsSongToPlaylist(@PathVariable String playlistCode,@ModelAttribute @Valid SongxPlaylistDTO data, BindingResult validations) {
        if (validations.hasErrors()) {
            return new ResponseEntity<>(validations.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        
        Song song = songService.searchSongByCode(data.getSongCode());
        
        List<SongXPlaylist> songXplaylists = songXPlaylistService.findAll();
        boolean flag = true;
        
        for (int i = 0; i < songXplaylists.size(); i++) {
            if (songXplaylists.get(i).getSong().equals(song)) {
                flag = false;
                break;
            }
        }
        
        if (!flag) {
            return new ResponseEntity<>(new MessageDTO("Song Duplicate"), HttpStatus.BAD_REQUEST);
        }
        
        Playlist playlist = playlistService.findOneById(playlistCode);
        
        if (song != null && playlist != null) {
            try {
                songXPlaylistService.save(new Timestamp(System.currentTimeMillis()), song, playlist);
                return new ResponseEntity<>(new MessageDTO("¡Canción añadida a la playlist!"), HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        return new ResponseEntity<>(new MessageDTO("No se encontró la canción o la playlist"), HttpStatus.NOT_FOUND);
    }
    
    
    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<?> getPlaylistDuration(@PathVariable String playlistId) {

        Playlist playlist = playlistService.findOneById(playlistId);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }

        List<SongXPlaylist> songXPlaylists = songXPlaylistService.findByPlaylist(playlist);
        int totalDurationInSeconds = songXPlaylists.stream()
                .mapToInt(songXPlaylist -> songXPlaylist.getSong().getDuration())
                .sum();

        int minutes = totalDurationInSeconds / 60;  // Obtiene los minutos
        int seconds = totalDurationInSeconds % 60;  // Obtiene los segundos

        List<Song> songs = songXPlaylists.stream().map(SongXPlaylist::getSong).collect(Collectors.toList());
        System.out.println(songs);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setCode(playlist.getCode());
        playlistDTO.setTitle(playlist.getTitle());
        playlistDTO.setDescription(playlist.getDescription());
        playlistDTO.setSongs(songs);
        playlistDTO.setTotalDurationMinutes(minutes);
        playlistDTO.setTotalDurationSeconds(seconds);

        return ResponseEntity.ok(playlistDTO);
    }
    
}	
   
