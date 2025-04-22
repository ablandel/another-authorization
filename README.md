# Another Authorization (╯°□°）╯︵ 👮‍

Simple project to test Maven + Kotlin + Keycloak + OpenFGA + GraalVM native images + Spring Boot.

## Start the project

A `compose.yaml` file is provided to easier the developments. The OpenFGA instance is defined in the
`openfga.compose.yaml` file to be able to import the authorization model and configure the `another-server` instance
before its execution. Another instances can be used; in this case the `application.yaml` may have to be updated.

**In any setup, the OpenFGA instance MUST BE manually imported and configured.**

First, start the OpenFGA instance via the `openfga.compose.yaml` compose file:

```shell
docker compose -f openfga.compose.yaml up
```

Then, import the authorization model defined in `openfga_model.fga` via the OpenFGA playground
(at http://localhost:3000/playground) or via the OpenFGA APIs, update the `another-server` `openfga.store-id` and
`openfga.authorization-model-id` values with the mounted OpenFGA instance.

Finally, start the other third-party instances:

```shell
docker compose up
```

### Start the server

The server can be run using `maven`:

```shell
./mvnw -pl another-server spring-boot:run
```

### Start the client

The server used as client can be run using `maven`:

```shell
./mvnw -pl another-client spring-boot:run
```

## Allow / Forbid access to the `restricted` place

With the OpenFGA playground (at http://localhost:3000/playground) or via the OpenFGA APIs, create or remove the
following tuple to make the GET request return the `restricted` place or a forbidden response to the client.

```
USER        user:39c0dc18-19e5-425a-aff8-daf9ec5d8e94
RELATION    participant
OBJECT      place:restricted
```

## Docker image build

A `local.compose.yaml` file is provided to easier the test using Docker images. For the moment, the Docker image are not
pushed and need to be locally built. Check the [Troubleshooting](#troubleshooting) before building the images.

Build the server image (native):

```shell
./mvnw -pl another-server -Pnative -Dspring-boot.build-image.imageName=another-authorization-server:local spring-boot:build-image
```

Build the client image (native):

```shell
./mvnw -pl another-client -Pnative -Dspring-boot.build-image.imageName=another-authorization-client:local spring-boot:build-image
```

### Start the docker compose with the native images

```shell
docker compose -f local.compose.yaml up
```

## Troubleshooting

Missing native configurations can be extracted using the GraalVM native agent. It is probably not the most optimized
manner to grab and reference the missing configurations, but it can fix broken features. If the images are not fully
functional, try to extract the configurations after running the client and the server against the infra, then build the
native images again.

For instance:

```shell
~/.jdks/graalvm-ce-21.0.2/bin/java -agentlib:native-image-agent=config-output-dir=./another-server/src/main/resources/META-INF/native-image -jar another-server/target/another-server-1.0.0.jar
```

Missing configurations have been already identified to have functional check requests to the OpenFGA instance. The
extraction of all server configurations with the previous command and with the client running fix the check request
failures.
