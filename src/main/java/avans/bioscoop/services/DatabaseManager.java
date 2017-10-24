package avans.bioscoop.services;

import avans.bioscoop.dao.CinemaRepository;
import avans.bioscoop.dao.MovieRepository;
import avans.bioscoop.dao.TicketTypeRepository;
import avans.bioscoop.models.Cinema;
import avans.bioscoop.models.Movie;
import avans.bioscoop.models.TicketType;
import avans.bioscoop.models.Viewing;
import org.springframework.beans.factory.annotation.Autowired;

// TODO: Create mock data for usage in the application
public class DatabaseManager {

    private CinemaRepository cinemaRepository;

    private MovieRepository movieRepository;

    private TicketTypeRepository ticketTypeRepository;

    public DatabaseManager(CinemaRepository cinemaRepository, MovieRepository movieRepository,
                           TicketTypeRepository ticketTypeRepository){
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
        this.ticketTypeRepository = ticketTypeRepository;

        prepareDatabase();
    }

    // Because we only have one cinema, we set it manually to retrieve the first one
    public Cinema getCinema(){
        return this.cinemaRepository.findAll().get(0);
    }

    // Adds mock data to our database by initializing it from bottom to start
    public void prepareDatabase(){
        TicketType[] ticketTypes = new TicketType[]{
                new TicketType()
        };

        Movie[] movies = new Movie[]{
            new Movie("Jigsaw", 91, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BNmRiZDM4ZmMtOTVjMi00YTNlLTkyNjMtMjI2OTAxNjgwMWM1XkEyXkFqcGdeQXVyMjMxOTE0ODA@._V1_SY1000_CR0,0,648,1000_AL_.jpg"),
            new Movie("Suburbicon", 124, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BMTA3MjA1NDkxMTReQTJeQWpwZ15BbWU4MDU2Njg3NDMy._V1_SY1000_CR0,0,639,1000_AL_.jpg"),
            new Movie("Thank You for Your Service", 108, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BMTA0NTM5NzgzMjJeQTJeQWpwZ15BbWU4MDAwODc3NjIy._V1_.jpg"),
            new Movie("All I See Is You", 110, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BNDI5NzU2OTM1MV5BMl5BanBnXkFtZTgwNzU3NzM3MzI@._V1_SY1000_CR0,0,675,1000_AL_.jpg"),
            new Movie("Novitiate", 123, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BMTU5NDQzNTY2MV5BMl5BanBnXkFtZTgwMjgxMTMyMzI@._V1_SY1000_CR0,0,674,1000_AL_.jpg"),
            new Movie("The Square", 142, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BODljYTUxYzktNzlkYS00OTc1LWE5ZWEtZTgwNTY0MzdlNzM4XkEyXkFqcGdeQXVyNTM2NTg3Nzg@._V1_SY1000_CR0,0,712,1000_AL_.jpg"),
            new Movie("Blade Runner 2049", 164, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BNzA1Njg4NzYxOV5BMl5BanBnXkFtZTgwODk5NjU3MzI@._V1_SY1000_CR0,0,674,1000_AL_.jpg"),
            new Movie("The Foreigner", 114, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BM2RlMjcyMGQtZTU3OC00NGRlLWExMGEtYjU3ZjUyMDc0NWZmXkEyXkFqcGdeQXVyNTI4MzE4MDU@._V1_SY1000_SX675_AL_.jpg"),
            new Movie("It", 135, false, 18, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BOTE0NWEyNDYtYWI5MC00MWY0LTg1NDctZjAwMjkyMWJiNzk1XkEyXkFqcGdeQXVyNjk5NDA3OTk@._V1_SY1000_CR0,0,674,1000_AL_.jpg"),
            new Movie("The Mountain Between Us", 112, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxNjQzMDk5NV5BMl5BanBnXkFtZTgwNjI0NjM2MjI@._V1_SY1000_CR0,0,674,1000_AL_.jpg"),
            new Movie("American Made", 115, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxNzUwMjk1Nl5BMl5BanBnXkFtZTgwNDkwODI1MjI@._V1_SY1000_CR0,0,675,1000_AL_.jpg"),
            new Movie("Kingsman: The Golden Circle", 141, false, 16, "english", false, "https://images-na.ssl-images-amazon.com/images/M/MV5BNTBlOWZhZTctOTY0MC00Y2QyLTljMmYtZDkxZDFlMWU4Y2EyXkEyXkFqcGdeQXVyNDg2MjUxNjM@._V1_SY1000_CR0,0,675,1000_AL_.jpg")
        };

        for(Movie m : movies){
            movieRepository.save(m);
        }

        Viewing[] viewings = new Viewing[]{};


        Cinema cinema = new Cinema();


    }


}
