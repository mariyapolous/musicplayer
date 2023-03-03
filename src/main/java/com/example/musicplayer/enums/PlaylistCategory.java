package com.example.musicplayer.enums;

public enum PlaylistCategory {
    Classical("Classical"),
    Rnb("RnB"),
    Blues("Blues"),
    Jazz("Jazz"),
    Rock("Rock");

    private String displayName;

    PlaylistCategory(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
