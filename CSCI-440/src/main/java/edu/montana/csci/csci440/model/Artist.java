package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Artist extends Model {

    Long artistId;
    String name;
    String cachedName;
    // Artist Constructor
    public Artist() {
    }

    // Method to go from query results to album object
    private Artist(ResultSet results) throws SQLException {
        name = results.getString("Name");
        artistId = results.getLong("ArtistId");
    }
    //---------------------- Helper Getters -----------
    // Get the albums that are associated with artistId
    public List<Album> getAlbums(){return Album.getForArtist(artistId);}

    //---------------------- Getter / Setters -----------

    // Getter and Setter for artistId
    public Long getArtistId() {return artistId;}
    private void setArtistId(Long artistId) {this.artistId = this.artistId;}

    // Getter and Setter for artist
    public void setArtist(Artist artist) {
        this.artistId = artist.getArtistId();
    }

    // Getter and Setter for artist name
    public String getName() {
        return name;
    }

    //---------------------- Redis Cache Helper -----------

    public void setName(String name) {
        setCachedName(this.name);
        this.name = name;
    }
    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String name) {
        this.cachedName = name;
    }

    /**
     * Get all the Artist with paging
     * @return a resultList of Artists using sql query
     */
    public static List<Artist> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Artist> all(int page, int count) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM artists LIMIT ? OFFSET ?"
             )) {
            stmt.setInt(1, count);
            stmt.setInt(2, (page*count)-count );
            ResultSet results = stmt.executeQuery();
            List<Artist> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Artist(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    /**
     * Find Artist by ArtistId
     * @param i ArtistId
     * @return An Artist
     */
    public static Artist find(long i) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artists WHERE ArtistId=?")) {
            stmt.setLong(1, i);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Artist(results);
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
                         "INSERT INTO artists (Name, ArtistId) VALUES (?,?)")) {
                stmt.setString(1, this.getName());
                this.artistId = DB.getLastID(conn);
                stmt.setLong(2, getArtistId());
                stmt.executeUpdate();
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
                         "UPDATE artists SET Name=? WHERE ArtistId=? AND Name=?")) {
                stmt.setString(1, this.getName());
                stmt.setLong(2, this.getArtistId());
                // Get cached name
                stmt.setString(3, this.getCachedName());
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
                     "DELETE FROM artists WHERE ArtistId=?")) {
            stmt.setLong(1, this.getArtistId());
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    // Verify that artist name is not null or blank
    @Override
    public boolean verify() {
        _errors.clear(); // clear any existing errors
        if (getName() == null || "".equals(getName())) {
            addError("Artist Name can't be null or blank!");
        }
        return !hasErrors();
    }

}
