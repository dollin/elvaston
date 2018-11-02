package org.elvaston.kafka.processor;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elvaston.kafka.api.KafkaConsumer;
import org.elvaston.kafka.api.Processor;
import org.elvaston.kafka.common.KafkaPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link Processor} used to receive a payload from
 * a {@link KafkaConsumer} and send onto the webService.
 */
public class GatewayProcessorImpl implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayProcessorImpl.class);

    private Vertx vertx;

    public GatewayProcessorImpl() {
        vertx = Vertx.vertx();
    }

    @Override
    public void process(ConsumerRecords<Long, KafkaPayload> consumerRecords) {
        consumerRecords.forEach(record -> {
            LOG.info("sending to vertx service transaction: {}", record.value());

            WebClient client = WebClient.create(vertx);
            //TODO: Turn record into JsonObject
            client.post(8080, "localhost", "/api/send")
                    .sendJsonObject(new JsonObject().put("id", "1234566"), ar -> {
                        if (ar.succeeded()) {
                            LOG.info("send ok, transaction: {}", record.value().transaction());
                        } else {
                            LOG.error("unable to send transaction: {}. cause: {}",
                                    record.value().transaction(),
                                    ar.cause());
                        }
                    });
        });
    }

    @Override
    public void stop() {
        vertx.close(ar -> {
            if (ar.succeeded()) {
                LOG.info("vertx instance shut down successfully");
            } else {
                LOG.error("Failed to close vertx. cause: {}", ar.cause());
            }
            vertx = null;
        });
    }
}
