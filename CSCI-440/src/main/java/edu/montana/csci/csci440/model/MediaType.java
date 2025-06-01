package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;
import org.eclipse.jetty.http.MetaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MediaType extends Model {

    private Long mediaTypeId;
    private String name;

    // MediaType Constructor
    public MediaType(){}

    // Method to go from query results to MediaType object
    private MediaType(ResultSet results) throws SQLException {
        name = results.getString("Name");
        mediaTypeId = results.getLong("MediaTypeId");
    }

    //---------------------- Helper Getters -----------
    // Get the MediaType that is associated with trackId
    public static MediaType getMediaTypeForTrack(Long trackId) {
        String query = "SELECT *\n" +
                "FROM media_types\n" +
                "JOIN tracks t on media_types.MediaTypeId = t.MediaTypeId\n" +
                "WHERE TrackId LIKE 1";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, trackId);
            ResultSet results = stmt.executeQuery();
            //List<Track> resultList = new LinkedList<>();
            return new MediaType(results);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    //---------------------- Getter / Setters -----------
    // Getter for MediaTypeId
    public Long getMediaTypeId() {
        return mediaTypeId;
    }

    // Getter and Setter for MediaType name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get all the MediaTypes with paging
     * @return a resultList of MediaType using sql query
     */
    public static List<MediaType> all() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM media_types"
             )) {
            ResultSet results = stmt.executeQuery();
            List<MediaType> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new MediaType(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

}
