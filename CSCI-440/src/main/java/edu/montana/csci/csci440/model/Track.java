package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Track extends Model {

    private String albumTitle;
    private String artistName;
    private Long trackId;
    private Long albumId;
    private Long mediaTypeId;
    private Long genreId;
    private String name;
    private Long milliseconds;
    private Long bytes;
    private BigDecimal unitPrice;

    public static final String REDIS_CACHE_KEY = "cs440-tracks-count-cache";

    // Track Constructor
    public Track() {
        mediaTypeId = 1L;
        genreId = 1L;
        milliseconds  = 0L;
        bytes  = 0L;
        unitPrice = new BigDecimal("0");
    }

    // Method to go from query results to Track object
    public Track(ResultSet results) throws SQLException {
        name = results.getString("Name");
        albumTitle = results.getString("AlbumTitle");
        artistName = results.getString("ArtistName");
        milliseconds = results.getLong("Milliseconds");
        bytes = results.getLong("Bytes");
        unitPrice = results.getBigDecimal("UnitPrice");
        trackId = results.getLong("TrackId");
        albumId = results.getLong("AlbumId");
        mediaTypeId = results.getLong("MediaTypeId");
        genreId = results.getLong("GenreId");
        Long playlistId = results.getLong("PlaylistId");

    }

    //---------------------- Helper Getters -----------
    // Get the Playlists that are associated with trackId
    public List<Playlist> getPlaylists(){return Playlist.getPlaylistTrackIsOn(this.getTrackId());}

    // Get media type that is associated with trackId
    public MediaType getMediaType() {return MediaType.getMediaTypeForTrack(this.getTrackId());}

    // Get the Tracks that are associated with albumId
    public static List<Track> forAlbum(Long albumId) {
        String query = "SELECT *, artists.Name as ArtistName, albums.Title as AlbumTitle,\n" +
                "                          p.PlaylistId as PlaylistId\n" +
                "                FROM tracks\n" +
                "                   JOIN albums ON tracks.AlbumId = albums.AlbumId\n" +
                "                   JOIN playlist_track pt on tracks.TrackId = pt.TrackId\n" +
                "                   Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId\n" +
                "                   JOIN artists ON albums.ArtistId = artists.ArtistId\n" +
                "WHERE albums.AlbumId LIKE ?\n" +
                "GROUP BY tracks.TrackId";
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, albumId);
            ResultSet results = stmt.executeQuery();
            List<Track> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Track(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    // Get the Tracks that are associated with playlistId
    public static List<Track> getTracksOnPlaylistById(Long playlistId){
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT pt.PlaylistId as PlaylistId, tracks.Name as Name, a.Title as AlbumTitle, " +
                             "artists.Name as ArtistName, tracks.Bytes as Bytes, \n" +
                             "       tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,\n" +
                             "       tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice\n" +
                             "\n" +
                             "FROM tracks\n" +
                             "        JOIN playlist_track pt on tracks.TrackId = pt.TrackId\n" +
                             "        JOIN playlists p on p.PlaylistId = pt.PlaylistId\n" +
                             "        JOIN albums a on tracks.AlbumId = a.AlbumId " +
                             "        JOIN artists ON a.ArtistId = artists.ArtistId \n" +
                             "WHERE pt.PlaylistId LIKE ?\n" +
                             "ORDER BY Name;"
             )) {
            stmt.setLong(1, playlistId);
            ResultSet results = stmt.executeQuery();
            List<Track> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Track(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    //---------------------- Getter / Setters -----------
    // Getter for Album
    public Album getAlbum() {
        return Album.find(albumId);
    }



    // Getter for firstName
    public Genre getGenre() {
        return null;
    }

    // Getter and Setter for trackId
    public Long getTrackId() {
        return trackId;
    }
    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    // Getter and Setter for Track name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for Track milliseconds
    public Long getMilliseconds() {
        return milliseconds;
    }
    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    // Getter and Setter for Track bytes
    public Long getBytes() {
        return bytes;
    }
    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }

    // Getter and Setter for Track Unit Price
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Getter and Setter for Track AlbumId
    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
    public void setAlbum(Album album) {
        albumId = album.getAlbumId();
    }

    // Getter and Setter for Track mediaTypeId
    public Long getMediaTypeId() {
        return mediaTypeId;
    }
    public void setMediaTypeId(Long mediaTypeId) {this.mediaTypeId = mediaTypeId;}

    // Getter and Setter for Track genreId
    public Long getGenreId() {
        return genreId;
    }
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    // Getter and Setter for Tracks album title
    public String getAlbumTitle() {return albumTitle;}
    public void setAlbumTitle(String albumName) {this.albumTitle = albumName;}

    // Getter and Setter for Tracks artistName
    public String getArtistName() {return artistName;}
    public void setArtistName(String artistName) {this.artistName = artistName;}

    /**
     * Get all the Track with paging
     * @return a resultList of Tracks using sql query
     */
    // Sure would be nice if java supported default parameter values
    public static List<Track> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Track> all(int page, int count) {
        return all(page, count, "TrackId");
    }

    public static List<Track> all(int page, int count, String orderBy) {
        String validatedInput;
        String preparedOrderAndLimit;

        if (orderBy != null) {
            validatedInput = validateUserInput(orderBy);
            preparedOrderAndLimit = validatedInput + " LIMIT " + count;
        }
        else {
            preparedOrderAndLimit = " LIMIT " + count;
        }
        int pageOffset = ((page*count)-count);
        String preparedStatement = "SELECT *, artists.Name as ArtistName, albums.Title as AlbumTitle," +
                "p.PlaylistId as PlaylistId " +
                "FROM tracks " +
                "   JOIN albums ON tracks.AlbumId = albums.AlbumId " +
                "   JOIN playlist_track pt on tracks.TrackId = pt.TrackId   " +
                "   Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId " +
                "   JOIN artists ON albums.ArtistId = artists.ArtistId " +
                "GROUP BY tracks.TrackId " +
                preparedOrderAndLimit +" OFFSET " + pageOffset;
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(preparedStatement)) {
            ResultSet results = stmt.executeQuery();
            List<Track> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Track(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    /**
     * Find Track by TrackId
     * @param i TrackId
     * @return A Track
     */
    public static Track find(long i) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement
                     ("SELECT *, artists.Name as ArtistName, albums.Title as AlbumTitle," +
                             "p.PlaylistId as PlaylistId " +
                           "FROM tracks " +
                             "  JOIN albums ON tracks.AlbumId = albums.AlbumId " +
                             "  JOIN playlist_track pt on tracks.TrackId = pt.TrackId   " +
                             "  Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId " +
                                "JOIN artists ON albums.ArtistId = artists.ArtistId " +
                           "WHERE tracks.TrackId=?"
                     )) {
            stmt.setLong(1, i);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Track(results);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }


    /**
     * Using redis to store a count of tracks
     * @return A long count of tracks
     */
    public static Long count() {
        Jedis redisClient = new Jedis(); // use this class to access redis and create a cache
        String cache = redisClient.get(REDIS_CACHE_KEY);
        if (cache == null) {
            // do the query and cache the result
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as Count FROM tracks")) {
                ResultSet results = stmt.executeQuery();
                if (results.next()) {
                    redisClient.set(REDIS_CACHE_KEY, String.valueOf(results.getLong("Count")));
                    return Long.valueOf(redisClient.get(REDIS_CACHE_KEY));
                } else {
                    throw new IllegalStateException("Should find a count!");
                }
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }

        }
        else {
            // return the cached value
            return Long.valueOf(redisClient.get(REDIS_CACHE_KEY));
        }
    }

    /**
     * Advanced Search of Tracks for multiple parameters all of which are optional
     * @param page page number;
     * @param count number of tracks on each page
     * @param trackSearch the trackName the search is looking for
     * @param artistId the artistId in search
     * @param albumId the albumId in search
     * @param mediaTypeId the mediaTypeId in search
     * @param genreId the genreId in search
     * @param maxRuntime the maxRunTime in search
     * @param minRuntime the minRunTime in search
     * @return A results list of Tracks fitting search parameters
     */
    public static List<Track> advancedSearch(int page, int count,
                                             String trackSearch,
                                             Integer artistId, Integer albumId,
                                             Integer mediaTypeId, Integer genreId,
                                             Integer maxRuntime, Integer minRuntime) {
        LinkedList<Object> args = new LinkedList<>();

        String query = "SELECT *, artists.Name as ArtistName, albums.title as AlbumTitle\n" +
                "FROM tracks\n" +
                "   JOIN albums ON tracks.AlbumId = albums.AlbumId\n" +
                "   JOIN artists ON albums.ArtistId = artists.ArtistId\n" +
                "   JOIN genres g on g.GenreId = tracks.GenreId\n" +
                "   JOIN media_types mt on mt.MediaTypeId = tracks.MediaTypeId " +
                "   JOIN playlist_track pt on tracks.TrackId = pt.TrackId   " +
                "   Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId " +
                "WHERE tracks.Name LIKE ? ";

        args.add("%" + trackSearch + "%");


        // Conditionally include the query and argument
        if (artistId != null) {
            query += " AND artists.ArtistId LIKE ? ";
            args.add(artistId);
        }
        if (albumId != null) {
            query += " AND albums.AlbumId LIKE ? ";
            args.add(albumId);
        }
        if (mediaTypeId != null) {
            query += " AND mt.MediaTypeId LIKE ? ";
            args.add(mediaTypeId);
        }
        if (genreId != null) {
            query += " AND g.GenreId LIKE ? ";
            args.add(genreId);
        }

        if (maxRuntime != null) {
            query += " AND Milliseconds <=? ";
            args.add(maxRuntime);
        }
        if (minRuntime != null) {
            query += " AND Milliseconds >=? ";
            args.add(minRuntime);
        }
        query += " GROUP BY tracks.TrackId ORDER BY tracks.TrackId ";

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < args.size(); i++) {
                Object arg = args.get(i);
                stmt.setObject(i + 1, arg);
            }
            ResultSet results = stmt.executeQuery();
            List<Track> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Track(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }



    /**
     * Advanced Search of Tracks for multiple parameters all of which are optional
     * @param page page number;
     * @param count number of tracks on each page
     * @param orderBy What the query should be orderedBy
     * @param search the simple query in questions (track name, album title, or artist name)
     * @return A results list of Tracks fitting search parameters
     */
    public static List<Track> search(int page, int count, String orderBy, String search) {
        String query = "SELECT *, artists.Name as ArtistName, albums.Title as AlbumTitle," +
                "p.PlaylistId as PlaylistId " +
                "FROM tracks " +
                "   JOIN albums ON tracks.AlbumId = albums.AlbumId " +
                "   JOIN playlist_track pt on tracks.TrackId = pt.TrackId   " +
                "   Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId " +
                "   JOIN artists ON albums.ArtistId = artists.ArtistId " +
                "WHERE tracks.Name LIKE ? OR AlbumTitle LIKE ? OR ArtistName LIKE ? "+
                "GROUP BY tracks.TrackId";
//
        search = "%" + search + "%";

        if (orderBy != null)
            search = search + validateUserInput(orderBy);

        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            ResultSet results = stmt.executeQuery();
            List<Track> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Track(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    /**
     * Validate User input
     * @param orderBy What the query should be orderedBy
     * @return A pre-formatted string that is safe to execute
     */
    private static String validateUserInput(String orderBy) {
        String validatedOrder;

        if (orderBy.equals("Milliseconds")){
            validatedOrder = "ORDER BY Milliseconds";
            return validatedOrder;
        }
        if (orderBy.equals("Bytes")){
            validatedOrder = "ORDER BY Bytes";
            return validatedOrder;
        }
        if (orderBy.equals("TrackId")){
            validatedOrder = "ORDER BY TrackId";
            return validatedOrder;
        }
        else {
            throw new IllegalStateException("Unexpected value: " + orderBy);
        }
    }

    /* Crud Apps below */
    // Invalidate cache as our count is changing
    @Override
    public boolean create() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO tracks (Name, AlbumId, MediaTypeId, " +
                                 "Milliseconds, UnitPrice) VALUES (?,?,?,?,?)")) {
                stmt.setString(1, this.getName());
                stmt.setLong(2, this.getAlbumId());
                stmt.setLong(3, this.getMediaTypeId());
                stmt.setLong(4, this.getMilliseconds());
                stmt.setBigDecimal(5, this.getUnitPrice());
                stmt.executeUpdate();
                trackId = DB.getLastID(conn);
                // Implementation of redis invalidation
                Jedis redisClient = new Jedis();
                redisClient.del(REDIS_CACHE_KEY);
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
                         "UPDATE tracks SET Name=? WHERE TrackId=?")) {
                stmt.setString(1, this.getName());
                stmt.setLong(2, this.getTrackId());
                stmt.executeUpdate();
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }


    // Invalidate cache as our count is changing
    @Override
    public void delete() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM tracks WHERE TrackId=?")) {
            stmt.setLong(1, this.getTrackId());
            stmt.executeUpdate();
            // Implementation of redis invalidation
            Jedis redisClient = new Jedis();
            redisClient.del(REDIS_CACHE_KEY);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    // Verify that track name and albumId are not null or blank
    @Override
    public boolean verify() {
        _errors.clear(); // clear any existing errors
        if (getName() == null || "".equals(getName())) {
            addError("Artist Name can't be null or blank!");
        }
        if (getAlbumId() == null) {
            addError("Album Id can't be null!");
        }
        return !hasErrors();
    }

}
