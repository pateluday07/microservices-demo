# microservices-demo
This project is to demonstrate the **Microservice Architecture**, 
so here I have included **two Microservices** which are **forex** 
based.

We have here **Zuul API Gateway** which is the **proxy** for our 
microservices, and it takes care of the **load balancing** and 
forwards the request accordingly to the particular service.

We have here **Eureka Server** which registers the **Services** and 
**Zuul API Gateway**. 

This is a high-level overview of what I have built, and the detailed 
information for all the components available below.

### Requirements To Run Applications
* Git (Optional)
* JDK 8 or later
* IDE

### Dependencies And Tools Used To Build Applications
* Git
* JDK 8 or later
* Gradle 6.3
* Spring Boot Web
* Spring Boot Data JPA
* H2 Database
* Lombok
* IDE
* Eureka Client
* Eureka Server
* Zuul Proxy
* Hystrix
* Feign

### Eureka Server
Eureka Server is an application that holds information about all 
client-service applications. 

Every Microservice will register into the Eureka server and the 
Eureka server knows all the client applications running on each 
port and IP address. 

Eureka Server is also known as Discovery Server.

##### To Run "eureka-server"
1. Go to the `/microservices-demo/eureka-server` directory and 
open terminal

2. Execute the following command in the terminal

       gradlew bootrun 
       
And now we can access the **Eureka Server** on this link: 
[Eureka Server](http://localhost:8761/)

You will see here registered **Services** and **Zuul API Gateway** 
once we start them.

### forex-rates-service
It provides exchange rates for the different currencies, for example,
if I want to know the rate **USD** to **INR**, **EUR** to  **INR**, 
or **AUD** to **INR** so this service will provide the exchange 
rate. The response will be like the following.
```json
{
    "id": 1,
    "from": "USD",
    "to": "INR",
    "rate": 75.56,
    "port": 8001
}
```
```json
{
    "id": 2,
    "from": "EUR",
    "to": "INR",
    "rate": 81.65,
    "port": 8001
}
```
```json
{
    "id": 3,
    "from": "AUD",
    "to": "INR",
    "rate": 48.60,
    "port": 8001
}
```
Once you start this service these records will be available by 
default, and it will be stored in memory in the H2 database.

##### To Start "forex-rates-service"
1. Go to the `/microservices-demo/forex-rates-service` directory and 
open terminal

2. Execute the following command in the terminal

       gradlew bootrun 
       
At this point you are might thinking that what are the APIs we can 
use to access this service. But don't worry we will talk about the 
APIs in the **Zuul API Gateway** section.

### forex-converter-service
This service basically converts a particular amount to another 
currency let's say I have **5 dollars** and I want to convert it to 
the **Indian rupees** so for that purpose, we can use this service.
Here the response sample we get from this service.
```json
{
    "id": 1,
    "from": "USD",
    "to": "INR",
    "rate": 75.56,
    "amount": 5,
    "convertedAmount": 377.80,
    "port": 9001
}
```
In the above example, we are converting **5 dollars** to the 
**Indian rupees** and it is giving us the converted amount.Guess what
we are taking conversion rate from **forex-rates-service** and then 
in this service, we are multiplying the **rate** with the given 
**amount**. 

For the communication between services we are using **Feign** here, 
so it takes care to call instances dynamically and balance the load 
accordingly.

This service calls the **forex-rates-service** so in order to make 
this service **fault-tolerant** and **resilient** we have implemented
the **Hystrix** mechanism.

##### To Start "forex-converter-service"
1. Go to the `/microservices-demo/forex-converter-service` directory and 
open terminal

2. Execute the following command in the terminal

       gradlew bootrun 
        
### Zuul API Gateway
Zuul Server is an API Gateway application. It handles all the 
requests and performs the dynamic routing of microservice 
applications. It works as a front door for all the requests. It is 
also known as Edge Server.

Zuul is built to enable dynamic routing, monitoring, resiliency, and
security. Here we are covering dynamic routing and resiliency.

We have implemented Hystrix to make our gateway resilient, so we 
have a fallback mechanism here, in case the services are not 
available, so the user can get a nice readable message instead of 
nasty errors.

##### To Start "zuul-gateway-service"
1. Go to the `/microservices-demo/zuul-gateway-service` directory and 
open terminal

2. Execute the following command in the terminal

       gradlew bootrun 

And now we can access the services through this gateway. I request 
you to start multiple instances of the **forex-rates-service** and 
**forex-converter-service**. So you can see that the requests are 
forwarding to the multiple instances to balance the load.

##### List of APIs To Access "forex-rates-service"
* [USD to INR](http://localhost:7001/forex-rates/api/exchange-rates/from/USD/to/INR)
* [EUR to INR](http://localhost:7001/forex-rates/api/exchange-rates/from/EUR/to/INR)
* [AUD to INR](http://localhost:7001/forex-rates/api/exchange-rates/from/AUD/to/INR)

##### List of APIs To Access "forex-converter-service"
* [USD to INR](http://localhost:7001/forex-converter/api/currency-converter/from/USD/to/INR/amount/5)
* [EUR to INR](http://localhost:7001/forex-converter/api/currency-converter/from/EUR/to/INR/amount/10)
* [AUD to INR](http://localhost:7001/forex-converter/api/currency-converter/from/AUD/to/INR/amount/15)
