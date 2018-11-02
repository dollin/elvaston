package org.elvaston.kafka.gateway;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Vertx service that create a VertxVerticle as the web service to
 * handle web and consumer requests.
 */
public class GatewayService {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayService.class);

    public static void main(String... args) {
        new GatewayService();
    }

    private GatewayService() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new GatewayVerticleImpl(new GatewayCacheImpl()));
        LOG.info("Started vertx service");
    }
}
