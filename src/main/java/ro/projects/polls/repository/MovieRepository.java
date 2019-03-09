package ro.projects.polls.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.Movie;

import java.util.List;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {
    List<Movie> findAllByStatus(Integer status);

    @Query(value = "SELECT m FROM Movie m INNER JOIN m.ratings r WHERE m.status = 1 GROUP BY m.id ORDER BY SUM(r.rating) DESC")
    List<Movie> topRatedActiveMovies();
}
