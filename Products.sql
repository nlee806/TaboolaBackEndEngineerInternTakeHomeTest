 
CREATE TABLE Product (
  name TEXT,
  category TEXT,
  timeupdated TIMESTAMP,
  author TEXT
);

CREATE TABLE ProductPrice (
  name TEXT,
  price DOUBLE,
  currentdiscountpercent DOUBLE DEFAULT 0,
  timeupdated TIMESTAMP,
  author TEXT
);

CREATE TABLE ProductPriceChangeLog(
  name TEXT,
  oldprice DOUBLE,
  oldPercent DOUBLE,
  newprice DOUBLE,
  newPercent DOUBLE,
  timeupdated TIMESTAMP,
  author TEXT
);

CREATE TRIGGER afterInsert AFTER INSERT ON ProductPrice 
FOR EACH ROW INSERT INTO ProductPriceChangeLog(name,oldprice,oldPercent,newprice,newPercent,timeupdated,author) 
VALUES (NEW.name,null, null, NEW.price, NEW.currentdiscountpercent, CURRENT_TIME(), NEW.author);

CREATE TRIGGER afterUpdate AFTER UPDATE ON ProductPrice 
FOR EACH ROW INSERT INTO ProductPriceChangeLog(name,oldprice,oldPercent,newprice,newPercent,timeupdated,author) 
VALUES (NEW.name,OLD.price,OLD.currentdiscountpercent, NEW.price, NEW.currentdiscountpercent, CURRENT_TIME(), NEW.author);

CREATE TRIGGER afterDelete AFTER DELETE ON ProductPrice 
FOR EACH ROW INSERT INTO ProductPriceChangeLog(name,oldprice,oldPercent,newprice,newPercent,timeupdated,author) 
VALUES (OLD.name,OLD.price,OLD.currentdiscountpercent, null, null, CURRENT_TIME(), OLD.author);

/*INSERT INTO Product VALUES
('A','TypeA','2017-07-23 13:10:11','Person A'),
('B','TypeA','2017-07-24 13:10:11','Person B'),
('C','TypeA','2017-07-25 13:10:11','Person A');
INSERT INTO ProductPrice VALUES
('A',6.0,4.0,'2017-07-25 13:10:11','Person B'),
('B',5.0,5.0,'2017-07-26 13:10:11','Person C'),
('C',4.0,4.0,'2017-07-29 13:10:11','Person A');*/

SELECT Product.name, Product.category,ProductPrice.price,
(SELECT GREATEST(Product.timeupdated, ProductPrice.timeupdated)) AS 'timeupdated' 
, (SELECT IF(Product.timeupdated = (SELECT GREATEST(Product.timeupdated, ProductPrice.timeupdated)), Product.author, ProductPrice.author)) AS 'author'
FROM Product CROSS JOIN ProductPrice 
WHERE Product.name = ProductPrice.name;

/*SELECT * FROM ProductPriceChangeLog;*/