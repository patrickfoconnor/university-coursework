package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Genre extends Model {

    private Long genreId;
    private String name;

    // Genre Constructor
    public Genre(){}

    // Method to go from query results to Employee object
    private Genre(ResultSet results) throws SQLException {
        name = results.getString("Name");
        genreId = results.getLong("GenreId");
    }

    //---------------------- Getter / Setters -----------
    // Getter for genreId
    public Long getGenreId() {
        return genreId;
    }

    // Getter and Setter for genre name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get all the genres with
     * @return a resultList of Genres using sql query
     */
    public static List<Genre> all() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM genres"
             )) {
            ResultSet results = stmt.executeQuery();
            List<Genre> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Genre(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }


}
