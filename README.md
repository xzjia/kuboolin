# What is this

This is a toy project which implements a counter following [this tutorial](https://todd.ginsberg.com/post/springboot2-reactive-kotlin/).
Deployed it on a Kubernetes cluster.

* Using `Kotlin` and Spring Boot 2.0 as backend
  * There is a Redis instance storing the counter state
* Using `Vue` as frontend

# How to start

There are two ways to get start

1.  Run the standalone container. You can check the available tag names [here](https://hub.docker.com/r/xzjia/kuboolin/tags/)
2.  Run from the source code

## Run the standalone container

Docker is necessary to run this.
`docker run --rm -p 8888:8080 xzjia/kuboolin:${TAG_NAME}`

## Run from the source code

### Server side

To spin up the server side, do the following.

```bash
# First have a redis instance running on localhost:6379

./gradlew bootRun

# Or build it into a jar and boot from there
./gradlew clean build
java -jar java -jar build/libs/kuboolin-0.0.1-SNAPSHOT.jar
```

Then, you can see the results on `localhost:8080`. I'm using [HTTPie](https://httpie.org/) here as a replacement of `curl`.

```bash
$ http :8080/api/counter
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
transfer-encoding: chunked

{
    "asOf": "2018-05-31T16:32:51.384",
    "by": "JiaXiaozhous-MacBook-Pro.local",
    "value": 630
}


$ http PUT :8080/api/counter/up
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
transfer-encoding: chunked

{
    "asOf": "2018-05-31T16:32:57.932",
    "by": "JiaXiaozhous-MacBook-Pro.local",
    "value": 631
}


$ http PUT :8080/api/counter/down
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
transfer-encoding: chunked

{
    "asOf": "2018-05-31T16:33:01.801",
    "by": "JiaXiaozhous-MacBook-Pro.local",
    "value": 630
}


$ http --stream :8080/api/counter Accept:text/event-stream
HTTP/1.1 200 OK
Content-Type: text/event-stream;charset=UTF-8
transfer-encoding: chunked

data:{
data:  "value" : 630,
data:  "type" : "UP",
data:  "at" : "2018-05-31T16:32:38.058",
data:  "by" : "JiaXiaozhous-MacBook-Pro.local"
data:}

data:{
data:  "value" : 631,
data:  "type" : "UP",
data:  "at" : "2018-05-31T16:32:57.933",
data:  "by" : "JiaXiaozhous-MacBook-Pro.local"
data:}

data:{
data:  "value" : 630,
data:  "type" : "DOWN",
data:  "at" : "2018-05-31T16:33:01.802",
data:  "by" : "JiaXiaozhous-MacBook-Pro.local"
data:}
```

## Client side

To run a develop server, first navigate into `kuboolin-board` and then run `yarn install & yarn dev`.
To make a production build, run `yarn build`.
NOTE: the production built files are located at `./src/main/resources/public`

# Deployment

```bash
# First build the front end, inside kuboolin-board
yarn build

# Build the jar file
./gradlew clean build

# Build and push the docker image
docker build -t xzjia/kuboolin:${TAG_NAME} .
docker push xzjia/kuboolin:${TAG_NAME}

# Change the image in Kubernetes deployment
kubectl set image deployment/kuboolin kuboolin=xzjia/kuboolin:${TAG_NAME}

# To scale up and down, simple do
kubectl scale deployment kuboolin --replicas=${HOW_MANY_PODS}
```

# Future work

* No health check from the load balancer
  * Figure out how to do this with Kubernetes
* Redis endpoint, back end endpoint are hard coded in code
  * Figure out how to setup `EventSource` without an absolute URL
* When there are more than one pods, updates on one pod will not be sent to other pods
  * Use something like Kafka to replace event bus
* Due to some cache issues on browser, there will be situations that
* Make it work with HTTPS, so that all static files can be served from an object storage
