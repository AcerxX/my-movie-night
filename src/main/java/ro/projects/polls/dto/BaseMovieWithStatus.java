package ro.projects.polls.dto;

import com.uwetrottmann.tmdb2.entities.BaseMovie;

public class BaseMovieWithStatus extends BaseMovie {
    private Integer alreadyAdded;

    public Integer getAlreadyAdded() {
        return alreadyAdded;
    }

    public void setAlreadyAdded(Integer alreadyAdded) {
        this.alreadyAdded = alreadyAdded;
    }
}
