package avans.bioscoop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
public class Viewing {

    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime startTime;

    @OneToOne
    private Movie movie;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public Viewing(){}

    public Viewing(LocalDateTime startTime, Movie movie){
        this.startTime = startTime;
        this.movie = movie;
    }

    public Viewing(Viewing viewing){
        this.startTime = viewing.getStartTime();
        this.movie = viewing.getMovie();
    }

}
