### see all triggers of gamego
-- select trigger_schema, trigger_name, action_statement from information_schema.triggers where information_schema.triggers.trigger_schema like '%gamego%';

USE GAMEGO;

DROP TRIGGER IF EXISTS afterInsertRentalDec;
DELIMITER //
CREATE TRIGGER afterInsertRentalDec
AFTER INSERT ON Rentals
FOR EACH ROW    
BEGIN
    UPDATE games
    SET stock = stock - 1
    WHERE games.gid = NEW.gid;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS afterDeleteRentalInc;
DELIMITER //
CREATE TRIGGER afterDeleteRentalInc
AFTER DELETE ON Rentals
FOR EACH ROW    
BEGIN
    UPDATE games
    SET stock = stock + 1
    WHERE games.gid = OLD.gid;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS afterInsertTransactionGameDec;
DELIMITER //
CREATE TRIGGER afterInsertTransactionGameDec
AFTER INSERT ON transactions
FOR EACH ROW	
BEGIN
    UPDATE games
    SET stock = stock - 1
    WHERE games.gid = NEW.gid;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS afterInsertTransactionConsoleDec;
DELIMITER //
CREATE TRIGGER afterInsertTransactionConsoleDec
AFTER INSERT ON transactions
FOR EACH ROW	
BEGIN
    UPDATE consoles
    SET stock = stock - 1
    WHERE consoles.cid = NEW.cid;
END//
DELIMITER ;   
