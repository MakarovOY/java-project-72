FROM czy21/jdk-21

##
ENV PORT=7070

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD ./build/install/app/bin/app