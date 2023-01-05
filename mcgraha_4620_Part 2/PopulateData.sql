-- (Manning Graham)

use Pizzeria;

INSERT INTO topping VALUES ('Pepperoni', 1.25, 0.2,100, 10, 2, 2.75, 3.5, 4.5);
INSERT INTO topping VALUES ('Sausage', 1.25, 0.15, 100, 10, 2.5, 3, 3.5, 4.25);
INSERT INTO topping VALUES ('Ham', 1.5, 0.15, 78, 10, 2, 2.5, 3.25, 4);
INSERT INTO topping VALUES ('Chicken', 1.75, 0.25, 56, 10, 1.5, 2, 2.25, 3);
INSERT INTO topping VALUES ('Green Pepper', 0.5, 0.02, 79, 10, 1, 1.5, 2, 2.5);
INSERT INTO topping VALUES ('Onion', 0.5, 0.02, 85, 10, 1, 1.5, 2, 2.75);
INSERT INTO topping VALUES ('Roma Tomato', 0.75, 0.03, 86, 10, 2, 3, 3.5, 4.5);
INSERT INTO topping VALUES ('Mushrooms', 0.75, 0.1, 52, 10, 1.5, 2, 2.5, 3);  
INSERT INTO topping VALUES ('Black Olives', 0.6, 0.1, 39, 10, 0.75, 1, 1.5, 2);
INSERT INTO topping VALUES ('Pineapple', 1, 0.25, 15, 10, 1, 1.25, 1.75, 2);   
INSERT INTO topping VALUES ('Jalapenos', 0.5, 0.05, 64, 10, 0.5, 0.75, 1.25, 1.75);  
INSERT INTO topping VALUES ('Banana Peppers', 0.5, 0.05, 36,10, 0.6, 1, 1.3, 1.75 );
INSERT INTO topping VALUES ('Regular Cheese', 1.5, 0.12, 250, 10, 2, 3.5, 5, 7 );
INSERT INTO topping VALUES ('Four Cheese Blend', 2, 0.15, 150, 10, 2, 3.5, 5, 7);  
INSERT INTO topping VALUES ('Feta Cheese', 2, 0.18, 75, 10, 1.75, 3, 4, 5.5);
INSERT INTO topping VALUES ('Goat Cheese', 2, 0.2, 54, 10, 1.6, 2.75, 4, 5.5);
INSERT INTO topping VALUES ('Bacon', 1.5, 0.25, 89, 10, 1, 1.5, 2, 3);  

INSERT INTO discount VALUES (1, 'Employee', NULL, 15);
INSERT INTO discount VALUES (2, 'Lunch Special Medium', 1.00, NULL);
INSERT INTO discount VALUES (3, 'Lunch Special Large', 2.00, NULL);
INSERT INTO discount VALUES (4, 'Specialty Pizza', 1.50, NULL);
INSERT INTO discount VALUES (5, 'Gameday Special', NULL, 20);  

INSERT INTO pizza_lookup VALUES ('Thin', 'small', 3, 0.5);
INSERT INTO pizza_lookup VALUES ('Original', 'small', 3, 0.75);
INSERT INTO pizza_lookup VALUES ('Pan', 'small', 3.5, 1);
INSERT INTO pizza_lookup VALUES ('Gluten-Free', 'small', 4, 2);
INSERT INTO pizza_lookup VALUES ('Thin', 'medium', 5, 1); 
INSERT INTO pizza_lookup VALUES ('Original','medium',  5, 1.5); 
INSERT INTO pizza_lookup VALUES ('Pan', 'medium', 6, 2.25);
INSERT INTO pizza_lookup VALUES ('Gluten-Free','medium', 6.25, 3); 
INSERT INTO pizza_lookup VALUES ('Thin', 'large', 8, 1.25); 
INSERT INTO pizza_lookup VALUES ('Original','large', 8, 2 ); 
INSERT INTO pizza_lookup VALUES ('Pan', 'large', 9, 3 );
INSERT INTO pizza_lookup VALUES ('Gluten-Free','large', 9.5, 4 ); 
INSERT INTO pizza_lookup VALUES ('Thin', 'x-large', 10, 2 ); 
INSERT INTO pizza_lookup VALUES ('Original','x-large', 10, 3 ); 
INSERT INTO pizza_lookup VALUES ('Pan', 'x-large', 11.5, 4.5 );
INSERT INTO pizza_lookup VALUES ('Gluten-Free','x-large', 12.5, 6 ); 



-- Order 1
INSERT INTO customer VALUES (1, NULL, 'Matt Engers', '864-474-9953');
INSERT INTO orders VALUES (1, 1, '2022-03-02 05:30:00 PM', 7.85, 16.85, 'pickup');
INSERT INTO order_discount VALUES (4,1); -- ( Discount id, Order id
INSERT INTO pickup VALUES (1,'Matt Engers', '864-474-9953');
-- pizza 1
INSERT INTO pizza VALUES(1, 1, 'Gluten-Free', 'x-large', 16.85, 7.85, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_discount VALUES (4,1); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (1,'Green Pepper', False);
INSERT INTO pizza_topping VALUES (1,'Onion', False);
INSERT INTO pizza_topping VALUES (1,'Roma Tomato', False);
INSERT INTO pizza_topping VALUES (1,'Mushrooms', False);
INSERT INTO pizza_topping VALUES (1,'Black Olives', False);
INSERT INTO pizza_topping VALUES (1,'Goat Cheese', False);



-- Order 2
INSERT INTO customer VALUES (2, '6745 Wessex St Anderson SC 29621', 'Frank Turner', '864-232-8944');
INSERT INTO orders VALUES (2, 2, '2022-03-02 06:17:00 PM', 3.20, 13.25, 'delivery');
INSERT INTO delivery VALUES (2,'Frank Turner', '864-232-8944', '6745 Wessex St Anderson SC 29621');
-- pizza 2
INSERT INTO pizza VALUES(2, 2, 'Thin', 'large', 13.25, 3.20, 'DONE');
INSERT INTO pizza_topping VALUES (2,'Four Cheese Blend', TRUE);
INSERT INTO pizza_topping VALUES (2,'Chicken', FALSE);
INSERT INTO pizza_topping VALUES (2,'Green Pepper', FALSE);
INSERT INTO pizza_topping VALUES (2,'Onion', FALSE);
INSERT INTO pizza_topping VALUES (2,'Mushrooms', FALSE);



-- order 3
INSERT INTO customer VALUES (3, NULL, 'Andrew Wilkes-Krier', '864-254-6861');
INSERT INTO orders VALUES (3, 3, '2022-03-03 09:30:00 PM', 19.8, 64.5, 'pickup');
INSERT INTO pickup VALUES (3,'Andrew Wilkes-Krier', '864-254-6861');
-- pizza 1
INSERT INTO pizza VALUES(3, 3, 'Original', 'large', 10.75, 3.30, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_topping VALUES (3,'Regular Cheese', False);
INSERT INTO pizza_topping VALUES (3,'Pepperoni', False);
-- pizza 2
INSERT INTO pizza VALUES(4, 3, 'Original', 'large', 10.75, 3.30, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_topping VALUES (4,'Regular Cheese', False);
INSERT INTO pizza_topping VALUES (4,'Pepperoni', False);
-- pizza 3
INSERT INTO pizza VALUES(5, 3, 'Original', 'large', 10.75, 3.30, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_topping VALUES (5,'Regular Cheese', False);
INSERT INTO pizza_topping VALUES (5,'Pepperoni', False);
-- pizza 4
INSERT INTO pizza VALUES(6, 3, 'Original', 'large', 10.75, 3.30, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_topping VALUES (6,'Regular Cheese', False);
INSERT INTO pizza_topping VALUES (6,'Pepperoni', False);
-- pizza 5
INSERT INTO pizza VALUES(7, 3, 'Original', 'large', 10.75, 3.30, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_topping VALUES (7,'Regular Cheese', False);
INSERT INTO pizza_topping VALUES (7,'Pepperoni', False);
-- pizza 6
INSERT INTO pizza VALUES(8, 3, 'Original', 'large', 10.75, 3.30, 'DONE');-- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_topping VALUES (8,'Regular Cheese', False);
INSERT INTO pizza_topping VALUES (8,'Pepperoni', False);



-- order 4
INSERT INTO orders VALUES (4, NULL, '2022-03-05 12:03:00 PM', 3.68, 13.50, 'dinein');
INSERT INTO order_discount VALUES (3,4); -- ( Discount id, Order id
INSERT INTO dine_in VALUES (4,14);
-- pizza 1
INSERT INTO pizza VALUES(9, 4, 'Thin', 'large', 13.50, 3.68, 'DONE');
INSERT INTO pizza_discount VALUES (3,4); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (9,'Regular Cheese', TRUE);
INSERT INTO pizza_topping VALUES (9,'Pepperoni', FALSE);
INSERT INTO pizza_topping VALUES (9,'Sausage', FALSE);



-- Order 5
INSERT INTO orders VALUES (5, NULL, '2022-04-03 12:05:00 PM', 4.63, 17.35, 'dinein'); -- (order id, cust_id, datetime, bus cost, cust price, order type)
INSERT INTO order_discount VALUES (2,5); -- ( Discount id, Order id)
INSERT INTO order_discount VALUES (4,5); -- ( Discount id, Order id)
INSERT INTO dine_in VALUES (5,4); -- ( Order id , Table Num)
-- pizza 1
INSERT INTO pizza VALUES(10, 5, 'Pan', 'medium', 10.60, 3.23, 'DONE'); -- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_discount VALUES (2,10); -- (Discount id, pizza id) 
INSERT INTO pizza_discount VALUES (4,10); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (10,'Feta Cheese', False); -- ( pizza id, Topping, extra?)
INSERT INTO pizza_topping VALUES (10,'Black Olives', False); -- ( pizza id, Topping)
INSERT INTO pizza_topping VALUES (10,'Roma Tomato', False); -- ( pizza id, Topping)
INSERT INTO pizza_topping VALUES (10,'Mushrooms', False); -- ( pizza id, Topping)
INSERT INTO pizza_topping VALUES (10,'Banana Peppers', False); -- ( pizza id, Topping)
-- pizza 2
INSERT INTO pizza VALUES(11, 5, 'Original', 'small', 6.75, 1.40, 'DONE'); -- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_discount VALUES (2,11); -- (Discount id, pizza id) 
INSERT INTO pizza_discount VALUES (4,11); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (11,'Regular Cheese', False); -- ( pizza id, Topping, extra?)
INSERT INTO pizza_topping VALUES (11,'Chicken', False); -- ( pizza id, Topping)
INSERT INTO pizza_topping VALUES (11,'Banana Peppers', False); -- ( pizza id, Topping)



-- Order 6
INSERT INTO customer VALUES (4, '8879 Suburban Home Anderson SC 29621', 'Milo Auckerman', '864-878-5679');
INSERT INTO orders VALUES (6, 4, '2022-04-13 08:32:00 PM', 6.3, 24.00, 'delivery'); -- (order id, cust_id, datetime, bus cost, cust price, order type)
INSERT INTO order_discount VALUES (1,6); -- ( Discount id, Order id)
INSERT INTO delivery VALUES (6,'Milo Auckerman', '864-878-5679', '8879 Suburban Home Anderson SC 29621');
-- pizza 1
INSERT INTO pizza VALUES(12, 6, 'Thin', 'large', 12.00, 3.75, 'DONE'); -- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_discount VALUES (1,12); -- (Discount id, pizza id)  
INSERT INTO pizza_topping VALUES (12,'Four Cheese Blend', TRUE); -- ( pizza id, Topping, extra?)
-- pizza 2
INSERT INTO pizza VALUES(13, 6, 'Thin', 'Large', 12.00, 2.55, 'DONE'); -- (pizzaid, orderid, crusttype, size, piz cust price, piz bus cost, pizza state)
INSERT INTO pizza_discount VALUES (1,13); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (13,'Regular Cheese', False); -- ( pizza id, Topping, extra?)
INSERT INTO pizza_topping VALUES (13,'Pepperoni', TRUE); -- ( pizza id, Topping)



-- order 7
UPDATE customer SET Customer_Address = '115 Party Blvd Anderson SC 29621' 
WHERE Customer_ID = 3;
INSERT INTO orders VALUES (7, 3, '2022-04-20 07:11:00 PM', 16.86, 45.5, 'delivery');
INSERT INTO delivery VALUES (7,'Andrew Wilkes-Krier', '864-254-5861', '115 Party Blvd Anderson SC 29621');
INSERT INTO order_discount VALUES (5,7); -- ( Discount id, Order id)
-- pizza 1
INSERT INTO pizza VALUES(14, 7, 'Original', 'x-large', 14.50, 5.59, 'DONE');
INSERT INTO pizza_discount VALUES (5,14); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (14,'Pepperoni', False);
INSERT INTO pizza_topping VALUES (14,'Sausage', FALSE);
INSERT INTO pizza_topping VALUES (14,'Four Cheese Blend', FALSE);
-- pizza 2
INSERT INTO pizza VALUES(15, 7, 'Original', 'x-large', 17, 5.59, 'DONE');
INSERT INTO pizza_discount VALUES (5,15); -- (Discount id, pizza id) 
INSERT INTO pizza_discount VALUES (4,15); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (15,'Ham', True);
INSERT INTO pizza_topping VALUES (15,'Pineapple', True);
INSERT INTO pizza_topping VALUES (15,'Four Cheese Blend', FALSE);
-- pizza 3
INSERT INTO pizza VALUES(16, 7, 'Original', 'x-large', 14.00, 5.68, 'DONE');
INSERT INTO pizza_discount VALUES (5,16); -- (Discount id, pizza id) 
INSERT INTO pizza_topping VALUES (16,'Jalapenos', False);
INSERT INTO pizza_topping VALUES (16,'Bacon', FALSE);
INSERT INTO pizza_topping VALUES (16,'Four Cheese Blend', FALSE);
