FROM quay.io/quarkus/quarkus-micro-image:2.0

COPY build/nutri-track-3-runner /app

COPY src/main/resources/application.properties /application.properties

ENV TZ=America/Sao_Paulo

ENTRYPOINT ["/app"]