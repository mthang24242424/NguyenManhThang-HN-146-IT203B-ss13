CREATE DATABASE bai5;
USE bai5;

CREATE TABLE Beds (
    bed_id INT PRIMARY KEY,
    status VARCHAR(20) DEFAULT 'EMPTY'
);

CREATE TABLE Patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    bed_id INT
);

CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    amount DOUBLE
);

INSERT INTO Beds VALUES
(1,'EMPTY'), (2,'EMPTY'), (3,'EMPTY');