FROM openjdk:21-oraclelinux8
WORKDIR /app
COPY ark-center-product.jar /app
EXPOSE 8083
ENV SYS_OPT=""
ENTRYPOINT java $SYS_OPT -jar /app/ark-center-product.jar