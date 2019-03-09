package ro.projects.polls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.projects.polls.config.JtwigRenderService;
import ro.projects.polls.controller.base.UserBoundController;
import ro.projects.polls.dto.BaseResponse;
import ro.projects.polls.dto.SearchMovieResponse;
import ro.projects.polls.repository.UserRepository;
import ro.projects.polls.service.MovieService;
import ro.projects.polls.service.TmdbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AppController extends UserBoundController {
    private final TmdbService tmdbService;
    private final JtwigRenderService jtwigRenderService;
    private final MovieService movieService;

    @Autowired
    public AppController(TmdbService tmdbService, JtwigRenderService jtwigRenderService, MovieService movieService, UserRepository userRepository) {
        super(userRepository);
        this.tmdbService = tmdbService;
        this.jtwigRenderService = jtwigRenderService;
        this.movieService = movieService;
    }

    @GetMapping("/login")
    public String alternativeLogin(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Username-ul sau parola sunt invalide!");

        return "login";
    }

    @GetMapping("/login/{username}/{password}")
    public String autoLogin(HttpServletRequest request, @PathVariable String username, @PathVariable String password) {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("nextDate", LocalDate.of(2019, 2, 27).format(DateTimeFormatter.ofPattern("EEEE, d MMMM")));

        return "homepage";
    }

    @GetMapping("/search")
    public String searchMovie() {
        return "searchMovie";
    }

    @GetMapping("/search/{movieName}")
    public String searchMovie(Model model, @PathVariable String movieName) {
        model.addAttribute("movieName", movieName);

        return "searchMovie";
    }

    @ResponseBody
    @GetMapping("/search-movie/{movieName}")
    public SearchMovieResponse searchMovieLines(Model model, @PathVariable String movieName) {
        var movies = tmdbService.getMoviesBySearchString(movieName);
        var moviesWithStatus = movieService.getMoviesWithStatus(movies);

        model.addAttribute("movies", moviesWithStatus);
        model.addAttribute("searchedText", movieName);

        var response = new SearchMovieResponse();
        response.setHtml(jtwigRenderService.renderTemplate("searchMovieLine", model.asMap()));

        return response;
    }

    @ResponseBody
    @PutMapping("/add/{movieId}")
    @Transactional
    public BaseResponse addMovie(@PathVariable Integer movieId) {
        var response = new BaseResponse();

        try {
            var movie = movieService.addMovieToDatabase(movieId, this.getLoggedUser());
            movieService.generateVotesForMovie(movie);
        } catch (Exception e) {
            response.setStatus(500)
                    .setMessage(e.getMessage());
        }

        return response;
    }


    @GetMapping("/vote")
    public String votePage(Model model) {
        model.addAttribute("movies", movieService.getActiveMovies());
        model.addAttribute("ratings", movieService.getRatingsForUserAsMap(this.getLoggedUser()));

        return "vote";
    }

    @ResponseBody
    @PutMapping("/vote/{movieId}/{rating}")
    public BaseResponse voteMovie(@PathVariable Integer movieId, @PathVariable Integer rating) {
        movieService.voteForMovie(movieId, rating, this.getLoggedUser());

        return new BaseResponse();
    }
}
