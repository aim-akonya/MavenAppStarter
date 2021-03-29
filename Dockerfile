FROM openjdk:8-jdk-alpine
MAINTAINER aim-akonya
COPY target/ProjectSetupTemplate.jar projectsetuptemplate.jar
ENTRYPOINT ["java"]
CMD ["-XX:MinHeapFreeRatio=10", "-XX:MaxHeapFreeRatio=70", "-XX:CompressedClassSpaceSize=64m", "-XX:ReservedCodeCacheSize=64m", "-XX:MaxMetaspaceSize=256m", "-Xms256m", "-Xmx750m", "-jar", "/projectsetuptemplate.jar"]
EXPOSE 8080
