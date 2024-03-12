package biuropodrozy.gotravel.service.impl;

import biuropodrozy.gotravel.model.*;
import biuropodrozy.gotravel.service.OwnOfferTypeOfRoomService;
import biuropodrozy.gotravel.service.ReservationsTypeOfRoomService;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.allegro.finance.tradukisto.ValueConverters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A utility class for generating invoices in PDF format.
 * This class provides methods to generate invoices for reservations and own offers.
 */

@Component
@Slf4j
public class InvoiceGenerator {

    /**
     * The service for managing reservations' type of rooms.
     */
    private final ReservationsTypeOfRoomService reservationsTypeOfRoomService;

    /**
     * The service for managing own offers' type of rooms.
     */
    private final OwnOfferTypeOfRoomService ownOfferTypeOfRoomService;

    /**
     * The font used for text in the generated PDF.
     */
    private PdfFont font;

    /**
     * The image of the logo used in the generated PDF.
     */
    private Image logoImage;

    /**
     * Resource bundle for translations used in the generated PDF.
     */
    private ResourceBundle translations;

    /**
     * Constructs an InvoiceGenerator with the provided services for managing reservations' type of rooms and own offers' type of rooms.
     *
     * @param reservationsTypeOfRoomService The service for managing reservations' type of rooms.
     * @param ownOfferTypeOfRoomService     The service for managing own offers' type of rooms.
     */
    public InvoiceGenerator(ReservationsTypeOfRoomService reservationsTypeOfRoomService, OwnOfferTypeOfRoomService ownOfferTypeOfRoomService) {
        this.reservationsTypeOfRoomService = reservationsTypeOfRoomService;
        this.ownOfferTypeOfRoomService = ownOfferTypeOfRoomService;
    }

    /**
     * Initializes the resources required by the InvoiceGenerator.
     * This method loads translations, font, and logo image.
     */
    private void initializeResources() {
        try {
            this.translations = ResourceBundle.getBundle("translations");
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("Curier.ttf");
            assert fontStream != null;
            byte[] fontBytes = fontStream.readAllBytes();
            this.font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H);
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("logo.png");
            if (logoStream != null) {
                this.logoImage = new Image(ImageDataFactory.create(logoStream.readAllBytes())).setHeight(80).setWidth(80).setMarginLeft(36).setMarginTop(5);
            }
        } catch (IOException e) {
            log.error("Error initializing resources: " + e.getMessage());
        }
    }

    /**
     * Adds a header section to the PDF document.
     *
     * @param document         The PDF document to which the header will be added.
     * @param dateOfReservation The date of reservation to be displayed in the header.
     * @param id                The ID of the reservation or own offer.
     * @param typeInvoice       The type of invoice (R for reservation, O for own offer).
     */
    private void addHeader(Document document, LocalDate dateOfReservation, long id, String typeInvoice) {
        float[] columnWidthsHeader = {1, 2};
        Table tableHeader = new Table(UnitValue.createPercentArray(columnWidthsHeader)).useAllAvailableWidth();
        if (logoImage != null) {
            tableHeader.addCell(new Cell().add(logoImage).setBorder(null));
        }
        Paragraph dateParagraph = new Paragraph("Data wystawienia: " + dateOfReservation).setFont(font).setFontSize(10).setTextAlignment(TextAlignment.RIGHT);
        tableHeader.addCell(new Cell().add(dateParagraph).setBorder(null));

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Paragraph title = new Paragraph("Faktura Nr GT" + typeInvoice + "/" + id + "/"+ year)
                .setFont(font)
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(40);

        document.add(tableHeader);
        document.add(title);
    }

    /**
     * Adds seller and buyer information to the PDF document.
     *
     * @param document The PDF document to which the information will be added.
     * @param user     The user information (buyer) to be displayed in the document.
     */
    private void addUsersInfo(Document document, User user) {
        float[] columnWidthsContact = {2, 1, 2};
        Table tableContact = new Table(UnitValue.createPercentArray(columnWidthsContact)).useAllAvailableWidth();

        Paragraph sellerInfo = new Paragraph("Sprzedawca:\nGo Travel Poland Sp. z o.o.\nWesoła 76A\n02-305 Kielce\nNIP: 000-00-00-000")
                .setFont(font)
                .setFontSize(10);
        tableContact.addCell(new Cell().add(sellerInfo));
        tableContact.addCell(new Cell(1, 1).setBorder(null));
        Paragraph buyerInfo = new Paragraph("Nabywca:\n" + user.getFirstname() + " " + user.getLastname() +"\n" +
                user.getStreet() + " " + user.getStreetNumber() + "\n" + user.getZipCode() + " " + user.getCity() +"\n")
                .setFont(font)
                .setFontSize(10);
        tableContact.addCell(new Cell().add(buyerInfo));
        tableContact.setMarginTop(40);

        document.add(tableContact);
    }

    /**
     * Initializes and returns a table with predefined column widths.
     *
     * @return The initialized table.
     */
    private Table initializeTable() {
        float[] columnWidths = {1, 2, 2, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
        addHeaderRow(table);
        return table;
    }

    /**
     * Adds a header row to the specified table.
     *
     * @param table The table to which the header row will be added.
     */
    private void addHeaderRow(Table table) {
        String[] headers = {"Lp.", "Nazwa oferty", "Data rezerwacji", "Data wyjazdu", "Ilość dni"};
        for (String header : headers) {
            table.addCell(new Cell().add(new Paragraph(header).setFont(font).setFontSize(10)));
        }
    }

    /**
     * Adds details about the reservation or own offer to the specified table.
     *
     * @param table           The table to which the details will be added.
     * @param numberOfAdults  The number of adults in the reservation or own offer.
     * @param numberOfChildren The number of children in the reservation or own offer.
     * @param food            The type of food included in the reservation or own offer.
     * @param insurance       The type of insurance included in the reservation or own offer.
     * @param insurancePrice  The price of the insurance included in the reservation or own offer.
     */
    private void tableDetails(Table table, int numberOfAdults, int numberOfChildren, String food, String insurance, double insurancePrice) {
        table.addCell(new Cell(1, 2).add(new Paragraph("Ilość osób dorosłych:").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph(String.valueOf(numberOfAdults)).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 2).setBorder(null));
        table.addCell(new Cell(1, 2).add(new Paragraph("Ilość dzieci:").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph(String.valueOf(numberOfChildren)).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 2).setBorder(null));
        table.addCell(new Cell(1, 2).add(new Paragraph("Wyżywienie").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph(translations.getString(food)).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 2).setBorder(null));
        table.addCell(new Cell(1, 2).add(new Paragraph("Ubezpieczenie").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph(translations.getString(insurance)).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).setBorder(null));
        table.addCell(new Cell(1, 1).add(new Paragraph(insurancePrice + " zł").setFont(font).setFontSize(10)));
    }

    /**
     * Adds reservation information to the document.
     *
     * @param document    The document to which the reservation information will be added.
     * @param reservation The reservation for which information will be added.
     */
    private void addReservationInfo(Document document, Reservation reservation) {
        List<ReservationsTypeOfRoom> reservationsTypeOfRooms = reservationsTypeOfRoomService.findByReservation_IdReservation(reservation.getIdReservation());

        Table table = initializeTable();

        table.addCell(new Cell().add(new Paragraph("1.").setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(translations.getString(reservation.getTrip().getTypeOfTrip().getName()) + "\n" + translations.getString(reservation.getTrip().getTripCity().getCountry().getNameCountry()) + " - " + translations.getString(reservation.getTrip().getTripCity().getNameCity())).setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getDateOfReservation())).setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getDepartureDate())).setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getTrip().getNumberOfDays())).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 4).add(new Paragraph("\nSzczegóły:").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph("\nCena: " + reservation.getTrip().getPrice() + " zł/os").setFont(font).setFontSize(10)));
        tableDetails(table, reservation.getNumberOfAdults(), reservation.getNumberOfChildren(), reservation.getTrip().getFood(), reservation.getInsuranceReservation().getName(), reservation.getInsuranceReservation().getPrice());
        table.addCell(new Cell(1, 2).add(new Paragraph("Zakwaterowanie").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph(translations.getString(reservation.getTrip().getTripAccommodation().getNameAccommodation())).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 2).setBorder(null));
        table.addCell(new Cell(1, 2).add(new Paragraph("Rodzaj pokoi").setFont(font).setFontSize(10)));

        Cell cellType = new Cell(1,1);
        reservationsTypeOfRooms.forEach(typeOfRoom -> {
            Paragraph paragraph = new Paragraph(translations.getString(typeOfRoom.getTypeOfRoom().getType()));
            cellType.add(paragraph);
        });
        table.addCell(cellType).setFont(font).setFontSize(10);
        Cell cellCount = new Cell(1,1);
        reservationsTypeOfRooms.forEach(typeOfRoom -> {
            Paragraph paragraph = new Paragraph("Ilość pokoi: " + typeOfRoom.getNumberOfRoom());
            cellCount.add(paragraph);
        });
        table.addCell(cellCount).setFont(font).setFontSize(10);

        table.addCell(new Cell(1, 1).setBorder(null));

        table.addCell(new Cell(1, 3).add(new Paragraph("\nRazem:").setFont(font).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)));
        table.addCell(new Cell(1, 2).add(new Paragraph("\n" + reservation.getTotalPrice() + " zl").setFont(font).setFontSize(10)));

        table.setMarginTop(40);
        document.add(table);
    }

    /**
     * Adds own offer information to the document.
     *
     * @param document The document to which the own offer information will be added.
     * @param ownOffer The own offer for which information will be added.
     */
    private void addOwnOfferInfo(Document document, OwnOffer ownOffer) {
        List<OwnOfferTypeOfRoom> ownOfferTypeOfRooms = ownOfferTypeOfRoomService.findByOwnOffer_IdOwnOffer(ownOffer.getIdOwnOffer());

        Table table = initializeTable();

        table.addCell(new Cell().add(new Paragraph("1.").setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(translations.getString(ownOffer.getOfferCity().getCountry().getNameCountry()) + " - " + translations.getString(ownOffer.getOfferCity().getNameCity())).setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(ownOffer.getDateOfReservation())).setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(ownOffer.getDepartureDate())).setFont(font).setFontSize(10)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(ownOffer.getNumberOfDays())).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 3).add(new Paragraph("\nSzczegóły:").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 2).add(new Paragraph("\nCena:").setFont(font).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
        tableDetails(table, ownOffer.getNumberOfAdults(), ownOffer.getNumberOfChildren(), String.valueOf(ownOffer.isFood()), ownOffer.getInsuranceOwnOffer().getName(), ownOffer.getInsuranceOwnOffer().getPrice());
        table.addCell(new Cell(1, 2).add(new Paragraph("Zakwaterowanie").setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).add(new Paragraph(translations.getString(ownOffer.getOfferAccommodation().getNameAccommodation())).setFont(font).setFontSize(10)));
        table.addCell(new Cell(1, 1).setBorder(null));
        table.addCell(new Cell(1, 1).add(new Paragraph(String.valueOf(ownOffer.getOfferAccommodation().getPriceAccommodation()))));
        table.addCell(new Cell(1, 2).add(new Paragraph("Rodzaj pokoi").setFont(font).setFontSize(10)));

        Cell cellType = new Cell(1,1);
        ownOfferTypeOfRooms.forEach(typeOfRoom -> {
            Paragraph paragraph = new Paragraph(translations.getString(typeOfRoom.getTypeOfRoom().getType()));
            cellType.add(paragraph);
        });
        table.addCell(cellType).setFont(font).setFontSize(10);
        Cell cellCount = new Cell(1,1);
        ownOfferTypeOfRooms.forEach(typeOfRoom -> {
            Paragraph paragraph = new Paragraph("Ilość pokoi: " + typeOfRoom.getNumberOfRoom());
            cellCount.add(paragraph);
        });
        table.addCell(cellCount).setFont(font).setFontSize(10);

        Cell cellCount1 = new Cell(1,1);
        ownOfferTypeOfRooms.forEach(typeOfRoom -> {
            Paragraph paragraph = new Paragraph(typeOfRoom.getTypeOfRoom().getRoomPrice() + " zł/szt");
            cellCount1.add(paragraph);
        });
        table.addCell(cellCount1).setFont(font).setFontSize(10);

        table.addCell(new Cell(1, 3).add(new Paragraph("\nRazem:").setFont(font).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)));
        table.addCell(new Cell(1, 2).add(new Paragraph("\n" + ownOffer.getTotalPrice() + " zl").setFont(font).setFontSize(10)));

        table.setMarginTop(40);
        document.add(table);
    }

    /**
     * Adds payment summary information to the document.
     *
     * @param document   The document to which the payment summary information will be added.
     * @param totalPrice The total price to be paid.
     */
    private void addPaymentSummary(Document document, double totalPrice) {
        Paragraph info = new Paragraph("Zapłacono: " + totalPrice + " zl")
                .setFont(font)
                .setFontSize(10)
                .setBold()
                .setMarginTop(20);

        int zloty = (int) totalPrice;
        int groszy = (int) ((totalPrice - zloty) * 100);
        ValueConverters intConverter = ValueConverters.POLISH_INTEGER;
        String zlotyWords = intConverter.asWords(zloty);

        Paragraph infoWords = new Paragraph("Słownie: " + zlotyWords + " "+ groszy + "/100 złotych" )
                .setFont(font)
                .setFontSize(10)
                .setMarginTop(10);

        Paragraph infoToPay = new Paragraph("Do zapłaty: 0.00 zl")
                .setFont(font)
                .setFontSize(10)
                .setMarginTop(10);

        document.add(info);
        document.add(infoWords);
        document.add(infoToPay);
    }

    /**
     * Generates an invoice in PDF format based on the provided object (either Reservation or OwnOffer).
     *
     * @param object The object for which the invoice will be generated.
     * @return Byte array representing the generated PDF invoice.
     */
    public byte[] generateInvoice(Object object) {
        initializeResources();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        if (object instanceof Reservation) {
            addHeader(document, ((Reservation) object).getDateOfReservation(), ((Reservation) object).getIdReservation(), "R");
            addUsersInfo(document, ((Reservation) object).getUser());
            addReservationInfo(document, (Reservation) object);
            addPaymentSummary(document, ((Reservation) object).getTotalPrice());
        }

        if (object instanceof OwnOffer) {
            addHeader(document, ((OwnOffer) object).getDateOfReservation(), ((OwnOffer) object).getIdOwnOffer(), "O");
            addUsersInfo(document, ((OwnOffer) object).getUser());
            addOwnOfferInfo(document, (OwnOffer) object);
            addPaymentSummary(document, ((OwnOffer) object).getTotalPrice());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateGenerator = sdf.format(new Date());
        Paragraph dateParagraphGenerator = new Paragraph("Data wygenerowania: \n" + formattedDateGenerator)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginRight(36)
                .setFontSize(10)
                .setMarginTop(5)
                .setFont(font)
                .setFontSize(8);

        document.add(dateParagraphGenerator);

        document.close();
        byte[] pdfBytes = outputStream.toByteArray();

        log.info("The invoice has been generated.");
        return pdfBytes;
    }

}
