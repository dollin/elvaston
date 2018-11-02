package org.elvaston.kafka.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.elvaston.kafka.api.GatewayCache;
import org.elvaston.kafka.api.ProcessorState;
import org.elvaston.model.api.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayVerticleImpl extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayVerticleImpl.class);

    private final GatewayCache cache;

    GatewayVerticleImpl(GatewayCache cache) {
        this.cache = cache;
    }

    @Override
    public void start(Future<Void> future) {

        Router router = Router.router(vertx);
        router.route("/api/send*").handler(BodyHandler.create());
        router.post("/api/send").handler(this::send);
        router.get("/api/query/:id").handler(this::query);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }
                });
    }

    private void send(RoutingContext context) {
        JsonObject json = context.getBodyAsJson();
        LOG.info("send json: {}", json);

        //TODO Validate, throw error if incomplete;
        Transaction transaction = json.mapTo(Transaction.class);
        cache.add(transaction);
        context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end("{id: " + json.getString("id") + ", state: PENDING}");

    }

    private void query(RoutingContext context) {
        String id = context.request().getParam("id");
        ProcessorState state = cache.stateOf(id);
        LOG.info("query id: {}, state: {}", id, state);

        context.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end("{id: " + id + ", state: " + state + "}");
    }
}
