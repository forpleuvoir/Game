package com.forpleuvoir.game.Event;

import java.util.EventObject;

public class GameEvent extends EventObject {
    private boolean isMove;
    private boolean isCollision;
    public GameEvent(Object source,boolean isMove,boolean isCollision) {
        super(source);
        this.isMove=isMove;
        this.isCollision=isCollision;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public boolean isCollision() {
        return isCollision;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
    }
}
