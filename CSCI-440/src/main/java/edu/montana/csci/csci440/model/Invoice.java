package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Invoice extends Model {

    Long invoiceId;
    Long customerId;
    String billingAddress;
    String billingCity;
    String billingState;
    String billingCountry;
    String billingPostalCode;
    BigDecimal total;

    // Invoice Constructor
    public Invoice() {
    }

    // Method to go from query results to Invoice object
    private Invoice(ResultSet results) throws SQLException {
        invoiceId = results.getLong("InvoiceId");
        customerId = results.getLong("CustomerId");
        billingAddress = results.getString("BillingAddress");
        billingCity = results.getString("BillingCity");
        billingState = results.getString("BillingState");
        billingCountry = results.getString("BillingCountry");
        billingPostalCode = results.getString("BillingPostalCode");
        total = results.getBigDecimal("Total");
    }

    //---------------------- Helper Getters -----------
    // Hel
    public List<InvoiceItem> getInvoiceItems(){
        return InvoiceItem.forInvoiceId(this.getInvoiceId());
    }

    // Get all Invoices with customerId like @param customerId
    public static List<Invoice> getInvoicesForCustomer(Long customerId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT *\n" +
                     "FROM invoices\n" +
                     "JOIN customers c on c.CustomerId = invoices.CustomerId\n" +
                     "WHERE c.CustomerId LIKE ?"
             )) {
            stmt.setLong(1, customerId);
            ResultSet results = stmt.executeQuery();
            List<Invoice> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Invoice(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

    }

    //---------------------- Getter / Setters -----------
    // Getter for Customer
    public Customer getCustomer() {
        return Customer.find(customerId);
    }

    // Getter for Invoice
    public Long getInvoiceId() {
        return invoiceId;
    }

    // Getter and Setter for Billing Address
    public String getBillingAddress() {return billingAddress;}
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    // Getter and Setter for Billing City
    public String getBillingCity() {return billingCity;}
    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    // Getter and Setter for Billing State
    public String getBillingState() {
        return billingState;
    }
    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    // Getter and Setter for Billing Country
    public String getBillingCountry() {
        return billingCountry;
    }
    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    // Getter and Setter for Billing Postal Code
    public String getBillingPostalCode() {
        return billingPostalCode;
    }
    public void setBillingPostalCode(String billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }

    // Getter and Setter for Total
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Get all the Invoices with paging
     * @return a resultList of Invoices using sql query
     */
    public static List<Invoice> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Invoice> all(int page, int count) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM invoices LIMIT ? OFFSET ?"
             )) {
            stmt.setInt(1, count);
            stmt.setInt(2, (page*count)-count );
            ResultSet results = stmt.executeQuery();
            List<Invoice> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Invoice(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    /**
     * Find Invoice by InvoiceId
     * @param invoiceId an InvoiceId
     * @return An Invoice
     */
    public static Invoice find(long invoiceId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoices WHERE InvoiceId=?")) {
            stmt.setLong(1, invoiceId);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Invoice(results);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
}
