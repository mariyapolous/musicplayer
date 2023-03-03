package com.example.musicplayer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
@Entity(name = "Song")
@Table(name = "song")
@EntityListeners(AuditingEntityListener.class)
public class SongEntity extends BaseEntity{

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private long createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false)
    private long lastModifiedAt;

    @NotNull
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "length", columnDefinition = "float default 0.0")
    private float length;

    @Override
    public String toString() {
        return String.format("SongEntityModel with id %d and title %s", id, title);
    }

    @ManyToMany(mappedBy = "songs")
    @OrderBy("created_at DESC")
    private List<PlaylistEntity> playlists;
}
