package com.example.musicplayer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UpdateDto {
    private String newTitle;
    private String newDescription;
}
