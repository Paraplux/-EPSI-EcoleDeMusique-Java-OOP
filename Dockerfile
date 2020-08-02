FROM openjdk:13
COPY ./out/production/EcoleDeMusique/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","com.marcbouchez.main.Main"]
