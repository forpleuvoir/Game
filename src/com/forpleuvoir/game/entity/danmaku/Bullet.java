package com.forpleuvoir.game.entity.danmaku;

import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.gamenIterface.Fly;
import com.forpleuvoir.game.model.Node;

public class Bullet extends Node implements Fly {
    private  int speed = 15;
    private Direction direction;
    private int size=6;
    public double dmg=1;
    public  Bullet(int x, int y, Direction direction) {
        this.setX(x);
        this.setY(y);
        this.direction=direction;

    }

    public  Bullet(int x, int y, Direction direction,int size) {
        this.setX(x);
        this.setY(y);
        this.direction=direction;
        this.size=size;
    }
    @Override
    public void move(Direction direction, boolean isMove) {
        direction=this.direction;
        switch (direction) {
            case UP:
                this.setY(getY() - speed);
                break;
            case DOWN:
                this.setY(getY() + speed);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {

        return speed;
    }

    @Override
    public int[] DecisionPoint() {
        int [] point;
        point= new int[]{getX(), getY(),size};
        return point;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
