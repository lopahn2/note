# MySQL Procedure 작성 방법

`Procedure` 란, 데이터베이스 내에서 특정 작업을 수행하는 코드 블록이다.  
여러 작업을 일괄 처리할 때 사용한다.  

여러 쿼리를 한 번에 수행하는 것이 중점이다.

## Procedure 전체 틀

### Query Base

```mysql
DROP PROCEDURE IF EXISTS TEST_PROC; /* 기존에 프로시저가 존재하면 DROP */

DELIMITER $$ /* 프로시저 작성이 완료되지 않았음에도 SQL문이 실행되는 것을 막기 위해 사용된다. */
CREATE PROCEDURE 'TEST_PROC' (
    -- 파라미터 선언
    PARAM_NAME VARCHAR(20),
    PARAM_AGE INT
)
BEGIN
    -- 변수 선언
    DECLARE PARAM_NUM INTEGER;
    
    -- 쿼리문1
    SELECT COUNT(*) + 1
    	INTO PARAM_NUM
        FROM table1;
        
    -- 쿼리문2
    INSERT INTO table1(total_count, user_name, user_age) VALUES(PARAM_NUM, PARAM_NAME, PARAM_AGE);
END $$
DELIMITER ;

CALL TEST_PROC('이름', 21);
```

### Loop Base

```mysql
DELIMITER $$
CREATE PROCEDURE 'TEST_PROC2'(
    IN loopCount1 INT,     -- input : 10
    IN loopCount2 INT,     -- input : 20
    OUT rst1 INT,
    OUT rst2 INT,
    INOUT rst3 INT
)
BEGIN
    DECLARE NUM1 INTEGER DEFAULT 0;    -- DEFAULT : 초기값 설정
    DECLARE NUM2 INTEGER DEFAULT 0;
    DECLARE NUM3 INTEGER DEFAULT 0;
    
    WHILE NUM1 < loopCount1 DO           -- NUM1은 0~9까지 10번반복
        WHILE NUM2 < loopCount2 DO       -- NUM2는 0~19까지 20번반복
            SET NUM3 = NUM3 + 1;
            SET NUM2 = NUM2 + 1;
        END WHILE;                     -- NUM2가 19가 되면 나옴
        
        SET NUM1 = NUM1 + 1;
        SET NUM2 = 0;
    END WHILE;
    
    SET rst1 = NUM1;
    SET rst2 = NUM3;
    SET rst3 = rst1 + rst2 + rst3;
END $$
DELIMITER ;
```

#### Parameter

- IN : 프로시저 호출 시 값을 전달받아 내부에서 사용되며 실행 중에도 원본 값은 변경되지 않음.
- OUT : 프로시저 실행 후 결과를 반환하는데 사용하며 호출자에게 전달됨. 초기값은 NULL이며 프로시저 내부에서 값 할당 필요
- INOUT : IN과 OUT의 역할을 모두 수행 가능

#### 조건문 예시

```mysql
IF total >= 95 AND total <= 100 THEN
    SET grade = 'A+';
ELSE
    IF total >= 90 THEN
        SET grade = 'A';
    END IF;
END IF;
```

```mysql
CASE NUM
    WHEN 'Apple' THEN
        SET 'result_Str' = 'apple_cookie';
    WHEN 'Banana' THEN
        SET 'result_Str' = 'banana_cookie';
    ELSE
        SET 'result_Str' = 'orange_cookie';
END CASE;
```
