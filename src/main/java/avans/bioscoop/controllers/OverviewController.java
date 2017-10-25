package avans.bioscoop.controllers;

import avans.bioscoop.dao.*;
import avans.bioscoop.models.*;
import avans.bioscoop.services.DataFilter;
import avans.bioscoop.services.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
        List<Viewing> viewings = viewingRepository.findAllViewings();

        model.addAttribute("viewings", viewings);
        model.addAttribute("searchobject", new SearchTerm());

        return "overview/overview";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String searchMovie(@ModelAttribute SearchTerm searchobject, Model model){

        DataFilter df = new DataFilter();

        List<Viewing> viewings = df.filterViewingsByMovieTitle(viewingRepository.findAllViewings(), searchobject.getSearch());

        model.addAttribute("viewings", viewings);
        model.addAttribute("searchobject", new SearchTerm());

        // TODO: fastest implementation is to navigate to a new page that looks the same as the movie
        return "overview/overview";
    }



//    @GetMapping(value = "/search/{term}")
//    public String searchMovie(@PathVariable(value = "term", required = false) String term, Model model){
//
//        DataFilter df = new DataFilter();
//
//        List<Viewing> viewings = df.filterViewingsByMovieTitle(viewingRepository.findAllViewings(), term);
//
//        if(term != ""){
//            model.addAttribute("viewings", viewings);
//        }else{
//            model.addAttribute("viewings", viewings);
//        }
//
//        // TODO: fastest implementation is to navigate to a new page that looks the same as the movie
//        return "overview/overview";
//    }

    
    @GetMapping(value = "/movie/details/{id}")
    public String getMovieDetails(@PathVariable(value ="id", required = true) Long movieid, Model model){
        //System.out.println("inside movie details");
        Movie movie = movieRepository.findOne(movieid);
        List<Viewing> viewings = viewingRepository.findAllViewingsByMovieId(movieid);

        model.addAttribute("movie", movie);
        model.addAttribute("viewings", viewings);
        return "overview/moviedetails";
    }

    @GetMapping ("/movies")
    public String allMovies(Model model) {
        List<Movie> movie = new ArrayList<Movie>();
        movie = movieRepository.findAll();

        model.addAttribute("movie", movie);
        // TODO: Retrieve movie data and pass it to your view with a model named after 'movies'
        return "overview/movies";
    }

    @GetMapping("/contact")
    public String getContact(Model model){
        Cinema contact = cinemaRepository.findOne(1L);

        model.addAttribute("contact", contact);

        return "overview/contact";
    }


}
