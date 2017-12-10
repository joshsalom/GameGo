### Some Test DATA: Tester Tim
-- INSERT INTO users VALUE(000, "Tester Tim", 100, "testertim@gmail.com", "testertim");
-- INSERT INTO users VALUE(NULL, "Jester Jim", 50, "jesterjim@gmail.com", "jesterjim");
-- INSERT INTO memberships VALUE(0, 1, 10000);
-- INSERT INTO games VALUES (null, 'Star Wars', 'EA Games', 'action', 'ps4', 5, 59.99, 50);
-- INSERT INTO games VALUES (null, 'League Of Legends', 'Riot Games', 'strategy', 'nintendoswitch', 5, 89.99, 25);
-- INSERT INTO consoles VALUES (null, 'ps4', 299.99, 40);
-- INSERT INTO consoles VALUES (null, 'nintendoswitch', 699.99, 10);

### USEFUL FOR LATER
-- SELECT timestampadd(day, 7, (SELECT date_rented FROM rentals WHERE rid = 1));

# Sign up for GameGo membership
INSERT 
INTO memberships(uid, points) 
SELECT uid, 1000 
FROM users 
WHERE name = "Tester Tim";

# Check GameGo Membership award points
SELECT points 
FROM users NATURAL JOIN memberships 
WHERE email = "testertim@gmail.com";

# Increment memebership points
UPDATE memberships
SET points = points + 100
WHERE mid = 0;

#Decrement membership award points ( Use GameGo Membership award points )
UPDATE memberships
SET points = points - 100
WHERE mid = 0;

# End membership
DELETE
FROM memberships 
WHERE email = "testertim@gmail.com";

DELETE
FROM memberships 
WHERE name = "Tester Tim";

DELETE
FROM memberships 
WHERE mid = 0;

# Search up member
SELECT * 
FROM users NATURAL JOIN memberships 
WHERE email = "testertim@gmail.com";

SELECT * 
FROM users NATURAL JOIN memberships 
WHERE name = "Tester Tim";

SELECT * 
FROM users NATURAL JOIN memberships 
WHERE mid = "0";


#Buy item
INSERT INTO transactions VALUES (null, 2, 1, null, (SELECT price FROM games WHERE gid=1), null);

INSERT INTO transactions VALUES (null, 2, null, 2, (SELECT price FROM consoles WHERE cid=2), null);


#Rent/Loan item
INSERT INTO rentals VALUES (null, 1, 2, null, null);


#Return rented item
delete FROM rentals WHERE mid=1 and gid=2;

#Check stock count of item
SELECT stock FROM games WHERE gid=1;
SELECT stock FROM consoles WHERE cid=1;

#Search by title genre, rating, price, console_type
SELECT * FROM games WHERE genre = 'action';
SELECT * FROM games WHERE rating > 3;
SELECT * FROM games WHERE price < 70;
SELECT * FROM games WHERE console_type = 'ps4';

SELECT * FROM consoles WHERE price < 700;
SELECT * FROM consoles WHERE stock > 0;

#Search overdue items
SELECT * FROM rentals WHERE timestampdiff(day, (SELECT date_due FROM rentals), (SELECT utc_timestamp())) > 7;

#Count preorders
SELECT COUNT(*)
FROM Preorders;

#update a title, genre, rating, price, etc
UPDATE Games
SET title = 'this title'
WHERE gid = 0;

UPDATE Games
SET author = 'this guy'
WHERE gid = 0;

UPDATE Games
SET genre = FPS
WHERE gid = 0;

UPDATE Games
SET rating = 5
WHERE gid = 0;

UPDATE Games
SET price = 1.00
WHERE gid = 0;

UPDATE Games
SET stock = 1
WHERE gid = 0;


UPDATE Games
SET title = 'this title', 
author = 'this guy', 
genre = FPS,
rating = 5,
price = 1.00,
stock = 1
WHERE gid = 0;

#find item's rental history
SELECT *
FROM Rentals
WHERE gid = 1;
#check specific person's purchase history
SELECT *
FROM Transactions
WHERE uid = 1;

#add/remove item on sale
INSERT INTO Sales(gid, discount)
VALUES(1, .25);

#promote user to admin
INSERT INTO Admins(uid, name, email, password)
SELECT uid, name, email, password
FROM Users
WHERE uid = 1;