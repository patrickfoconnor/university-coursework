

CREATE TABLE albums_bak (
    AlbumId  INTEGER,
    Title    NVARCHAR(160),
    ArtistId INTEGER
);

DROP TABLE albums_bak;

DROP TABLE grammy_categories;

CREATE TABLE grammy_categories(
                  Name STRING
                  GrammyCategoryId INTEGER PRIMARY KEY  AUTOINCREMENT
            );

DROP TABLE albums_backup;


-- Proof SQLite doesn't care...
INSERT INTO albums (Title, ArtistId) VALUES ("Lopado­temacho­selacho­galeo­kranio­leipsano­drim­hypo­trimmato­silphio­karabo­melito­katakechy­meno­kichl­epi­kossypho­phatto­perister­alektryon­opte­kephallio­kigklo­peleio­lagoio­siraio­baphe­tragano­pterygon", 1);

INSERT INTO genres (Name) VALUES ("Indie"),("MetalCore"),("Lofi"),("Folk"),("Classical New Age");

ALTER TABLE
albums_bak
RENAME TO albums_backup;

ALTER TABLE
albums_backup
ADD COLUMN NEW_COL TEXT;

ALTER TABLE
albums_backup
RENAME COLUMN NEW_COL to NewColumn;

ALTER TABLE
albums_backup
RENAME COLUMN NEW_COL to NewColumn;;

DROP COLUMN NewColumn;

CREATE VIEW tracksPlus AS
SELECT tracks.*,
       albums.Title as AlbumTitle,
       artists.Name as ArtistName
FROM tracks
         JOIN albums ON
             tracks.AlbumId = albums.AlbumId
         JOIN artists ON
             albums.ArtistId = artists.ArtistId;

SELECT *
from tracksPlus
WHERE ArtistName = "AC/DC";

DROP VIEW "tracksPlus";

-- Create a view tracksPlus to display the artist, song title, album, and genre for all tracks. --
CREATE VIEW tracksPlus AS
SELECT
    artists.Name as ArtistName,
    tracks.Name AS TrackName,
    albums.Title as AlbumTitle,
    genres.Name as GenreName
FROM tracks
JOIN albums ON
    tracks.AlbumId = albums.AlbumId
JOIN artists ON
    albums.ArtistId = artists.ArtistId
JOIN genres ON
        tracks.GenreId = genres.GenreId;

-- Quiz 05 --

CREATE TABLE sales(
                      SalesID INTEGER PRIMARY KEY  AUTOINCREMENT,
                      CustomerEmail NVARCHAR(60),
                      SalesDate DATE,
                      Apples INTEGER,
                      Oranges INTEGER
);

SELECT *
FROM sales
WHERE sales.Apples + sales.Oranges > 10;


ALTER TABLE
    sales
    ADD COLUMN TotalFruit INTEGER;

UPDATE sales
SET TotalFruit=(sales.Apples+sales.Oranges)
WHERE (sales.Apples+sales.Oranges) > 10;


