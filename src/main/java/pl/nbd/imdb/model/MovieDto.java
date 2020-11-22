package pl.nbd.imdb.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class MovieDto
{
    @NotBlank
    private String imdbId;

    private String titleType;

    private String primaryTitle;

    private String originalTitle;

    @NotNull
    private Boolean isAdult;

    private Integer startYear;

    private Integer endYear;

    @Min(0)
    private Integer runtimeMinutes;

    private String genres;

    @DecimalMin("0")
    @DecimalMax("10")
    private Double averageRating;

    @Min(0)
    private Integer numVotes;

    private String regions;

    private String director;
}
