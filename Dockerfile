FROM openjdk:17
EXPOSE 8088
ADD target/email-application-0.0.1-SNAPSHOT.jar email-application-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/email-application-0.0.1-SNAPSHOT.jar"]