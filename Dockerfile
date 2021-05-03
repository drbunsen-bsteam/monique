FROM openjdk:8
WORKDIR /
ADD target/monique-1.0-SNAPSHOT.jar monique.jar
RUN useradd -m myuser
USER myuser
EXPOSE 7090
CMD java -jar -Dspring.profiles.active=prod monique.jar
