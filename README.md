# Meter
## U can find Full APIs documentations at: http://rooot.azurewebsites.net/swagger-ui.html

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
