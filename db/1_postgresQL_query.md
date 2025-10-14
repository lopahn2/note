
# 📘 PostgreSQL 실무 예제 학습 자료

현업에서 자주 사용하는 PostgreSQL 문법을 테이블 생성부터 SELECT, INSERT, UPDATE, DELETE, JOIN까지 폭넓게 학습할 수 있는 실습 예제입니다. 주제: **회사 인사/근무 관리 시스템**

---

## 🏗️ 1. 테이블 생성 SQL

```sql
-- 부서 테이블
CREATE TABLE departments (
    dept_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

-- 직원 테이블
CREATE TABLE employees (
    emp_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    hire_date DATE NOT NULL,
    salary INTEGER NOT NULL,
    dept_id INT REFERENCES departments(dept_id)
);

-- 프로젝트 테이블
CREATE TABLE projects (
    project_id SERIAL PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE
);

-- 직원-프로젝트 매핑 테이블
CREATE TABLE employee_projects (
    emp_id INT REFERENCES employees(emp_id),
    project_id INT REFERENCES projects(project_id),
    role VARCHAR(50),
    PRIMARY KEY (emp_id, project_id)
);
```

---

## 📥 2. INSERT 문 예제

### ✅ Q1. 부서, 직원, 프로젝트, 매핑 데이터를 추가하라.

```sql
-- 부서
INSERT INTO departments (name, location) VALUES
('개발팀', '서울'),
('마케팅팀', '부산'),
('인사팀', '대전');

-- 직원
INSERT INTO employees (name, email, hire_date, salary, dept_id) VALUES
('김철수', 'kim@example.com', '2022-01-10', 5000000, 1),
('이영희', 'lee@example.com', '2021-03-15', 6000000, 1),
('박민수', 'park@example.com', '2023-06-01', 4500000, 2),
('최지우', 'choi@example.com', '2020-07-07', 5500000, 3),
('정우성', 'jung@example.com', '2019-10-20', 7000000, 1);

-- 프로젝트
INSERT INTO projects (project_name, start_date, end_date) VALUES
('웹사이트 리뉴얼', '2024-01-01', '2024-06-30'),
('신제품 런칭', '2024-02-01', NULL);

-- 프로젝트 참여
INSERT INTO employee_projects (emp_id, project_id, role) VALUES
(1, 1, '프론트엔드'),
(2, 1, '백엔드'),
(3, 2, '디자이너'),
(4, 2, '기획자'),
(5, 1, 'PM');
```

---

## 🔍 3. SELECT 문 예제

### ✅ Q1. 모든 직원의 이름과 이메일을 조회하라.
```sql
SELECT name, email FROM employees;
```

### ✅ Q2. 연봉이 500만원 이상인 직원을 조회하라.
```sql
SELECT * FROM employees WHERE salary >= 5000000;
```

### ✅ Q3. 최근 입사한 3명을 조회하라.
```sql
SELECT * FROM employees ORDER BY hire_date DESC LIMIT 3;
```

### ✅ Q4. 부서별 평균 연봉을 조회하라.
```sql
SELECT d.name AS dept_name, AVG(e.salary) AS avg_salary
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.name;
```

---

## ✏️ 4. UPDATE 문 예제

### ✅ Q1. ‘박민수’의 연봉을 4800000으로 수정하라.
```sql
UPDATE employees
SET salary = 4800000
WHERE name = '박민수';
```

### ✅ Q2. 인사팀 직원들의 연봉을 10% 인상하라.
```sql
UPDATE employees
SET salary = salary * 1.10
WHERE dept_id = (SELECT dept_id FROM departments WHERE name = '인사팀');
```

---

## 🗑️ 5. DELETE 문 예제

### ✅ Q1. ‘신제품 런칭’ 프로젝트를 삭제하라.
```sql
DELETE FROM projects
WHERE project_name = '신제품 런칭';
```

### ✅ Q2. 마케팅팀 소속 직원 모두 삭제하라.
```sql
DELETE FROM employees
WHERE dept_id = (SELECT dept_id FROM departments WHERE name = '마케팅팀');
```

---

## 🔗 6. JOIN 문 예제

### ✅ Q1. 직원과 부서명을 함께 조회하라. (INNER JOIN)
```sql
SELECT e.name AS emp_name, d.name AS dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id;
```

### ✅ Q2. 프로젝트에 참여 중인 직원 이름과 역할 조회 (LEFT JOIN)
```sql
SELECT e.name, ep.role
FROM employees e
LEFT JOIN employee_projects ep ON e.emp_id = ep.emp_id;
```

### ✅ Q3. 모든 프로젝트와 참여한 직원 이름을 조회하되, 참여한 직원이 없어도 프로젝트는 보이게 하라. (LEFT JOIN)
```sql
SELECT p.project_name, e.name
FROM projects p
         LEFT JOIN employee_projects ep ON p.project_id = ep.project_id
         LEFT JOIN employees e ON ep.emp_id = e.emp_id;
```

### ✅ Q4. 직원과 프로젝트 매칭이 모두 없는 경우도 포함해서 전체 조인하라. (FULL OUTER JOIN)
```sql
SELECT e.name AS emp_name, p.project_name
FROM employees e
FULL OUTER JOIN employee_projects ep ON e.emp_id = ep.emp_id
FULL OUTER JOIN projects p ON ep.project_id = p.project_id;
```

---

📚 더 많은 예제나 각 쿼리의 결과 예시가 필요하다면 추가로 제작해줄 수 있습니다.
