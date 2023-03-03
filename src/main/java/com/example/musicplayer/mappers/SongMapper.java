package com.example.musicplayer.mappers;

import com.example.musicplayer.entities.PlaylistEntity;
import com.example.musicplayer.entities.SongEntity;
import com.example.musicplayer.models.Song;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface SongMapper {
    List<Song> toSongList(List<SongEntity> songEntityList);

    Song toSong(SongEntity songEntity);

    @AfterMapping
    default void afterSongMapping(SongEntity songEntity,
                                  @MappingTarget Song song) {
        if (songEntity.getPlaylists() != null) {
            List<Long> playlistIds = new ArrayList<>();
            for (PlaylistEntity playlist: songEntity.getPlaylists()) {
                playlistIds.add(playlist.getId());
            }
            song.setPlaylistIds(playlistIds);
        }
        else
            song.setPlaylistIds(null);
    }

    List<SongEntity> toSongEntityList(List<Song> songList);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    SongEntity toSongEntity(Song song);

    @AfterMapping
    default void afterSongEntityMapping(Song song,
                                             @MappingTarget SongEntity songEntity) {
        if (song.getPlaylistIds() != null) {
            List<PlaylistEntity> playlists = new ArrayList<>();
            for (Long playlistId: song.getPlaylistIds()) {
                PlaylistEntity playlist = new PlaylistEntity();
                playlist.setId(playlistId);
                playlists.add(playlist);
            }
            songEntity.setPlaylists(playlists);
        }
        else
            songEntity.setPlaylists(null);
    }
}
