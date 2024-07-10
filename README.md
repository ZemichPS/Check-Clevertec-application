# Check clevertec application
### PROJECT POWERED BY HEXAGONAL ARCHITECTURE WITH DDD APPROACH

solution of the test task of the company Clevertec

## Description
The project solves the problem of processing and storing the user's order information.

### Requirements:
- [JAVA 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.zip);
- [Apache Maven v. 3.9.1 and latest](https://dlcdn.apache.org/maven/maven-3/3.9.8/source/apache-maven-3.9.8-src.zip).

### To run the application do the following:
- download the project from the github repository [Check-Clevertec-application](https://github.com/ZemichPS/Check-Clevertec-application);
- navigate to the project folder
- Run the command "mvn clean" in the terminal
- Run the command "mvn compile" in the terminal
- Run the command with requared arguments "java -cp target/classes ru.clevertec.check.CheckRunner 3-1 2-5 5-1 discountCard=1111 balanceDebitCard=100"
where:
    * `CheckRunner` - CheckRunner.class the main entrypoint
    * `3-1 2-5 5-1` - the `product id` and `product quantity` parameter set;
    * `discountCard=1111` - the `1111` discount card number;
    * `balanceDebitCard=100` - the `100` debit card balance.
 
  ### The following patterns were used in the development process:
    * `Adapter` - base of hexagonal architecture;
    * `Factory method`; 
    * `Strategy`;
  

