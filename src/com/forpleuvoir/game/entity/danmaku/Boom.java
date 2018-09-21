package com.forpleuvoir.game.entity.danmaku;

import com.forpleuvoir.game.gamenIterface.Fly;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.model.Node;

public class Boom extends Node implements Fly {
    private int boomTime = 20;
    private int size=100;

    public Boom() {
    }

    public Boom(int x, int y, int boomTime, int size) {
        super(x, y);
        this.boomTime = boomTime;
        this.size = size;
    }

    @Override
    public void move(Direction direction, boolean isMove) {

    }
    @Override
    public int[] DecisionPoint() {
        int[] point;
        point = new int[]{this.getX(), this.getY(), size/2};
        return point;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBoomTime() {
        return boomTime;
    }

    public void setBoomTime(int boomTime) {
        this.boomTime = boomTime;
    }
}
