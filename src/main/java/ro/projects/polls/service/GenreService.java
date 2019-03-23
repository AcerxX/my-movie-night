package ro.projects.polls.service;


import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.projects.polls.entity.Genre;
import ro.projects.polls.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre getGenreByIdAndName(Integer genreId, @Nullable String name) {
        var genreResult = genreRepository.findById(genreId);

        Genre genre;
        if (genreResult.isEmpty()) {
            genre = new Genre()
                    .setId(genreId)
                    .setName(name)
                    .setStatus(Genre.STATUS_ENABLED);

            genreRepository.save(genre);
        } else {
            genre = genreResult.get();
        }

        return genre;
    }

    public List<Genre> getGenresByIds(List<Integer> genreIds) {
        return genreRepository.findAllByIdIn(genreIds);
    }
}
