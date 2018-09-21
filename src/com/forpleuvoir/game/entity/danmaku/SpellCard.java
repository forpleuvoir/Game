package com.forpleuvoir.game.entity.danmaku;

import com.forpleuvoir.game.entity.Enemy;
import com.forpleuvoir.game.gamenIterface.Fly;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.model.Map;
import com.forpleuvoir.game.model.Node;

import java.util.Random;

public class SpellCard extends Node implements Fly {
    private int position;
    private int speed = 6;
    private int bevelSpeed = 4;
    private int spacing = 0;
    private int size = 100;
    private Map map;
    private int px = 0, py = 0;
    int acceleration = 0;
    boolean isMove = true;
    int y = 5000, x = 0;
    boolean rl;
    int rspeed = 4;
    public double dmg=30;

    public SpellCard(int x, int y, int position, Map map) {
        super(x, y);
        this.map = map;
        this.position = position;
        px = map.getPlayer().getX();
        py = map.getPlayer().getY();
    }

    @Override
    public void move(Direction direction, boolean isMove) {
        if (this.isMove) {
            if (spacing >= 144) {

                if (y == 5000) {
                    y = py - getY();
                }
                if (acceleration > 14) {
                    this.setY(getY() - 30);
                    py = py - 10;
                }else
                rotate();

                if (x == 0) {
                    acceleration++;
                    rspeed++;
                }

            } else move();


        }
    }

    public void rotate() {
        x = (int) Math.sqrt((spacing * spacing) - ((y - 0) * (y - 0)) - (0 * 0)) + 0;
        setY((int) (py - y));
        switch (getDirection()) {
            case 1:
                y = y - rspeed;
                setX(px + x);
            case 2:
                y = y - rspeed;
                setX(px + x);
                break;
            case 3:
                y = y + rspeed;
                setX(px - x);
                break;
            case 4:
                y = y + rspeed;
                setX(px - x);
                break;
        }
        if(y>154){
            y=144;
        }else if(y<-144){
            y=-154;
        }
    }

    public void move() {
        int nposition;
        if (position <= 8) {
            nposition = position;
        } else {
            nposition = position / 4;
        }
        switch (nposition) {
            case 1:
                this.setY(getY() - speed);
                spacing = spacing + speed;
                rl = true;
                break;
            case 2:
                this.setY(getY() - bevelSpeed);
                this.setX(getX() + bevelSpeed);
                spacing = spacing + speed;
                rl = false;
                break;
            case 3:
                this.setX(getX() + speed);
                spacing = spacing + speed;
                rl = false;
                break;
            case 4:
                this.setY(getY() + bevelSpeed);
                this.setX(getX() + bevelSpeed);
                spacing = spacing + speed;
                rl = false;
                break;
            case 5:
                this.setY(getY() + speed);
                spacing = spacing + speed;
                rl = true;
                break;
            case 6:
                this.setY(getY() + bevelSpeed);
                this.setX(getX() - bevelSpeed);
                spacing = spacing + speed;
                rl = false;
                break;
            case 7:
                this.setX(getX() - speed);
                spacing = spacing + speed;
                rl = true;
                break;
            case 8:
                this.setX(getX() - bevelSpeed);
                this.setY(getY() - bevelSpeed);
                spacing = spacing + speed;
                rl = false;
                break;
        }
    }
    public int getDirection() {

        if (py - getY() > 0) {
            if (px - getX() > 0) {
                return 4;
            } else if (px - getX() == 0) {
                return 1;
            } else {
                return 1;
            }
        } else if (py - getY() == 0) {
            if (px - getX() > 0) return 4;
            else return 2;
        } else {
            if (px - getX() > 0) {
                return 3;
            } else if (px - getX() == 0) {
                return 3;
            } else {
                return 2;
            }
        }
    }

    @Override
    public int[] DecisionPoint() {
        int[] point;
        int r = this.size/2;
        point = new int[]{this.getX(), this.getY(), r};
        return point;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
