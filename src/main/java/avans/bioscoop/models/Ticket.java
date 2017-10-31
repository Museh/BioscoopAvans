package avans.bioscoop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// Adding the decorator Entity, tells spring that it's a managed entity
@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue
    private long id;

    private String barcode;

    @OneToOne(cascade = CascadeType.ALL)
    private TicketType ticketType;

    @ManyToOne
    private Seat seat;

    private Ticket(){}

    private Ticket(String barcode, TicketType ticketType, Seat seat){
        this.barcode = barcode;
        this.ticketType = ticketType;
        this.seat = seat;
    }

}
