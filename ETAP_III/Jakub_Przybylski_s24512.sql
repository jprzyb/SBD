-- PROJEKT PL/SQL | ETAP III
-- Jakub_Przybylski_s24512



-- 1. 1 blok PL/SQL z kursorem(2p) + EXCEPTION------------------------------------------------------------------------------------------------------------------------------------------------------------
DECLARE
  CURSOR my_cursor IS
    SELECT ID, BLOCK FROM APARTMENT WHERE ID >= 170;
  v_id APARTMENT.ID%TYPE;
  v_block APARTMENT.BLOCK%TYPE;
BEGIN
  OPEN my_cursor;
  LOOP
    FETCH my_cursor INTO v_id, v_block;
    EXIT WHEN my_cursor%NOTFOUND;

    UPDATE APARTMENT SET ID=v_id*10 WHERE ID=v_id;

    DBMS_OUTPUT.PUT_LINE('ID: ' || v_id || ', BLOCK: ' || v_block);
  END LOOP;
  CLOSE my_cursor;

  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20001, 'NO DATA FOUND');
END;

SELECT ID, BLOCK FROM APARTMENT WHERE ID >= 170;



--2. 1 procedura zwracająca wartości  + EXCEPTION---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE my_procedure (n_date DATE , v_date DATE , v_result OUT INTEGER)
AS
BEGIN
  IF n_date < v_date THEN
    v_result:=1;
  ELSIF n_date = v_date THEN
    v_result:=0;
  ELSIF n_date > v_date THEN
    RAISE_APPLICATION_ERROR(-20001, 'NO DATA TO RETURN');
  ELSE
      v_result:=-1;
  END IF;
END;



-- 3. 1 wyzwalacz FOR EACH ROW (2p)  + EXCEPTION + używanie procedury w triggerze-------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER my_trigger
AFTER UPDATE OR INSERT
ON DATES
FOR EACH ROW
DECLARE
v_val INTEGER;
BEGIN
    -- checking DATE validation
    DECLARE
        v_result INTEGER;
    BEGIN
        my_procedure(TO_DATE(:NEW.DATES , 'YYYY-MM-DD'), TO_DATE(SYSDATE , 'YYYY-MM-DD') ,  v_result);
        v_val:=v_result;
    END;

    IF v_val>=0 THEN
        DBMS_OUTPUT.PUT_LINE('Data valid. v_val: ' || v_val);
    ELSE
        RAISE_APPLICATION_ERROR(-20001, 'DATA INVALID. DATE MUST BE <= CURRENT DATE');
    END IF;
END;

INSERT INTO Dates(ID , Dates) VALUES (9999, TO_DATE(SYSDATE , 'YYYY-MM-DD'));
INSERT INTO Dates(ID , Dates) VALUES (9998, '2022-12-31');
INSERT INTO Dates(ID , Dates) VALUES (9997, '2045-12-31');

UPDATE DATES SET DATES=TO_DATE(SYSDATE , 'YYYY-MM-DD') WHERE ID =9999;
UPDATE DATES SET DATES=TO_DATE('2045-12-31' , 'YYYY-MM-DD') WHERE ID =9998;
UPDATE DATES SET DATES=TO_DATE('2022-12-31' , 'YYYY-MM-DD') WHERE ID =9997;




-- 4. 1 funkcja (2p) + EXCEPTION--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION my_function(worker_id NUMBER) RETURN NUMBER
AS
    boss_id NUMBER;
    boss_sal NUMBER;
BEGIN

    SELECT w.SALARY INTO boss_sal FROM WORKERS w
    INNER JOIN WORKERS boss ON boss.ID = w.BOSS_ID
    WHERE w.ID = worker_id;
    RETURN boss_sal;
      EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001, 'NO BOSS OR BOSS SALARY FOUND FOR worker_id=' || worker_id);
    RETURN -1;
 END;

--UPDATE WORKERS SET ID=999 WHERE ID=8;

DECLARE
    test_id NUMBER;
    test_sal NUMBER;
BEGIN
    test_id:=9;
    DBMS_OUTPUT.PUT_LINE('BOSS SALARY for worker_id=' || test_id ||' equals: ' ||my_function(test_id));
END;