package fr.ekod.cda.ja.tp7.mapper;

import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.tp7.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toDto(Movie movie);

    Movie toEntity(CreateMovieDTO movieDTO);

    @Mapping(
            target = "directorFullName",
            expression = "java(movie.getDirector().getFirstName() + \" \" + movie.getDirector().getLastName())"
    )
    MovieSummaryDTO toSummaryDto(Movie movie);
}