package com.arekusu.ejercicioclase.models.dtos;

import java.util.List;
import java.util.UUID;

import com.arekusu.ejercicioclase.models.entities.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
	private UUID code;
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	private List<Song> songs;
	@JsonIgnore
	private int totalDuration;
	@JsonIgnore
	private int totalDurationMinutes;
	@JsonIgnore
	private int totalDurationSeconds;
	
	
	public String getPlaylistDuration() {
        return String.format("%02d:%02d", totalDurationMinutes, totalDurationSeconds);
    }

}
