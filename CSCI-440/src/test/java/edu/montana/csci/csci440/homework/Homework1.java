package edu.montana.csci.csci440.homework;

import edu.montana.csci.csci440.DBTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class Homework1 extends DBTest {

    @Test
    /*
     * Write a query in the string below that returns all artists that have an 'A' in their name
     */
    void selectArtistsWhoseNameHasAnAInIt(){
        List<Map<String, Object>> results = executeSQL("SELECT Name FROM artists WHERE Name LIKE \"%A%\";");
        assertEquals(211, results.size());
    }

    @Test
    /*
     * Write a query in the string below that returns all artists that have more than one album
     */
    void selectAllArtistsWithMoreThanOneAlbum(){
        List<Map<String, Object>> results = executeSQL(
                "SELECT artists.Name\n" +
                        "FROM artists\n" +
                        "WHERE artists.ArtistId IN\n" +
                        "      (SELECT ArtistId\n" +
                        "        FROM albums\n" +
                        "        GROUP BY ArtistId\n" +
                        "          HAVING 1 < COUNT(albums.ArtistId)\n" +
                        "          );");

        assertEquals(56, results.size());
        assertEquals("AC/DC", results.get(0).get("Name"));
    }

    @Test
        /*
         * Write a query in the string below that returns all tracks longer than six minutes along with the
         * album and artist name
         */
    void selectTheTrackAndAlbumAndArtistForAllTracksLongerThanSixMinutes() {
        List<Map<String, Object>> results = executeSQL(
                "SELECT tracks.Name AS TrackName, albums.Title AS AlbumTitle, artists.Name AS ArtistsName FROM tracks\n" +
                        "    JOIN albums\n" +
                        "        ON tracks.AlbumId = albums.AlbumId\n" +
                        "    JOIN artists\n" +
                        "        ON albums.ArtistId = artists.ArtistId\n" +
                        "\n" +
                        "    WHERE tracks.Milliseconds > 6 * 60 * 1000;");

        assertEquals(623, results.size());
    }

}
