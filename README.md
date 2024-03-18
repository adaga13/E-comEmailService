# **Email Service**
Service to handle emails using kafka events after user signs up on user service.

## **Requirements**
* JDK 17+
* Maven 3
* Apache Kafka
* Gmail Account

## **Prerequisites**
Set these environment variables gmail application password.
`EMAIL_PASSWORD=<gmail_password>`

Follow this link to create app password
[https://support.google.com/mail/answer/185833?hl=en](https://support.google.com/mail/answer/185833?hl=en)


## **Running the application locally**
`mvn spring-boot:run`

It will run on port 8383

To create jar and execute jar file:

`mvn package`

This will create jar file inside target\E-comEmailService-1.0.0.jar
To execute jar file:

`java -jar E-comEmailService-1.0.0.jar`