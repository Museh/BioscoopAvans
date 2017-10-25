package avans.bioscoop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
public class Viewing {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @OneToOne
    private Movie movie;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "VIEWING_ID", referencedColumnName = "id")
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public Viewing(){}

    public Viewing(Date startTime, Movie movie){
        this.startTime = startTime;
        this.movie = movie;
    }

    public Viewing(Viewing viewing){
        this.startTime = viewing.getStartTime();
        this.movie = viewing.getMovie();
    }


}
