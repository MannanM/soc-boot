package com.mannanlive.settlers.boot.model.lobby;

import com.mannanlive.settlers.core.model.player.Player;

import java.util.Collection;

public class Players {
    private Collection<Player> data;

    public Players(Collection<Player> data) {
        this.data = data;
    }

    public Collection<Player> getData() {
        return data;
    }
}
