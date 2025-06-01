package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Customer extends Model {

    private Long customerId;
    private Long supportRepId;
    private String firstName;
    private String lastName;
    private String email;
    private String billingAddress;
    private String billingCity;
    private String company;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String fax;

    // Customer Constructor
    public Customer() {
    }

    // Method to go from query results to album object
    private Customer(ResultSet results) throws SQLException {
        firstName = results.getString("FirstName");
        lastName = results.getString("LastName");
        billingAddress = results.getString("BillingAddress");
        billingCity = results.getString("BillingCity");
        company = results.getString("Company");
        state = results.getString("State");
        country = results.getString("Country");
        postalCode = results.getString("PostalCode");
        phone = results.getString("Phone");
        fax = results.getString("Fax");
        customerId = results.getLong("CustomerId");
        supportRepId = results.getLong("SupportRepId");
        email = results.getString("Email");

    }

    //---------------------- Helper Getters -----------
    // Get the Customers that are associated with employeeId
    public static List<Customer> forEmployee(long employeeId) {
        String query = "SELECT * FROM customers WHERE SupportRepId=?";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, employeeId);
            ResultSet results = stmt.executeQuery();
            List<Customer> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Customer(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public List<Invoice> getInvoices(){
        return Invoice.getInvoicesForCustomer(customerId);
    }

    //---------------------- Getter / Setters -----------

    // Getter for firstName
    public String getFirstName() {
        return firstName;
    }

    // Getter for lastName
    public String getLastName() {
        return lastName;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for customerId
    public Long getCustomerId() {
        return customerId;
    }

    // Getter for Company
    public String getCompany(){return company;}

    // Getter for supportRepId
    public Long getSupportRepId() {
        return supportRepId;
    }
    public Employee getSupportRep() {return Employee.find(supportRepId);}

    // Getter for Billing Address
    public String getBillingAddress(){return billingAddress;}

    // Getter for Billing City
    public String getBillingCity(){return billingCity;}

    // Getter for Billing State
    public String getBillingState(){return state;}

    // Getter for Billing Country
    public String getBillingCountry(){return country;}

    // Getter for Billing Postal Code
    public String getBillingPostalCode(){return postalCode;}

    // Getter for Phone
    public String getPhone(){return phone;}

    // Getter for fax number
    public String getFax(){return fax;}

    /**
     * Get all the customers with paging
     * @return a resultList of customers using sql query
     */
    public static List<Customer> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Customer> all(int page, int count) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT *, invoices.* FROM customers\n" +
                             "JOIN invoices  on invoices.CustomerId = customers.CustomerId\n" +
                             "GROUP BY invoices.CustomerId LIMIT ? OFFSET ?"
             )) {
            stmt.setInt(1, count);
            stmt.setInt(2, (page*count)-count );
            ResultSet results = stmt.executeQuery();
            List<Customer> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Customer(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
    /**
     * Find Customer by customerId
     * @param customerId the customerId in search
     * @return A Customer
     */
    public static Customer find(long customerId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT *, invoices.* FROM customers\n" +
                     "JOIN invoices  on invoices.CustomerId = customers.CustomerId\n" +
                     "WHERE customers.CustomerId=? GROUP BY invoices.CustomerId ")) {
            stmt.setLong(1, customerId);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Customer(results);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }






    /* Crud Apps below */
    @Override
    public boolean create() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO customers (FirstName, LastName, Company, " +
                                 "Address, City, State, Country, PostalCode, Phone, " +
                                 "Fax, Email, SupportRepId) " +
                                 "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)")) {
                stmt.setString(1, this.getFirstName());
                stmt.setString(2, this.getLastName());
                stmt.setString(3, this.getCompany());
                stmt.setString(4, this.getBillingAddress());
                stmt.setString(5, this.getBillingCity());
                stmt.setString(6, this.getBillingState());
                stmt.setString(7, this.getBillingCountry());
                stmt.setString(8, this.getBillingPostalCode());
                stmt.setString(9, this.getPhone());
                stmt.setString(10, this.getFax());
                stmt.setString(11, this.getEmail());
                stmt.setLong(12, this.getSupportRepId());

                stmt.executeUpdate();
                customerId = DB.getLastID(conn);
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean update() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "UPDATE customers SET FirstName=?, LastName=?, Email=?," +
                                 "Company=?,Address=?,City=?, State=?, Country=?, " +
                                 "PostalCode=?, Phone=?,Fax=?, Email=? WHERE CustomerId=?")) {
                stmt.setString(1, this.getFirstName());
                stmt.setString(2, this.getLastName());
                stmt.setString(3, this.getCompany());
                stmt.setString(4, this.getBillingAddress());
                stmt.setString(5, this.getBillingCity());
                stmt.setString(6, this.getBillingState());
                stmt.setString(7, this.getBillingCountry());
                stmt.setString(8, this.getBillingPostalCode());
                stmt.setString(9, this.getPhone());
                stmt.setString(10, this.getFax());
                stmt.setString(11, this.getEmail());
                int updatedRowsCount = stmt.executeUpdate();
                return (updatedRowsCount > 0);
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }


    @Override
    public void delete() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM customers WHERE CustomerId=?")) {
            stmt.setLong(1, this.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    // Verify that customer first name, last name, email,
    // billing country, billing city, billing address, and
    // support rep id are not null or blank
    @Override
    public boolean verify() {
        _errors.clear(); // clear any existing errors
        if (getFirstName() == null || "".equals(getFirstName())) {
            addError("Customer First Name can't be null or blank!");
        }
        if (getLastName() == null || "".equals(getLastName())) {
            addError("Customer Last Name can't be null or blank!");
        }
        if (getEmail() == null || "".equals(getEmail())) {
            addError("Customer Email can't be null or blank!");
        }
        if (getBillingCountry() == null || "".equals(getBillingCountry())) {
            addError("Customer Country can't be null or blank!");
        }
        if (getBillingCity() == null || "".equals(getBillingCity())) {
            addError("Customer City can't be null or blank!");
        }
        if (getBillingAddress() == null || "".equals(getBillingAddress())) {
            addError("Customer Address can't be null or blank!");
        }
        if (getSupportRepId() == null || -1L == (getSupportRepId())) {
            addError("Customer Support Rep Id can't be null or negative!");
        }
        return !hasErrors();
    }

}
