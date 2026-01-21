TRUNCATE TABLE library_item RESTART IDENTITY CASCADE;

INSERT INTO library_item (title, published_year, available, isbn, author)
VALUES ('Effective Java', 2018, true, '978-0134685991', 'Joshua Bloch'),
       ('Clean Code', 2008, true, '978-0132350884', 'Robert C. Martin');


INSERT INTO library_item (title, published_year, available, issue_number, month)
VALUES ('National Geographic', 2023, true, 202301, 'January'),
       ('Science Monthly', 2004, false, 202402, 'February')
