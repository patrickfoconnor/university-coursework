select * from main.customers
where SupportRepId=3 AND
      CustomerId IN (SELECT CustomerId from invoices
          JOIN invoice_items ii on invoices.InvoiceId = ii.InvoiceId
          JOIN tracks t on ii.TrackId = t.TrackId
          JOIN  genres g on t.GenreId = g.GenreId
          WHERE g.Name == "Rock");


-- Quiz Number 5 --

-- How to create a table with DDL --
CREATE TABLE grammy_categories(
                              Name STRING,
                              GrammyCategoryId INTEGER PRIMARY KEY AUTOINCREMENT
                        );
CREATE TABLE grammy_infos(
                              ArtistId INTEGER,
                              AlbumId  INTEGER,
                              TrackId  INTEGER,
                              GrammyCategoryId INTEGER,
                              Status STRING
                        );
-- How to create an index with DDL --

-- How to denormalize data to make a query easier --

-- Different types of violations of normalization: --

    -- The Key --

    -- The Whole Key --

    -- Nothing But The Key --