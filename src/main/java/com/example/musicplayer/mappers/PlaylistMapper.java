package com.example.musicplayer.mappers;

import com.example.musicplayer.entities.PlaylistEntity;
import com.example.musicplayer.entities.SongEntity;
import com.example.musicplayer.models.Playlist;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SongMapper.class})
@Component
public interface PlaylistMapper {
    List<Playlist> toPlaylistList(List<PlaylistEntity> playlistEntityList);

    Playlist toPlaylist(PlaylistEntity playlistEntity);

    @AfterMapping
    default void afterPlaylistMapping(PlaylistEntity playlistEntity,
                                      @MappingTarget Playlist playlist) {
        float totalLength = 0;
        int songCount = 0;
        if (playlistEntity.getSongs() != null) {
            List<Long> songIds = new ArrayList<>();
            for (SongEntity songEntity: playlistEntity.getSongs()) {
                songIds.add(songEntity.getId());
                totalLength += songEntity.getLength();
                songCount++;
            }
            playlist.setSongIds(songIds);
            playlist.setTotalLength(totalLength);
            playlist.setSongCount(songCount);
        }
        else {
            playlist.setSongIds(null);
            playlist.setTotalLength(0);
            playlist.setSongCount(0);
        }
    }

    List<PlaylistEntity> toPlaylistEntityList(List<Playlist> playlists);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    PlaylistEntity toPlaylistEntity(Playlist playlist);

    @AfterMapping
    default void afterPlaylistEntityMapping(Playlist playlist,
                                                 @MappingTarget PlaylistEntity playlistEntity) {
        if (playlist.getSongIds() != null) {
            List<SongEntity> songEntityList = new ArrayList<>();
            for (Long songId: playlist.getSongIds()) {
                SongEntity songEntity = new SongEntity();
                songEntity.setId(songId);
                songEntityList.add(songEntity);
            }
            playlistEntity.setSongs(songEntityList);
        }
        else
            playlistEntity.setSongs(null);
    }
}
