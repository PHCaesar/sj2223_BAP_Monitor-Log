FROM openjdk:20 as builder
LABEL maintainer="Cserich Philipp"
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:20 as my-app
LABEL maintainer="Cserich Philipp"
COPY --from=builder dependencies/ ./
# COPY --from=builder internal-dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]