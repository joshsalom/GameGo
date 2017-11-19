CREATE TRIGGER trgRentalInsert
AFTER INSERT ON Rentals
FOR EACH ROW    
    UPDATE games
    SET stock = stock - 1
    WHERE games.gid = NEW.gid;
    
CREATE TRIGGER trgTransactionGame
AFTER INSERT ON transactions
FOR EACH ROW	
    UPDATE games
    SET stock = stock - 1
    WHERE games.gid = NEW.gid;

CREATE TRIGGER trgTransactionConsole
AFTER INSERT ON transactions
FOR EACH ROW	
    UPDATE consoles
    SET stock = stock - 1
    WHERE consoles.cid = NEW.cid;
    
