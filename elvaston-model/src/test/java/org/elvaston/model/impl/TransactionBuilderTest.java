package org.elvaston.model.impl;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import javax.json.JsonObject;
import java.math.BigDecimal;

/**
 * Unit tests for creating a Transaction from the TransactionBuilder.
 */
public class TransactionBuilderTest {

    @Test(expected = IllegalStateException.class)
    public void whenIdIsNullThrowException() {
        new TransactionBuilder().build();
    }

    @Test
    public void buildWithId() {
        JsonObject json = new TransactionBuilder().withId("1234").build().print(new JsonMedia()).json();
        assertEquals("1234", json.getString("id"));
    }

    @Test
    public void descriptionIsBlankByDefault() {
        JsonObject json = new TransactionBuilder().withId("id").build().print(new JsonMedia()).json();
        assertEquals("", json.getString("description"));
    }

    @Test
    public void buildWithDescription() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withDescription("a desc")
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals("a desc", json.getString("description"));
    }

    @Test
    public void buildWithoutQuantityIsZero() {
        JsonObject json = new TransactionBuilder().withId("id").build().print(new JsonMedia()).json();
        assertEquals(BigDecimal.ZERO, json.getJsonNumber("quantity").bigDecimalValue());
    }

    @Test
    public void buildWithNullQuantityIsZero() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withQuantity(null)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(BigDecimal.ZERO, json.getJsonNumber("quantity").bigDecimalValue());
    }

    @Test
    public void buildWithQuantity() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withQuantity(BigDecimal.valueOf(49))
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(BigDecimal.valueOf(49), json.getJsonNumber("quantity").bigDecimalValue());
    }

    @Test
    public void buildWithoutAmountIsZero() {
        JsonObject json = new TransactionBuilder().withId("id").build().print(new JsonMedia()).json();
        assertEquals(BigDecimal.ZERO, json.getJsonNumber("amount").bigDecimalValue());
    }

    @Test
    public void withNullAmountIsZero() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withAmount(null)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(BigDecimal.ZERO, json.getJsonNumber("amount").bigDecimalValue());
    }

    @Test
    public void buildWithAmount() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withAmount(BigDecimal.valueOf(13.45))
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(BigDecimal.valueOf(13.45), json.getJsonNumber("amount").bigDecimalValue());
    }

    @Test
    public void currencyNotSet() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Currency.NOT_SET), json.getString("currency"));
    }

    @Test
    public void buildWithCurrency() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withCurrency(Currency.GBP)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Currency.GBP), json.getString("currency"));
    }

    @Test
    public void buildWithNullCurrency() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withCurrency(null)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Currency.NOT_SET), json.getString("currency"));
    }


    @Test
    public void buildWithSource() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withSource(Entity.CO)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Entity.CO), json.getString("source"));
    }

    @Test
    public void sourceNotSet() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Entity.NOT_SET), json.getString("source"));
    }

    @Test
    public void buildWithNullSource() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withSource(null)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Entity.NOT_SET), json.getString("source"));
    }

    @Test
    public void buildWithDestination() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withDestination(Entity.INTL)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Entity.INTL), json.getString("destination"));
    }

    @Test
    public void destinationNotSet() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Entity.NOT_SET), json.getString("destination"));
    }

    @Test
    public void buildWithNullDestination() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withDestination(null)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(Entity.NOT_SET), json.getString("destination"));
    }

    @Test
    public void buildWithState() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withState(State.SETTLED)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(State.SETTLED), json.getString("state"));
    }

    @Test
    public void stateIsNotSetByDefault() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(State.NOT_SET), json.getString("state"));
    }

    @Test
    public void buildWithNullState() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withState(null)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(String.valueOf(State.NOT_SET), json.getString("state"));
    }

    @Test
    public void buildWithCreated() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withCreated(123L)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(123L, json.getJsonNumber("created").longValue());
    }

    @Test
    public void createdIsZeroByDefault() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(0L, json.getJsonNumber("created").longValue());
    }

    @Test
    public void createdIsPositive() {
        JsonObject json = new TransactionBuilder()
                .withId("id")
                .withCreated(-12_345L)
                .build()
                .print(new JsonMedia())
                .json();
        assertEquals(0L, json.getJsonNumber("created").longValue());
    }
}
