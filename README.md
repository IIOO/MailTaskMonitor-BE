# Task Monitor Backend App demo

### Due to significant change in requirements, this demo repository is currently suspended
Authentication, fetching emails, downloading attachments on local disk, mappings work.

You can test it:
* Change following lines of _application.properties_ to configure database and test mail (gmail)
```$xslt
spring.datasource.url=jdbc:postgresql://localhost:[port]/[db name]
store.connection.host=imap.gmail.com
store.connection.username=[enter your test email address]
store.connection.password=[enter account password]

mail.download.attachment.path=[enter path to download mail attachments]
```
* Run TaskApplication.java
* Run sample requests from attached postman project: src/main/resources/TaskMonitor.postman_collection.json

### Login
To login use endpoint /api/authorize with basic auth and sample user data (configured in Bootstrap.java)
eg:
AUser / password

Token from response will be saved in postman environment variable and used in following requests.

## Most interesting things to test:
### Fetching all emails matching criteria to database.
__Criteria:__
* From address existing in database (table: mail_address, assigned to some existing company)
* Subject matching regex from SubjectSearchUtil.java sample matching subjects:
```
Fwd zlecenie odprawy numer 4
Zlecenie odprawy numer 10
zlecenie odprawy numer 4
Zlecenie odprawy nr:5
```

__Steps:__
1. send email from address existing in mail_address table to address configured in application.properties - store.connection.username
__(bootstrap configuration allow sending from store.connection.username to itself)__, with subject matching criteria
2. make request: /api/mail/fetch
3. check response body & table mail_task

### Receiving email matching criteria while application is running.
1. send email matching criteria described above
2. check logs & table mail_task

### Downloading email attachments on local disk.
1. make request /api/mail/{message_number}/attachment/download
__message number is no longer used in app because it's not always unique but it can be obtained in debugger from object message (f.e getMails method)__
2. check path configured in application.properties - mail.download.attachment.path

## Description
**Task management application**

Fetch messages matching specified criteria from mail server, actively listen for new incoming messages. Assign them to users,
track their status and history of changes.


# Tech stack
* Java 8
* Spring Boot, Security
* JavaMail
* PostgresSQL
* Lombok
* Maven
