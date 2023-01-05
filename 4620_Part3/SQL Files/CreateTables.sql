-- (Manning Graham)

CREATE schema Pizzeria;
USE Pizzeria;

CREATE TABLE discount (
  Discount_ID INT PRIMARY KEY,
  Discount_Name VARCHAR(255),
  Discount_Dollar DECIMAL(5,2),
  Discount_Percent INT
  );
  
CREATE TABLE customer (
  Customer_ID INT PRIMARY KEY,
  Customer_Address VARCHAR(255),
  Customer_Name VARCHAR(255),
  Customer_Phone VARCHAR(255)
);

CREATE TABLE orders (
  Order_ID INT PRIMARY KEY,
  Order_Cust_ID INT,
  Order_DateTime DATETIME,
  Order_Cost DECIMAL(5,2),
  Order_Price DECIMAL(5,2),
  Order_Type VARCHAR(255),
  FOREIGN KEY (Order_Cust_ID) REFERENCES customer(Customer_ID)
);

CREATE TABLE order_discount (
  Order_Discount_ID INT,
  Order_Discount_Order_ID INT,
  FOREIGN KEY (Order_Discount_ID) REFERENCES discount(Discount_ID),
  FOREIGN KEY (Order_Discount_Order_ID) REFERENCES orders(Order_ID),
  PRIMARY KEY (Order_Discount_ID, Order_Discount_Order_ID)
);

CREATE TABLE topping (
  Topping_Name VARCHAR(255) PRIMARY KEY,
  Topping_Price DECIMAL(5,2),
  Topping_Cost_Per_Unit DECIMAL(5,2),
  Topping_Cur_Inventory INT,
  Topping_Min_Inventory INT,
  Topping_Small_Unit_Amt DECIMAL(5,2),
  Toping_Med_Unit_Amt DECIMAL(5,2),
  Topping_Large_Unit_Amt DECIMAL(5,2),
  Topping_XLarge_Unit_Amt DECIMAL(5,2)
);

CREATE TABLE pizza_lookup (
  Pizza_Lookup_Crust_Type VARCHAR(30),
  Pizza_Lookup_Size VARCHAR(30),
  Pizza_Lookup_Base_Price DECIMAL(5,2),
  Pizza_Lookup_Base_Cost DECIMAL(5,2),
  PRIMARY KEY(Pizza_Lookup_Crust_Type, Pizza_Lookup_Size)
);

CREATE TABLE pizza (
  Pizza_ID INT PRIMARY KEY,
  Pizza_Order_ID INT,
  Pizza_Crust_Type VARCHAR(30),
  Pizza_Size VARCHAR(30),
  Pizza_Cust_Price DECIMAL(5,2),
  Pizza_Bus_Cost DECIMAL(5,2),
  Pizza_State VARCHAR(30),
  FOREIGN KEY (Pizza_Order_ID) REFERENCES orders(Order_ID),
  FOREIGN KEY (Pizza_Crust_Type, Pizza_Size) REFERENCES pizza_lookup(Pizza_Lookup_Crust_Type, Pizza_Lookup_Size)
);

CREATE TABLE pizza_discount (
  Pizza_Discount_Discount_ID INT,
  Pizza_Discount_Pizza_ID INT,
  FOREIGN KEY (Pizza_Discount_Discount_ID) REFERENCES discount(Discount_ID),
  FOREIGN KEY (Pizza_Discount_Pizza_ID) REFERENCES pizza(Pizza_ID),
  PRIMARY KEY(Pizza_Discount_Discount_ID, Pizza_Discount_Pizza_ID)
);

CREATE TABLE pizza_topping (
  Pizza_Topping_Pizza_ID INT,
  Pizza_Topping_Topping_Name VARCHAR(255),
  Pizza_Topping_Request_Extra BOOL,
  FOREIGN KEY (Pizza_Topping_Topping_Name) REFERENCES topping(Topping_Name),
  FOREIGN KEY (Pizza_Topping_Pizza_ID) REFERENCES pizza(Pizza_ID),
  PRIMARY KEY (Pizza_Topping_Pizza_ID, Pizza_Topping_Topping_Name)
);

CREATE TABLE delivery (
  Delivery_Order_ID INT PRIMARY KEY,
  Delivery_Name VARCHAR(255),
  Delivery_Phone VARCHAR(255),
  Delivery_Address VARCHAR(255),
  FOREIGN KEY(Delivery_Order_ID) REFERENCES orders(Order_ID)
);

CREATE TABLE dine_in (
  Dine_In_Order_ID INT PRIMARY KEY,
  Dine_In_Table_Num INT,
  FOREIGN KEY (Dine_In_Order_ID) REFERENCES orders(Order_ID)
);

CREATE TABLE pickup (
  Pickup_Order_ID INT PRIMARY KEY,
  Pickup_Name VARCHAR(255),
  Pickup_Phone VARCHAR(255),
  FOREIGN KEY (Pickup_Order_ID) REFERENCES orders(Order_ID)
);