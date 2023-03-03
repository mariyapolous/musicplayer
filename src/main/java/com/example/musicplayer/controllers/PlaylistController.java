package com.example.musicplayer.controllers;

import com.example.musicplayer.dtos.PlaylistUpdateDto;
import com.example.musicplayer.models.Playlist;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.rest.RestApiResponseBody;
import com.example.musicplayer.services.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/playlists", headers = "Accept=application/json")
public class PlaylistController {
    private PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<RestApiResponseBody<Resources<Playlist>>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return this.successCollection(playlists);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<RestApiResponseBody<Resource<Playlist>>> getPlaylistById(
            @PathVariable Long playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return this.success(playlist);
    }

    @PostMapping
    public ResponseEntity<RestApiResponseBody<Resource<Playlist>>> createPlaylist(
            @RequestBody Playlist playlist) {
        playlistService.createPlaylist(playlist);
        return this.success(playlist);
    }


    @PutMapping("/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> updatePlaylistById(
            @PathVariable Long playlistId,
            @RequestBody PlaylistUpdateDto requestBody) {
        playlistService.updatePlaylistById(playlistId, requestBody.getNewTitle(),
                requestBody.getNewDescription(), requestBody.getNewCategory());
        return this.success();
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> deletePlaylistById(
            @PathVariable Long playlistId) {
        playlistService.deletePlaylistById(playlistId);
        return this.success();
    }

    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<RestApiResponseBody<Resources<Song>>> getAllSongsOfPlaylist(
            @PathVariable Long playlistId) {
        //надо чтобы был LinkedList
        List<Song> songs = playlistService.getAllSongsOfPlaylist(playlistId);
        return this.successCollection(songs);
    }

    @PutMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return this.success();
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.deleteSongFromPlaylist(playlistId, songId);
        return this.success();
    }
}
