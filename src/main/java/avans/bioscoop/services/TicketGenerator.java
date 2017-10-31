package avans.bioscoop.services;

import avans.bioscoop.dao.SeatsRepository;
import avans.bioscoop.dao.TicketRepository;
import avans.bioscoop.dao.ViewingRepository;
import avans.bioscoop.models.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TicketGenerator{

    private TicketRepository ticketRepository;

    private ViewingRepository viewingRepository;

    private SeatsRepository seatsRepository;

    // Name of file
    private String fileName;

    private TicketViewModel ticketViewModel;

    private List<Ticket> ticketsForDatabase = new ArrayList<>();

    private Integer total;

    private Map.Entry selectedTicketPair;

    public TicketGenerator(TicketViewModel ticketViewModel, TicketRepository ticketRepository, ViewingRepository viewingRepository, SeatsRepository seatsRepository){
        this.ticketViewModel = ticketViewModel;
        this.ticketRepository = ticketRepository;
        this.viewingRepository = viewingRepository;
        this.seatsRepository = seatsRepository;
    }

    private void processViewModel(){

        Iterator selectedTickets = ticketViewModel.getSelectedTickets().entrySet().iterator();
        Iterator selectedSeats = ticketViewModel.getSelectedSeats().entrySet().iterator();

        selectedTicketPair = (Map.Entry) selectedTickets.next();
        total = Integer.parseInt(selectedTicketPair.getValue().toString());

        while (selectedSeats.hasNext()) {

            System.out.println("TOTAL IS: " + total);

            if(total == 0){
                selectedTicketPair = (Map.Entry) selectedTickets.next();
                total = Integer.parseInt(selectedTicketPair.getValue().toString());
            }

            Map.Entry selectedSeatPair = (Map.Entry) selectedSeats.next();

            TicketType ticketType = getTicketType((String) selectedTicketPair.getKey());
            Seat seat = seatsRepository.getOne(Long.parseLong(selectedSeatPair.getKey().toString()));

            ticketsForDatabase.add(new Ticket(generateRandomBarcode(), ticketType, seat, ticketViewModel.getSelectedViewing()));

            total--;

        }

    }

    private TicketType getTicketType(String ticketType){

        for(TicketType type : ticketViewModel.getTicketTypes()){
            if(ticketType.equals(type.getName())){
                return type;
            }
        }

        return null;
    }

    public void setValuesTickets(String fileName){
        this.fileName = fileName;

    }

    public Document generateTickets(){

        // Generate tickets
        processViewModel();

        // Save the generated tickets
        addTicketsToDatabase();

        Document document = null;

        try{
            document = new Document();

            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addMetaData(document);
            addTitlePage(document);

            System.out.println("TICKETGENERATOR - GENERATETICKETS - CREATE TABLE TICKETS SIZE: " + ticketsForDatabase.size());
            Integer index = 0;
            for(Ticket t : ticketsForDatabase){
                createTable(index, t, document, pdfWriter);

                Paragraph paragraph = new Paragraph();
                createEmptyLine(paragraph, 3);
                document.add(paragraph);

                index++;
            }

            Paragraph paragraph = new Paragraph(new Phrase("Totaal betaald: " + ticketViewModel.getTotalPrice()));
            createEmptyLine(paragraph, 1);
            document.add(paragraph);


            document.close();
        }catch(Exception e){
            System.out.println("ERROR - TICKETGENERATOR - generateTickets(): " + e);
        }

        return document;
    }

    public ByteArrayOutputStream convertPDFToByteArray(String fileName){

        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try{
            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        }catch(Exception e){
            System.out.println("ERROR - TICKETGENERATOR - convertPDFToByteArray(): " + e);
        }

        return byteArrayOutputStream;
    }

    // Adds metadata such as the author
    private void addMetaData(Document document){
        document.addTitle("Bioscoop Avans");
        document.addSubject("Bestelling");
        document.addAuthor("Bioscoop Avans");
        document.addCreator("Bioscoop Avans");

    }

    // Adds a title and subtitle to the page
    private void addTitlePage(Document document){
        try{
            Paragraph title = new Paragraph();
            createEmptyLine(title, 1);
            title.add(new Paragraph("Bioscoop Avans - Order"));

            createEmptyLine(title, 1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            title.add(new Paragraph("Purchased on: " + simpleDateFormat.format(new Date())));

            document.add(title);

        }catch(Exception e){
            System.out.println("ERROR - TICKETGENERATOR - addTitlePage(): " + e);
        }
    }

    // Adds given amount of empty lines to document
    private void createEmptyLine(Paragraph paragraph, int numberOfLines){
        for (int i = 0; i < numberOfLines; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Integer index, Ticket ticket, Document document, PdfWriter pdfWriter){
        try{
            // Add empty lines to divide content
            Paragraph paragraph = new Paragraph();
            createEmptyLine(paragraph, 3);
            document.add(paragraph);

            String[] headers = new String[]{
                    "Type Kaartje",
                    "Prijs",
                    "Film",
                    "Starttijd",
                    "zaal",
                    "Stoel",
                    "Barcode"
            };

            // create a table with amount of columns
            PdfPTable table = new PdfPTable(headers.length);
            table.setWidthPercentage(100);

            PdfPCell headerCell;
            for(String header : headers){
                headerCell = new PdfPCell(new Phrase(header));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setMinimumHeight(30);
                table.addCell(headerCell);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

            String[] phrases = new String[]{
                    ticket.getTicketType().getName(),
                    String.valueOf(ticket.getTicketType().getPrice()),
                    ticketViewModel.getSelectedViewing().getMovie().getTitle(),
                    String.valueOf(dateFormat.format(ticketViewModel.getSelectedViewing().getStartTime().getTime())),
                    String.valueOf(ticketViewModel.getSelectedViewing().getRoom().getRoomNumber()),
                    String.valueOf(ticket.getSeat().getSeatNumber())
            };

            PdfPCell cell;
            for(String phrase : phrases){
                cell = new PdfPCell(new Phrase(phrase));
                cell.setMinimumHeight(100);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            cell = new PdfPCell(addBarcode(Integer.parseInt(ticket.getBarcode()), pdfWriter));
            cell.setMinimumHeight(100);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);
        }catch(Exception e){
            System.out.println("ERROR ADDING TABLE TO TICKETS: " + e);
        }

    }

    private String generateRandomBarcode(){

        int min = 1000000;
        int max = 9999998;
        String currentBarcode = "";

        currentBarcode = String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));

        List<Ticket> tickets = ticketRepository.findTicketByBarcode(currentBarcode);
        if(tickets.size() != 0){
            currentBarcode = generateRandomBarcode();
        }

        return currentBarcode;
    }

    private Image addBarcode(Integer barcode, PdfWriter writer){

        BarcodeEAN barcodeEAN = new BarcodeEAN();
        barcodeEAN.setCodeType(barcodeEAN.EAN8);
        barcodeEAN.setCode(String.format("%08d", barcode));

        return barcodeEAN.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY);

    }

    private void addTicketsToDatabase(){
        ticketRepository.save(ticketsForDatabase);

    }





}
