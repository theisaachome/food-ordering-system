## Running Docker compose files


### 1 Run zookeeper first

`
docker compose -f common.yml -f zookeeper.yml up
`

### Check if the zookeeper is up

`
echo ruok | nc localhost 2181 {zookeeper port}
`
#### it should return : output
``

~$: echo ruok | nc localhost 2181

imok%

``
### 2 Run Kafka cluster
`
docker compose -f common.yml -f kafka_cluster.yml up
`


### 3 Run Kafka init
`
docker compose -f common.yml -f init_kafka.yml up
`

kafka admin portal
localhost:9000

### 4 Stop the container with below command
docker compose -f {common.yml} -f {init_kafka.yml} down