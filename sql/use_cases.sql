### Some Test DATA: Tester Tim
### INSERT INTO users VALUE(000, "Tester Tim", 100, "testertim@gmail.com", "testertim");
### INSERT INTO memberships VALUE(0, 0, 10000);

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

#Rent/Loan item

#Return item

#Check stock count of item

#Search by title genre, rating, price, console_type

#Search overdue items


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