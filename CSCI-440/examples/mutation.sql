SELECT count(*) FROM tracks;
SELECT * FROM tracks order by TrackId desc;

INSERT INTO tracks
(Name, AlbumId, MediaTypeId, GenreId,
 Composer, Milliseconds, Bytes, UnitPrice)
VALUES ("Example", 1, 1, 1,
        "Carson", 1000, 22, 1000.00);

select * from tracks;

SELECT last_insert_rowid();

INSERT INTO artists (name)
VALUES
("Ricky Skaggs"),
("Steve'n'Seagulls"),
("Earl Skruggs");

SELECT * from artists
WHERE Name like
      ("Steve'n'Seagulls");

select * from artists order by ArtistId desc;

INSERT INTO artists_bak
SELECT ArtistId, Name
FROM artists;

UPDATE employees
SET FirstName='Carson';

SELECT * FROM employees;

UPDATE employees
SET email = LOWER(
            firstname || "." ||
            lastname || "@montana.edu"
    );

SELECT * FROM artists
WHERE name LIKE '%Santana%';

DELETE FROM artists
WHERE name LIKE '%Santana%';

REPLACE INTO employees
    (FirstName, LastName, Email)
VALUES
    ("Carson", "Gross", "carson@bigsky.software");


