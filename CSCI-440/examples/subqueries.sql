-- Select all albums with a total size over 1Mb
SELECT title
FROM albums
WHERE 10000000 > (SELECT SUM(bytes)
    FROM tracks
WHERE tracks.AlbumId = albums.AlbumId) ;

SELECT SUM(bytes)
FROM tracks
WHERE tracks.AlbumId = 1;

-- Move condition into subquery
SELECT Name from artists where
ArtistId IN
(SELECT albums.ArtistId
FROM albums
WHERE albums.AlbumId IN
      (SELECT AlbumId FROM tracks
       GROUP BY AlbumId
    HAVING  10000000 > SUM(tracks.Bytes)));


SELECT TrackId, Name, tracks.AlbumId, MediaTypeId,
       GenreId, Composer, Milliseconds, Bytes, UnitPrice,a.Title
    FROM tracks
        JOIN albums a on a.AlbumId = tracks.AlbumId
    WHERE a.ArtistId LIKE 1
    ORDER BY Name;

SELECT Title, AlbumId
       FROM albums
JOIN artists a on a.ArtistId = albums.ArtistId
WHERE albums.ArtistId LIKE 1;

SELECT *, artists.Name as ArtistName, albums.Title as AlbumTitle,
                          p.PlaylistId as PlaylistId
                FROM tracks
                   JOIN albums ON tracks.AlbumId = albums.AlbumId
                   JOIN playlist_track pt on tracks.TrackId = pt.TrackId
                   Left JOIN main.playlists p on p.PlaylistId = pt.PlaylistId
                   JOIN artists ON albums.ArtistId = artists.ArtistId
WHERE albums.AlbumId LIKE 1
GROUP BY tracks.TrackId;

SELECT *
FROM customers
WHERE SupportRepId LIKE 2;

SELECT InvoiceLineId, i.InvoiceId, t.TrackId, Quantity, t.UnitPrice, t.Name as TrackName, a.Title as AlbumName
FROM invoice_items
JOIN invoices i on i.InvoiceId = invoice_items.InvoiceId
JOIN tracks t on t.TrackId = invoice_items.TrackId
JOIN albums a on a.AlbumId = t.AlbumId
WHERE i.InvoiceId LIKE 1;

SELECT *
FROM invoices
JOIN customers c on c.CustomerId = invoices.CustomerId
WHERE c.CustomerId LIKE 1;

SELECT *, invoices.* FROM customers
JOIN invoices  on invoices.CustomerId = customers.CustomerId
GROUP BY invoices.CustomerId;

SELECT *
FROM media_types
JOIN tracks t on media_types.MediaTypeId = t.MediaTypeId
WHERE TrackId LIKE 1;
