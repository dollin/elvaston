package com.dollin.albury.selection;

public enum Team {

    EAGLES(1,         "Eagles  "),
    LIONS(2,          "Lions   "),
    STANDBY(3,       "Standby"),
    DID_NOT_PLAY(99,  "n/a     "),
    NOT_SELECTED(100, "n/a     ");

    final int value;
    final String name;

    Team(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Convert abc into a Team.
     * @param team to convert into a Team
     * @return the Team
     */
    public static Team team(String team) {
        switch (team.toLowerCase()) {
            case "a":
                return Team.EAGLES;
            case "b":
                return Team.LIONS;
            case "c":
                return Team.STANDBY;
            default:
                return Team.DID_NOT_PLAY;
        }
    }
}
