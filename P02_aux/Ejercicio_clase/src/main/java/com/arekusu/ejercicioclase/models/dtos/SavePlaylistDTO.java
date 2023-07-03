package com.arekusu.ejercicioclase.models.dtos;

import com.arekusu.ejercicioclase.models.entities.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Valid
@AllArgsConstructor
public class SavePlaylistDTO {
    
    @NotEmpty
    private String title;
    
    @NotEmpty
    private String description;
 
    @NotEmpty
    @Pattern(regexp = "^[0-9A-Z]{4}$")
    private User user;
}
