package avans.bioscoop.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketGenerator{

    public TicketGenerator(){

    }

    public Document generateTickets(String file){

        Document document = null;

        try{
            document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);
            addTitlePage(document);

            document.close();
        }catch(Exception e){
            System.out.println("ERROR PRINTING TICKETS: " + e.getLocalizedMessage());
        }

        return document;
    }

    private void addMetaData(Document document){
        document.addTitle("Bioscoop Avans");
        document.addSubject("Bestelling");
        document.addAuthor("Bioscoop Avans");
        document.addCreator("Bioscoop Avans");

    }

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
            System.out.println("ERROR ADDING TITLE TO TICKETS: " + e.getLocalizedMessage());
        }
    }

    private void createEmptyLine(Paragraph paragraph, int numberOfLines){
        for (int i = 0; i < numberOfLines; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Document document){
        try{
            // Add empty lines to divide content
            Paragraph paragraph = new Paragraph();
            createEmptyLine(paragraph, 2);
            document.add(paragraph);

            // create a table with amount of columns
            PdfPTable table = new PdfPTable(3);

            PdfPCell c1 = new PdfPCell(new Phrase("First Name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Last Name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Test"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);

            for (int i = 0; i < 5; i++) {
                table.setWidthPercentage(100);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell("Java");
                table.addCell("Honk");
                table.addCell("Success");
            }

            document.add(table);
        }catch(Exception e){
            System.out.println("ERROR ADDING TABLE TO TICKETS: " + e.getLocalizedMessage());
        }

    }


}
