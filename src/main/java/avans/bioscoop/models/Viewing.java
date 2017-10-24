package avans.bioscoop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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

    private Date startTime;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public Viewing(){

    }

    public Viewing(Date startTime){
        this.startTime = startTime;
    }

}
