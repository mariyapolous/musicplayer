package com.example.musicplayer.services;

import com.example.musicplayer.entities.SongEntity;
import com.example.musicplayer.exceptions.ResourceNotFoundException;
import com.example.musicplayer.mappers.PlaylistMapper;
import com.example.musicplayer.mappers.SongMapper;
import com.example.musicplayer.models.Playlist;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.repositories.SongRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private SongRepository songRepository;
    private PlaylistService playlistService;
    private SongMapper songMapper;
    private PlaylistMapper playlistMapper;

    public SongService(SongRepository songRepository,
                       PlaylistService playlistService,
                       SongMapper songMapper,
                       PlaylistMapper playlistMapper) {
        this.songRepository = songRepository;
        this.playlistService = playlistService;
        this.songMapper = songMapper;
        this.playlistMapper = playlistMapper;
    }

    public List<Song> getAllSongs() {
        List<SongEntity> queryResult = songRepository.findAll();
        return songMapper.toSongList(queryResult);
    }

    public List<Playlist> getAllPlaylistsThatSongBelongs(Long songId) {

        Optional<SongEntity> queryResult = songRepository.findById(songId);
        if (queryResult.isPresent())
            return playlistMapper.toPlaylistList(queryResult.get().getPlaylists());
        else
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
    }

    public Song getSongById(Long songId) {

        Optional<SongEntity> queryResult = songRepository.findById(songId);
        if (queryResult.isPresent())
            return songMapper.toSong(queryResult.get());
        else
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
    }

    public Song addSong(Song newSong) {

        SongEntity songEntity = this.persistSong(newSong);
        newSong.setId(songEntity.getId());
        return newSong;
    }

    public void updateSongById(Long songId, String newTitle, String newDesc, Float newLength) {

        Optional<SongEntity> queryResult = songRepository.findById(songId);
        if (!queryResult.isPresent()) {
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
        }

        SongEntity songEntity = queryResult.get();

        if (newTitle != null)
            songEntity.setTitle(newTitle);

        if (newDesc != null)
            songEntity.setDescription(newDesc);

        if (newLength != null)
            songEntity.setLength(newLength);

        this.persistSong(songEntity);
    }

    @Transactional
    public void deleteSongById(Long songId) {

        Optional<SongEntity> queryResult = songRepository.findById(songId);
        if (queryResult.isPresent()) {
            songRepository.delete(queryResult.get());
        }
    }

    @Transactional
    protected SongEntity persistSong(Song song) {

        SongEntity songEntity = songMapper.toSongEntity(song);
        songRepository.save(songEntity);
        return songEntity;
    }

    @Transactional
    protected void persistSong(SongEntity songEntity) {
        songRepository.save(songEntity);
    }
}
