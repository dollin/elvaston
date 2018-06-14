package com.dollin.albury.selection;

public enum Comment {

    OK(1),
    DROP(3),
    NOT_AVAILABLE(99);

    final int value;

    Comment(int value) {
        this.value = value;
    }

    /**
     * Update the string comment into an enum Comment.
     * @param comment the string to convert
     * @return Comment
     */
    public static Comment comment(String comment) {
        switch (comment) {
            case "drop":
                return Comment.DROP;
            case "n/a":
                return Comment.NOT_AVAILABLE;
            default:
                return Comment.OK;
        }
    }
}
