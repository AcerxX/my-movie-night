package ro.projects.polls.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.Genre;

import java.util.List;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Integer> {
    List<Genre> findAllByIdIn(List<Integer> ids);
}
