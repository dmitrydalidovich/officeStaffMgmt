CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    salary NUMERIC(10, 2),
    department_id INT REFERENCES department(id),
    manager_flag BOOLEAN
);
