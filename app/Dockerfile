FROM czy21/jdk-21

##
ENV PORT=7070

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD java -jar build/libs/app-1.0-SNAPSHOT-all.jar