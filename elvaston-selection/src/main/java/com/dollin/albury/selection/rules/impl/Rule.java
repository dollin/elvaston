package com.dollin.albury.selection.rules.impl;

import com.dollin.albury.selection.Comment;
import com.dollin.albury.selection.Player;
import com.dollin.albury.selection.Team;
import com.dollin.albury.selection.rules.api.IRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Rule implements IRule {

    private static final Logger LOG = LogManager.getLogger(Rule.class);
    private final Comment[] comment = new Comment[2];
    private Team in;

    private Team into;
    private final int rank;
    private final String desc;

    /**
     * Create a rule.
     * @param rule date needed to create the Rule
     */
    public Rule(String rule) {
        into = Team.team(rule.substring(0,1));
        rank = Integer.parseInt(rule.substring(2,5));
        comment[0] = Comment.comment(rule.substring(6,7));
        comment[1] = Comment.comment(rule.substring(8,9));
        in = Team.team(rule.substring(10,11));
        desc = rule.substring(12);
    }

    @Override
    public void check(Player player) {
        LOG.info("checking player {}", player);
        if (player.community()) {
            player.allocate(Team.STANDBY, 900, "Community player");
        } else if (player.development()
                && player.form(1) == comment[1]
                && player.form(0) == comment[0]
                && player.in() == in) {
            if (player.moved()) {
                into = in;
            }
            if (player.trained()) {
                player.allocate(into, rank, desc);
            } else {
                player.allocate(into, rank + 1, desc + ". Missed training");
            }
        }
    }
}
