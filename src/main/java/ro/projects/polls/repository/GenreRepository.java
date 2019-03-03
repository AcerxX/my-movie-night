package ro.projects.polls.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.Genre;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Integer> {
}
