# Oracle Procedure 작성 방법

`Procedure` 란, 데이터베이스 내에서 특정 작업을 수행하는 코드 블록이다.  
여러 작업을 일괄 처리할 때 사용한다.  

## Procedure 전체 틀

```oracle
-- 1. 프로시저 선언
CREATE OR REPLACE PROCEDURE 프로시저_이름( 

-- 2. 매개변수 선언
     매개변수 [ IN || OUT ] 타입; 
)
IS
-- 3. 변수 선언
NAME VARCHAR2(10) := 'TEST'  -- := 연산자로 변수에 값을 할당 

-- 4. 프로시저 본문
BEGIN
[EXCEPTION 예외처리]
END;
```

## Procedure 본문

### SELECT INTO

```oracle
DECLARE -- 변수선언
    my_variable NUMBER;
BEGIN
    SELECT COUNT(*) INTO my_variable
    FROM employees
    WHERE department_id = 10;
    DBMS_OUTPUT.PUT_LINE('Number of employees: ' || my_variable);
END;
```

1. 변수를 선언 후
2. employees 테이블에서 조건에 해당하는 컬럼의 개수를 집계 후
3. my_variable 변수에 할당
4. `DBMS_OUTPUT.PUT_LINE` 을 사용해서 결과 출력

### IF THEN ELSE

```oracle
DECLARE
    my_variable NUMBER := 5;
BEGIN
    IF my_variable < 10 THEN --my_variable 변수가 10보다 작으면 'Value is less than 10'을 출력
        DBMS_OUTPUT.PUT_LINE('Value is less than 10');
    ELSIF my_variable = 10 THEN  --my_variable 변수가 10이면 'Value is 10'을 출력
        DBMS_OUTPUT.PUT_LINE('Value is 10');
    ELSE --그 외의 경우에는 'Value is greater than 10'을 출력
        DBMS_OUTPUT.PUT_LINE('Value is greater than 10');
    END IF;
END;
```

1. 변수 선언 후
2. `IF-THEN` / `ELSEIF-THEN` / `ELSE` 조건에 따른 처리를 마친 뒤
3. `END IF` 로 조건 블럭 종료

### LOOP

#### 기본 루프

```oracle
DECLARE
    counter NUMBER := 1;
BEGIN
    LOOP
        DBMS_OUTPUT.PUT_LINE('Counter value: ' || counter);
        counter := counter + 1;
        EXIT WHEN counter > 5;  -- counter 변수가 5보다 커지면 루프를 종료
    END LOOP;
END;
```

1. 기본 LOOP는 무한 루프
2. `EXIT WHEN` 으로 종료 조건 명시 필요
3. `END LOOP` 로 루프 종료 명시 필요

#### WHILE LOOP

```oracle
DECLARE
    counter NUMBER := 1;
BEGIN
    WHILE counter <= 5 LOOP  --counter 변수가 5보다 작거나 같을 때까지 루프를 실행
        DBMS_OUTPUT.PUT_LINE('Counter value: ' || counter);
        counter := counter + 1;
    END LOOP;
END;
```

1. `EXIT WHEN` 대신 `WHILE [Condition] LOOP` 로 종료 조건 명시
2. `END LOOP` 로 루프 종료 명시 필요

#### FOR LOOP

```oracle
BEGIN
    FOR counter IN 1..5 LOOP
        DBMS_OUTPUT.PUT_LINE('Counter value: ' || counter);
    END LOOP;
END;
```

1. `[Start]..[End]` 문으로 범위 지정
2. `FOR [Iterator] IN [Range] LOOP` 문으로 루프 시작
3. `END LOOP` 로 루프 종료 명시 필요

### CURSOR

```oracle
-- department_id가 10인 employees 테이블의 데이터를 조회하고, 
-- 각 직원의 ID와 이름을 출력하기
DECLARE
    CURSOR emp_cursor IS
        SELECT employee_id, first_name, last_name
        FROM employees
        WHERE department_id = 10;
    emp_record emp_cursor%ROWTYPE; -- %ROWTYPE : 특정 테이블이나 커서의 행 구조와 동일한 레코드 변수 선언
BEGIN
    OPEN emp_cursor;  -- 커서 열기
    LOOP
        FETCH emp_cursor INTO emp_record; -- 커서에서 행을 가져와 emp_record에 저장
        EXIT WHEN emp_cursor%NOTFOUND;  -- 커서의 모든 행을 처리하면 루프 종료
        DBMS_OUTPUT.PUT_LINE('Employee ID: ' || emp_record.employee_id || ', Name: ' || emp_record.first_name || ' ' || emp_record.last_name);
    END LOOP;
    CLOSE emp_cursor; -- 커서 닫기
END;
```
0. Cursor는 SELECT 문의 결과를 한 행씩 처리할 수 있도록 해줌  
1. `CURSOR [Variable] IS` 로 변수 지정  
2. 커서에 넣을 데이터를 `SELECT` 문으로 받아옴  
3. `[Variable]%ROWTYPE` 으로 특정 테이블이나 커서의 행 구조와 동일한 레코드 변수 선언  
    - 즉, `emp_cursor`와 행 구조가 동일한 `emp_record` 변수 선언  
4. 커서를 `OPEN` 으로 열어서  
5. `LOOP`를 태운 다음  
6. `FETCH [Cursor Variable] INTO [Target Variable]` 로 데이터를 가져와서 저장함  
7. `[Cursor Variable]%NOTFOUND으로 종료 조건 명시`  
8.  `END LOOP` 로 루프 종료 명시 필요  
9. `CLOSE`로 커서 닫기  

### EXCEPTION

```oracle
DECLARE
    my_variable NUMBER;
BEGIN
    my_variable := 1 / 0; -- 오류 발생
EXCEPTION  --EXCEPTION 블록에서 오류를 처리
    WHEN ZERO_DIVIDE THEN  -- ZERO_DIVIDE 예외가 발생하면 아래 메시지를 출력
        DBMS_OUTPUT.PUT_LINE('Division by zero is not allowed');
    WHEN OTHERS THEN  -- 다른 예외 발생시 아래 메시지 출력
        DBMS_OUTPUT.PUT_LINE('An unexpected error occurred');
END;
```

1. `EXCEPTION` 문에서
2. `WHEN-THEN` / `WHEN-OTHERS-THEN` 문으로 예외 분기 처리
