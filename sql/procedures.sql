-- show procedure status where db = 'gamego';
-- source procedures.sql
USE GAMEGO;
# Create user
drop procedure if exists createUser;
DELIMITER //
create procedure createUser(IN newName varchar(50), IN newAge INT, IN newEmail varchar(50), IN newPassword varchar(50))
BEGIN
insert into users
values (null, newName, newAge, newEmail, newPassword);
select uid from users
where users.email = newEmail and users.password = newPassword;
END //
DELIMITER ;

#login user
drop procedure if exists loginUser;
create procedure loginUser(IN newEmail varchar(50), IN newPassword varchar(50))
select users.uid from users
where users.email = newEmail and users.password = newPassword;

# Sign up for GameGo membership
DROP PROCEDURE IF EXISTS createMember;

DELIMITER //
CREATE PROCEDURE createMember(IN mEmail VARCHAR(50), IN newPassword varchar(50))
BEGIN
INSERT INTO memberships(uid, points) 
SELECT uid, 1000 
FROM users 
WHERE email = mEmail and password = newPassword;

SELECT mid
from memberships natural join users
where email = mEmail and password = newPassword;
END //
DELIMITER ;

# Check GameGo Membership award points
DROP PROCEDURE IF EXISTS getMemberPoints;

CREATE PROCEDURE getMemberPoints(IN memberID INT)
SELECT points
FROM memberships
WHERE mid = memberID;

# Increment memebership points
DROP PROCEDURE IF EXISTS addMemberPoints;

CREATE PROCEDURE addMemberPoints(IN p INT, IN mEmail VARCHAR(50))
UPDATE memberships NATURAL JOIN users
SET points = points + p
WHERE email = mEmail;

#Decrement membership award points ( Use GameGo Membership award points )
DROP PROCEDURE IF EXISTS subMemberPoints;

CREATE PROCEDURE subMemberPoints(IN p INT, IN mEmail VARCHAR(50))
UPDATE memberships NATURAL JOIN users
SET points = points - p
WHERE email = mEmail;

# End membership
DROP PROCEDURE IF EXISTS endMembership;

CREATE PROCEDURE endMembership(IN memberID INT)
DELETE
FROM memberships
WHERE mid = memberID;


# Search up member
DROP PROCEDURE IF EXISTS searchMember;

CREATE PROCEDURE searchMember(IN mEmail VARCHAR(50))
SELECT * 
FROM users NATURAL JOIN memberships 
WHERE email = mEmail;

#get member by uid
drop procedure if exists getMemberId;
create procedure getMemberId(IN newUid INT)
select * from memberships
where uid = newUid;

#Buy game
drop procedure if exists buyGame;
DELIMITER //
create procedure buyGame(IN newUid INT, IN newGid INT)
BEGIN
insert into transactions 
values (null, newUid, newGid, null, -999.99, null);
update transactions t1
set price = (select price from games where games.gid=newGid)
where t1.uid=newUid and t1.gid=newGid and price = -999.99;
select * from games
where gid = newGid;
END //
DELIMITER ;

#Buy console
drop procedure if exists buyConsole;
DELIMITER //
create procedure buyConsole(IN newUid INT, IN newCid INT)
BEGIN
insert into transactions 
values (null, newUid, null, newCid, -999.99, null);
update transactions t1
set price = (select price from consoles where consoles.cid=newCid)
where t1.uid=newUid and t1.cid=newCid and price = -999.99;
select * from consoles
where cid = newCid;
END //
DELIMITER ;


#Rent/Loan item
drop procedure if exists rentGame;
DELIMITER //
create procedure rentGame(IN newMid INT, IN newGid INT)
BEGIN
insert into rentals 
values (null, newMid, newGid, -999.99, null, null);
update rentals r1
set price = (select price from games where games.gid=newGid)
where r1.mid=newMid and r1.gid=newGid and price=-999.99;
select * from games
where gid = newGid;
END //
DELIMITER ;

#Return rented item
drop procedure if exists returnGameRental;
DELIMITER //
create procedure returnGameRental(IN newMid INT, IN newGid INT)
BEGIN
delete from rentals 
where rentals.mid= newMid and rentals.gid=newGid;
select * from games
where gid = newGid;
END //
DELIMITER ;

#Check stock count of game
drop procedure if exists getGameStock;
create procedure getGameStock(IN newTitle varchar(50), IN newAuthor varchar(50))
select stock from games 
where games.title=newTitle and games.author=newAuthor;

#Check stock count of game
drop procedure if exists getConsoleStock;
create procedure getConsoleStock(IN newName varchar(50))
select stock from consoles 
where games.name=newName;

#View games by ascending order of title
drop procedure if exists viewGamesByTitle;
create procedure viewGamesByTitle()
select * from games
order by title asc;

#View games by ascending author
drop procedure if exists viewGamesByAuthor;
create procedure viewGamesByAuthor()
select * from games
order by author asc;

#View games by ascending genre
drop procedure if exists viewGamesByGenre;
create procedure viewGamesByGenre()
select * from games
order by genre asc;

#view games by ascending console
drop procedure if exists viewGamesByConsole;
create procedure viewGamesByConsole()
select * from games
order by console_type asc;

#view games by rating
drop procedure if exists viewGamesByRating;
create procedure viewGamesByRating()
select * from games
order by rating desc;

#view games by price
drop procedure if exists viewGamesByPrice;
create procedure viewGamesByPrice()
select * from games
order by price asc;

#Search by title
drop procedure if exists searchGamesByTitle;
create procedure searchGamesByTitle(IN newTitle varchar(50))
select * from games 
where games.title LIKE CONCAT('%', newTitle, '%');

#Search by author
drop procedure if exists searchGamesByAuthor;
create procedure searchGamesByAuthor(IN newAuthor varchar(50))
select * from games 
where games.author LIKE CONCAT('%', newAuthor, '%');

#Search by genre
drop procedure if exists searchGamesByGenre;
create procedure searchGamesByGenre(IN newGenre varchar(50))
select * from games 
where games.genre LIKE CONCAT('%', newGenre, '%');

#Search by rating
drop procedure if exists searchGamesByRatingGreaterThan;
create procedure searchGamesByRatingGreaterThan(IN newRating INT)
select * from games 
where games.rating > newRating;

#Search by rating
drop procedure if exists searchGamesByRatingLessThan;
create procedure searchGamesByRatingLessThan(IN newRating INT)
select * from games 
where games.rating < newRating;

#Search by price less than input value
drop procedure if exists searchGamesByPriceLessThan;
create procedure searchGamesByPriceLessThan(IN newPrice DOUBLE(6, 2))
select * from games 
where games.price < newPrice;

#Search by price greater than input value
drop procedure if exists searchGamesByPriceGreaterThan;
create procedure searchGamesByPriceGreaterThan(IN newPrice DOUBLE(6, 2))
select * from games 
where games.price > newPrice;

#search by console_type
drop procedure if exists searchGamesByConsole;
create procedure searchGamesByConsole(IN newConsole varchar(50))
select * from games 
where games.console_type LIKE CONCAT('%', newConsole, '%');

#Search console by price less than input value
drop procedure if exists searchConsolesByPriceLessThan;
create procedure searchConsolesByPriceLessThan(IN newPrice DOUBLE(6, 2))
select * from consoles 
where consoles.price < newPrice;

#Search console by price greater than input value
drop procedure if exists searchConsolesByPriceGreaterThan;
create procedure searchConsolesByPriceGreaterThan(IN newPrice DOUBLE(6, 2))
select * from consoles 
where consoles.price > newPrice;

#Search overdue items
drop procedure if exists getOverdueRentals;
create procedure getOverdueRentals(IN cutoffDays INT)
select * from rentals 
where timestampdiff(day, (select rentals.date_due from rentals), (select utc_timestamp())) > cutoffDays;

#view transactions by uid
drop procedure if exists viewGameTransactionsById;
create procedure viewGameTransactionsById(IN newId INT)
select * from transactions natural join games
where uid = newId
order by date asc;

#view transactions by uid
drop procedure if exists viewConsoleTransactionsById;
create procedure viewConsoleTransactionsById(IN newId INT)
select * from transactions natural join consoles
where uid = newId
order by date asc;

#View consoles by console's name
drop procedure if exists viewConsolesByName;
create procedure viewConsolesByName()
select * from consoles
order by name asc;

#view consoles by price
drop procedure if exists viewConsolesByPrice;
create procedure viewConsolesByPrice()
select * from consoles
order by price asc;

#view games on sale
drop procedure if exists viewGamesOnSale;
create procedure viewGamesOnSale()
select * from games natural join sales
order by price asc;