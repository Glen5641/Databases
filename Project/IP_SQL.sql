--Drop Previous Tables and Reinitialize Empty Tables
drop table complaint
drop table purchase
drop table product_3
drop table product_2
drop table product_1
drop table customer
drop table account
drop table repair
drop table accident
drop table product
drop table worker
drop table technical_staff
drop table quality_controller
drop table employee


--Create Employee Table With Unique Index on the 
--Primary Key for Derived Table References
CREATE TABLE employee (
    name VARCHAR(64) NOT NULL,
    address VARCHAR(64) NOT NULL,
    primary key(name, address))
CREATE UNIQUE NONCLUSTERED INDEX employee_info
ON employee (name, address);


--Create a Technical_Staff Table that is Specialized From the 
--Employee Table and adds a foreign key for Syncronization.
CREATE TABLE technical_staff (
    name VARCHAR(64) NOT NULL,
    address VARCHAR(64) NOT NULL,
    FOREIGN key(name, address) REFERENCES employee(name, address),
    education VARCHAR(5) CHECK (education IN ('BS', 'MS', 'PH.D')),
    position VARCHAR(32)
    primary key(name, address))
CREATE UNIQUE NONCLUSTERED INDEX ts_info
ON technical_staff (name, address);

--Create a Quality Controller Table that is Specialized from the
--Employee Table and adds a foreign key for Syncronization
CREATE TABLE quality_controller (
    name VARCHAR(64) NOT NULL,
    address VARCHAR(64) NOT NULL,
    FOREIGN key(name, address) REFERENCES employee(name, address),
    type_of_product_checked INT NOT NULL CHECK (type_of_product_checked IN (1, 2, 3))
    primary key(name, address))
CREATE UNIQUE NONCLUSTERED INDEX qc_info
ON quality_controller (name, address);

--Create a Worker Table that is Specialized from the
--Employee Table and adds a foreign key for Syncronization
CREATE TABLE worker (
    name VARCHAR(64) NOT NULL,
    address VARCHAR(64) NOT NULL,
    FOREIGN key(name, address) REFERENCES employee(name, address),
    max_produced_per_day INT DEFAULT 1000
    primary key(name, address))
CREATE UNIQUE NONCLUSTERED INDEX w_info
ON worker (name, address);

--Create a Product Table that Uses Multiple Foreign Keys
--to stay in sync with specialized Employee Tables and
--creates a unique index on product ID for multiple tables
--To reference
CREATE TABLE product (
    ID INT NOT NULL,
    time_spent INT DEFAULT 10,
    worker_name VARCHAR(64) NOT NULL,
    worker_address VARCHAR(64) NOT NULL,
    quality_controller_name VARCHAR(64) NOT NULL,
    quality_controller_address VARCHAR(64) NOT NULL,
    technical_staff_name VARCHAR(64),
    technical_staff_address VARCHAR(64),
    size VARCHAR(7) CHECK (size IN ('SMALL', 'MEDIUM', 'LARGE')) DEFAULT 'MEDIUM',
    defect VARCHAR(3) CHECK (defect IN ('YES', 'NO')) DEFAULT 'NO',
    certified VARCHAR(3) CHECK (certified IN ('YES', 'NO')) DEFAULT 'NO'
    primary key(ID))
CREATE UNIQUE NONCLUSTERED INDEX product_index
ON product (ID);


--Create an accident Table that Has a foreign key
--on the employee and product tables for syncronization
CREATE TABLE accident (
    accident_number INT NOT NULL,
    employee_name VARCHAR(64) NOT NULL,
    employee_address VARCHAR(64) NOT NULL,
    FOREIGN key(employee_name, employee_address) REFERENCES employee(name, address),
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    date DATE DEFAULT getdate(),
    num_work_days_lost INT DEFAULT 5 
    primary key(accident_number))


--Create repair table that has quality controller
--and technical staff foreign keys to keep Sync.
--Added custom constraint to check if technical
--staff member has qualifying education. 
--Hashing allows for a year and half of repairs on record.
CREATE TABLE repair (
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    quality_controller_name VARCHAR(64) NOT NULL,
    quality_controller_address VARCHAR(64) NOT NULL,
    technical_staff_name VARCHAR(64) NOT NULL,
    technical_staff_address VARCHAR(64) NOT NULL,
    date DATE DEFAULT getdate(),
    primary key(product_ID))
ALTER TABLE repair
ADD CONSTRAINT check_if_qualified
CHECK (dbo.technical_staff_qualify(product_ID, technical_staff_name, technical_staff_address) = 0);

--Create product 1 table with foreign key on product
CREATE TABLE product_1 (
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    NAME_OF_MAJOR_SOFTWARE VARCHAR(64) NOT NULL
    primary key(product_ID))


--Create product 2 table with foreign key on product
CREATE TABLE product_2 (
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    color VARCHAR(32) NOT NULL
    primary key(product_ID))


--Create product 3 table with foreign key on product
CREATE TABLE product_3 (
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    weight NUMERIC(6,2) DEFAULT 10
    primary key(product_ID))


--Create account Table with foreign key on product
CREATE TABLE account (
    account_number INT NOT NULL,
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    date_established DATE DEFAULT getdate(),
    cost_to_make NUMERIC(6,2) DEFAULT 100,
    primary key(account_number))


--Create customer table with unique index on the customer 
--information that allows for speedy searching and referencing.
CREATE TABLE customer (
    name VARCHAR(64) NOT NULL,
    address VARCHAR(64) NOT NULL,
    primary key(name, address))
CREATE UNIQUE INDEX custome_index
ON customer (name, address);


--Create purchase table with foreign keys on 
--customer and product tables
CREATE TABLE purchase (
    name VARCHAR(64) NOT NULL,
    address VARCHAR(64) NOT NULL,
    FOREIGN key(name, address) REFERENCES customer(name, address),
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    primary key(name, address, product_ID))


--Create complaint Table with foreign keys on 
--customer and product tables
CREATE TABLE complaint (
    ID int not null,
    customer_name VARCHAR(64) not null,
    customer_address VARCHAR(64) not null,
    FOREIGN key(customer_name, customer_address) REFERENCES customer(name, address),
    product_ID INT NOT NULL FOREIGN key REFERENCES product(ID),
    date DATE DEFAULT getdate(),
    description VARCHAR(200),
    treatment_expected VARCHAR(200)
    primary key(ID))
--------------------------------------------------------------------------------------------------------------------------------------------------------
--Function to check whether or not a technical_staff member is 
--qualified to repair a product in the category product_1. 
--Returns 0 if the technical staff qualifies 
--and 1 if the technical staff does not.
CREATE FUNCTION dbo.technical_staff_qualify(@product_ID INT, 
@technical_staff_name VARCHAR(64), @technical_staff_address VARCHAR(64))
    RETURNS INT
AS
    BEGIN
        DECLARE @val int
        SET @val = 0;
        IF EXISTS ( 
            SELECT 1 
            FROM technical_staff
            WHERE technical_staff.name = @technical_staff_name
                AND technical_staff.address = @technical_staff_address
                AND technical_staff.education IS NULL) 
        AND EXISTS( 
            SELECT 1 
            FROM product_1 
            WHERE product_1.product_ID = @product_ID)
        BEGIN
            SET @val = 1
        END
        RETURN @val
    END
GO
----------------------------------------------------------------------------------------------------------------------------------
--Drop previously made procedure
DROP PROCEDURE sel7Func;
DROP PROCEDURE sel8Func;
DROP PROCEDURE sel9Func;
DROP PROCEDURE sel10Func;
DROP PROCEDURE sel11Func;
DROP PROCEDURE sel12Func;
DROP PROCEDURE sel13Func;
DROP PROCEDURE sel14Func;
DROP PROCEDURE sel15Func;


--Stored Procedure for selection 7
--Retrieves the date produced and time spent 
--to produce a particular product (100/day).
GO
CREATE PROCEDURE sel7Func
    @product_ID INT
AS
    BEGIN
        SELECT account.date_established, product.time_spent
        FROM product, account
        WHERE product.ID = @product_ID
            AND account.product_ID = @product_ID;
    END
GO

--Stored Procedure for selection 8
--Retrieves all products made by a particular worker (2000/day).
GO
CREATE PROCEDURE sel8Func    
    @worker_name VARCHAR(64),
    @worker_address VARCHAR(64)
AS
    BEGIN
        SELECT ID
        FROM product
        WHERE worker_name = @worker_name 
            AND worker_address = @worker_address;
    END
GO

--Stored Procedure for selection 9
--Retrieves the total number of errors a particular quality controller made. This is the total
--number of products certified by this controller and got some complaints (400/day). 
GO
CREATE PROCEDURE sel9Func
    @quality_controller_name VARCHAR(64),
    @quality_controller_address VARCHAR(64)
AS
    BEGIN
        SELECT COUNT(product.ID)
        FROM product
        WHERE quality_controller_name = @quality_controller_name
            AND quality_controller_address = @quality_controller_address
            AND defect = 'yes'
            AND certified = 'yes'
    END
GO

--Stored Procedure for selection 10
--Retrieve the total costs of the products in the product3 category which 
--were repaired at the request of a particular quality controller (40/day).
GO
CREATE PROCEDURE sel10Func
    @quality_controller_name VARCHAR(64),
    @quality_controller_address VARCHAR(64)
AS
    BEGIN
        SELECT SUM(account.cost_to_make)
        FROM product, product_3, repair, account
        WHERE product.ID = product_3.product_ID
            AND product.quality_controller_name = @quality_controller_name
            AND product.quality_controller_address = @quality_controller_address
            AND product.ID = repair.product_ID
            AND account.product_ID = product.ID;
    END
GO

--Stored Procedure for selection 11
--Retrieve all customers who purchased all products of a particular color (5/month).
GO
CREATE PROCEDURE sel11Func
    @color VARCHAR(20)
AS
    BEGIN
        SELECT purchase.name, purchase.address
        FROM purchase, product_2
        WHERE product_2.color = @color
            AND purchase.product_ID = product_2.product_ID;
    END
GO

--Stored Procedure for selection 12
--Retrieve the total number of work days lost due to accidents 
--in repairing the products which got complaints (1/month). 
GO
CREATE PROCEDURE sel12Func
AS
    BEGIN
        SELECT SUM(accident.num_work_days_lost)
        FROM accident, repair, complaint
        WHERE accident.product_ID = repair.product_ID
            AND accident.product_ID = complaint.product_ID;
    END
GO

--Stored Procedure for selection 13
--Retrieve all customers who are also workers (10/month). 
GO
CREATE PROCEDURE sel13Func
AS
    BEGIN
        SELECT customer.name, customer.address
        FROM customer, worker
        WHERE customer.name = worker.name
            AND customer.address = worker.address;
    END
GO

--Stored Procedure for selection 14
--Retrieve all the customers who have purchased the products 
--made or certified or repaired by themselves (5/day).
GO
CREATE PROCEDURE sel14Func
AS
    BEGIN
        SELECT purchase.name, purchase.address
        FROM purchase, product
        WHERE 
            (purchase.name = product.worker_name
            AND purchase.address = product.worker_address
            AND purchase.product_ID = product.ID)
        OR
            (purchase.name = product.quality_controller_name
            AND purchase.address = product.quality_controller_address
            AND purchase.product_ID = product.ID)
        OR
            (purchase.name = product.technical_staff_name
            AND purchase.address = product.technical_staff_address
            AND purchase.product_ID = product.ID)
    END
GO

--Stored Procedure for selection 15
--Retrieve the average cost of all products made in a particular year (5/day).
GO
CREATE PROCEDURE sel15Func
@year INT
AS
    BEGIN
        SELECT AVG(account.cost_to_make) 
        FROM account
        WHERE year(account.date_established) = @year;

    END
GO