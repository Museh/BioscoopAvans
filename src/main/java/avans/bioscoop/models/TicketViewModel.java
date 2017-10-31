package avans.bioscoop.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TicketViewModel {

    private Viewing selectedViewing;

    private Map<String, String> selectedTickets;

    private Map<Long, String> selectedSeats;

    private List<TicketType> ticketTypes = new ArrayList<>();

    private Double totalPrice;

    public TicketViewModel(Viewing selectedViewing, Map<String, String> selectedTickets, Map<Long, String> selectedSeats
            , List<TicketType> ticketTypes, Double totalPrice){
        this.selectedViewing = selectedViewing;
        this.selectedTickets = selectedTickets;
        this.selectedSeats = selectedSeats;
        this.ticketTypes = ticketTypes;
        this.totalPrice = totalPrice;
    }
}

