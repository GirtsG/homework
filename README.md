##### Introduction
Homework application according to requirements from https://github.com/4finance/homework-lv
##### System requirements
Java 11 installed https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html
##### Start using
1) In project root directory execute ./gradlew bootRun
2) Navigate to root (e.g. http://localhost:8080) to view available REST API options 
##### Usage example
###### Create new loan when submitting loan application
curl -i -X POST -H "Content-Type:application/hal+json;charset=UTF-8" -d '{ 
"amount" : 500.12, 
"termInDays" : 30,
"ipAddress" : "127.0.0.1",
"borrowerPersonId" : "010190-10028",
"borrowerFullName" : "Juris Ozols"
}' http://localhost:8080/apply-for-loan

###### Extend loan
curl -i -X POST -H "Content-Type:application/hal+json;charset=UTF-8" -d '{ 
"termInDays" : 15,
"loanId" : 3
}' http://localhost:8080/extend-loan

###### Discover all links for data browsing in Spring Data REST root
User can view their loans (Customer Loans) and all other data by following links here:

curl http://localhost:8080/data
