### Some Test DATA: Tester Tim
### INSERT INTO users VALUE(000, "Tester Tim", 100, "testertim@gmail.com", "testertim");
### INSERT INTO memberships VALUE(0, 0, 10000);

# Sign up for GameGo membership

# Check GameGo Membership award points
SELECT points 
FROM users NATURAL JOIN memberships 
WHERE email = "testertim@gmail.com";

# Use GameGo Membership award points

# Modifying member / membership

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