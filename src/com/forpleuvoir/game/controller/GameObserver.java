package com.forpleuvoir.game.controller;

import com.forpleuvoir.game.entity.Prop.Prop;
import com.forpleuvoir.game.model.Map;

import java.util.Observable;
import java.util.Observer;

public class GameObserver implements Observer {
    private Map map;
    public GameObserver(Map map){
        this.map=map;
    }
    @Override
    public void update(Observable o, Object arg) {
        map=(Map)o;
        if(arg!=null) {
            if (arg instanceof Prop) {
                map.getProps().remove(arg);
            }






        }
    }
}
