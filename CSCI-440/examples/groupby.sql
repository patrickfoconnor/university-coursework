

-- Select all AlbumID FKs from the tracks table
SELECT AlbumID
FROM tracks
GROUP BY AlbumID;

-- Select all AlbumID FKs from the tracks table
SELECT DISTINCT AlbumID
FROM tracks;

-- Select all AlbumID FKs from the tracks table
SELECT AlbumID, COUNT(*)
FROM tracks
GROUP BY AlbumID;

-- Select all AlbumID FKs from the tracks table
SELECT AlbumID, COUNT(*) as TrackCount
FROM tracks
GROUP BY AlbumID;

-- Select all AlbumID FKs from the tracks table
SELECT tracks.AlbumID, Title, COUNT(*) as TrackCount
FROM tracks
JOIN albums on tracks.AlbumId = albums.AlbumId
GROUP BY tracks.AlbumID;

-- Calculate run time of albums by summing the tracks
SELECT tracks.AlbumID, Title,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
JOIN albums on tracks.AlbumId = albums.AlbumId
GROUP BY tracks.AlbumID;

-- Calculate run time of artists by summing the tracks
SELECT artists.Name,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
JOIN albums on tracks.AlbumId = albums.AlbumId
JOIN artists on albums.ArtistId = artists.ArtistId
GROUP BY albums.ArtistId;


-- Calculate run time of artists by summing the tracks
SELECT artists.Name,
       COUNT(tracks.TrackId) as Tracks,
       COUNT(albums.AlbumId) as Albums,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
JOIN albums on tracks.AlbumId = albums.AlbumId
JOIN artists on albums.ArtistId = artists.ArtistId
GROUP BY albums.ArtistId;


-- Simple Join to show the rows
SELECT TrackId, albums.AlbumId, artists.ArtistId
FROM tracks
         JOIN albums on tracks.AlbumId = albums.AlbumId
         JOIN artists on albums.ArtistId = artists.ArtistId;

-- Calculate run time of artists by summing the tracks
SELECT artists.Name,
       COUNT(tracks.TrackId) as Tracks,
       COUNT(DISTINCT albums.AlbumId) as Albums,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
         JOIN albums on tracks.AlbumId = albums.AlbumId
         JOIN artists on albums.ArtistId = artists.ArtistId
GROUP BY albums.ArtistId;

SELECT artists.Name,
       COUNT(tracks.TrackId) as Tracks,
       COUNT(DISTINCT albums.AlbumId) as Albums,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
         JOIN albums on tracks.AlbumId = albums.AlbumId
         JOIN artists on albums.ArtistId = artists.ArtistId
GROUP BY albums.ArtistId
HAVING Tracks >= 10;

SELECT employees.Email,
       SUM(invoices.Total) as Total
FROM invoices
         JOIN customers c on c.CustomerId = invoices.CustomerId
    --JOIN employees e on e.Employee = customers.Su
         JOIN customers on employees.EmployeeId = c.CustomerId;
---------
SELECT artists.Name,
       COUNT(tracks.TrackId) as Tracks,
       COUNT(DISTINCT albums.AlbumId) as Albums,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
         JOIN albums on tracks.AlbumId = albums.AlbumId
         JOIN artists on albums.ArtistId = artists.ArtistId
GROUP BY albums.ArtistId
HAVING Tracks >= 10 AND tracks.Name LIKE "A%";
---------

---------
-- QUIZ 04 Query 01
SELECT artists.Name,
       COUNT(tracks.TrackId) as Tracks,
       COUNT(DISTINCT albums.AlbumId) as Albums,
       SUM(tracks.Milliseconds) as Milliseconds
FROM tracks
         JOIN albums on tracks.AlbumId = albums.AlbumId
         JOIN artists on albums.ArtistId = artists.ArtistId
WHERE tracks.Name LIKE "A%"
GROUP BY albums.ArtistId
HAVING Tracks > 2;
-- QUIZ 04 Query 02
-- Write a query that returns the name of all tracks in a Genre, where the Genre's name starts with "Ro

SELECT LastName, FirstName

FROM employees

WHERE ReportsTo IN
    (SELECT EmployeeId
        FROM employees
        GROUP BY EmployeeId
        HAVING FirstName LIKE "Steve" AND LastName LIKE "Jobs");

select tracks.Name,GenreId

from tracks

    WHERE GenreId IN
    (SELECT GenreId
        FROM genres
        GROUP BY GenreId
        HAVING Name LIKE "Ro%");

-- Working for the employee group by
select tracks.Name

from tracks

    left join genres using(genreid)

group by genres.name
HAVING genres.Name LIKE "Ro%";


SELECT employees.Email,
       COUNT(customers.SupportRepId) as SalesCount
       --SUM(invoices.Total) as SalesTotal
FROM employees
         JOIN customers on customers.CustomerId = employees.EmployeeId
         JOIN invoices i on customers.CustomerId = i.CustomerId
GROUP BY customers.CustomerId;


SELECT e.FirstName, e.LastName,e.Email,
       COUNT(DISTINCT i.InvoiceId) as SalesCount, SUM(i.Total) as SalesTotal
FROM employees e
    JOIN customers c on e.EmployeeId = c.SupportRepId
    JOIN invoices i on c.CustomerId = i.CustomerId
GROUP BY EmployeeId;

SELECT t.Name as TrackName, playlists.Name as Name, *
FROM playlists
JOIN playlist_track pt on playlists.PlaylistId = pt.PlaylistId
JOIN tracks t on t.TrackId = pt.TrackId
WHERE pt.PlaylistId LIKE 1;

SELECT t.*
FROM playlists
         JOIN playlist_track pt on playlists.PlaylistId = pt.PlaylistId
         JOIN tracks t on t.TrackId = pt.TrackId
WHERE pt.PlaylistId LIKE 1
ORDER BY t.Name;

SELECT * FROM tracks
JOIN albums a on a.AlbumId = tracks.AlbumId
WHERE Name LIKE "%%"
AND ArtistId=1 AND a.AlbumId=1
LIMIT 10
OFFSET 0


SELECT *, artists.Name as ArtistName, albums.title as AlbumTitle
FROM tracks
JOIN albums ON tracks.AlbumId = albums.AlbumId
JOIN artists ON albums.ArtistId = artists.ArtistId
JOIN genres g on g.GenreId = tracks.GenreId
JOIN media_types mt on mt.MediaTypeId = tracks.MediaTypeId
WHERE tracks.Name LIKE "%%"
  AND artists.ArtistId=1 AND albums.AlbumId=1
  AND mt.MediaTypeId=1 AND g.GenreId=1
LIMIT 10
    OFFSET 0

-- get trackId albumId mediaTypeId genreId, milliseconds, unitPrice
SELECT playlists.Name as Name, pt.PlaylistId as PlaylistId, t.Name as TrackName,
       t.TrackId as TrackId, t.AlbumId as AlbumId, t.MediaTypeId as MediaTypeId,
       t.GenreId as GenreId, t.Milliseconds as Milliseconds, t.UnitPrice as UnitPrice

FROM playlists
    JOIN playlist_track pt on playlists.PlaylistId = pt.PlaylistId
    JOIN tracks t on t.TrackId = pt.TrackId
WHERE pt.PlaylistId LIKE 3
ORDER BY TrackName;

SELECT p.Name as Name, pt.PlaylistId as PlaylistId, tracks.Name as TrackName,
       tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,
       tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice

FROM tracks
        JOIN playlist_track pt on tracks.TrackId = pt.TrackId
        JOIN playlists p on p.PlaylistId = pt.PlaylistId
WHERE pt.PlaylistId LIKE 3
ORDER BY TrackName;

SELECT playlists.Name as Name,
       playlists.PlaylistId as PlaylistId
FROM playlists
    Left JOIN playlist_track pt on playlists.PlaylistId = pt.PlaylistId
GROUP BY playlists.PlaylistId

SELECT *, artists.Name as ArtistName,
       albums.Title as AlbumTitle,p.PlaylistId as PlaylistId
FROM tracks
         JOIN albums ON tracks.AlbumId = albums.AlbumId
         JOIN playlist_track pt on tracks.TrackId = pt.TrackId
         Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId
         JOIN artists ON albums.ArtistId = artists.ArtistId
GROUP BY tracks.TrackId
ORDER BY TrackId
LIMIT 2147483647 OFFSET -2147483647

SELECT playlists.Name as Name,
       playlists.PlaylistId as PlaylistId,
       tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,
       tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice
FROM playlists
         Left JOIN playlist_track pt on  pt.PlaylistId = playlists.PlaylistId
         JOIN tracks ON pt.TrackId = tracks.TrackId
WHERE pt.TrackId=?
GROUP BY playlists.PlaylistId



SELECT playlists.Name as Name,
       playlists.PlaylistId as PlaylistId,
       tracks.TrackId as TrackId, tracks.AlbumId as AlbumId, tracks.MediaTypeId as MediaTypeId,
       tracks.GenreId as GenreId, tracks.Milliseconds as Milliseconds, tracks.UnitPrice as UnitPrice
FROM playlists
         LEFT JOIN playlist_track ON playlists.PlaylistId = playlist_track.PlaylistId
         Left JOIN tracks ON playlist_track.TrackId = tracks.TrackId
GROUP BY playlists.PlaylistId
LIMIT 100 OFFSET 0

SELECT *
FROM employees
WHERE ReportsTo IS NOT NULL

