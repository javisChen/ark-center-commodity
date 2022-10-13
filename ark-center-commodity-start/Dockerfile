FROM openjdk:11-oraclelinux8
WORKDIR /app
COPY ./target/ark-center-commodity.jar /app
EXPOSE 8080
ENV SYS_OPT=""
ENTRYPOINT java $SYS_OPT -jar /app/ark-center-commodity.jar