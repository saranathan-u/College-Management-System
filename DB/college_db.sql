create database college_db;
use college_db;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact VARCHAR(20),
    department VARCHAR(100),
    roll_no VARCHAR(50),
    dtype VARCHAR(50) NOT NULL, -- Discriminator for inheritance (Student, Teacher, Admin)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

drop table users;

ALTER TABLE users ADD COLUMN dtype VARCHAR(50) NOT NULL;
describe users;

SELECT username, password FROM users;

SELECT id, username, email, password FROM users;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM user_roles;
DELETE FROM users;

ALTER TABLE users DROP COLUMN user_type;

SET SQL_SAFE_UPDATES = 0;
UPDATE users SET dtype = 'STUDENT' WHERE dtype = 'Student';
UPDATE users SET dtype = 'ADMIN' WHERE dtype = 'Admin';

SET SQL_SAFE_UPDATES = 1;


INSERT INTO users (id, dtype, username, name, email, password) VALUES
(1, 'STUDENT', 'john', 'John Doe', 'john@example.com', '$2a$10$Dow1h9N4t0EoR2q1DLO5ZORx8bS5smbz1I3mS5S6pK0t2pJ8l6k1K'),
(2, 'ADMIN', 'saranathan', 'Saranathan U', 'saranathlogu@gmail.com', '$2a$10$2YlQ3uC1tr4Xr0aUO3HQ2OPqfHcU8bLh.NE5O9FYo7aSZbPtkd1yG');


create table students (
id bigint primary key,
name VARCHAR(100) NOT NULL,
contact varchar(15),
roll_no varchar(50),
department varchar(100),
foreign key (id) references users(id) on delete cascade
);

ALTER TABLE students ADD COLUMN image_url VARCHAR(255);


INSERT INTO students (id, name, contact, roll_no, department) VALUES
(11, 'Aarav Mehta', '9988776655', 'CS2025-03', 'Computer Science'),
(12, 'Sanya Kapoor', '9876543211', 'ME2025-04', 'Mechanical Engineering');

UPDATE users 
SET contact = '9361049772', image_url = 'http://localhost:8080/uploads/logakrishna.jpeg'
WHERE id = 102;

SHOW TABLES;
SELECT * FROM students;
DESCRIBE students;
select email, password from students;

CREATE TABLE faculty (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contact VARCHAR(15),
    department VARCHAR(50),
    designation VARCHAR(50),
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO faculty (name, email, contact, department, designation, image_url) 
VALUES ('Dr. Sarath', 'sarath.babu@college.edu', '9911223344', 'Information Technology', 'Assistant Professor', '/uploads/sarath.jpeg');

ALTER TABLE faculty 
ADD COLUMN name VARCHAR(100),
ADD COLUMN email VARCHAR(100),
ADD COLUMN contact VARCHAR(15),
ADD COLUMN image_url VARCHAR(255);


ALTER TABLE courses DROP FOREIGN KEY FKkb7y7evjam6951oast40wqk1b;
DROP TABLE faculty;
describe faculty;

SELECT * FROM faculty;
SELECT email, password FROM faculty;


ALTER TABLE faculty ADD COLUMN password VARCHAR(255);
UPDATE faculty 
SET password = '$2a$10$Zb6mR8jX0Q0FjGu6J6k7xe4kbk5gEeh6NqPp0HZs6bP/mH6ZcFImK' 
WHERE id = 9;

SELECT * FROM roles;

SELECT id, username, email
FROM users
WHERE email = 'diwalove@gmail.com' OR username = 'diwalove@gmail.com';


SELECT id, title, credits, duration
FROM courses;

SELECT id, title
FROM courses
WHERE id = 102;

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (101, 'Computer Science', 3, '4 months', 9);

DELETE FROM enrollments WHERE course_id = 101;
DELETE FROM courses WHERE id = 101;

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (102, 'Mechanical Engineering', 5, '5 months', 12);

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (103, 'Electrical Engineering', 4, '4 months', 9);

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (104, 'Civil Engineering', 3, '3 months', 10);

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (105, 'Information Technology', 3, '4 months', 14);

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (106, 'Agricultural Engineering', 4, '5 months', 17);

INSERT INTO courses (id, title, credits, duration, faculty_id)
VALUES (107, 'Cybersecurity', 5, '3 months', 13);


SELECT id, name, email FROM faculty;



