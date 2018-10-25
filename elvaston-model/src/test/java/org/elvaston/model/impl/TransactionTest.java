package org.elvaston.model.impl;

import static org.junit.Assert.assertEquals;

import org.elvaston.model.api.Media;

import org.junit.Test;

/**
 * Unit tests for the TransactionImpl.
 */
public class TransactionTest {

    @Test
    public void showDefaultTransactionImplWithToString() {
        assertEquals("TransactionImpl: {\"id\":\"id\","
                + "\"description\":\"\","
                + "\"currency\":\"NOT_SET\","
                + "\"quantity\":0,"
                + "\"amount\":0,"
                + "\"source\":\"NOT_SET\","
                + "\"destination\":\"NOT_SET\","
                + "\"state\":\"NOT_SET\","
                + "\"created\":0}", new TransactionBuilder().withId("id").build().toString());
    }

    @Test
    public void showDefaultTransactionImplWithJson() {
        Media media = new JsonMedia();
        assertEquals("{\"id\":\"id\","
                + "\"description\":\"\","
                + "\"currency\":\"NOT_SET\","
                + "\"quantity\":0,"
                + "\"amount\":0,"
                + "\"source\":\"NOT_SET\","
                + "\"destination\":\"NOT_SET\","
                + "\"state\":\"NOT_SET\","
                + "\"created\":0}", new TransactionBuilder().withId("id").build().print(media).json().toString());
    }
}
