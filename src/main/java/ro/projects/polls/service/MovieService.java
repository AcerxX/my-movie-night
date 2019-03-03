package ro.projects.polls.service;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.projects.polls.entity.Movie;
import ro.projects.polls.entity.User;
import ro.projects.polls.repository.MovieRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class MovieService {
    private final TmdbService tmdbService;
    private final MovieRepository movieRepository;
    private final GenreService genreService;

    @Autowired
    public MovieService(TmdbService tmdbService, MovieRepository movieRepository, GenreService genreService) {
        this.tmdbService = tmdbService;
        this.movieRepository = movieRepository;
        this.genreService = genreService;
    }

    public void addMovieToDatabase(Integer movieId, User user) throws IOException, NotFoundException {
        var movieResult = movieRepository.findById(movieId);

        Movie movie;
        if (movieResult.isEmpty()) {
            movie = this.getNewMovie(movieId, user);
        } else {
            movie = movieResult.get();
            if (movie.getStatus().equals(Movie.STATUS_DELETED)) {
                movie.setStatus(Movie.STATUS_AVAILABLE);
            }
        }

        movieRepository.save(movie);
    }

    private Movie getNewMovie(Integer movieId, User user) throws IOException, NotFoundException {
        var movieInfo = tmdbService.getMovieInfo(movieId);

        assert movieInfo.release_date != null;
        var movie = new Movie()
                .setId(movieId)
                .setTitle(movieInfo.title)
                .setOriginalTitle(movieInfo.original_title)
                .setPosterPath(movieInfo.poster_path)
                .setBackdropPath(movieInfo.backdrop_path)
                .setRuntime(movieInfo.runtime)
                .setOverview(movieInfo.overview)
                .setReleaseDate(LocalDate.ofInstant(movieInfo.release_date.toInstant(), ZoneId.systemDefault()))
                .setImdbId(movieInfo.imdb_id)
                .setRating(movieInfo.vote_average)
                .setStatus(Movie.STATUS_AVAILABLE)
                .setUser(user);

        assert movieInfo.genres != null;
        movieInfo.genres.forEach(genre1 -> movie.addGenre(genreService.getGenreByIdAndName(genre1.id, genre1.name)));

        return movie;
    }
}
