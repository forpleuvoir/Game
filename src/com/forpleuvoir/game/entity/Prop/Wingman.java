package com.forpleuvoir.game.entity.Prop;


import com.forpleuvoir.game.entity.Player;
import com.forpleuvoir.game.entity.danmaku.Bullet;
import com.forpleuvoir.game.entity.danmaku.Ofuda;
import com.forpleuvoir.game.model.Direction;

import java.util.ArrayList;

public class Wingman extends Player {
    private int size=24;
    private int position;
    private int power=1;
    public Wingman(int x, int y,int position) {
        setX(x);
        setY(y);
        setMoveSpeed(3);
        this.position=position;
    }
    public  ArrayList<Bullet> bShoot(){
        ArrayList<Bullet> bullet = new ArrayList<>();
        switch (power){
            case 1:
            case 2:
            case 3:
                bullet.add(new Bullet(this.getX(), this.getY() , Direction.UP,12));break;
            case 4:
            case 5:
            case 6:
                bullet.add(new Bullet(this.getX()-6, this.getY() , Direction.UP,12));
                bullet.add(new Bullet(this.getX()+6, this.getY() , Direction.UP,12));break;

        }

        return bullet;
    }
    public ArrayList<Ofuda> oShoot() {
        ArrayList<Ofuda> ofudas=new ArrayList<>();
        if(position==1) {
            ofudas.addAll(right(ofudas));
        }else {
            ofudas.addAll(left(ofudas));
        }
        return ofudas;
    }
    public ArrayList<Ofuda> right(ArrayList<Ofuda> ofudas){
        switch (power) {
            case 1:
            case 2:
                ofudas.add(new Ofuda(this, Direction.RIGHT, 0.4));break;
            case 3:
            case 4:
                ofudas.add(new Ofuda(this, Direction.RIGHT, 0.2));
                ofudas.add(new Ofuda(this, Direction.RIGHT, 0.4));break;
            case 5:
            case 6:
                ofudas.add(new Ofuda(this, Direction.RIGHT, 0.2));
                ofudas.add(new Ofuda(this, Direction.RIGHT, 0.4));
                ofudas.add(new Ofuda(this, Direction.RIGHT, 0.6));break;
        }
        return ofudas;
    }
    public ArrayList<Ofuda> left(ArrayList<Ofuda> ofudas){
        switch (this.getPower()) {
            case 1:
            case 2:
                ofudas.add(new Ofuda(this, Direction.LEFT, 0.4));break;
            case 3:
            case 4:
                ofudas.add(new Ofuda(this, Direction.LEFT, 0.2));
                ofudas.add(new Ofuda(this, Direction.LEFT, 0.4));break;
            case 5:
            case 6:
                ofudas.add(new Ofuda(this, Direction.LEFT, 0.2));
                ofudas.add(new Ofuda(this, Direction.LEFT, 0.4));
                ofudas.add(new Ofuda(this, Direction.LEFT, 0.6));break;
        }
        return ofudas;
    }
    public int[] DecisionPoint() {
        return new int[]{getX(),getY(),size/2};
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
