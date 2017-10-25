package avans.bioscoop.controllers;

import avans.bioscoop.dao.TicketTypeRepository;
import avans.bioscoop.models.TicketType;
import avans.bioscoop.models.Viewing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ticketview")
public class OrderController {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public ModelAndView getTickets(){

        return new ModelAndView();
    }

/*    @GetMapping
    public ModelAndView getAvailableTicketTypes(){
        System.out.println("inside getAvailableTickets");
        // TODO: Return a view that shows available ticketTypes
        return new ModelAndView("overview/ticketview");
    }*/

    @GetMapping
    public String getTickets(Model model) {
        List<TicketType> ticketTypes = new ArrayList<TicketType>();
        ticketTypes = ticketTypeRepository.getAllTicketTypes();

        model.addAttribute("ticketTypes", ticketTypes);
        // TODO: Retrieve ticketTypes data and pass it to your view with a model named after 'ticketTypes'
        return "overview/ticketview";
    }
}
