package avans.bioscoop.services;

import avans.bioscoop.models.TicketType;
import avans.bioscoop.models.Viewing;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TicketGenerator{

    // Name of file
    private String fileName;
    // Viewing object for which the tickets are bought for
    private Viewing ticketViewings;
    // List of tickettypes to set the right price on ticket
    private List<TicketType> ticketTypes;

    String[] phrases;

    public TicketGenerator(){

    }

    public void setValuesTickets(String fileName){
        this.fileName = fileName;
    }

    public Document generateTickets(){

        Document document = null;

        try{
            document = new Document();

            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addMetaData(document);
            addTitlePage(document);

            createTable(document, pdfWriter);

            Paragraph paragraph = new Paragraph();
            createEmptyLine(paragraph, 1);
            document.add(paragraph);

            document.close();
        }catch(Exception e){
            System.out.println("ERROR - TICKETGENERATOR - generateTickets(): " + e.getLocalizedMessage());
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
            System.out.println("ERROR - TICKETGENERATOR - convertPDFToByteArray(): " + e.getLocalizedMessage());
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            title.add(new Paragraph("Purchased on: " + simpleDateFormat.format(new Date())));

            document.add(title);

        }catch(Exception e){
            System.out.println("ERROR - TICKETGENERATOR - addTitlePage(): " + e.getLocalizedMessage());
        }
    }

    // Adds given amount of empty lines to document
    private void createEmptyLine(Paragraph paragraph, int numberOfLines){
        for (int i = 0; i < numberOfLines; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Document document, PdfWriter pdfWriter){
        try{
            // Add empty lines to divide content
            Paragraph paragraph = new Paragraph();
            createEmptyLine(paragraph, 2);
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

            String[] phrases = new String[]{
                    "Regulier",
                    "7.50",
                    "Jigsaw",
                    "13:30",
                    "1",
                    "1"
            };

            PdfPCell cell;
            for(String phrase : phrases){
                cell = new PdfPCell(new Phrase(phrase));
                cell.setMinimumHeight(100);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            cell = new PdfPCell(addBarcode(9999999, pdfWriter));
            cell.setMinimumHeight(100);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);
        }catch(Exception e){
            System.out.println("ERROR ADDING TABLE TO TICKETS: " + e.getLocalizedMessage());
        }

    }

    private Image addBarcode(Integer barcode, PdfWriter writer){

        BarcodeEAN barcodeEAN = new BarcodeEAN();
        barcodeEAN.setCodeType(barcodeEAN.EAN8);
        barcodeEAN.setCode(String.format("%08d", barcode));

        return barcodeEAN.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY);

    }





}
