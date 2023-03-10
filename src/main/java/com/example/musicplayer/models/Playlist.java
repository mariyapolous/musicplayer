package com.example.musicplayer.models;

import com.example.musicplayer.enums.PlaylistCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class Playlist {
    private Long id;
    private long createdAt;
    private long lastModifiedAt;
    private String title;
    private String description;
    private PlaylistCategory category;
    private float totalLength;
    private int songCount;
    private List<Long> songIds; // we keep songs as a list of song ids in playlist DTO

}
