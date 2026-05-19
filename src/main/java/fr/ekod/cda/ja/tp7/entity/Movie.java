package fr.ekod.cda.ja.tp7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "release_year")
    int releaseYear;

    @Column(name = "duration_minutes")
    int durationMinutes;

    @Column(name = "genre")
    String genre;

    @Column(name = "synopsis")
    String synopsis;

    @ManyToOne
    @JoinColumn(name = "director_id")
    Director director;

}