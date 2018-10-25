package org.elvaston.model.impl;

import org.elvaston.model.api.Media;
import org.elvaston.model.api.Transaction;

import java.math.BigDecimal;

/**
 * TODO Add javadoc.
 */

public class TransactionImpl implements Transaction {

    private String id;
    private String description;
    private BigDecimal quantity;
    private BigDecimal amount;
    private Currency currency;
    private Entity source;
    private Entity  destination;
    private State state;
    private long created;

    TransactionImpl(String id,
                    String description,
                    BigDecimal quantity,
                    BigDecimal amount,
                    Currency currency,
                    Entity source,
                    Entity destination,
                    State state,
                    long created) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.currency = currency;
        this.source = source;
        this.destination = destination;
        this.state = state;
        this.created = created;
    }

    public Media print(Media media) {
        return media
                .with("id", id)
                .with("description", description)
                .with("currency", String.valueOf(currency))
                .with("quantity", quantity)
                .with("amount", amount)
                .with("source", String.valueOf(source))
                .with("destination", String.valueOf(destination))
                .with("state", String.valueOf(state))
                .with("created", created);
    }

    @Override
    public String toString() {
        JsonMedia media = new JsonMedia();
        print(media);
        return String.format(
                "TransactionImpl: %s",
                media.json()
        );
    }
}
