package avans.bioscoop.controllers;

import avans.bioscoop.dao.*;
import avans.bioscoop.models.Movie;
import avans.bioscoop.models.Row;
import avans.bioscoop.models.TicketType;
import avans.bioscoop.models.Viewing;
import avans.bioscoop.services.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
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

    @Autowired
    private ViewingRepository viewingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RowRepository rowRepository;

    @Autowired
    private SeatsRepository seatsRepository;

    public OverviewController(CinemaRepository cinemaRepository, MovieRepository movieRepository, TicketTypeRepository ticketTypeRepository,
                              ViewingRepository viewingRepository, RoomRepository roomRepository, RowRepository rowRepository,
                              SeatsRepository seatsRepository){
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.viewingRepository = viewingRepository;
        this.roomRepository = roomRepository;
        this.rowRepository = rowRepository;
        this.seatsRepository = seatsRepository;


        this.db = new DatabaseManager(cinemaRepository, movieRepository, ticketTypeRepository, viewingRepository, roomRepository, rowRepository, seatsRepository);
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping
    public String movieOverview(Model model) {
        List<Viewing> viewings = new ArrayList<Viewing>();
        viewings = viewingRepository.findAllViewings();

        model.addAttribute("viewings", viewings);
        // TODO: Retrieve movie data and pass it to your view with a model named after 'movies'
        return "overview/overview";
    }

    public ModelAndView searchMovie(String term){

        // TODO: fastest implementation is to navigate to a new page that looks the same as the movie
        return new ModelAndView();
    }

    
    @GetMapping(value = "/movie/details/{id}")
    public String getMovieDetails(@PathVariable(value ="id", required = true) Long movieid, Model model){
        //System.out.println("inside movie details");
        Movie movie = movieRepository.findOne(movieid);
        List<Viewing> viewings = viewingRepository.findAllViewingsByMovieId(movieid);

        model.addAttribute("movie", movie);
        model.addAttribute("viewings", viewings);
        return "overview/moviedetails";
    }

}
