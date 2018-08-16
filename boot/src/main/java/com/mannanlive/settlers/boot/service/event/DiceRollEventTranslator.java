package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.Roll;
import com.mannanlive.settlers.core.model.game.observer.DiceRollEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@Order(300)
public class DiceRollEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.ROLL;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        Roll roll = ((DiceRollEvent) gameEvent).getRoll();
        return new GameEventsData(format("@p%d has rolled %s for a total of <b>%d</b>",
                player, getText(roll), roll.getValue()), player);
    }

    public String getText(Roll roll) {
        if (roll.getDie1() == roll.getDie2()) {
            return format("two %ds", roll.getDie1());
        }
        return format("a %d and a %d", roll.getDie1(), roll.getDie2());
    }
}
