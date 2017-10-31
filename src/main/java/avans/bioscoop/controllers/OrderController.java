package avans.bioscoop.controllers;

import avans.bioscoop.dao.*;
import avans.bioscoop.models.*;
import avans.bioscoop.services.SessionTracker;
import avans.bioscoop.services.TicketGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    private SeatsRepository seatsRepository;

    private List<TicketType> ticketTypes;

    public OrderController(TicketRepository ticketRepository, ViewingRepository viewingRepository,
                           TicketTypeRepository ticketTypeRepository, SeatsRepository seatsRepository){
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.viewingRepository = viewingRepository;
        this.seatsRepository = seatsRepository;

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

        // All taken seats
        List<Long> takenSeats = new ArrayList<>();
        for(Number n : ticketRepository.findTicketsByViewingId(viewing.getId())){
            takenSeats.add(n.longValue());
        }

        model.addAttribute("totalSeats", totalSeats);
        model.addAttribute("viewing", viewing);
        model.addAttribute("takenSeats", takenSeats);

        return "order/seatselection";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String getPaymentView(@RequestParam Map<Long, String> params, Model model){

        List<TicketType> ticketTypes = ticketTypeRepository.getAllTicketTypes();

        Double totalPrice = 0.0;

        // Get all selected tickets from session
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

        System.out.println("Selected seats were: " + params.keySet());

        Viewing selectedViewing = (Viewing) SessionTracker.getSession().getAttribute("selectedViewing");

        System.out.println("KEYS: " + selectedTickets.keySet() + " - " + "VALUES: " + selectedTickets.values());
        model.addAttribute("selectedTickets", selectedTickets);
        model.addAttribute("selectedViewing", selectedViewing);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("selectedSeats", params.keySet());

        return "order/payment";
    }

    @GetMapping(value = "/printing")
    public String getPrintingView(){

        /*
            Session objects: Viewing, Selected Seats, TotalPrice, Selected Tickets,
         */
        return "order/printing";
    }

    @RequestMapping(value="/print", method = RequestMethod.POST)
    public void printTickets(HttpServletRequest request, HttpServletResponse response){

        // Empty ticket list to be filled with final tickets that are going to be inserted into the database
        List<Ticket> ticketsToBeSaved = new ArrayList<>();

        // Retrieve all needed data from current session
        Viewing selectedViewing = (Viewing) SessionTracker.getSession().getAttribute("selectedViewing"); // current viewing information
        Map<String, String> selectedTickets = (Map<String, String>) SessionTracker.getSession().getAttribute("selectedTickets"); // type and amount
        Map<Long, String> selectedSeats = (Map<Long, String>) SessionTracker.getSession().getAttribute("selectedSeats"); // seat number of viewing room
        Double totalPrice = (Double) SessionTracker.getSession().getAttribute("totalPrice"); // total costs to show at bottom
        TicketViewModel ticketViewModel = new TicketViewModel(selectedViewing, selectedTickets, selectedSeats, ticketTypes, totalPrice);

        ServletContext servletContext = request.getSession().getServletContext();
        File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        // Create ticketGenerator with the needed values to generate one or multiple tickets
        TicketGenerator ticketGenerator = new TicketGenerator(ticketViewModel, ticketRepository, viewingRepository, seatsRepository);

        // Get default paths and set file name
        String temporaryFilePath = tempDirectory.getAbsolutePath();
        String fileName = "Order_BioscoopAvans";
        ticketGenerator.setValuesTickets(temporaryFilePath+"\\"+fileName);

        // Set response type to pdf
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename="+fileName+".pdf");

        try{
            // Generate tickets
            ticketGenerator.generateTickets();
            // Convert to Byte Array
            ByteArrayOutputStream byteArrayOutputStream = ticketGenerator.convertPDFToByteArray(temporaryFilePath+"\\"+fileName);
            OutputStream os = response.getOutputStream();
            byteArrayOutputStream.writeTo(os);
            os.flush();
        }catch(Exception e){
            System.out.println("ERROR GENERATING TICKETS IN ORDERCONTROLLER: " + e.getLocalizedMessage());
        }

        System.out.println("Called printTickets()");

    }

}
