## Kafka Help Starting the Infrastructure: -
This is a quick startup helper taken from [kafka.apache.org](https://kafka.apache.org/quickstart)

1. First, from the kafka install directory, for me this is at: C:\Users\elvastooon5\kafka_2.11-2.0.0

2. Start ZOOKEEPER
```bash
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

3. Start SERVER
```bash
bin\windows\kafka-server-start.bat config\server.properties
```

4. Create TOPIC:
```bash
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 100 --topic test
```

5. List TOPICS:
```bash
bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
```

6. PRODUCER FROM CMD:
```bash
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test
```

7. CONSUMER FROM CMD:
```bash
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
```
