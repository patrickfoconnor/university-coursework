
-- Inner join
SELECT tracks.name as TrackName, albums.title as AlbumTitle
FROM tracks
JOIN albums on tracks.AlbumId = albums.AlbumId
WHERE albums.Title = "Machine Head";


-- Outer join
SELECT name as ArtistName, Title as AlbumTitle
FROM artists
         LEFT OUTER JOIN albums on artists.ArtistId = albums.ArtistId;


-- Self join
SELECT employees.FirstName as FirstName,
       employees.EmployeeId as EmployeeId,
       bosses.FirstName as BossFirstName,
       bosses.EmployeeId as BossEmployeeId
FROM employees
JOIN employees AS bosses ON employees.ReportsTo = bosses.EmployeeId;


SELECT *
FROM albums
    CROSS JOIN artists;

SELECT
    tracks.Name as TrackName, Title, artists.Name as ArtistsName
FROM
    tracks
        JOIN albums
             ON tracks.AlbumId = albums.AlbumId
        JOIN artists
             ON albums.ArtistId = artists.ArtistId
WHERE artists.name = "AC/DC";

SELECT tracks.Name as TrackName, COUNT(invoice_items.TrackId) as TracksSales
FROM tracks
         JOIN invoice_items ON tracks.TrackId = invoice_items.TrackId
GROUP BY invoice_items.TrackId
HAVING TracksSales > 1;
-- HINT: join to tracks and invoice items and do a group by/having to get the right answer
--         -- note: you will need to use the DISTINCT operator to get the right result!

SELECT tracks.Name as TrackName, COUNT(DISTINCT invoice_items.TrackId) as TracksSales
FROM tracks
         JOIN invoice_items ON tracks.TrackId = invoice_items.TrackId

GROUP BY invoice_items.TrackId
HAVING TracksSales > 1;


--- Homework 03 Problem 03
-- Select customers emails who are assigned to Jane Peacock as a Rep and
--     * who have purchased something from the 'Rock' Genre
--  *
--    * Please use an IN clause and a sub-select to generate customer IDs satisfying the criteria

--  HINT: join to invoice items and do a group by/having to get the right answer

SELECT customers.Email as CustomerEmail

From customers
    JOIN employees ON EmployeeId = customers.SupportRepId;

SELECT Name from artists where
        ArtistId IN
        (SELECT albums.ArtistId
         FROM albums
         WHERE albums.AlbumId IN
               (SELECT AlbumId FROM albums WHERE Title LIKE "A%"));


SELECT *
FROM playlists
         JOIN playlist_track pt on playlists.PlaylistId = pt.PlaylistId
         JOIN tracks t on t.TrackId = pt.TrackId
WHERE pt.PlaylistId LIKE 3;