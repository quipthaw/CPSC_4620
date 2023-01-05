-- (Manning Graham)

use Pizzeria;

CREATE VIEW ToppingPopularity AS SELECT Pizza_Topping_Topping_Name AS Topping, Count(Pizza_Topping_Topping_Name) + Count( if(Pizza_Topping_Request_Extra = 1, Pizza_Topping_Request_Extra, NULL)) AS ToppingCount from pizza_topping
Group by Topping
Order by ToppingCount DESC;

CREATE VIEW ProfitByPizza AS SELECT A.Pizza_Size AS PizzaSize, A.Pizza_Crust_Type AS PizzaCrust, SUM(A.Pizza_Cust_Price - A.Pizza_Bus_Cost) AS Profit, B.Order_DateTime AS LastOrderDate 
from pizza A
Join orders B ON A.Pizza_Order_ID = B.Order_ID
Group by PizzaSize, PizzaCrust
Order BY Profit DESC;

CREATE VIEW ProfitByOrderType AS SELECT Order_Type AS CustomerType, Order_DateTime AS OrderDate, SUM(Order_Price) as TotalOrderPrice, SUM(Order_Cost) AS TotalOrderCost, SUM(Order_Price - Order_Cost) AS Profit  FROM orders
Group BY CustomerType, OrderDate;

SELECT * FROM ToppingPopularity;
SELECT * FROM ProfitByPizza;
Select * FROM ProfitByOrderType;