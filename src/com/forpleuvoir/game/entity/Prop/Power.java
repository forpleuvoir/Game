package com.forpleuvoir.game.entity.Prop;

import com.forpleuvoir.game.model.Map;

public class Power extends Prop {
    public Power(int x, int y, Map map) {
        super(x, y, map);
        setSize(48);
    }

    public Power create(){
        return this;
    }

}
