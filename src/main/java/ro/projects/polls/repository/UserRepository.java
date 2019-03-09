package ro.projects.polls.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.projects.polls.entity.User;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByUsername(String username);

    User findByUsernameAndStatus(String username, Integer status);

    List<User> findAllByStatus(Integer status);

    Page<User> findAll(Pageable pageable);
}
