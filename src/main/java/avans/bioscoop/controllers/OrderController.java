package avans.bioscoop.controllers;

import avans.bioscoop.dao.RoomRepository;
import avans.bioscoop.dao.TicketRepository;
import avans.bioscoop.dao.TicketTypeRepository;
import avans.bioscoop.dao.ViewingRepository;
import avans.bioscoop.models.*;
import avans.bioscoop.services.SessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigInteger;
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

    public ModelAndView getTickets(){

        return new ModelAndView();
    }

/*    @GetMapping
    public ModelAndView getAvailableTicketTypes(){
        System.out.println("inside getAvailableTickets");
        // TODO: Return a view that shows available ticketTypes
        return new ModelAndView("overview/ticketview");
    }*/

    //ticket/{id}
    @GetMapping(value = "/{id}")
    public String getTickets(@PathVariable(value = "id", required = true) Long viewingId, Model model) {
        ticketTypes = ticketTypeRepository.getAllTicketTypes();

        model.addAttribute("ticketTypes", ticketTypes);

        Viewing v = viewingRepository.findOne(viewingId);
        SessionTracker.getSession().setAttribute("selectedViewing", v);

        // TODO: Retrieve ticketTypes data and pass it to your view with a model named after 'ticketTypes'
        return "overview/ticketview";
    }

    @RequestMapping(value = "/chairselection", method = RequestMethod.POST)
    public String getChairSelection(@RequestParam Map<String, String> params, Model model){

        int totalSeats = 0;

        for(String count : params.values()){
            totalSeats += Integer.parseInt(count);
        }

        for(TicketType type : ticketTypes){
            SessionTracker.getSession().setAttribute("tickets", params);
        }

        Viewing viewing = (Viewing) SessionTracker.getSession().getAttribute("selectedViewing");
        System.out.println("VIEWING ROOM: " + viewing.getRoom().getId());
        Room room = viewing.getRoom();

        List<BigInteger> takenSeats = ticketRepository.findTicketsByViewingId(viewing.getId());

        model.addAttribute("totalSeats", totalSeats);
        model.addAttribute("room", room);
        model.addAttribute("viewing", viewing);
        model.addAttribute("takenSeats", takenSeats);

        return "order/seatselection";

    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String getPaymentView(@RequestParam Map<Long, String> params, Model model){

        System.out.println(params);

        return "order/payment";
    }
}
