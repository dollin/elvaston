To start the Kafka infrastructure: -
From directory C:\Users\elvastooon5\kafka_2.11-2.0.0
Start ZOOKEEPER: bin\windows\zookeeper-server-start.bat config\zookeeper.properties
Start SERVER: bin\windows\kafka-server-start.bat config\server.properties
Create TOPIC:
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 100 --topic test
List TOPICS:
bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
PRODUCER FROM CMD:
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test
CONSUMER FROM CMD:
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning