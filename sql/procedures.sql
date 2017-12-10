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

#login admin
drop procedure if exists loginAdmin;
create procedure loginAdmin(IN newEmail varchar(50), IN newPassword varchar(50))
select admins.uid from admins
where admins.email = newEmail and admins.password = newPassword;

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

drop procedure if exists viewMemberRentals;
create procedure viewMemberRentals(IN newMid INT)
select * from rentals natural join games
where mid = newMid;

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
 
#admin_viewMemberships
drop procedure if exists admin_viewMemberships;
create procedure admin_viewMemberships()
select uid, mid, points, name, age, email
from users left outer join memberships using(uid)
order by mid desc, name asc;

#admin_searchMembershipsByEmail
drop procedure if exists admin_searchMembershipsByEmail;
create procedure admin_searchMembershipsByEmail(IN newEmail varchar(50))
select uid, mid, points, name, age, email
from users left outer join memberships using(uid)
where email LIKE CONCAT('%', newEmail, '%')
order by mid desc, name asc; 

#create admin
drop procedure if exists admin_addNewAdmin;
DELIMITER //
create procedure admin_addNewAdmin(IN newUid INT, IN newEmail varchar(50))
BEGIN
insert into admins
select uid, name, email, password
from users
where uid = newUid and email = newEmail;
select uid from admins
where admins.email = newEmail and admins.uid = newUid;
END //
DELIMITER ;

#count games group by author
drop procedure if exists countGamesByAuthor;
create procedure countGamesByAuthor()
select author, count(*) as count
from games
group by author
order by author asc;

drop procedure if exists countGamesByGenre;
create procedure countGamesByGenre()
select genre, count(*) as count
from games
group by genre
order by genre asc;

drop procedure if exists countGamesByConsole;
create procedure countGamesByConsole()
select console_type, count(*) as count
from games
group by console_type
order by console_type asc;

drop procedure if exists countGamesByRating;
create procedure countGamesByRating()
select rating, count(*) as count
from games
group by rating
order by rating asc;

#co-related query 
drop procedure if exists ratingGreaterThanAvgByGenre;
create procedure ratingGreaterThanAvgByGenre()
select *
from games g1
where g1.rating > (select avg(rating) from games g2 where g1.genre=g2.genre)
order by title asc;

drop procedure if exists ratingGreaterThanAvgByConsole;
create procedure ratingGreaterThanAvgByConsole()
select *
from games g1
where g1.rating > (select avg(rating) from games g2 where g1.console_type=g2.console_type)
order by title asc;

#get current transactions
drop procedure if exists viewTransactions;
create procedure viewTransactions()
select *
from transactions;

#get archived transactions
drop procedure if exists vewArchiveTransactions;
create procedure viewArchiveTransactions()
select * 
from archive_transactions;

#UNION between transaction and archive_transaction
drop procedure if exists viewAllTransactions;
create procedure viewAllTransactions()
select *
from transactions
union
select * 
from archive_transactions;

############ TRANSACTION SORTING ############

drop procedure if exists sortTransactionsByTID;
create procedure sortTransactionsByTID()
select *
from transactions
order by tid asc;

drop procedure if exists sortTransactionsByUID;
create procedure sortTransactionsByUID()
select *
from transactions
order by uid asc;

drop procedure if exists sortTransactionsByGID;
create procedure sortTransactionsByGID()
select *
from transactions
order by gid asc;

drop procedure if exists sortTransactionsByCID;
create procedure sortTransactionsByCID()
select *
from transactions
order by cid asc;

drop procedure if exists sortTransactionsByPRICE;
create procedure sortTransactionsByPRICE()
select *
from transactions
order by price asc;

drop procedure if exists sortTransactionsByDATE;
create procedure sortTransactionsByDATE()
select *
from transactions
order by transactions.date asc;

### Archive Sorting

drop procedure if exists sortArchiveTransactionsByTID;
create procedure sortArchiveTransactionsByTID()
select *
from archive_transactions
order by tid asc;

drop procedure if exists sortArchiveTransactionsByUID;
create procedure sortArchiveTransactionsByUID()
select *
from archive_transactions
order by uid asc;

drop procedure if exists sortArchiveTransactionsByGID;
create procedure sortArchiveTransactionsByGID()
select *
from archive_transactions
order by gid asc;

drop procedure if exists sortArchiveTransactionsByCID;
create procedure sortArchiveTransactionsByCID()
select *
from archive_transactions
order by cid asc;

drop procedure if exists sortArchiveTransactionsByPRICE;
create procedure sortArchiveTransactionsByPRICE()
select *
from archive_transactions
order by price asc;

drop procedure if exists sortArchiveTransactionsByDATE;
create procedure sortArchiveTransactionsByDATE()
select *
from archive_transactions
order by archive_transactions.date asc;

############ END TRANSACTION SORTING ############

############ TRANSACTION SEARCHING ############

drop procedure if exists searchAllTransactionsByTID;
DELIMITER //
create procedure searchAllTransactionsByTID(IN passedValue INT)
BEGIN
select *
from transactions
where tid = passedValue
union
select *
from archive_transactions
where tid = passedValue;
END //
DELIMITER ; 

drop procedure if exists searchAllTransactionsByUID;
DELIMITER //
create procedure searchAllTransactionsByUID(IN passedValue INT)
BEGIN
select *
from transactions
where uid = passedValue
union
select *
from archive_transactions
where uid = passedValue;
END //
DELIMITER ; 

drop procedure if exists searchAllTransactionsByGID;
DELIMITER //
create procedure searchAllTransactionsByGID(IN passedValue INT)
BEGIN
select *
from transactions
where gid = passedValue
union
select *
from archive_transactions
where gid = passedValue;
END //
DELIMITER ; 

drop procedure if exists searchAllTransactionsByCID;
DELIMITER //
create procedure searchAllTransactionsByCID(IN passedValue INT)
BEGIN
select *
from transactions
where cid = passedValue
union
select *
from archive_transactions
where cid = passedValue;
END //
DELIMITER ;

drop procedure if exists searchAllTransactionsByTWODATES;
DELIMITER //
create procedure searchAllTransactionsByTWODATES(IN date1 varchar(50), IN date2 varchar(50))
BEGIN
select *
from transactions
where date between DATE(date1) and DATE(date2)
union
select *
from archive_transactions
where date between DATE(date1) and DATE(date2);
END //
DELIMITER ;  

############ END TRANSACTION SEARCHING ############

drop procedure if exists sumOfTransactions;
create procedure sumOfTransactions()
select sum(price) as revenue
from transactions;

drop procedure if exists sumOfTransactionsByTwoDates;
create procedure sumOfTransactionsByTwoDates(IN date1 varchar(50), IN date2 varchar(50))
select sum(price) as revenue
from transactions
where date between DATE(date1) and DATE(date2);

drop procedure if exists archiveAllTransactions;
DELIMITER //
create procedure archiveAllTransactions()
BEGIN
insert into archive_transactions(tid, uid, gid, cid, price, date)
select tid, uid, gid, cid, price, date
from transactions;
delete
from transactions
where exists (select tid from archive_transactions);
END //
DELIMITER ;

drop procedure if exists archiveTransactionsByTwoDates;
DELIMITER //
create procedure archiveTransactionsByTwoDates(IN date1 varchar(50), IN date2 varchar(50))
BEGIN
insert into archive_transactions(tid, uid, gid, cid, price, date)
select tid, uid, gid, cid, price, date
from transactions
where date between DATE(date1) and DATE(date2);
delete
from transactions
where exists (select tid from archive_transactions);
END //
DELIMITER ;

drop procedure if exists viewPrizes;
create procedure viewPrizes()
select * from prizes
order by prize_name asc;

drop procedure if exists redeemPrize;
create procedure redeemPrize(IN memberID TIMESTAMP, IN prizeID TIMESTAMP)
update memberships
set points = points - (select prize_points from prizes where pid = prizeID)
where mid = memberID;

#view rented games
drop procedure if exists viewRentedGames;
create procedure viewRentedGames()
select * from rentals
order by rid;

#Search rentals by email
drop procedure if exists searchRentalsByEmail;
create procedure searchRentalsByEmail(IN newEmail varchar(50))
select * from rentals 
where rentals.email LIKE CONCAT('%', newEmail, '%');

#Search rentals by gid
drop procedure if exists searchRentalsByGid;
create procedure searchRentalsByGid(IN newGid INT)
select * from rentals 
where rentals.gid LIKE CONCAT('%', newGid, '%');

#view rented games with the renter's name
drop procedure if exists viewOverdueRentals;
create procedure viewOverdueRentals()
select rid, mid, gid, date_due, users.name
from rentals
inner join memberships on memberships.mid = rentals.mid
inner join users on users.uid = memberships.uid
where date_due < CURDATE();

#view games on sale by title
drop procedure if exists viewGamesOnSaleByTitle;
create procedure viewGamesOnSaleByTitle()
select * from games natural join sales
order by title asc;

#add new sale
drop procedure if exists admin_addNewSale;
DELIMITER //
create procedure admin_addNewSale(IN newGid INT, IN newDiscount DOUBLE)
BEGIN
insert into sales (gid, discount, originalPrice)
select gid, newDiscount, price as origPrice
from games
where gid = newGid;
update games
set price = price * newDiscount
where gid = oldGid;
END //
DELIMITER ;

#remove sale
drop procedure if exists admin_removeSale;
DELIMITER //
create procedure admin_removeSale(IN newGid INT)
BEGIN
update games 
set price = (select originalPrice from sales where gid = newGid) 
where gid = newGid;
delete from sales
where gid = newGid;
END //
DELIMITER ;

#update sale gid
drop procedure if exists admin_updateSaleGid;
create procedure admin_updateSaleGid(IN oldGid INT, IN newGid INT)
update sales
set gid = newGid
where gid = oldGid;

#update sale discount
drop procedure if exists admin_updateSaleDiscount;
create procedure admin_updateSaleDiscount(IN oldGid INT, IN newDiscount DOUBLE(6, 2))
update sales
set discount = newDiscount
where gid = oldGid;

#update sale orignal price
drop procedure if exists admin_updateSaleOriginalPrice;
create procedure admin_updateSaleOriginalPrice(IN oldGid INT, IN newOriginalPrice DOUBLE(6, 2))
update sales
set originalPrice = newOriginalPrice
where gid = oldGid;

# insert game into inventory
drop procedure if exists inventoryInsertGame;
DELIMITER //
create procedure inventoryInsertGame(IN newTitle varchar(50), IN newAuthor varchar(50), IN newGenre varchar(50),
IN newConsoleType varchar(50), IN newRating INT, IN newPrice DOUBLE(6, 2), IN newStock INT)
BEGIN
insert into games
values (null, newTitle, newAuthor, newGenre, newConsoleType, 0, newPrice, newStock);
select title, price from games
where games.title = newTitle and games.price = newPrice;
END //
DELIMITER ;

#remove game
drop procedure if exists inventoryDeleteGame;
create procedure inventoryDeleteGame(IN oldGid INT, IN oldTitle varchar(50))
delete from games
where gid = oldGid and title = oldTitle;

# insert game into inventory
drop procedure if exists inventoryInsertConsole;
DELIMITER //
create procedure inventoryInsertConsole(IN newName varchar(50), IN newPrice DOUBLE(6, 2), IN newStock INT)
BEGIN
insert into consoles
values (null, newName, newPrice, newStock);
select name, price from consoles
where consoles.name = newName and consoles.price = newPrice;
END //
DELIMITER ;

#remove console
drop procedure if exists inventoryDeleteConsole;
create procedure inventoryDeleteConsole(IN oldCid INT)
delete from consoles
where gid = oldCid;


#update game title
drop procedure if exists updateGameTitle;
create procedure updateGameTitle(IN oldGid INT, IN newTitle varchar(50))
update games
set title = newTitle
where gid = oldGid;

#update game author
drop procedure if exists updateGameAuthor;
create procedure updateGameAuthor(IN oldGid INT, IN newAuthor varchar(50))
update games
set author = newAuthor
where gid = oldGid;

#update game genre
drop procedure if exists updateGameGenre;
create procedure updateGameGenre(IN oldGid INT, IN newGenre varchar(50))
update games
set genre = newGenre
where gid = oldGid;

#update game console type
drop procedure if exists updateGameConsoleType;
create procedure updateGameConsoleType(IN oldGid INT, IN newConsoleType varchar(50))
update games
set console_type = newConsoleType
where gid = oldGid;

#update game rating
drop procedure if exists updateGameRating;
create procedure updateGameRating(IN oldGid INT, IN newRating INT)
update games
set rating = newRating
where gid = oldGid;

#update game price
drop procedure if exists updateGamePrice;
create procedure updateGamePrice(IN oldGid INT, IN newPrice DOUBLE(6, 2))
update games
set price = newPrice
where gid = oldGid;

#update game stock
drop procedure if exists updateGameStock;
create procedure updateGameStock(IN oldGid INT, IN newStock INT)
update games
set stock = newStock
where gid = oldGid;


#update console name
drop procedure if exists updateConsoleName;
create procedure updateConsoleName(IN oldName varchar(50), IN newName varchar(50))
update consoles
set title = newName
where name = oldName;

drop procedure if exists updateConsolePrice;
create procedure updateConsolePrice(IN oldName varchar(50), IN newPrice DOUBLE(6, 2))
update consoles
set price = newPrice
where name = oldName;

#update console name
drop procedure if exists updateConsoleStock;
create procedure updateConsoleStock(IN oldName varchar(50), IN newStock INT)
update consoles
set stock = newStock
where name = oldName;
