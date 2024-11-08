FROM amazoncorretto:21

COPY build/nutri-track-2-runner.jar /app/application.jar

# Set the timezone to São Paulo
RUN ln -snf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && echo America/Sao_Paulo > /etc/timezone

CMD java -jar app/application.jar




Desenhe uma logo para um projeto pessoal, o projeto se chama SyncLife,
o projeto em se consiste em desenvolvimento de vários micro serviços para realizar o mapeamento de minhas atividades diárias,
 além de registrar alimentação e outros, idealmente a logo deve conter um elemento que remeta a programação