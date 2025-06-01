
INSERT INTO tracks
    (Name, AlbumId, MediaTypeId, GenreId,
     Composer, Milliseconds, Bytes, UnitPrice)
    VALUES
    ("Demo", 1, 1, 1, NULL, 2000, 300, .99)

SELECT * FROM employees WHERE ReportsTo=1

UPDATE tracks
SET Bytes=(Bytes - 10)
WHERE TrackId = 1;

UPDATE tracks
SET Bytes=(Bytes + 10)
WHERE TrackId = 2;

ALTER TABLE albums ADD COLUMN Bytes INTEGER;

BEGIN TRANSACTION;

UPDATE albums
SET Bytes=(SELECT SUM(Bytes) FROM tracks WHERE tracks.AlbumId = 1)
WHERE AlbumId = 1;

COMMIT;

UPDATE tracks
SET Bytes=(Bytes + 10)
WHERE TrackId = 2;

COMMIT;



UPDATE artists
SET Name="DC/AC", Name=2
WHERE Name="AC/DC" AND ArtistId=1;


UPDATE artists
SET Name="DC/AC", Version=2
WHERE Version=1 AND ArtistId=1;


BEGIN TRANSACTION;

ALTER TABLE
    invoice_items
    ADD COLUMN TotalFruit INTEGER;



UPDATE invoice_items
SET TotalFruit=(UnitPrice+InvoiceId)
WHERE UnitPrice+Quantity > 0;

COMMIT;


SELECT *, artists.Name as ArtistName, albums.title as AlbumTitle
FROM tracks
         JOIN albums ON tracks.AlbumId = albums.AlbumId
         JOIN artists ON albums.ArtistId = artists.ArtistId
         JOIN genres g on g.GenreId = tracks.GenreId
         JOIN media_types mt on mt.MediaTypeId = tracks.MediaTypeId
    JOIN playlist_track pt on tracks.TrackId = pt.TrackId
    Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId
WHERE tracks.Name LIKE "%A%"
      GROUP BY tracks.TrackId ORDER BY tracks.TrackId LIMIT 100 OFFSET 0