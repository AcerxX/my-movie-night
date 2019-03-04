package ro.projects.polls.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.Movie;

import java.util.List;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {
    List<Movie> findAllByStatus(Integer status);
}
