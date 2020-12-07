# Build container
FROM gradle:jre15 AS GRADLE_BUILD

COPY ./ ./

RUN gradle clean \
    && gradle build -x test

# Runtime environment container
FROM gradle:jre15

COPY --from=GRADLE_BUILD ./home/gradle/build/libs/monitoring-0.0.1-SNAPSHOT.jar /monitoring.jar

CMD ["java", "-jar", "/monitoring.jar"]
