package com.example.musicplayer.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
public class Song {
    private Long id;
    private long createdAt;
    private long lastModifiedAt;
    private String title;
    private String description;
    private float length;
    private String artistName;
    private String albumName;
    private List<Long> playlistIds;
}
