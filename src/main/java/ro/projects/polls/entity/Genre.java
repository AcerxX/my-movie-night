package ro.projects.polls.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {
    public static final Integer STATUS_DELETED = 0;
    public static final Integer STATUS_ENABLED = 1;

    @Id
    private Integer id;
    private String name;
    private Integer status;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    public Integer getId() {
        return id;
    }

    public Genre setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Genre setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Genre setMovies(List<Movie> movies) {
        this.movies = movies;
        return this;
    }
}

