FROM gradle:8.5.0-jdk21

##
ENV PORT=7070

WORKDIR /app

#COPY /app .

#RUN gradle installDist
#COPY gradle gradle
#COPY build.gradle.kts .
#COPY settings.gradle.kts .
#COPY gradlew .


#RUN ./gradlew --no-daemon dependencies

COPY app/src src
#COPY config config

#RUN ./gradlew --no-daemon build

ENV JAVA_OPTS "-Xmx512M -Xms512M"
#EXPOSE 7070
COPY app/. .

RUN gradle installDist

CMD ./build/install/app/bin/app