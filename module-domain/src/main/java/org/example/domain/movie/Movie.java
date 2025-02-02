package org.example.domain.movie;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Movie", indexes = {
        @Index(name = "genre_title_idx", columnList = "genre, title"),
        @Index(name = "title_idx", columnList = "title"),
})
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private int runningTime;

    @Column(nullable = false)
    private boolean isPlaying;
}
