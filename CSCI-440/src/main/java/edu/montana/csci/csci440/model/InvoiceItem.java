package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class InvoiceItem extends Model {

    String trackName;
    String albumName;
    Long invoiceLineId;
    Long invoiceId;
    Long trackId;
    Long quantity;
    BigDecimal unitPrice;

    // InvoiceItem Constructor
    public InvoiceItem() {
    }

    // Method to go from query results to album object
    private InvoiceItem(ResultSet results) throws SQLException {
        trackName = results.getString("TrackName");
        albumName = results.getString("AlbumName");
        invoiceLineId = results.getLong("InvoiceLineId");
        invoiceId = results.getLong("InvoiceId");
        trackId = results.getLong("TrackId");
        quantity = results.getLong("Quantity");
        unitPrice = results.getBigDecimal("UnitPrice");
    }

    //---------------------- Helper Getters -----------
    // Get the InvoiceItems that are associated with InvoiceId
    public static List<InvoiceItem> forInvoiceId(Long invoiceId) {
        String query = "SELECT InvoiceLineId, i.InvoiceId, t.TrackId, Quantity, t.UnitPrice, t.Name as TrackName, a.Title as AlbumName\n" +
                "FROM invoice_items\n" +
                "JOIN invoices i on i.InvoiceId = invoice_items.InvoiceId\n" +
                "JOIN tracks t on t.TrackId = invoice_items.TrackId\n" +
                "JOIN albums a on a.AlbumId = t.AlbumId\n" +
                "WHERE i.InvoiceId LIKE ?";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, invoiceId);
            ResultSet results = stmt.executeQuery();
            List<InvoiceItem> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new InvoiceItem(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }


    //---------------------- Getter / Setters -----------
    // Getter for Invoice
    public Invoice getInvoice() {return Invoice.find(invoiceId);}

    // Getter and Setter for Invoice Line Id
    public Long getInvoiceLineId() {return invoiceLineId;}
    public void setInvoiceLineId(Long invoiceLineId) {this.invoiceLineId = invoiceLineId;}

    // Getter and Setter for InvoiceId
    public Long getInvoiceId() {return invoiceId;}
    public void setInvoiceId(Long invoiceId) {this.invoiceId = invoiceId;}

    // Getter for Track
    public Track getTrack() {return Track.find(trackId);}

    // Getter and Setter for TrackId
    public Long getTrackId() {return trackId;}
    public void setTrackId(Long trackId) {this.trackId = trackId;}

    // Getter and Setter for Unit Price
    public BigDecimal getUnitPrice() {return unitPrice;}
    public void setUnitPrice(BigDecimal unitPrice) {this.unitPrice = unitPrice;}

    // Getter and Setter for Quantity
    public Long getQuantity() {return quantity;}
    public void setQuantity(Long quantity) {this.quantity = quantity;}
}
