package org.elvaston.model.api;

import java.math.BigDecimal;
import javax.json.JsonObject;

/**
 * Interface for the different Media to use.
 */
public interface Media {

    /**
     * Adds a String to the Media.
     * @param name of the field we are adding
     * @param value to add
     * @return Media
     */
    Media with(String name, String value);

    /**
     * Adds a BigDecimal to the Media.
     * @param name of the field we are adding
     * @param value to add
     * @return Media
     */
    Media with(String name, BigDecimal value);

    /**
     * Adds a long to the Media.
     * @param name of the field we are adding
     * @param value to add
     * @return Media
     */
    Media with(String name, long value);

    /**
     * Gets a JsonObject view of the Media.
     * @return JsonObject
     */
    JsonObject json();
}
