
CREATE DATABASE ss13;
USE ss13;

CREATE TABLE Medicine_Inventory (
    medicine_id INT PRIMARY KEY AUTO_INCREMENT,
    medicine_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE Prescription_History (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    medicine_id INT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Medicine_Inventory (medicine_name, quantity) VALUES
('Paracetamol', 100),
('Aspirin', 50),
('Vitamin C', 200);

CREATE TABLE Patient_Wallet (
    patient_id INT PRIMARY KEY,
    balance DOUBLE NOT NULL
);


CREATE TABLE Invoices (
    invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    amount DOUBLE,
    status VARCHAR(20) DEFAULT 'UNPAID'
);


INSERT INTO Patient_Wallet (patient_id, balance) VALUES
(101, 1000),
(102, 500);

INSERT INTO Invoices (patient_id, amount, status) VALUES
(101, 300, 'UNPAID'),
(102, 200, 'UNPAID');

CREATE TABLE Patients (
    patient_id INT PRIMARY KEY,
    status VARCHAR(50)
);

CREATE TABLE Beds (
    bed_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    status VARCHAR(50)
);

INSERT INTO Patients VALUES
(101, 'DANG_DIEU_TRI'),
(102, 'DANG_DIEU_TRI');

INSERT INTO Beds (patient_id, status) VALUES
(101, 'DANG_SU_DUNG'),
(102, 'DANG_SU_DUNG');