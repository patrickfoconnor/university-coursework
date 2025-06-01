package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Album extends Model {

    Long albumId;
    Long artistId;
    String title;
    String name;

    // Album Constructor
    public Album() {
    }

    // Method to go from query results to album object
    private Album(ResultSet results) throws SQLException {
        title = results.getString("Title");
        name = results.getString("Name");
        albumId = results.getLong("AlbumId");
        artistId = results.getLong("ArtistId");
    }

    //---------------------- Helper Getters -----------
    // Get the tracks that are associated with albumId
    public List<Track> getTracks() {
        return Track.forAlbum(albumId);
    }
    // Get the all albums for artist by artistId
    public static List<Album> getForArtist(Long artistId) {
        return Album.getAlbumForArtist(artistId);
    }

    // Get all albums with artistId like @param artistId
    private static List<Album> getAlbumForArtist(Long artistId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT Title, AlbumId\n, a.ArtistId, a.Name as Name" +
                     "       FROM albums\n" +
                     "JOIN artists a on a.ArtistId = albums.ArtistId\n" +
                     "WHERE albums.ArtistId LIKE ?"
             )) {
            stmt.setLong(1, artistId);
            ResultSet results = stmt.executeQuery();
            List<Album> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Album(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

    }

    //---------------------- Getter / Setters -----------
    // Getter and Setter for artist
    public void setArtist(Artist artist) {
        this.artistId = artist.getArtistId();
    }
    public void setArtist(){this.name = name;}
    public Artist getArtist() {return getArtist(this.getArtistId());}
    public Artist getArtist(Long artistId) {
        if (artistId == null){
            return null;
        }
        else{
            return Artist.find(artistId);
        }
    }

    // Getter and Setter for albumId
    public void setAlbumId() {this.albumId = albumId;}
    public void setAlbumId(Long albumId) {this.albumId = albumId;}
    public Long getAlbumId() {
        return albumId;
    }

    // Getter and Setter for artist
    public void setAlbum(Album album) {
        this.albumId = album.getAlbumId();
    }

    // Getter and Setter for album title
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    // Getter and Setter for artistId
    public Long getArtistId() {
        return artistId;
    }
    public Long getArtistId(Artist artistName) {return artistName.getArtistId();}
    public void setArtistId(){this.artistId = artistId;}
    public void setArtistId(String artistId){this.artistId = Long.valueOf(artistId);}

    /**
     * Get all the albums with paging
     * @return a resultList of albums using sql query
     */
    public static List<Album> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Album> all(int page, int count) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT *,a.Name as Name FROM albums " +
                             " JOIN artists a on a.ArtistId = albums.ArtistId" +
                             " LIMIT ? OFFSET ?"
             )) {
            stmt.setInt(1, count);
            stmt.setInt(2, (page*count)-count );
            ResultSet results = stmt.executeQuery();
            List<Album> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Album(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    /**
     * Find Album by AlbumId
     * @param i AlbumId
     * @return An Album
     */
    public static Album find(long i) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT *, artists.name as Name " +
                     "FROM albums " +
                     "JOIN artists on artists.ArtistId = albums.ArtistId" +
                     "  WHERE AlbumId=?")) {
            stmt.setLong(1, i);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Album(results);
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
                         "INSERT INTO albums (Title, ArtistId, AlbumId) VALUES (?, ?,?)")) {
                stmt.setString(1, this.getTitle());
                stmt.setLong(2, this.getArtistId());
                this.albumId = DB.getLastID(conn);
                stmt.setLong(3, getAlbumId());
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
                         "UPDATE albums SET Title=? WHERE AlbumId=?")) {
                stmt.setString(1, this.getTitle());
                stmt.setLong(2, this.getAlbumId());
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
    public void delete() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM albums WHERE AlbumId=?")) {
            stmt.setLong(1, this.getAlbumId());
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    // Verify that album title and artist is not null or blank
    @Override
    public boolean verify() {
        _errors.clear(); // clear any existing errors
        if (getTitle() == null || "".equals(getTitle())) {
            addError("Album Title can't be null or blank!");
        }
        if (getArtist() == null) {
            addError("Artist Name can't be null!");
        }
        return !hasErrors();
    }

}
