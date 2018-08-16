package com.mannanlive.settlers.core.model.player;

public class Player {
    private boolean human;
    private long id;
    private String name;

    public Player(boolean human, long id, String name) {
        this.human = human;
        this.id = id;
        this.name = name;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
