# ShopForEmployer
This project was made for employers to show my skills in making a simple application.
The application uses SpringBoot, PostgreSQL, Hibernate and JSON formatted views. 
In it, you can create a new product with its description and price, which can be specified in different languages and in different currencies.
When creating a new product, the time and date of its creation are indicated, and when modifying the time and date of its change.
# How to make everything work
1. You must connect to your empty PostgreSQL database via __`application.properties`__
2. Then in __`DB.sql`__ you need to copy the script and execute it in the database
# Working with Postman JSON
## __shop_admin__
1. In your Postman connect to http://__your_db_server__/shop_admin/products. This link will take you to your entire product list.
2. http://__your_db_server__/shop_admin/products/__id__. This link will show the product by the id you specified.
3. If you need to add a new product, then in __Postman__ select `POST` and paste the link: http://__your_db_server__/shop_admin/products and create it from a template![Postman 11 03 2022 16_25_53](https://user-images.githubusercontent.com/90369211/157849712-e2d028f7-2637-4102-92ef-2eaa8486f365.png)
4. If you need to update product information, select `PUT` and paste this link: http://__your_db_server__/shop_admin/products/__id__ and replace the required data ![Postman 11 03 2022 16_33_27](https://user-images.githubusercontent.com/90369211/157850915-38b0c7ea-722a-4226-861a-a5b4313b252d.png)
5. And to delete the product, paste the same link and select `DELETE` ![Postman 11 03 2022 16_38_02](https://user-images.githubusercontent.com/90369211/157854240-aa47df91-0a98-4690-85e4-be9809608bfd.png)
## __shop_client__
1. Show all products: http://__your_db_server__/shop_client/products
2. Show product by id: http://__your_db_server__/shop_admin/products/__id__
3. Show all products by name: http://__your_db_server__/shop_admin/products/name/__name__
4. Show all products by description: http://__your_db_server__/shop_admin/products/description/__description__
