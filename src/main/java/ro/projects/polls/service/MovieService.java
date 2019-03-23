package ro.projects.polls.service;


import com.uwetrottmann.tmdb2.entities.BaseMovie;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.projects.polls.dto.BaseMovieWithExtraInfo;
import ro.projects.polls.entity.Movie;
import ro.projects.polls.entity.Rating;
import ro.projects.polls.entity.User;
import ro.projects.polls.repository.MovieRepository;
import ro.projects.polls.repository.RatingRepository;
import ro.projects.polls.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MovieService {
    private final TmdbService tmdbService;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final GenreService genreService;

    @Autowired
    public MovieService(TmdbService tmdbService, MovieRepository movieRepository, UserRepository userRepository, RatingRepository ratingRepository, GenreService genreService) {
        this.tmdbService = tmdbService;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.genreService = genreService;
    }

    @Transactional
    public Movie addMovieToDatabase(Integer movieId, User user) throws IOException, NotFoundException {
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

        return movie;
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
                .setReleaseDate(movieInfo.release_date)
                .setImdbId(movieInfo.imdb_id)
                .setRating(movieInfo.vote_average)
                .setStatus(Movie.STATUS_AVAILABLE)
                .setUser(user);

        assert movieInfo.genres != null;
        movieInfo.genres.forEach(genre1 -> movie.addGenre(genreService.getGenreByIdAndName(genre1.id, genre1.name)));

        return movie;
    }

    public List<BaseMovieWithExtraInfo> getMoviesWithStatus(List<BaseMovie> movies) {
        List<BaseMovieWithExtraInfo> result = new ArrayList<>();

        movies.forEach(baseMovie -> {
            var movie = movieRepository.findById(baseMovie.id);

            var temp = new BaseMovieWithExtraInfo();
            temp.id = baseMovie.id;
            temp.title = baseMovie.title;
            temp.poster_path = baseMovie.poster_path;
            temp.vote_average = baseMovie.vote_average;
            temp.release_date = baseMovie.release_date;

            if (movie.isEmpty() || movie.get().getStatus().equals(Movie.STATUS_DELETED)) {
                temp.setAlreadyAdded(0);
            } else {
                temp.setAlreadyAdded(1);
            }
            temp.setGenres(genreService.getGenresByIds(baseMovie.genre_ids));

            result.add(temp);
        });

        return result;
    }

    public List<Movie> getActiveMovies() {
        return movieRepository.topRatedActiveMovies();
    }

    public List<Rating> getRatingsForUser(User user) {
        return ratingRepository.findByUser(user);
    }

    public Map<Integer, Rating> getRatingsForUserAsMap(User user) {
        var ratings = ratingRepository.findByUser(user);

        Map<Integer, Rating> results = new HashMap<>();

        ratings.forEach(rating -> results.put(rating.getMovie().getId(), rating));

        return results;
    }

    public Map<Integer, Integer> getMoviesRatings() {
        var movies = movieRepository.findAllByStatus(Movie.STATUS_AVAILABLE);

        Map<Integer, Integer> results = new HashMap<>();
        movies.forEach(movie -> results.put(movie.getId(), this.getMovieRating(movie)));

        return results;
    }

    private Integer getMovieRating(Movie movie) {
        AtomicReference<Integer> temp = new AtomicReference<>(0);
        movie.getRatings().forEach(rating -> temp.updateAndGet(v -> v + rating.getRating()));

        return temp.get();
    }


    public void generateVotesForMovie(Movie movie) {
        var users = userRepository.findAll();
        List<Rating> votes = new ArrayList<>();

        users.forEach(user -> votes.add(new Rating().setMovie(movie).setUser(user)));

        ratingRepository.saveAll(votes);
    }

    public void voteForMovie(Integer movieId, Integer rating, User user) {
        var movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new RuntimeException("Filmul cu id-ul " + movieId + " nu a fost adaugat de nimeni in baza de date!");
        }

        var movieVote = ratingRepository.findByMovieAndUser(movie.get(), user);

        movieVote.setRating(rating);
        ratingRepository.save(movieVote);
    }

    public Movie getTopRatedMovie() {
        return movieRepository.topRatedActiveMovies().get(0);
    }
}
