DROP DATABASE Cars

CREATE DATABASE Cars

USE Cars

CREATE TABLE [User](
UserId			VARCHAR(50)		PRIMARY KEY,
[Password]		VARCHAR(50)		,
Username		NVARCHAR(50)	,
Phone			VARCHAR(10)		,
[Address]		NVARCHAR(MAX)	,
CreateDate		DATETIME		,
IsActive		BIT				,
IsAdmin			BIT				,
[Status]		VARCHAR(10)		
)
CREATE TABLE Category(
CategoryId		VARCHAR(10)		PRIMARY KEY,
CatogoryName	VARCHAR(50)		,
)

CREATE TABLE Product(
ProductId	VARCHAR(10)		PRIMARY KEY,
ProductName	VARCHAR(MAX)	,
[Image]		VARCHAR(100)	,
Color		VARCHAR(20)		,
[Year]		INT				,
CategoryId	VARCHAR(10)		,
Price		FLOAT			,
Quantity	INT				,
Available	INT				,
FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId)
)

CREATE TABLE [Order](
OrderId		VARCHAR(50)		PRIMARY KEY,
UserId		VARCHAR(50)		,
Total		FLOAT			,
CreateDate	DATETIME		,
[Status]	VARCHAR(50)		,
FOREIGN KEY (UserId) REFERENCES [User](UserId)
)

CREATE TABLE OrderDetail(
DetailId	VARCHAR(50)		PRIMARY KEY,
OrderId		VARCHAR(50)		,
ProductId	VARCHAR(10)		,
Quantity	INT				,
Price		FLOAT			,
PickupDate	DATETIME		,
ReturnDate	DATETIME		,
FOREIGN KEY (OrderId) REFERENCES [Order](OrderId),
FOREIGN KEY (ProductId) REFERENCES Product(ProductId)
)

CREATE TABLE Feedback(
FeedbackId	INT IDENTITY(1,1)	PRIMARY KEY,
UserId		VARCHAR(50)		,
ProductId	VARCHAR(10)		,
Rate		INT				,
FOREIGN KEY (UserId) REFERENCES [User](UserId),
FOREIGN KEY (ProductId) REFERENCES Product(ProductId)
)

INSERT INTO [User](UserId, [Password], Username, Phone, [Address], CreateDate, IsActive, IsAdmin, [Status])
VALUES('admin@gmail.com','123',N'Admin','0123456789','tphcm','2021-03-20','true','true','New'),('user@gmail.com','123',N'User','0987654321','tphcm','2021-03-20','true','false','new')


