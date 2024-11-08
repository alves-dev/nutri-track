# nutri-track

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre o Quarkus, visite seu website: <https://quarkus.io/>.

## Executando o aplicativo no modo dev

Você pode executar seu aplicativo no modo de desenvolvimento que permite codificação ao vivo usando:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  O Quarkus agora vem com uma Dev UI, que está disponível no modo dev apenas em <http://localhost:8080/q/dev/>.

## Empacotando e executando o aplicativo

O aplicativo pode ser empacotado usando:

```shell script
./gradlew build
```

Ele produz o arquivo `quarkus-run.jar` no diretório `build/quarkus-app/`.
Esteja ciente de que não é um _über-jar_ pois as dependências são copiadas para o diretório `build/quarkus-app/lib/`.

O aplicativo agora pode ser executado usando `java -jar build/quarkus-app/quarkus-run.jar`.

Se você deseja construir um _über-jar_, execute o seguinte comando:
```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

O aplicativo, empacotado como um _über-jar_, agora pode ser executado usando `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Ou, se você não tiver o GraalVM instalado, você pode executar a compilação executável nativa em um contêiner usando:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./build/nutri-track-1.0.0-SNAPSHOT-runner`
Se você quiser saber mais sobre como construir executáveis nativos, consulte <https://quarkus.io/guides/gradle-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
