package com.forpleuvoir.game.entity.Prop;

import com.forpleuvoir.game.gamenIterface.Fly;
import com.forpleuvoir.game.model.Direction;
import com.forpleuvoir.game.model.Map;
import com.forpleuvoir.game.model.Node;

public class Prop extends Node implements Fly {
    private Map map;
    private int size;
    private int speed;
    public Prop(int x,int y,Map map) {
        this.setX(x);
        this.setY(y);
        this.map = map;
    }

    @Override
    public void move(Direction direction, boolean isMove) {
        speed=3;
        this.setY(getY()+speed);
        if(Math.abs(this.getX()-map.getPlayer().getX())<=90&&Math.abs(this.getY()-map.getPlayer().getY())<=90){
            if(this.getY()-map.getPlayer().getY()>0){
                setY(getY()-5);
            }else {
                setY(getY()+2);
            }
            if(this.getX()-map.getPlayer().getX()>0){
                setX(getX()-2);
            }else {
                setX(getX()+2);
            }
        }
    }


    @Override
    public int[] DecisionPoint() {
        int[] point;
        int size =this.size;
        point = new int[]{this.getX() - (size / 2), this.getY() - (size / 2), size, size};
        return point;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
