package ro.projects.polls.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.Movie;
import ro.projects.polls.entity.Rating;
import ro.projects.polls.entity.User;

import java.util.List;

public interface RatingRepository extends PagingAndSortingRepository<Rating, Integer> {
    Rating findByMovieAndUser(Movie movie, User user);

    List<Rating> findByUser(User user);
}
