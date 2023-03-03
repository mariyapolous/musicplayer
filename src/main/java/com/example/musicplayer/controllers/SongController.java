package com.example.musicplayer.controllers;

import com.example.musicplayer.models.Song;
import com.example.musicplayer.rest.RestApiController;
import com.example.musicplayer.rest.RestApiResponseBody;
import com.example.musicplayer.services.PlaylistService;
import com.example.musicplayer.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/songs", headers = "Accept=application/json")
public class SongController extends RestApiController {
    private SongService songService;
    private PlaylistService playlistService;

    public SongController(SongService songService,
                          PlaylistService playlistService) {
        this.songService = songService;
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<RestApiResponseBody<Resources<Song>>> getAllSongs() {
        List<Song> songList = songService.getAllSongs();
        return this.successCollection(songList);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<RestApiResponseBody<Resource<Song>>> getSongById(
            @PathVariable Long songId) {
        Song song = songService.getSongById(songId);
        return this.success(song);
    }

    @PostMapping
    public ResponseEntity<RestApiResponseBody<Resource<Song>>> addSong(
            @RequestBody Song newSong,
            @PathVariable Optional<Long> albumId) {
        songService.addSong(newSong);
        return this.success(newSong);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteSongById(
            @PathVariable Long songId) {
        songService.deleteSongById(songId);
        return this.success();

        @PutMapping("/{songId}/playlists/{playlistId}")
        public ResponseEntity<RestApiResponseBody<?>> addSongToPlaylist(
                @PathVariable Long playlistId,
                @PathVariable Long songId) {
            playlistService.addSongToPlaylist(playlistId, songId);
            return this.success();
        }
    }

    @DeleteMapping("/{songId}/playlists/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.deleteSongFromPlaylist(playlistId, songId);
        return this.success();
    }
}
