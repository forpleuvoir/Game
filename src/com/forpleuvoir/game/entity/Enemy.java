package com.forpleuvoir.game.entity;

import com.forpleuvoir.game.Settings;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.gameInterface.Fly;
import com.forpleuvoir.game.model.Node;

import java.util.Random;

public class Enemy extends Node implements Fly {
    private int speed = 4;
    private int size = 50;
    private double hp;
    private int nx;


    public Enemy(Player player, double hp) {
        nx = player.getX();
        this.hp = hp;
        Random random = new Random();
        int x = random.nextInt(Settings.WIDTH-10) + 10;
        this.setX(x);

    }

    public double beHit(double hit) {
        hp=hp-hit;
        return hp;
    }


    public int getSize() {
        return size;
    }

    @Override
    public void move(Direction direction, boolean isMove) {
        if (getX() - nx > new Random().nextInt(30) ){
            setX(getX() - 1);
            setY(getY() + speed);
        } else if (getX() - nx < -new Random().nextInt(30)) {
            setX(getX() + 1);
            setY(getY() + speed);
        } else {
            setY(getY() + speed);
        }
    }

    @Override
    public int[] DecisionPoint() {
        int [] point;
        int size=this.size-10;
        point= new int[]{this.getX(), this.getY(), size/2};
        return point;
    }
}
