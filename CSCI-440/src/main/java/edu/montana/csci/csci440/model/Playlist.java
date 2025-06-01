package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Playlist extends Model {

    private Long playlistId;
    private String name;
    private Long trackId;
    private Long albumId;
    private Long mediaTypeId;
    private Long genreId;
    private Long milliseconds;
    private BigDecimal unitPrice;

    // Playlist Constructor
    public Playlist() {
        mediaTypeId = 1L;
        genreId = 1L;
        trackId = 1L;
        albumId = 1L;
        unitPrice = new BigDecimal("0");
    }

    // Method to go from query results to Playlist object
    private Playlist(ResultSet results) throws SQLException {
        playlistId = results.getLong("PlaylistId");
        name = results.getString("Name");
        trackId = results.getLong("TrackId");
        albumId = results.getLong("AlbumId");
        mediaTypeId = results.getLong("MediaTypeId");
        genreId = results.getLong("GenreId");
        milliseconds = results.getLong("Milliseconds");
        unitPrice = results.getBigDecimal("UnitPrice");
    }
    //---------------------- Helper Getters -----------
    // Get the Tracks that are associated with Playlists
    public List<Track> getTracks(){
        return Track.getTracksOnPlaylistById(this.getPlaylistId());
    }

    // return a list of all the playlists that track # is on
    public static List<Playlist> getPlaylistTrackIsOn(Long trackId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT playlists.Name as Name,\n" +
                     "       playlists.PlaylistId as PlaylistId,\n" +
                     "       tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,\n" +
                     "       tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice\n" +
                     "FROM playlists\n" +
                     "         Left JOIN playlist_track pt on  pt.PlaylistId = playlists.PlaylistId\n" +
                     "         JOIN tracks ON pt.TrackId = tracks.TrackId\n" +
                     "WHERE pt.TrackId=? \n" +
                     "GROUP BY playlists.PlaylistId"
             )) {
            stmt.setLong(1, trackId);
            ResultSet results = stmt.executeQuery();
            List<Playlist> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Playlist(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    //---------------------- Getter / Setters -----------
    // Getter and Setter for playlist name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for playlistId
    public Long getPlaylistId() {return playlistId;}
    public void setPlaylistId(Long playlistId) {this.playlistId = playlistId;}

    // Getter and Setter for mediaType
    public Long getMediaTypeId() {
        return mediaTypeId;
    }
    public void setMediaTypeId(Long mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    // Getter and Setter for genreId
    public Long getGenreId() {
        return genreId;
    }
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    // Getter for UnitPrice
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    // Getter and Setter for trackId
    public Long getTrackId() {
        return trackId;
    }
    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    // Getter and Setter for albumId
    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    /**
     * Get all the playlist with paging
     * @return a resultList of Playlists using sql query
     */
    public static List<Playlist> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Playlist> all(int page, int count) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT playlists.Name as Name,\n" +
                     "  playlists.PlaylistId as PlaylistId,\n" +
                     "  tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,\n" +
                     "  tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice \n" +
                     "FROM playlists\n" +
                     "      LEFT JOIN playlist_track ON playlists.PlaylistId = playlist_track.PlaylistId \n" +
                     "      LEFT JOIN tracks ON playlist_track.TrackId = tracks.TrackId " +
                     "GROUP BY playlists.PlaylistId " +
                     "LIMIT ? OFFSET ?"
             )) {
            stmt.setInt(1, count);
            stmt.setInt(2, (page*count)-count );
            ResultSet results = stmt.executeQuery();
            List<Playlist> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Playlist(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
    /**
     * Find Playlist by PlaylistId
     * @param i PlaylistId
     * @return A Playlist
     */
    public static Playlist find(int i) {
        try (Connection conn = DB.connect();
             //PreparedStatement stmt = conn.prepareStatement("SELECT * FROM playlists WHERE PlaylistId=?")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT playlists.Name as Name, " +
                    "playlist_track.PlaylistId as PlaylistId," +
                    "tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,\n" +
                    "       tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice " +
                    "FROM playlists\n" +
                    "   LEFT JOIN playlist_track ON playlists.PlaylistId = playlist_track.PlaylistId " +
                    "   JOIN tracks ON playlist_track.TrackId = tracks.TrackId\n" +
                    "WHERE playlist_track.PlaylistId LIKE ?")){
             stmt.setLong(1, i);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Playlist(results);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }


    // Verify that album playlistName and playlistId is not null or blank
    @Override
    public boolean verify() {
        _errors.clear(); // clear any existing errors
        if (getName() == null || "".equals(getName())) {
            addError("Playlist Name can't be null or blank!");
        }
        if (getPlaylistId() == null) {
            addError("Playlist Id can't be null!");
        }
        return !hasErrors();
    }
}
