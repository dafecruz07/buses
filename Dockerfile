FROM openjdk:14-alpine
COPY target/buses-*.jar buses.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "buses.jar"]