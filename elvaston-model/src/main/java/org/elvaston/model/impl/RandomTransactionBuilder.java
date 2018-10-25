package org.elvaston.model.impl;

import org.elvaston.model.api.Transaction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * Generate a transaction w/ random default values if not already set.
 */
public class RandomTransactionBuilder {

    private TransactionBuilder builder = new TransactionBuilder();

    /**
     * Set the id.
     * @param id String
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withId(String id) {
        builder.withId(id);
        return this;
    }

    /**
     * Default id if not already set by {@link #withId(String)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withId() {
        if (Objects.isNull(builder.id)) {
            withId("id_" + UUID.randomUUID());
        }
        return this;
    }

    /**
     * Set the description.
     * @param description String 
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withDescription(String description) {
        builder.withDescription(description);
        return this;
    }

    /**
     * Default description if not already set by {@link #withDescription(String)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withDescription() {
        if (builder.description.equals("")) {
            withDescription("description_" + UUID.randomUUID());
        }
        return this;
    }

    public RandomTransactionBuilder withAmount(BigDecimal amount) {
        builder.withAmount(amount);
        return this;
    }

    /**
     * Default amount if not already set by {@link #withAmount(BigDecimal)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withAmount() {
        if (builder.amount.equals(BigDecimal.ZERO)) {
            withAmount(BigDecimal.valueOf(random()));
        }
        return this;
    }

    public RandomTransactionBuilder withQuantity(BigDecimal quantity) {
        builder.withQuantity(quantity);
        return this;
    }

    /**
     * Default quantity if not already set by {@link #withQuantity(BigDecimal)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withQuantity() {
        if (builder.quantity.equals(BigDecimal.ZERO)) {
            withQuantity(BigDecimal.valueOf(random()));
        }
        return this;
    }

    public RandomTransactionBuilder withCurrency(Currency currency) {
        builder.withCurrency(currency);
        return this;
    }

    /**
     * Default currency if not already set by {@link #withCurrency(Currency)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withCurrency() {
        if (builder.currency == Currency.NOT_SET) {
            int index = random() % Currency.values().length;
            index += Currency.NOT_SET.ordinal() == index ? 1 : 0;
            withCurrency(Currency.values()[index]);
        }
        return this;
    }

    public RandomTransactionBuilder withSource(Entity source) {
        builder.withSource(source);
        return this;
    }

    /**
     * Default source if not already set by {@link #withSource(Entity)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withSource() {
        if (builder.source == Entity.NOT_SET) {
            int index = random() % Entity.values().length;
            index += Entity.NOT_SET.ordinal() == index ? 1 : 0;
            withSource(Entity.values()[index]);
        }
        return this;
    }

    public RandomTransactionBuilder withDestination(Entity destination) {
        builder.withDestination(destination);
        return this;
    }

    /**
     * Default destination if not already set by {@link #withDestination(Entity)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withDestination() {
        if (builder.destination == Entity.NOT_SET) {
            int index = random() % Entity.values().length;
            index += Entity.NOT_SET.ordinal() == index ? 1 : 0;
            withDestination(Entity.values()[index]);
        }
        return this;
    }


    public RandomTransactionBuilder withState(State state) {
        builder.withState(state);
        return this;
    }

    /**
     * Default state if not already set by {@link #withState(State)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withState() {
        if (builder.state == State.NOT_SET) {
            int index = random() % State.values().length;
            index += State.NOT_SET.ordinal() == index ? 1 : 0;
            withState(State.values()[index]);
        }
        return this;
    }

    public RandomTransactionBuilder withCreated(long created) {
        builder.withCreated(created);
        return this;
    }

    /**
     * Default created if not already set by {@link #withCreated(long)} .
     * @return this DefaultTransactionBuilder
     */
    public RandomTransactionBuilder withCreated() {
        if (builder.created == 0L) {
            withCreated(System.currentTimeMillis());
        }
        return this;
    }

    /**
     * builds a Transaction impl from the values passed to the builder. Uses
     * a random selection of values (some values are fixed lists e.g. {@code Currency})
     * if they haven't already been set.
     * @return Transaction
     */
    public Transaction build() {
        withId();
        withDescription();
        withAmount();
        withQuantity();
        withCurrency();
        withSource();
        withDestination();
        withState();
        withCreated();
        return builder.build();
    }

    private int random() {
        Random random = new Random() ;
        return random.nextInt(1_000);
    }
}
