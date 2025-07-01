### O que ele faz:
- Registrar líquidos consumidos
    - Pode ser ou não saudável para a saúde
- Registrar solidos (comida)
- Remedios/Suplementos
- Definir metas de alimentação
    - Quantidade de liquido que deve ser ingerido por dia
    - Lembrar de tomar suplementos
    - Outros

### Modelagem
- **V1**: [link](https://dbdiagram.io/d/NutriTrack-v1-6722ad3b2c337ee119f19e44)

### Eventos

__base event__
```json
{
    "type": "EVENT_TYPE",
    "person_id": "igor_alves",
    "datetime": "2024-01-23T14:40",

    "meta_data":{
        "origin": "application_name",
        "created_at": "2024-01-23T14:45"
    }
}
```
- **meta_data.origin**: Aplicação que lançou o evento
- **meta_data.created_at**: Hora em que o evento foi lançado

__liquid event__
```json
{
    "type": "HEALTH.NUTRI_TRACK.LIQUID.V1",
    "person_id": "igor_alves",
    "datetime": "2024-01-23T14:40",
    "liquid": "water",
    "amount": 200,
    "meta_data": {}
}
```
- **amount**: Inteiro em ml

__solid event__
```json
[{
    "type": "HEALTH.NUTRI_TRACK.SOLID.V1",
    "person_id": "igor_alves",
    "datetime": "2024-01-23T14:40",
    "meal": "Jantar",
    "food": "arroz",
    "weight": 200,
    "meta_data": {}
},
{
    "type": "HEALTH.NUTRI_TRACK.SOLID.V1",
    "person_id": "igor_alves",
    "datetime": "2024-01-23T14:40",
    "meal": "Café da manha",
    "food": "banana",
    "amount": 2,
    "meta_data": {}
}]
```
- **weight**: Inteiro em gramas
- **amount**: Inteiro em unidades


-----
-----
-----
-----
-----

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

