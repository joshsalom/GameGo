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

