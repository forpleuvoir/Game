package com.forpleuvoir.game.entity.danmaku;

import com.forpleuvoir.game.entity.Prop.Wingman;
import com.forpleuvoir.game.gameInterface.Fly;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.model.Node;

public class Ofuda extends Node implements Fly {
    private int ox, oy;
    private int size = 24;
    private Direction direction;
    private double tano;
    private double nx, ny;
    public double dmg = 0.25;

    public Ofuda(Wingman onmyo, Direction direction, double tano) {
        ox = onmyo.getX();
        oy = onmyo.getY();
        setX(ox);
        setY(oy);
        this.tano = tano;
        this.direction = direction;
    }

    @Override
    public void move(Direction direction, boolean isMove) {
        setY(getY() - 6);
        ny = oy - getY();
        switch (this.direction) {
            case RIGHT:
                setX((int) (ox + ny * tano));
                break;
            case LEFT:
                setX((int) (ox - ny * tano));
                break;
        }
    }

    @Override
    public int[] DecisionPoint() {
        return new int[]{getX(), getY(), this.size / 2};
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
