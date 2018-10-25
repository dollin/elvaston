package org.elvaston.model.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import javax.json.JsonObject;

/**
 * Unit tests for creating a Transaction from the DefaultTransactionBuilder.
 */
public class RandomTransactionBuilderTest {

    @Test
    public void buildWithRandomId() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        assertNotNull(json.getString("id"));
    }

    @Test
    public void buildWithRandomDescription() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        assertNotNull(json.getString("description"));
        assertNotEquals("", json.getString("description"));
    }

    @Test
    public void buildWithRandomQuantity() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        assertThat(json.getJsonNumber("quantity").bigDecimalValue(), greaterThan(BigDecimal.ZERO));
    }

    @Test
    public void buildWithRandomAmount() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        assertThat(json.getJsonNumber("amount").bigDecimalValue(), greaterThan(BigDecimal.ZERO));
    }

    @Test
    public void buildWithRandomCurrency() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        boolean matched = Arrays.stream(Currency.values())
                .anyMatch(currency -> currency == Currency.valueOf(json.getString("currency")));
        assertTrue("Checking " + json.getString("currency") + " is in the Currency enum", matched);
        assertNotEquals(Currency.NOT_SET.toString(), json.getString("currency"));
    }

    @Test
    public void buildWithRandomSource() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        boolean matched = Arrays.stream(Entity.values())
                .anyMatch(source -> source == Entity.valueOf(json.getString("source")));
        assertTrue("Checking " + json.getString("source") + " is in the Entity enum", matched);
        assertNotEquals(Entity.NOT_SET.toString(), json.getString("source"));
    }

    @Test
    public void buildWithRandomDestination() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        boolean matched = Arrays.stream(Entity.values())
                .anyMatch(destination -> destination == Entity.valueOf(json.getString("destination")));
        assertTrue("Checking " + json.getString("destination") + " is in the Entity enum", matched);
        assertNotEquals(Entity.NOT_SET.toString(), json.getString("destination"));
    }

    @Test
    public void buildWithRandomState() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        boolean matched = Arrays.stream(State.values())
                .anyMatch(state -> state == State.valueOf(json.getString("state")));
        assertTrue("Checking " + json.getString("state") + " is in the State enum", matched);
        assertNotEquals(State.NOT_SET.toString(), json.getString("state"));
    }

    @Test
    public void buildWithRandomCreated() {
        JsonObject json = new RandomTransactionBuilder().build().print(new JsonMedia()).json();
        assertThat(json.getJsonNumber("created").longValue(), greaterThan(0L));
    }
}
