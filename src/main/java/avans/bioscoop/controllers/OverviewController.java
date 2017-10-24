package avans.bioscoop.controllers;

import avans.bioscoop.dao.CinemaRepository;
import avans.bioscoop.dao.MovieRepository;
import avans.bioscoop.dao.TicketTypeRepository;
import avans.bioscoop.models.Movie;
import avans.bioscoop.models.TicketType;
import avans.bioscoop.services.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class OverviewController {

    private DatabaseManager db;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public OverviewController(CinemaRepository cinemaRepository, MovieRepository movieRepository, TicketTypeRepository ticketTypeRepository){
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
        this.ticketTypeRepository = ticketTypeRepository;

        this.db = new DatabaseManager(cinemaRepository, movieRepository, ticketTypeRepository);
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping
    public String movieOverview(Model model) {
        List<Movie> movies = new ArrayList<Movie>();
        movies = movieRepository.findAll();

        System.out.println("Size is: " + movies.size());

        model.addAttribute("movies", movies);
        // TODO: Retrieve movie data and pass it to your view with a model named after 'movies'
        return "overview/overview";
    }

    public ModelAndView searchMovie(String term){

        // TODO: fastest implementation is to navigate to a new page that looks the same as the movie
        return new ModelAndView();
    }

    public ModelAndView getMovieDetails(long id){

        // TODO: Return a view that shows the details of a movie
        return new ModelAndView();
    }

}
