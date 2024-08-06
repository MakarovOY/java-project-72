FROM czy21/jdk-21

##
ENV PORT=7070

WORKDIR /app

#COPY /app .

#RUN gradle installDist
#COPY gradle gradle
#COPY build.gradle.kts .
#COPY settings.gradle.kts .
#COPY gradlew .
COPY /app .

RUN ./gradlew --no-daemon dependencies

COPY src src
#COPY config config

RUN ./gradlew --no-daemon build

ENV JAVA_OPTS "-Xmx512M -Xms512M"
EXPOSE 7070
RUN gradle installDist

CMD java -jar build/libs/app-1.0-SNAPSHOT-all.jar