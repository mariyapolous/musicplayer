package com.example.musicplayer.entities;
import com.example.musicplayer.enums.PlaylistCategory;
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
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
@Entity(name = "Playlist")
@Table(name = "playlist")
@EntityListeners(AuditingEntityListener.class)
public class PlaylistEntity extends BaseEntity{

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
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PlaylistCategory category;

    @Override
    public String toString() {
        return String.format("PlaylistEntityModel with id %d and title %s", id, title);
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "songs",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id")
    )
    @OrderBy("created_at DESC")
    private List<SongEntity> songs;

}
