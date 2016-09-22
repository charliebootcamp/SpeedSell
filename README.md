bootcamp
==============
Before RUN WebRunner please make Project -> Clean
 
## INITIAL SETUP FOR WEB 
1. Create a new DB on Postgres
2. create **hibernate-external.properties** file in **${user.home}/bootcamp/main**
3. create **portal.properties** file in **${user.home}/bootcamp/main**
4. run webapplication. DB will be upgraded to the latest version of patches
5. to login use - http://localhost:8082/login?userId=123&userName=userName&sessionId=&org=00Di0000000IuYU&server=
sessionId should be empty.
org - is company id

## INITIAL SETUP FOR RUNNING TESTS  
1. Create a new DB on Postgres
2. create **hibernate-external.properties** file in **${user.home}/bootcamp/test**

##Example of hibernate config content:
- hibernate.connection.url=jdbc:postgresql://localhost/bootcamp
- hibernate.connection.username=bootcamp
- hibernate.connection.password=bootcamp
- hibernate.c3p0.min_size=1
- hibernate.c3p0.max_size=10
- hibernate.c3p0.timeout=1800
- hibernate.c3p0.max_statements=20
- hibernate.show_sql=true

## List of portal.properties settings

## FlyWay
FlyWay does not need any configuration. It reads new db updates from /migration folder in .war and upgrades DB automatically
DB updates are stored in format: V<Number>__*.sql

## ANT TASKS 

###Build war file:
ant clean dist

###run tests:
ant test

### run test coverage
ant test-coverage

### clean db and generates an admin: administrator / 12345
ant cleandb
