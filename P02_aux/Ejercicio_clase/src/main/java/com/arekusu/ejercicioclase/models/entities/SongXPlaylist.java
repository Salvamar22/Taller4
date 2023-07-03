package com.arekusu.ejercicioclase.models.entities;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "songxplaylist")
public class SongXPlaylist {

	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "song_code", nullable = true)
	private Song song;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playlist_code", nullable = true)
	private Playlist playlist;
	
	@Column(name = "date_added")
	@NotEmpty
	private Timestamp dateAdded;	

	public SongXPlaylist(@NotEmpty Timestamp dateAdded, Song song, Playlist playlist) {
		super();
		this.dateAdded = dateAdded;
		this.song = song;
		this.playlist = playlist;
	}
	
	
	
}
