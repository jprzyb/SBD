-- PROJEKT PL/SQL | ETAP II
-- Jakub_Przybylski_s24512



--1. 1 procedura z parametrami wyjściowymi(2p)
CREATE OR ALTER PROCEDURE my_procedure
    @n_date DATE,
    @o_date DATE,
AS
BEGIN

	DECLARE @Results TABLE (
        date1 DATE,
        date2 DATE
        comp VARCHAR2(10)
    );

	IF @n_date < o_date
		INSERT INTO @Results (date1, date2, comp) VALUES (n_date, o_date , 'LOWER')
	ELSIF @n_date > o_date
		INSERT INTO @Results (date1, date2, comp) VALUES (n_date, o_date , 'BIGGER')
	ELSE
		INSERT INTO @Results (date1, date2, comp) VALUES (n_date, o_date , 'EQUAL')
		
	SELECT date1, date2, comp FROM @Results;
END;


-- DECLARE #Results TABLE (
--     date1 DATE,
--     date2 DATE,
--     comp VARCHAR(10)
-- );
-- DECLARE @n_date DATE;
-- DECLARE @o_date DATE;
-- 
-- SET @n_date = '2023-06-01';
-- SET @o_date = '2023-06-02';
-- EXEC my_procedure @n_date, @o_date;
-- INSERT INTO @Results
-- EXEC my_procedure @n_date, @o_date;
-- 
-- SET @n_date = '2023-06-02';
-- SET @o_date = '2023-06-01';
-- EXEC my_procedure @n_date, @o_date;
-- INSERT INTO @Results
-- EXEC my_procedure @n_date, @o_date;
-- 
-- SET @n_date = '2023-06-01';
-- SET @o_date = '2023-06-01';
-- EXEC my_procedure @n_date, @o_date;
-- INSERT INTO @Results
-- EXEC my_procedure @n_date, @o_date;
-- 
-- SELECT * FROM #Results;
-- DROP TABLE #Results;

--2. 1 wyzwalacz(2p)
CREATE TRIGGER my_trigger
INSTEAD OF INSERT
ON Apartament
AS
BEGIN
    IF EXISTS (SELECT 1 FROM inserted i JOIN Apartament a ON i.apartament_number = a.apartament_number)
    BEGIN
        RAISERROR ('Apartament with the same number already exists.', 16, 1);
        ROLLBACK TRANSACTION;
    END
    ELSE
    BEGIN
        INSERT INTO Apartament (apartament_number, other_columns)
        SELECT apartament_number, other_columns
        FROM inserted;
    END
END;



--3. 1 funkcja TABLE lub SKALAR(2p)
CREATE FUNCTION GetRentAmount(@rentId INT)
RETURNS DECIMAL(10,2)
AS
BEGIN
    DECLARE @amount DECIMAL(10,2);
    SELECT @amount = Amount
    FROM Rent
    WHERE ID = @rentId;

    RETURN @amount;
END;

-- DECLARE @rentAmount DECIMAL(10,2);
-- SET @rentAmount = dbo.GetRentAmount(123);

-- SELECT @rentAmount AS RentAmount;
