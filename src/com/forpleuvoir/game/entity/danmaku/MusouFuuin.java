package com.forpleuvoir.game.entity.danmaku;


import com.forpleuvoir.game.entity.Player;
import com.forpleuvoir.game.model.Map;

import java.util.ArrayList;
import java.util.LinkedList;

public class MusouFuuin {
    private LinkedList<SpellCard> body = new LinkedList<SpellCard>();

    public void create(Player player, Map map) {
        for (int i = 1; i <= 8; i++) {
            body.add(new SpellCard(player.getX(), player.getY(), i, map));
        }
    }

    public LinkedList<SpellCard> getBody() {
        return body;
    }

    public Boom boom(SpellCard spellCard) {
        Boom boom = new Boom();
        boom.setX(spellCard.getX());
        boom.setY(spellCard.getY());
        return boom;
    }

    public void setBody(LinkedList<SpellCard> body) {
        this.body = body;
    }

}
