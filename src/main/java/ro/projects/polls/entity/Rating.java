package ro.projects.polls.entity;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer rating = 0;

    public Movie getMovie() {
        return movie;
    }

    public Rating setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Rating setUser(User user) {
        this.user = user;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public Rating setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Rating setId(Integer id) {
        this.id = id;
        return this;
    }
}

