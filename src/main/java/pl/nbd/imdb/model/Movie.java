package pl.nbd.imdb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Movie
{
    @Id
    private String id;

    @Indexed(unique = true)
    private String imdbId;

    @Indexed
    private String titleType;

    private String primaryTitle;

    private String originalTitle;

    @Indexed
    private Boolean isAdult;

    @Indexed
    private Integer startYear;

    private Integer endYear;

    @Indexed
    private Integer runtimeMinutes;

    private String genres;

    @Indexed
    private Double averageRating;

    private Integer numVotes;

    private String regions;

    @Indexed
    private String director;
}
