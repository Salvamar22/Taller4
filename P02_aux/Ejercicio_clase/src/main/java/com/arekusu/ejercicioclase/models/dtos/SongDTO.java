package com.arekusu.ejercicioclase.models.dtos;

import java.util.UUID;

public class SongDTO {
	private UUID code;
    private String title;
    private int minutes;
    private int seconds;

    public SongDTO() {
    }

    public SongDTO(String title, int minutes, int seconds) {
        this.title = title;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getTotalDurationInSeconds() {
        return (minutes * 60) + seconds;
    }

    public void setTotalDurationInSeconds(int totalDurationInSeconds) {
        this.minutes = totalDurationInSeconds / 60;
        this.seconds = totalDurationInSeconds % 60;
    }

    public String getSongDuration() {
        return String.format("%02d:%02d", minutes, seconds);
    }

	public UUID getCode() {
		return code;
	}

	public void setCode(UUID code) {
		this.code = code;
	}
}
