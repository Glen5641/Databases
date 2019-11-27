DROP TABLE staff;
DROP TABLE restaurant;
DROP TABLE rowner;
DROP TABLE chef;
DROP TABLE rserver;
DROP TABLE tips;
DROP TABLE customer;
DROP TABLE rtable;
DROP TABLE training;
DROP TABLE has;
DROP TABLE owns;
DROP TABLE splits;
DROP TABLE share;
DROP TABLE cookFor;
DROP TABLE gives;
DROP TABLE wait;
DROP TABLE seats;
DROP TABLE pays;
DROP TABLE employ;



CREATE TABLE staff (
    staffID INT NOT NULL,
    sname VARCHAR(64) NOT NULL,
    dob VARCHAR(64) NOT NULL,
    age INT NOT NULL
    primary key(staffID)
)
INSERT INTO staff 
    (staffID, sname, dob, age)
VALUES
    (10001, 'Bob', '10/11/1995', 23),
    (10002, 'matt', '3/22/1996', 22),
    (10003, 'melkam', '4/5/1992', 25),
    (10004, 'mark', '8/16/1998', 20),
    (10005, 'steve', '7/17/1997', 21),
    (10006, 'chase', '10/11/1995', 23),
    (10007, 'Jimmy', '3/22/1996', 22),
    (10008, 'John', '4/5/1992', 25),
    (10009, 'James', '8/16/1998', 20),
    (10010, 'Nick', '7/17/1997', 21);


CREATE TABLE restaurant (
    rname VARCHAR(64) NOT NULL,
    rlocation VARCHAR(64) NOT NULL,
    rating VARCHAR(1)
    primary key(rname, rlocation)
)
INSERT INTO restaurant 
    (rname, rlocation, rating)
VALUES
    ('bob''s burgers', 'Norman, 73071, OK', 'B'),
    ('pizza''s pizzeria', 'Okalhoma City, 73120, OK', 'C');


CREATE TABLE rowner(
    ownerID INT NOT NULL,
    PRIMARY KEY(ownerID)
)
INSERT INTO rowner
    (ownerID)
VALUES
    (10001),
    (10006);

CREATE TABLE chef(
    chefID INT NOT NULL,
    cookingSkills varchar(64),
    position varchar(64),
    PRIMARY KEY(chefID)
)
INSERT INTO chef
    (chefID, cookingSkills, position)
VALUES
    (10002, 'Italian', 'Head chef'),
    (10003, 'Italian', 'assitant chef' ),
    (10004, 'Burger', 'Head chef'),
    (10005, 'Burger', 'assitant chef');


CREATE TABLE rserver(
    serverID INT NOT NULL,
    szone varchar(64),
    PRIMARY KEY(serverID)
)
INSERT INTO rserver
    (serverID, szone)
VALUES
    (10007, 'zone 1'),
    (10008, 'zone 2'),
    (10009, 'zone 1'),
    (10010, 'zone 3');


CREATE TABLE tips(
    time_stamp varchar(64) NOT NULL,
    amount numeric(5,2) NOT NULL,
    PRIMARY KEY(time_stamp, amount)
)
INSERT INTO tips
    (time_stamp, amount)
VALUES
    ('10/1/2018', 5.00),
    ('9/4/2018', 4.00),
    ('11/1/2018', 6.00),
    ('10/22/2018', 2.00);


CREATE TABLE customer(
    receipt_ID INT NOT NULL,
    cname varchar(64),
    PRIMARY KEY(receipt_ID)
)
INSERT INTO customer
    (receipt_ID, cname)
VALUES
    (1223, 'Natty'),
    (1224, 'Abel'),
    (0001, 'Thomas'),
    (0002, 'Ronaldo');


CREATE TABLE rtable(
    tnumber INT NOT NULL,
    tlocation varchar(64),
    PRIMARY KEY(tnumber)
)
INSERT INTO rtable
    (tnumber, tlocation)
VALUES
    (12, 'front of the restaurant'),
    (10, 'back of the restaurant'),
    (8, 'front of the restaurant'),
    (11, 'back of the restaurant');

CREATE TABLE training(
    senior INT NOT NULL,
    junior INT NOT NULL,
    PRIMARY KEY(senior, junior)
)
INSERT INTO training
    (senior, junior)
VALUES
    (10002, 10003),
    (10004, 10005),
    (10007, 10008),
    (10009, 10010);

CREATE TABLE has(
    rname VARCHAR(64) NOT NULL,
    tnumber INT NOT NULL,
    PRIMARY KEY(rname, tnumber)
)
INSERT INTO has
    (rname, tnumber)
VALUES
    ('bob''s burgers',  12),
    ('bob''s burgers', 10),
    ('pizza''s pizzeria', 8),
    ('pizza''s pizzeria', 11);

CREATE TABLE owns(
    ownerID INT NOT NULL,
    rname VARCHAR(64) NOT NULL,
    PRIMARY KEY(ownerID, rname)
)
INSERT INTO owns
    (ownerID, rname)
VALUES
    (10001, 'bob''s burgers'),
    (10006, 'pizza''s pizzeria');

CREATE TABLE splits(
    ownerID INT NOT NULL,
    amount NUMERIC(5,2) NOT NULL,
    chefID INT NOT NULL,
    serverID INT NOT NULL,
    PRIMARY KEY(OwnerID, amount, chefID, serverID)
)
INSERT INTO splits
    (ownerID, amount, chefID, serverID)
VALUES
    (10001, 5.00, 10002, 10007),
    (10001, 4.00, 10003, 10008),
    (10006, 6.00, 10004, 10009),
    (10006, 2.00, 10005, 10010);

CREATE TABLE share(
    time_stamp VARCHAR(64) NOT NULL,
    serverID INT NOT NULL,
    chefID INT NOT NULL,
    amount NUMERIC(5,2) NOT NULL,
    PRIMARY KEY(time_stamp, serverID, chefID)
)
INSERT INTO share
    (time_stamp, serverID, chefID, amount)
VALUES
    ('10/1/2018', 10002, 10007, 5.00),
    ('9/4/2018', 10003, 10008, 4.00),
    ('11/1/2018', 10004, 10009, 6.00),
    ('10/22/2018', 10005, 10010, 2.00);

CREATE TABLE cookFor(
    chefID INT NOT NULL,
    tnumber INT NOT NULL,
    PRIMARY KEY(chefID, tnumber)
)
INSERT INTO cookFor
    (chefID, tnumber)
VALUES
    (10002, 12),
    (10003, 10),
    (10004, 8),
    (10005, 11);

CREATE TABLE gives(
    receipt_ID INT NOT NULL,
    time_stamp VARCHAR(64) NOT NULL,
    PRIMARY KEY(receipt_ID, time_stamp)
)
INSERT INTO gives
    (receipt_ID, time_stamp)
VALUES
    (1223, '10/1/2018'),
    (1224, '9/4/2018'),
    (0001, '11/1/2018'),
    (0002, '10/22/2018');

CREATE TABLE wait(
    serverID INT NOT NULL,
    tnumber INT NOT NULL,
    PRIMARY KEY(serverID, tnumber)
)
INSERT INTO wait
    (serverID, tnumber)
VALUES
    (10007, 12),
    (10008, 10),
    (10009, 8),
    (10010, 11);

CREATE TABLE seats(
    tnumber INT NOT NULL,
    receipt_ID INT NOT NULL,
    PRIMARY KEY(tnumber, receipt_ID)
)
INSERT INTO seats
    (tnumber, receipt_ID)
VALUES
    (12, 1223),
    (10, 1224),
    (8, 0001),
    (11, 0002);

CREATE TABLE pays(
    receipt_ID INT NOT NULL,
    rname varchar(64) NOT NULL,
    dollarAmount NUMERIC(5,2) NOT NULL
    PRIMARY KEY(receipt_ID, rname)
)
INSERT INTO pays
    (receipt_ID, rname, dollarAmount)
VALUES
    (1223, 'bob''s burgers', 20.45),
    (1224, 'bob''s burgers', 34.76),
    (0001, 'pizza''s pizzeria', 12.74),
    (0002, 'pizza''s pizzeria', 45.76);

CREATE TABLE employ(
    rname varchar(64) NOT NULL,
    staffID INT NOT NULL,
    PRIMARY KEY(rname, staffID)
)
INSERT INTO employ
    (rname, staffID)
VALUES
    ('bob''s burgers', 10002),
    ('bob''s burgers', 10003),
    ('bob''s burgers', 10007),
    ('bob''s burgers', 10008),
    ('pizza''s pizzeria', 10004),
    ('pizza''s pizzeria', 10005),
    ('pizza''s pizzeria', 10009),
    ('pizza''s pizzeria', 10010);

SELECT * FROM staff
SELECT * FROM restaurant
SELECT * FROM rowner
SELECT * FROM chef
SELECT * FROM rserver
SELECT * FROM tips
SELECT * FROM customer
SELECT * FROM rtable
SELECT * FROM training
SELECT * FROM has
SELECT * FROM owns
SELECT * FROM splits
SELECT * FROM share
SELECT * FROM cookFor
SELECT * FROM gives
SELECT * FROM wait
SELECT * FROM seats
SELECT * FROM pays
SELECT * FROM employ