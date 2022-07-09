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

## Our Overall System Architecture
![overviewsysdesign](https://user-images.githubusercontent.com/51336081/178088183-a468c441-5748-4c7b-b624-12ac6882a865.png)

From the Figure above, we can see that we have three main blocks:
1- cloud
2- meter
3- Web Application(will be included in the cloud part)
We will discuss each block in details and how each block interacts with the other blocks


## 1-Software Architecture on cloud

- The Most Abstract view
![0](https://user-images.githubusercontent.com/51336081/166923748-ac36f080-fdf6-49d1-8171-de51265283b1.png)

- The detailed overview
- ![1 - Copy](https://user-images.githubusercontent.com/51336081/166923863-0eb90432-f77b-4b21-9264-2ed242524992.png)

We use the cloud to provide our services to the clients and to be the mediator between the meter and the client (web app and mobile app)

Our cloud system consists of three major sub-systems:

1- apis 
2-database
3- web application

We will discuss each in details:
## 1-APIs
Provides services like :
sending meter readings to be saved and to perform some financial processing on it to calculate the cost of the consumption of that meter
Send a Post request to the meter to turn it on or off
Send a GET request to the meter to get the results of the ML models that run on it.

### consumption API:
Provides services like: 
- Get the consumption of a given user for a specific period of time
- Calculate the meter consumption of money based on the readings sended to it and the where the meter allocated
 
### User API: 
- provide the user with services like view his information including meter information, consumption information, ..etc .
- Also the capabilities to change any of his saved information on our system, like email, phone number, password, …etc.

### Payment API:
- Perform the payment process by online payment with integration with Stripe(Stripe is a suite of APIs powering online payment processing).

### Bill API
- Generate the current bill that contains all the consumption of that user since the last payment date
- Also the capability of providing the user with any past bills he performed by the date he/she chooses.

### Event API
- Perform the necessary work when tampering happened as our meter is capable of detecting tampering and generate signals that send a request to our system on the cloud, specially the event api to inform the company that there’s tampering happened and also provide it with all the required information like which meter, user, and what time stamp of that tampering.

Of course some of these APIs collaborate with each other to fullfile the request.
For example the payment api uses the bill api to know how much money is in the debt of that user.
Also the user api uses both the consumption api and meter api and so on.

## 2- Database
Databases represent the data storage layer for our system on cloud.
Why do we need a database?
Database is a storage for persistence data, and our project have a lot of data that needs to be persisted(saved for a period and not Temporary)
 
### We need database to:
- save meters basic information
- save users basic information
- save each meter readings
- save the consumption(energy and money) of each meter
- save the payments of users

the schema of our database is shown below

![erd drawio](https://user-images.githubusercontent.com/51336081/178088651-4782dc54-3123-4afb-8088-53755d9fd045.png)


### When we started the designing phase of our system we asked ourselves some very important questions:
1-which database type to use?relational or non-relational database (no sql)?
2-do we need to use database replication or not?
3-do we need database scaling?
**Our answers to these questions are based on our needs and our project scope**

### Our answers all these three questions:
1- We definitely need a relational database as we deal with relational data like users, meters of these users, readings of these meters, events of these meters, bills of these users, … etc.

### 2- Database Replication:
![Screenshot 2022-07-06 093655](https://user-images.githubusercontent.com/51336081/178088738-f901f988-167a-4fce-9c78-f3aaf7a5f5de.png)

In database replication, a master database generally only supports write operations. A slave database gets copies of the data from the master database and only supports read operations. All the data-modifying commands like insert, delete, or update must be sent to the master database. Most applications require a much higher ratio of reads to writes;thus this isn’t our case as we write more than we read from the database.
**So we don’t need database replication.**

### 3- Database scaling
There Are two types of scaling: 
- vertical scaling
- horizontal scaling

### Vertical scaling: 
Vertical scaling is about adding more hardware (cpu, rams,.. etc.)
But it has limitations as at some point when the users and requests become very large(milions) and you need to add more hardware and of course there’s hardware 
limitations.

#### Draw backs:
- Greater risk of single point of failures. 
- The overall cost of vertical scaling is high. Powerful servers are much more expensive.

### Horizontal scaling:
Also known as sharding, it is the process of adding more servers.
And because we have millions of users and very large number of request to the database, we will need to shard our database as shown in figure 7.7
 
 ![shard drawio](https://user-images.githubusercontent.com/51336081/178088821-d5f27dad-df89-4b95-af05-7552a04b18df.png)


## - Web Application

Our web app consumes the REST APIs we ‘ve built to provide the user with all the functionality and services that the user might think of or need to guarantee the highest level of user’s satisfaction. 

### design 
- Our web app is based on spring mvc.
- We ‘ve used thymeleaf to render the html pages(templates) from the server and binding data models to it

We have used the MVC which is a design pattern that emphasizes a separation between the software's business logic and display. This "separation of concerns'' provides for a better division of labor and improved maintenance
 
### Interacting with a simple web application
In a web application, there are two components: the client that sends HTTP requests, and the server, which sends HTTP responses back. In the case of a web browser client, the responses the server sends need to be in the format of HTML, the document language of the web. The HTML that is sent to the client both defines the data that the user sees, as well as the actions a user can take - things like buttons, links, and input forms are all part of what the server is responsible for generating.

## - Meter Software System :

- ![30](https://user-images.githubusercontent.com/51336081/166924016-9626a6a7-1357-4203-8056-b9fa777766dd.png)

Form figure above we can see we have 4 main components:

- Web server
- SQLite DataBase
- Services
- Message Queue
 
## Web Server:

![server on meter need drawio (4)](https://user-images.githubusercontent.com/51336081/178089051-f54097e3-9d30-4662-b24d-879e6d2e63f8.png)

As shown in the figure above, we need to be able to communicate with the meter and the meter to be able to communicate with us.
So we had the need to implement a web server on the meter itself.
As usual we needed to ask ourselves in the designing phase some questions:

- Q1 Which web server to use?
- Q2 which programming language to use?
- Q3 Do we need a toolkit or framework?
- Q4 which database to use?

**Our answers to these questions are based on our needs and our project scope**

- A1 our project is embedded linux which is targeted to run on the f1c200, so we need the possible lightest web server which is lighttpd.

- A2 Because we have very limited resources, we need the lightest possible programing language which C

- A3 yes we need a toolkit as we use C as a programming language some low level layer will be too hard and time consuming to handle, so we will use gSOAP as the toolkit for C.

- A4 we need to save relational data so we will use a relational database NOT nosql.
Also we need the lightest possible database to run locally on the meter, so our choice is SQLite

### Database on meter:

We need a local database to save readings and events temporarily until sending it to the server on the cloud.

we used SQLite database because it's a very lite weighted and relational database.

### Services:
We used the daemons' architecture.

Daemon is a computer program that runs as a background process, rather than being under the direct control of an interactive user. When the system boot is complete, the system initialization process starts spawning (creating) daemons through a method called forking, eliminating the need for a terminal (this is what is meant by no controlling terminal).

We choose this architecture for many reasons:

- 1 our services works for back ground
- 2 we have more than one service
- 3 our services are always running
- 4 if one service faced an error, it can restart it self

### Message Queue:
A message queue is a durable component, stored in memory, that supports asynchronous communication.
It serves as a buffer and distributes asynchronous requests.

With the message queue, a producer can send a message to the consumer when the consumer isn’t available, and vice-versa.

We have faced a problem that required us to use the message queue architecture.

The problem is DATABASE LOCKING!!

When we try to perform concurrency operations on the database, the database is locked until one finishes, then unlocked to let the other operation operate.

That happened when we tried to read and write from and to the same database table “Readings” at the same time. As shown in figure below:
![image](https://user-images.githubusercontent.com/51336081/178089177-a8a54f33-0f77-4ca0-9296-b12b7fb34b90.png)

So, the solution is to use message queue to hold the new readings values that supposed to be saved to the database but locked because another service (read from database) is running, 
And send to be sent to the server directly as shown in Figure below:

![softwareonmeter drawio (1)](https://user-images.githubusercontent.com/51336081/178089149-9790e256-cedf-4d61-a398-016a4e02e739.png)

## Used Tools:
- Spring Boot, Date, Security
- Stripe as payment processor
-  mssql
