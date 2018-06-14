package com.dollin.albury.selection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommentTest {

    @Test
    public void notAvailable() {
        Comment comment = Comment.comment("n/a");
        assertEquals(Comment.NOT_AVAILABLE.toString(), comment.name());
        assertEquals(99, comment.value);
    }

    @Test
    public void dropped() {
        Comment comment = Comment.comment("drop");
        assertEquals(Comment.DROP.toString(), comment.name());
        assertEquals(3, comment.value);
    }

    @Test
    public void ok() {
        assertEquals(1, Comment.comment("").value);
        assertEquals(1, Comment.comment("dfd").value);
        assertEquals(1, Comment.comment("z").value);
        assertEquals(1, Comment.comment("g").value);
        assertEquals(1, Comment.comment("a").value);
        assertEquals(1, Comment.comment("100").value);
        assertEquals(1, Comment.comment("10").value);
    }
}
