package com.example.musicplayer.services;

import com.example.musicplayer.entities.PlaylistEntity;
import com.example.musicplayer.entities.SongEntity;
import com.example.musicplayer.enums.PlaylistCategory;
import com.example.musicplayer.exceptions.ResourceNotFoundException;
import com.example.musicplayer.mappers.PlaylistMapper;
import com.example.musicplayer.mappers.SongMapper;
import com.example.musicplayer.models.Playlist;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.repositories.PlaylistRepository;
import com.example.musicplayer.repositories.SongRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private PlaylistRepository playlistRepository;
    private PlaylistMapper playlistMapper;
    private SongRepository songRepository;
    private SongMapper songMapper;


    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistMapper playlistMapper,
                           SongRepository songRepository,
                           SongMapper songMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    public List<Playlist> getAllPlaylists() {

        List<PlaylistEntity> queryResult = playlistRepository.findAll();
        return playlistMapper.toPlaylistList(queryResult);
    }

    public List<Song> getAllSongsOfPlaylist(Long playlistId) {

        Optional<PlaylistEntity> queryResult = playlistRepository.findById(playlistId);
        if (queryResult.isPresent())
            return songMapper.toSongList(queryResult.get().getSongs());
        else
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
    }

    public Playlist getPlaylistById(Long playlistId) {

        Optional<PlaylistEntity> queryResult = playlistRepository.findById(playlistId);
        if (queryResult.isPresent())
            return playlistMapper.toPlaylist(queryResult.get());
        else
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
    }

    public Playlist createPlaylist(Playlist newPlaylist, String username) {

        /*
        Optional<UserEntityModel> userEntityModel = userRepository.findByUsername(username);
        if (!userEntityModel.isPresent()) {
            /* This if should never be entered since user is already authenticated!
            throw new UsernameNotFoundException(String.format("User not found by name: %s", username));
        }*/

        /*
        newPlaylist.setCreatedById(userEntityModel.get().getId());
        newPlaylist.setCreatedByName(username);
        */
        PlaylistEntity playlistEntity = this.persistPlaylist(newPlaylist);
        newPlaylist.setId(playlistEntity.getId());
        return newPlaylist;
    }

    public void updatePlaylistById(Long playlistId, String newTitle, String newDesc, PlaylistCategory newCategory) {

        Optional<PlaylistEntity> queryResult = playlistRepository.findById(playlistId);
        if (!queryResult.isPresent()) {
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
        }

        PlaylistEntity playlistEntity = queryResult.get();

        if (newTitle != null)
            playlistEntity.setTitle(newTitle);

        if (newDesc != null)
            playlistEntity.setDescription(newDesc);

        if (newCategory != null)
            playlistEntity.setCategory(newCategory);

        this.persistPlaylist(playlistEntity);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) {

        Optional<PlaylistEntity> queryResultPlaylist = playlistRepository.findById(playlistId);
        if (!queryResultPlaylist.isPresent()) {
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
        }

        Optional<SongEntity> queryResultSong = songRepository.findById(songId);
        if (!queryResultSong.isPresent()) {
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
        }

        PlaylistEntity playlistEntity = queryResultPlaylist.get();
        SongEntity songEntity = queryResultSong.get();
        playlistEntity.getSongs().add(songEntity);

        this.persistPlaylist(playlistEntity);
    }

    public void deleteSongFromPlaylist(Long playlistId, Long songId) {

        Optional<PlaylistEntity> queryResultPlaylist = playlistRepository.findById(playlistId);
        if (!queryResultPlaylist.isPresent()) {
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
        }

        Optional<SongEntity> queryResultSong = songRepository.findById(songId);
        if (!queryResultSong.isPresent()) {
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
        }

        PlaylistEntity playlistEntityModel = queryResultPlaylist.get();
        SongEntity songEntity = queryResultSong.get();
        playlistEntityModel.getSongs().remove(songEntity);

        this.persistPlaylist(playlistEntityModel);
    }

    @Transactional
    public void deletePlaylistById(Long playlistId) {

        Optional<PlaylistEntity> queryResult = playlistRepository.findById(playlistId);
        if (queryResult.isPresent()) {
            playlistRepository.delete(queryResult.get());
        }
    }

    @Transactional
    protected PlaylistEntity persistPlaylist(Playlist playlist) {

        PlaylistEntity playlistEntity = playlistMapper.toPlaylistEntity(playlist);
        playlistRepository.save(playlistEntity);
        return playlistEntity;
    }

    @Transactional
    protected void persistPlaylist(PlaylistEntity playlistEntity) {
        playlistRepository.save(playlistEntity);
    }
}
