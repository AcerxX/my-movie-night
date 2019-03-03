package ro.projects.polls.service;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TmdbService {
    private static final String API_KEY = "b6f6c6f443b1d7539b693375f86bba93";
    private Tmdb tmdb;

    public TmdbService() {
        this.tmdb = new Tmdb(API_KEY);
    }

    public List<BaseMovie> getMoviesBySearchString(String searchedText) {
        List<BaseMovie> results = new ArrayList<>();

        SearchService searchService = tmdb.searchService();
        try {
            var response = searchService
                    .movie(searchedText, 1, "ro-RO", null, null, null, null)
                    .execute();
            if (response.isSuccessful()) {
                MovieResultsPage movies = response.body();
                assert movies != null;

                results = movies.results;
            }
        } catch (Exception e) {
            // see execute() javadoc
        }

        return results;
    }
}
