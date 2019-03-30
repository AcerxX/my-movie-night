package ro.projects.polls.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {
    public static final Integer STATUS_DELETED = 0;
    public static final Integer STATUS_AVAILABLE = 1;
    public static final Integer STATUS_SEEN = 2;

    @Id
    private Integer id;
    private String title;
    private String originalTitle;
    private String posterPath;
    private String backdropPath;
    private Integer runtime;
    private String overview;
    private Date releaseDate;
    private String imdbId;
    private Double rating;
    private Integer status;
    private LocalDateTime created;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "movie_genre",
            joinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")}
    )
    private List<Genre> genres;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings;

    public Movie() {
        this.genres = new ArrayList<>();
        this.created = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Movie setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Movie setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Movie setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Movie setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Movie setRuntime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Movie setImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public Movie setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Movie setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Movie setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Movie setGenres(List<Genre> genre) {
        this.genres = genre;
        return this;
    }

    public Movie addGenre(Genre genre) {
        this.genres.add(genre);
        return this;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public Movie setRatings(List<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Movie setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public Integer getTotalVotesCount() {
        return this.getRatings().stream().mapToInt(Rating::getRating).sum();
    }
}

