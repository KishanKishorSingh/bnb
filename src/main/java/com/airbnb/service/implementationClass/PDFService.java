package com.airbnb.service.implementationClass;


import com.airbnb.entity.Bookings;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;


@Service
public class PDFService {
    public void generatePDF(Bookings bookings) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("D://Intellij//bnb//pdf//"+bookings.getId()+"_booking_confirmation.pdf"));
        document.open();
        // Adjust table to have 2 columns and set specific column widths
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); // Set table width to 100% of the page width
        table.setWidths(new float[]{2, 4}); // Set relative widths (2 for Field, 4 for Value)
        addTableHeader(table);
        addRows(table, bookings);

        document.add(table);
        document.close();
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Field", "Value")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table, Bookings bookings) {
        // Ensure each field and value is on a new row
        table.addCell("Booking ID");
        table.addCell(bookings.getId().toString());

        table.addCell("Guest Name");
        table.addCell(bookings.getGuestName());

        table.addCell("Guest Email");
        table.addCell(bookings.getGuestEmail());

        table.addCell("Guest Mobile");
        table.addCell(bookings.getGuestMobile());

        table.addCell("Property ID");
        table.addCell(bookings.getProperty().getId().toString());

        table.addCell("Check-In Date");
        table.addCell(bookings.getCheckInDate().format(DateTimeFormatter.ISO_DATE));

        table.addCell("Check-Out Date");
        table.addCell(bookings.getCheckOutDate().format(DateTimeFormatter.ISO_DATE));

        table.addCell("Number of Rooms");
        table.addCell(bookings.getNumberOfRooms().toString());

        table.addCell("Total Price");
        table.addCell(bookings.getTotalPrice().toString());

        table.addCell("Number of Nights");
        table.addCell(bookings.getNumberOfNights().toString());

        table.addCell("Room Type");
        table.addCell(bookings.getTypesOfRooms());
    }

}

