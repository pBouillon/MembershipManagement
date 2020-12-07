# Build container
FROM gradle:jre15 AS GRADLE_BUILD

COPY ./ ./

RUN gradle clean \
    && gradle build -x test

# Runtime environment container
FROM gradle:jre15

# Downloading the "wait-for-it" utility to allow the docker-compose to wait for other services
RUN apt-get update \
    && apt-get install -y "wait-for-it"

COPY --from=GRADLE_BUILD ./home/gradle/build/libs/monitoring-0.0.1-SNAPSHOT.jar /monitoring.jar

CMD ["java", "-jar", "/monitoring.jar"]
