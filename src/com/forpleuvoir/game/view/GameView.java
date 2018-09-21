package com.forpleuvoir.game.view;

import com.forpleuvoir.game.Settings;
import com.forpleuvoir.game.entity.Cirno;
import com.forpleuvoir.game.entity.Player;
import com.forpleuvoir.game.entity.Prop.Power;
import com.forpleuvoir.game.entity.Prop.Wingman;
import com.forpleuvoir.game.entity.danmaku.*;
import com.forpleuvoir.game.entity.Enemy;
import com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall.Icebomb;
import com.forpleuvoir.game.model.Map;
import com.forpleuvoir.game.model.Node;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class GameView {
    private final Map map;
    private static JPanel canvas;
    private Image player, enemy, spellCard, boom, power, onmyo, bullet, ofuda, huopao, bomb, cirno,iceBomb,iceBomb1;
    int width = Settings.WIDTH;
    int height = Settings.HEIGHT;

    public GameView(Map map) {
        this.map = map;
    }

    public void init() {
        try {
            ImageIcon icon = new ImageIcon("img\\reimu.png");
            ImageIcon icon1 = new ImageIcon("img\\maoyu.png");
            onmyo = new ImageIcon("img\\onmyoudama.png").getImage();
            iceBomb= new ImageIcon("img\\icebomb.png").getImage();
            iceBomb1= new ImageIcon("img\\icebomb1.png").getImage();
            cirno = new ImageIcon("img\\cirno.png").getImage();
            bomb = new ImageIcon("img\\explode.png").getImage();
            ofuda = new ImageIcon("img\\ofuda.png").getImage();
            huopao = new ImageIcon("img\\huopao.png").getImage();
            bullet = new ImageIcon("img\\bullet.png").getImage();
            power = new ImageIcon("img\\power.png").getImage();
            boom = new ImageIcon("img\\boom.png").getImage();
            spellCard = new ImageIcon("img\\spellBulletA004.png").getImage();
            enemy = icon1.getImage();
            player = icon.getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        canvas = new JPanel() {
            @Override
            public void paint(Graphics g1) {
                Graphics2D g = (Graphics2D) g1;
                drawBackground(g);
                for (int i = 0; i < map.getMusouFuuin().getBody().size(); i++) {
                    if (map.getMusouFuuin().getBody().get(i) != null) {
                        drawSpellCard(g, map.getMusouFuuin().getBody().get(i));
                    }
                }

                if (map.getCirno() != null) drawCirno(g, map.getCirno());
                for (int i = 0; i <map.getNodes().size() ; i++) {
                    if(map.getNodes().get(i)instanceof Icebomb){
                        drawIcebomb(g,(Icebomb) map.getNodes().get(i));
                    }
                }
                for (int i = 0; i < map.getBoom().size(); i++) {
                    if (map.getBoom().get(i) != null) {
                        if (map.getBoom().get(i).getSize() < 100) {
                            drawBoom(g, map.getBoom().get(i), bomb);
                        } else
                            drawBoom(g, map.getBoom().get(i), boom);
                    }
                }

                for (int i = 0; i < map.getOfudas().size(); i++) {
                    if (map.getOfudas().get(i) != null) {
                        drawOfuda(g, map.getOfudas().get(i));
                    }
                }
                for (int i = 0; i < map.getBullet().size(); i++) {
                    if (map.getBullet().get(i) != null) {
                        if (map.getBullet().get(i).getSize() == 6) {
                            drawBullet(map.getBullet().get(i), g);
                        } else if (map.getBullet().get(i).getSize() == 12) {
                            drawHuopao(map.getBullet().get(i), g);
                        }
                    }
                }
                for (int i = 0; i < map.getProps().size(); i++) {
                    if (map.getProps().get(i) instanceof Power) {
                        drawPower(g, (Power) map.getProps().get(i));
                    }
                }
                for (int i = 0; i < map.getEnemy().size(); i++) {
                    if (map.getEnemy().get(i) != null) {
                        drawEnemy(g, map.getEnemy().get(i));
                    }
                }

                drawPlayer(g, map.getPlayer());
                for (int i = 0; i < map.getPlayer().getOnmyo().size(); i++) {
                    if (map.getPlayer().getOnmyo().get(i) != null) {
                        drawOnmyo(g, map.getPlayer().getOnmyo().get(i));
                    }
                }
                if (!map.getController().isRunning()) {
                    drawGameOver(g);
                }
                drawScore(g);

            }
        };
    }

    public static void draw() {
        canvas.repaint();
    }

    public static JPanel getCanvas() {
        return canvas;
    }

    public void drawScore(Graphics2D g) {
        g.setColor(Color.red);
        g.drawString("分数:" + map.getPlayer().getScore(), 10, 15);
    }

    public void drawOfuda(Graphics2D g, Ofuda ofuda) {
        int[] point = ofuda.DecisionPoint();
        g.drawImage(this.ofuda, point[0] - point[2], point[1] - point[2], point[2] * 2, point[2] * 2, null);
    }


    public void drawBoom(Graphics2D g, Boom boom, Image bomb) {
        int[] point = boom.DecisionPoint();
        g.setColor(Color.red);
        //g.fillOval(point[0] , point[1], point[2], point[3]);
        g.drawImage(bomb, point[0] - point[2], point[1] - point[2], point[2] * 2, point[2] * 2, null);
        //  drawXYLine(g,boom,Color.blue);
    }

    public void drawOnmyo(Graphics2D g, Wingman onmyo) {
        int[] point = onmyo.DecisionPoint();
        g.drawImage(this.onmyo, point[0] - point[2], point[1] - point[2], onmyo.getSize(), onmyo.getSize(), null);
    }

    public void drawEnemy(Graphics2D g, Enemy enemy) {
        int x = enemy.getX();
        int y = enemy.getY();
        int size = enemy.getSize();
        g.drawImage(this.enemy, x - (size / 2), y - (size / 2), size, size, null);
        //绘制判定点
//        g.setColor(Color.red);
//        int[] point=enemy.DecisionPoint();
//        g.fillRect(point[0],point[1],point[2],point[3]);
        //绘制坐标线
        // drawXYLine(g,enemy,Color.red);
    }

    public void drawPower(Graphics2D g, Power power) {
        int[] point = power.DecisionPoint();
        g.drawImage(this.power, point[0], point[1], point[2] / 2, point[3] / 2, null);
    }


    public void drawPlayer(Graphics2D g, Player player) {
        g.setColor(Color.white);
        int[] point = player.DecisionPoint();
        int x = player.getX();
        int y = player.getY();
        //绘制人物贴图
        g.drawImage(this.player, x - 25, y - 25, 50, 50, null);
        //绘制人物判定点
        g.fillOval(point[0] - 4, point[1] - 4, point[2] * 2, point[2] * 2);
        //drawXYLine(g,map.getPlayer(),Color.blue);
    }

    public void drawCirno(Graphics2D g, Cirno cirno) {
        int[] hpBar = cirno.hpBar();
        int[] point = cirno.DecisionPoint();
        g.setColor(Color.white);
        g.fillRect(hpBar[0] - hpBar[2] / 2, hpBar[1], hpBar[2] + 6, 26);
        g.setColor(Color.black);
        g.fillRect(hpBar[0] - (hpBar[2] / 2) + 3, hpBar[1] + 3, hpBar[2], 20);
        g.setColor(Color.red);
        g.fillRect(hpBar[0] - (hpBar[2] / 2) + 3, hpBar[1] + 3, hpBar[3], 20);
        g.drawImage(this.cirno, point[0] - point[2] - 10, point[1] - point[2] - 10, cirno.getSize(), cirno.getSize(), null);
        g.setColor(Color.red);
        g.drawString((int) cirno.getHp() + "/" + (int) cirno.getMaxHp(), hpBar[0] + 80, hpBar[1] + 10);
        DecimalFormat df = new DecimalFormat("#.000");
        g.drawString("DPS:" + df.format(cirno.getDPS()), hpBar[0] + 80, hpBar[1] + 30);
//        drawXYLine(g,cirno,Color.blue);
//        drawPoint(g,cirno,Color.blue);
    }

    public void drawBullet(Bullet bullet, Graphics2D g) {
        int[] point = bullet.DecisionPoint();
        g.drawImage(this.bullet, point[0] - (point[2] / 2), point[1] - (point[2] / 2), point[2], 28, null);
        //g.setColor(Color.white);
        //  g.drawRect(point[0], point[1], point[2] + 1, 18);
    }

    public void drawHuopao(Bullet huopao, Graphics2D g) {
        int[] point = huopao.DecisionPoint();
        g.drawImage(this.huopao, point[0] - (point[2] / 2), point[1] - (point[2] / 2), point[2], 56, null);
        //g.setColor(Color.white);
        //  g.drawRect(point[0], point[1], point[2] + 1, 18);
    }

    public void drawGameOver(Graphics2D g) {
        int width = 250, height = 100;
        int x = map.getWidth() / 2 - (width / 2);
        int y = map.getHeight() / 2 - (height / 2);
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
        g.setColor(Color.white);
        g.drawString("满 身 疮 痍", x + 90, y + 50);
    }

    public void drawIcebomb(Graphics2D g, Icebomb icebomb) {
        int[] point = icebomb.DecisionPoint();
        g.setColor(Color.black);
        //drawPoint(g,icebomb,Color.blue);
        if(icebomb.getT())
        g.drawImage(this.iceBomb,point[0]-point[2],point[1]-point[2]-4,point[2]*2+6,point[2]*2+12,null);
        else g.drawImage(this.iceBomb1,point[0]-point[2]-2,point[1]-point[2],point[2]*2+12,point[2]*2+4,null);
    }

    public void drawBackground(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.red);
        g.drawLine(0, 0, 0, height);
        g.drawLine(0, 0, width, 0);
        g.drawLine(0, height, width, height);
        g.drawLine(width, 0, width, height);
    }

    public void drawSpellCard(Graphics2D g, SpellCard sc) {
        int x = sc.getX() - sc.getSize() / 2;
        int y = sc.getY() - sc.getSize() / 2;
        g.drawImage(spellCard, x, y, sc.getSize(), sc.getSize(), null);
        //绘制坐标线
        //drawXYLine(g,sc,Color.black);
        //绘制与玩家的线
        //g.setColor(Color.black);
        //g.drawLine(sc.getX(),sc.getY(),map.getPlayer().getX(),map.getPlayer().getY());
        //绘制圆
        //g.drawOval(sc.getX()-144,sc.getY()-144,288,288);

    }

    public void drawPoint(Graphics2D g, Node node, Color color) {
        g.setColor(color);
        int[] point = node.DecisionPoint();
        g.drawOval(point[0] - point[2], point[1] - point[2], point[2] * 2, point[2] * 2);
    }

    public void drawXYLine(Graphics2D g, Node node, Color color) {
        g.setColor(color);
        g.drawLine(0, node.getY(), width, node.getY());
        g.drawLine(node.getX(), 0, node.getX(), height);
    }
}
