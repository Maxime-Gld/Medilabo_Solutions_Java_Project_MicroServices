CREATE TABLE IF NOT EXISTS patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adress VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

INSERT INTO patient (lastname, name, birthdate, gender, adress, phone)
VALUES (
        'TestNone',
        'Test',
        '1966-12-31',
        'F',
        '1 Brookside St',
        '100-222-3333'
    ),
    (
        'TestBorderline',
        'Test',
        '1945-06-24',
        'M',
        '2 High St',
        '200-333-4444'
    ),
    (
        'TestInDanger',
        'Test',
        '2004-06-18',
        'M',
        '3 Club Road',
        '300-444-5555'
    ),
    (
        'TestEarlyOnset',
        'Test',
        '2002-06-28',
        'F',
        '4 Valley Dr',
        '400-555-6666'
    );