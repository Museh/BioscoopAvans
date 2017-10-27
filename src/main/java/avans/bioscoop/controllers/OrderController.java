package avans.bioscoop.controllers;

import avans.bioscoop.dao.RoomRepository;
import avans.bioscoop.dao.TicketRepository;
import avans.bioscoop.dao.TicketTypeRepository;
import avans.bioscoop.dao.ViewingRepository;
import avans.bioscoop.models.*;
import avans.bioscoop.services.SessionTracker;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ticketview")
public class OrderController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private ViewingRepository viewingRepository;

    @Autowired
    private RoomRepository roomRepository;

    private List<TicketType> ticketTypes;

    public OrderController(TicketRepository ticketRepository,
                           ViewingRepository viewingRepository,
                           RoomRepository roomRepository){
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.viewingRepository = viewingRepository;
        this.roomRepository = roomRepository;

        ticketTypes = new ArrayList<>();

    }

    //Ticket Type Selection
    @GetMapping(value = "/{id}")
    public String getTickets(@PathVariable(value = "id", required = true) Long viewingId, Model model) {
        ticketTypes = ticketTypeRepository.getAllTicketTypes();

        model.addAttribute("ticketTypes", ticketTypes);

        Viewing v = viewingRepository.findOne(viewingId);
        SessionTracker.getSession().setAttribute("selectedViewing", v);

        // TODO: Retrieve ticketTypes data and pass it to your view with a model named after 'ticketTypes'
        return "overview/ticketview";
    }

    // Ticket types post to params
    @RequestMapping(value = "/chairselection", method = RequestMethod.POST)
    public String getChairSelection(@RequestParam Map<String, String> params, Model model){

        // Total seats based on selected tickets
        int totalSeats = 0;
        for(String count : params.values()){
            totalSeats += Integer.parseInt(count);
        }
        SessionTracker.getSession().setAttribute("selectedTickets", params);

        Viewing viewing = (Viewing) SessionTracker.getSession().getAttribute("selectedViewing");
        System.out.println("VIEWING ROOM: " + viewing.getRoom().getId());
//        Room room = viewing.getRoom();

        // All taken seats
        List<Long> takenSeats = new ArrayList<>();
        for(Number n : ticketRepository.findTicketsByViewingId(viewing.getId())){
            takenSeats.add(n.longValue());
        }

        model.addAttribute("totalSeats", totalSeats);
//        model.addAttribute("room", room);
        model.addAttribute("viewing", viewing);
        model.addAttribute("takenSeats", takenSeats);

        return "order/seatselection";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String getPaymentView(@RequestParam Map<Long, String> params, Model model){

        List<TicketType> ticketTypes = ticketTypeRepository.getAllTicketTypes();

        Double totalPrice = 0.0;
        Map<String, String> selectedTickets = (Map<String, String>) SessionTracker.getSession().getAttribute("selectedTickets");
        // For each ticket type
        for(TicketType t : ticketTypes){
            // Check if selectedTickets has a key with the same name
            if(selectedTickets.containsKey(t.getName())){
                // If so: retrieve price and multiply by value with the key (selected amount of tickets of specified type)
                totalPrice += t.getPrice() * Integer.parseInt(selectedTickets.get(t.getName()));
            }
        }

        SessionTracker.getSession().setAttribute("totalPrice", totalPrice);
        SessionTracker.getSession().setAttribute("selectedSeats", params);


        Viewing selectedViewing = (Viewing) SessionTracker.getSession().getAttribute("selectedViewing");

        model.addAttribute("selectedTickets", selectedTickets);
        model.addAttribute("selectedViewing", selectedViewing);
        model.addAttribute("totalPrice", totalPrice);

        return "order/payment";
    }

    @GetMapping(value = "/printing")
    public String getPrintingView(){

        return "order/printing";
    }
}
