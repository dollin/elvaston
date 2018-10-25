package org.elvaston.model.impl;

import org.elvaston.model.api.Transaction;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * General builder to help create a {@code Transaction}.
 */
public class TransactionBuilder {

    String id = null;
    //TODO add a TradeDate
    String description = "";
    BigDecimal quantity = BigDecimal.ZERO;
    BigDecimal amount = BigDecimal.ZERO;
    Currency currency = Currency.NOT_SET;
    Entity source = Entity.NOT_SET;
    Entity destination = Entity.NOT_SET;
    State state = State.NOT_SET;
    long created = 0L;

    /**
     * Non null id to use.
     * @param id to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withId(String id) {
        if (Objects.nonNull(id)) {
            this.id = id;
        }
        return this;
    }

    /**
     * Builder w/ description to use.
     * @param description to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withDescription(String description) {
        if (Objects.nonNull(description)) {
            this.description = description;
        }
        return this;
    }

    /**
     * Builder w/ amount to use.
     * @param amount to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withAmount(BigDecimal amount) {
        if (Objects.nonNull(amount)) {
            this.amount = amount;
        }
        return this;
    }

    /**
     * Builder w/ quantity to use.
     * @param quantity to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withQuantity(BigDecimal quantity) {
        if (Objects.nonNull(quantity)) {
            this.quantity = quantity;
        }
        return this;
    }

    /**
     * Builder w/ currency {@link Currency} to use.
     * @param currency to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withCurrency(Currency currency) {
        if (Objects.nonNull(currency)) {
            this.currency = currency;
        }
        return this;
    }

    /**
     * Builder w/ source {@link Entity} to use.
     * @param source to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withSource(Entity source) {
        if (Objects.nonNull(source)) {
            this.source = source;
        }
        return this;
    }

    /**
     * Builder w/ stats {@link State} to use.
     * @param state to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withState(State state) {
        if (Objects.nonNull(state)) {
            this.state = state;
        }
        return this;
    }

    /**
     * Builder w/ destination {@link Entity} to use.
     * @param destination to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withDestination(Entity destination) {
        if (Objects.nonNull(destination)) {
            this.destination = destination;
        }
        return this;
    }

    /**
     * Builder w/ created time as a {@code long}.
     * @param created to set on the builder
     * @return TransactionBuilder
     */
    public TransactionBuilder withCreated(long created) {
        if (created > 0L) {
            this.created = created;
        }
        return this;
    }

    /**
     * builds a Transaction impl from the values passed to the builder.
     * @return Transaction
     */
    public Transaction build() {
        validate();
        return new TransactionImpl(id,
                description,
                quantity,
                amount,
                currency,
                source,
                destination,
                state,
                created);
    }

    private void validate() {
        if (Objects.isNull(id)) {
            throw new IllegalStateException("[id] is missing a mandatory value");
        }
    }
}
