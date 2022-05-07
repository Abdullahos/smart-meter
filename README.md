# Meter
## U can find Full APIs documentations at: https://rooot.azurewebsites.net/swagger-ui.html

## Backend on the cloud:
#### We need some services to be in a centralized place to serve many customers and meters.
#### Services like:

- Each meter buffer its readings to the head-end system each period of time T.
- Calculate the consumption as money based on the KWH price of a meter of a given state
- Display consumption and the result of AI/ML model like forecasting
- Generate Bill
- Perform online payment
- Turn meter ON/OFF

Another very important reason is the customer can’t connect directly to his smart meter as security-wise. Hence the machine learning models are deployed on the meter to provide the on-edge concept, so the results of these models have to be sent to the user through a trusted subsystem. This subsystem is the head-end system. Our application is a spring boot app that runs multiple services that can be accessed by authorized REST API requests. We use the Microsoft SQL database to store persistent data like users, meters, consumption, and payments. 

## Software Architecture on cloud

- The Most Abstract view
![0](https://user-images.githubusercontent.com/51336081/166923748-ac36f080-fdf6-49d1-8171-de51265283b1.png)

- The detailed overview
- ![1 - Copy](https://user-images.githubusercontent.com/51336081/166923863-0eb90432-f77b-4b21-9264-2ed242524992.png)

- The Interaction Between Server on meter(gsoap) and server on cloud
- ![30](https://user-images.githubusercontent.com/51336081/166924016-9626a6a7-1357-4203-8056-b9fa777766dd.png)

## Used Tools:
- Spring Boot, Date, Security
- Stripe as payment processor
-  mssql
