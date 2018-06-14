package com.dollin.albury.selection;

import com.dollin.albury.selection.rules.impl.Rule;
import com.dollin.albury.selection.rules.impl.Rules;

public class Player implements Comparable<Player> {

    private final Comment[] comment = new Comment[2];
    private Team in = Team.NOT_SELECTED;
    private boolean trained;
    private boolean community;
    private final String name;
    private String reason;
    private int rank = 999;
    private Team into = Team.NOT_SELECTED;
    private boolean keeper;
    private boolean moved;

    private static final int[] FORM1 = new int[]{0,1};
    private static final int[] TRAINING1 = new int[]{2,3};
    private static final int[] TEAM1 = new int[]{1,2};
    private static final int[] FORM2 = new int[]{4,5};
    private static final int[] TEAM2 = new int[]{5,6};
    private static final int[] TRAINING2 = new int[]{6,7};
    private static final int[] GK = new int[]{10,12};

    /**
     * Player object to represent one of the boys.
     * @param yearGroup The year group we are running for
     * @param history the last 2 weeks information
     */
    public Player(String yearGroup, String history) {
        name = history.substring(history.lastIndexOf(',') + 1);

        if (history.substring(history.lastIndexOf(',') - 1, history.lastIndexOf(',')).equalsIgnoreCase("c")) {
            community = true;
        } else {
            keeper = history.substring(GK[0], GK[1]).equalsIgnoreCase("gk");
            comment[0] = Comment.comment(history.substring(FORM1[0], FORM1[1]));
            comment[1] = Comment.comment(history.substring(FORM2[0], FORM2[1]));
            in = Team.team(history.substring(TEAM1[0], TEAM1[1]));
            moved = Team.team(history.substring(TEAM2[0], TEAM2[1])).value() != in.value();
            trained = history.substring(TRAINING1[0], TRAINING1[1]).equalsIgnoreCase("y")
                    || history.substring(TRAINING2[0], TRAINING2[1]).equalsIgnoreCase("y");
        }
        allocatePlayer(yearGroup);
    }

    private void allocatePlayer(String team) {
        Rules.get(team)
                .filter(s -> !s.startsWith("#") && !s.isEmpty())
                .map(Rule::new)
                .forEach(rule -> rule.check(this));
    }

    @Override
    public String toString() {
        String place;
        if (in.value() == into.value() || in == Team.DID_NOT_PLAY || in == Team.NOT_SELECTED) {
            place = "--";
        } else if (in.value() < into.value()) {
            place = "Dn";
        } else {
            place = "Up";
        }
        return String.format("[%s] %s %s %4$-25s %5$s", rank, place, into, name, reason);
    }

    @Override
    public int compareTo(Player player) {
        return Integer.compare(into.value(), player.into.value()) * 10 + Integer.compare(rank, player.rank);
    }

    public boolean community() {
        return community;
    }

    /**
     * Allocate to a selected team w/ a rank and reason.
     * @param selectedTeam team selected for
     * @param rank weighting of that selection
     * @param reason description as to why
     */
    public void allocate(Team selectedTeam, int rank, String reason) {
        this.into = selectedTeam;
        this.rank = rank;
        this.reason = reason;
    }

    public boolean development() {
        return !community;
    }

    public boolean trained() {
        return trained;
    }

    public boolean moved() {
        return moved;
    }

    public Comment form(int index) {
        return comment[index];
    }

    public Team in() {
        return in;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        Player player = (Player) that;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean outfield() {
        return !keeper();
    }

    public boolean keeper() {
        return keeper;
    }

}
