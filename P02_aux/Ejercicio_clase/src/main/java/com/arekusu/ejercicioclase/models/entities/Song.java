package com.arekusu.ejercicioclase.models.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private UUID code;

    @Column(name = "title")
    @NotEmpty
    private String title;

    @Column(name = "duration")
    @NotEmpty
    private int duration;

    public Song(@NotEmpty String title, @NotEmpty int duration) {
        this.title = title;
        this.duration = duration;
        
        }
    
    public String getSongDuration() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
    
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
     
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "code")
    private Playlist playlist;
}
