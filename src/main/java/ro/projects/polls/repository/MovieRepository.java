package ro.projects.polls.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {
}
