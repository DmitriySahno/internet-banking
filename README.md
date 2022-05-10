# Internet-banking

My personal project for portfolio.
<br>It is a REST-API application, that allows you to manage of user balances by http-requests:
* <ins>/getBalance/userId</ins> - get current balance of user. Instead of "userId" you must substitude id of user;
* <ins>/putMoney</ins> - add amount of money to user balance. Http-request should contain request body:
  * *userId* - user id to add amount of money;
  * *amount* - amount of money, that should be added to user;
* <ins>/takeMoney</ins> - get amount of money from user balance. Http-request should contain request body:
  * *userId* - user id to substruct amount of money;
  * *amount* - amount of money, that should be substructed from user;
* <ins>/transferMoney</ins> - transfer money between two users. Http-request should contain request body:
  * *userFromId* - source user id;
  * *userToId* - destination user id;
  * *amount* - amount of money to transfer. 
* <ins>/getOperationList</ins> - get all operations by user in period. Http-request should contain request body:
  * *userId* - user id;
  * *dateFrom* - start date of period;
  * *dateTo* - end date of period.

The API is accessible and can be checked by [url](https://sf-internet-banking.herokuapp.com/) + methods below.

The database structure is displayed at the picture:

![](https://github.com/DmitriySahno/internet-banking/blob/master/database-structure.png)
