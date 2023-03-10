package com.example.musicplayer.rest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RestApiError {
    private String title;
    private String message;
    private String type;
}
