-- show procedure status where db = 'gamego';
-- source procedures.sql
USE GAMEGO;

# Sign up for GameGo membership
DROP PROCEDURE IF EXISTS createMember;

CREATE PROCEDURE createMember(IN mEmail VARCHAR(50))
INSERT 
INTO memberships(uid, points) 
SELECT uid, 1000 
FROM users 
WHERE email = mEmail;

# Check GameGo Membership award points
DROP PROCEDURE IF EXISTS getMemberPoints;

CREATE PROCEDURE getMemberPoints(IN mEmail VARCHAR(50))
SELECT points
FROM users NATURAL JOIN memberships
WHERE email = mEmail;

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
END //
DELIMITER ;


#Rent/Loan item
drop procedure if exists rentGame;
create procedure rentGame(IN newMid INT, IN newGid INT)
insert into rentals values (null, newMid, newGid, null, null);


#Return rented item
drop procedure if exists returnRentGame;
create procedure returnRentGame(IN newMid INT, IN newGid INT)
delete from rentals 
where rentals.mid= newMid and rentals.gid=newGid;

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

#Search by title
drop procedure if exists searchGamesByTitle;
create procedure searchGamesByTitle(IN newTitle varchar(50))
select * from games 
where games.title = newTitle;

#Search by author
drop procedure if exists searchGamesByAuthor;
create procedure searchGamesByAuthor(IN newAuthor varchar(50))
select * from games 
where games.author = newAuthor;

#Search by genre
drop procedure if exists searchGamesByGenre;
create procedure searchGamesByGenre(IN newGenre varchar(50))
select * from games 
where games.genre = newGenre;

#Search by rating
drop procedure if exists searchGamesByRating;
create procedure searchGamesByRating(IN newRating INT)
select * from games 
where games.rating > newRating;

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
drop procedure if exists searchGamesByConsoleType;
create procedure searchGamesByConsoleType(IN newConsole varchar(50))
select * from games 
where games.console_type = newConsole;

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