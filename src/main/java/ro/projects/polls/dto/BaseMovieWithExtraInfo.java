package ro.projects.polls.dto;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import ro.projects.polls.entity.Genre;

import java.util.List;

public class BaseMovieWithExtraInfo extends BaseMovie {
    private Integer alreadyAdded;
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public BaseMovieWithExtraInfo setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public BaseMovieWithExtraInfo addGenre(Genre genre) {
        this.genres.add(genre);
        return this;
    }

    public Integer getAlreadyAdded() {
        return alreadyAdded;
    }

    public void setAlreadyAdded(Integer alreadyAdded) {
        this.alreadyAdded = alreadyAdded;
    }
}
