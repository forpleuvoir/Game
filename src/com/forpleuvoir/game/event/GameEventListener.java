package com.forpleuvoir.game.event;

import java.util.EventListener;

public interface GameEventListener extends EventListener {
    public void isMove(GameEvent gameEvent);
    public void isCollision(GameEvent gameEvent);
}
