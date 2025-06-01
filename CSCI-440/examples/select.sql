SELECT 1 + 1;

select 1 + 1;

SELECT TrackId,
       Name,
       Composer,
       AlbumId,
       UnitPrice
FROM tracks;

-- Homework Practice
-- Question 01: COMPLETED
SELECT Name FROM artists WHERE Name LIKE "%A%";
-- Question 02: COMPLETED

SELECT artists.Name
FROM artists
WHERE artists.ArtistId IN
      (SELECT ArtistId
        FROM albums
        GROUP BY ArtistId
          HAVING 1 < COUNT(albums.ArtistId)
          );
-- Question 03: COMPLETED

SELECT tracks.Name AS TrackName, albums.Title AS AlbumTitle, artists.Name AS ArtistsName

FROM tracks
    JOIN albums
        ON tracks.AlbumId = albums.AlbumId
    JOIN artists
        ON albums.ArtistId = artists.ArtistId
    WHERE tracks.Milliseconds > 6 * 60 * 1000
    ORDER BY ArtistsName;

-- End Homework Practice
SELECT *
FROM tracks;

SELECT name
FROM tracks
WHERE Milliseconds > 3 * 60 * 1000;

SELECT name, AlbumId
FROM tracks
WHERE AlbumId = 1;

SELECT Title
FROM albums
WHERE   AlbumId = 1;

SELECT name, Milliseconds, AlbumId
FROM tracks
WHERE AlbumId = 1 OR Milliseconds > 3 * 60 * 1000;

SELECT name, Milliseconds, AlbumId
FROM tracks
WHERE NOT AlbumId = 1 AND Milliseconds > 3 * 60 * 1000;

select Name from tracks where Composer IS NULL;



