package com.forpleuvoir.game.entity;

import com.forpleuvoir.game.entity.danmaku.Bullet;
import com.forpleuvoir.game.entity.Prop.Wingman;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.gameInterface.Fly;
import com.forpleuvoir.game.model.Node;

import java.util.ArrayList;

public class Player extends Node implements Fly {
    private int moveSpeed = 6;
    private int score = 0;
    private int power = 1;
    private int shootType = 1;
    private ArrayList<Wingman> onmyo=new ArrayList<>();

    public Player(int x,int y) {
        this.setX(x);
        this.setY(y);
        onmyo.add(new Wingman(this.getX()+40,this.getY()-10,1));
        onmyo.add(new Wingman(this.getX()-40,this.getY()-10,0));
    }

    public Player() {
    }

    @Override
    public void move(Direction direction, boolean isMove) {

        if (isMove) {
            switch (direction) {
                case UP:
                    this.setY(this.getY() - moveSpeed);
                    break;
                case DOWN:
                    this.setY(this.getY() + moveSpeed);
                    break;
                case LEFT:
                    this.setX(this.getX() - moveSpeed);
                    break;
                case RIGHT:
                    this.setX(this.getX() + moveSpeed);
                    break;
                case UP_RIGHT:
                    this.setY(this.getY() - moveSpeed);
                    this.setX(this.getX() + moveSpeed);
                    break;
                case UP_LEFT:
                    this.setX(this.getX() - moveSpeed);
                    this.setY(this.getY() - moveSpeed);
                    break;
                case DOWN_LEFT:
                    this.setX(this.getX() - moveSpeed);
                    this.setY(this.getY() + moveSpeed);
                    break;
                case DOWN_RIGHT:
                    this.setY(this.getY() + moveSpeed);
                    this.setX(this.getX() + moveSpeed);
                    break;
            }
        }
    }

    @Override
    public int[] DecisionPoint() {
        return new int[]{getX(), getY(), 4};
    }

    public ArrayList<Bullet> shoot() {
        ArrayList<Bullet> bullet = new ArrayList<>();
        switch (shootType) {
            case 1:
                normal(bullet);
                break;
        }
        return bullet;
    }

    public void normal(ArrayList<Bullet> bullet) {
        switch (power) {
            case 1:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                break;
            case 2:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                break;
            case 3:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9, this.getY() - 20, Direction.UP));
                break;
            case 4:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9, this.getY() - 20, Direction.UP));
                break;
            case 5:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9 + 9, this.getY() - 15, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9 - 9, this.getY() - 15, Direction.UP));
                break;
            case 6:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9 + 9, this.getY() - 15, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9 - 9, this.getY() - 15, Direction.UP));
                break;
            case 233:
                bullet.add(new Bullet(this.getX() + 2, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7, this.getY() - 25, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9, this.getY() - 20, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9 + 9, this.getY() - 15, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9 + 9, this.getY() - 15, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9 + 9 + 9, this.getY() - 10, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9 - 9 - 9, this.getY() - 10, Direction.UP));
                bullet.add(new Bullet(this.getX() + 2 + 9 + 9 + 9 + 9, this.getY() - 5, Direction.UP));
                bullet.add(new Bullet(this.getX() - 7 - 9 - 9 - 9 - 9, this.getY() - 5, Direction.UP));
                break;

        }
    }

    public int powerUp(int level) {
        power = power + level;
        if (power > 6) {
            power = 6;
        }
        onmyo.get(1).setPower(onmyo.get(1).getPower()+level);
        onmyo.get(0).setPower(onmyo.get(0).getPower()+level);
        if(onmyo.get(1).getPower()>6){
            onmyo.get(1).setPower(6);
            onmyo.get(0).setPower(6);
        }

        return power;
    }
    public int powerDown(int level) {
        power = power - level;
        if (power <= 0) {
            power = 1;
        }
        onmyo.get(1).setPower(onmyo.get(1).getPower()-level);
        onmyo.get(0).setPower(onmyo.get(0).getPower()-level);
        if(onmyo.get(1).getPower()<=0){
            onmyo.get(1).setPower(1);
            onmyo.get(0).setPower(1);
        }

        return power;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getType() {
        return 1;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getShootType() {
        return shootType;
    }

    public void setShootType(int shootType) {
        this.shootType = shootType;
    }

    public ArrayList<Wingman> getOnmyo() {
        return onmyo;
    }

    public void setOnmyo(ArrayList<Wingman> onmyo) {
        this.onmyo.addAll(onmyo);
    }
}
