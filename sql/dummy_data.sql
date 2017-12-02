USE GAMEGO;

INSERT
INTO users
VALUES (2, "Tester Tim", 100, "testertim@gmail.com", "testertim");

INSERT
INTO users
VALUES (3, "R.O.B.", 32, "robot@gmail.com", "beepboop");

INSERT
INTO users
VALUES (4, "Charizard", 23, "lizardon@gmail.com", "flareblitz");

INSERT
INTO users
VALUES (5, "Sans", 2, "megalovania@gmail.com", "hotdogs");

INSERT
INTO games
VALUES (1, "Super Mario", "Nintendo", "Platformer", "NES", 5, 25.00, 8);

INSERT
INTO games
VALUES (2, "Super Smash Bros", "Nintendo", "Fighting", "Wii U", 5, 50.00, 10);

INSERT
INTO games
VALUES (3, "Bioshock", "2K Games", "First Person Shooter", "XBox", 3, 40.00, 7);

INSERT
INTO games
VALUES (4, "Bioshock", "2K Games", "First Person Shooter", "PS3", 4, 45.00, 7);

INSERT
INTO games
VALUES (5, "Injustice", "Warner Brothers", "Fighting", "PS3", 4, 50.00, 12);

INSERT
INTO games
VALUES (6, "Injustice 2", "Warner Brothers", "Fighting", "PS3", 3, 60.00, 19);

INSERT
INTO games
VALUES (7, "Pokemon Ultra Sun", "Nintendo", "RPG", "3DS", 4, 40.00, 16);

INSERT
INTO games
VALUES (8, "Pokemon Ultra Moon", "Nintendo", "RPG", "3DS", 4, 40.00, 16);

INSERT
INTO consoles
VALUES (1, "NES", 60.00, 10);

INSERT
INTO consoles
VALUES (2, "Wii U", 200.00, 20);

INSERT
INTO consoles
VALUES (3, "XBox", 180.00, 10);

INSERT
INTO consoles
VALUES (4, "PS3", 175.50, 7);

INSERT
INTO consoles
VALUES (5, "3DS", 150.00, 10);

INSERT
INTO consoles
VALUES (6, "Nintendo Switch", 299.99, 10);

INSERT
INTO sales(gid, discount)
SELECT gid, 10.00
FROM games
WHERE price > 49;

INSERT 
INTO memberships(uid, points) 
SELECT uid, 1010 
FROM users 
WHERE name = "R.O.B." or name = "Charizard";

INSERT
INTO transactions(uid, gid, price)
SELECT uid, gid, price
FROM users, games
WHERE name = "R.O.B." and author = "Nintendo";

INSERT
INTO rentals(mid, gid, date_due)
SELECT mid, gid, '2018-12-31 23:59:59'
FROM users natural join memberships, games
WHERE name = "Charizard" and NOT (author = "Nintendo");