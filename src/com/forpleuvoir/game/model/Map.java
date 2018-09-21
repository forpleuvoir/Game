package com.forpleuvoir.game.model;

import com.forpleuvoir.game.Event.GameEvent;
import com.forpleuvoir.game.Event.GameEventListener;
import com.forpleuvoir.game.GameApp;
import com.forpleuvoir.game.controller.GameController;
import com.forpleuvoir.game.entity.Cirno;
import com.forpleuvoir.game.entity.Prop.Power;
import com.forpleuvoir.game.entity.Prop.Prop;
import com.forpleuvoir.game.entity.danmaku.*;
import com.forpleuvoir.game.entity.Enemy;
import com.forpleuvoir.game.entity.Player;
import com.forpleuvoir.game.entity.danmaku.cirno.IcicleFall.Icebomb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

public class Map extends Observable implements GameEventListener {
    private ArrayList<Node> nodes;
    private Player player;
    private Cirno cirno;
    private MusouFuuin musouFuuin;
    private ArrayList<Bullet> bullet;
    private ArrayList<Ofuda> ofudas;
    private ArrayList<Prop> props;
    private ArrayList<Enemy> enemy;
    private ArrayList<Boom> boom;

    private int width;
    private int height;
    private Direction playerDirection = Direction.UP;
    private double enemyHp;
    private GameController controller;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public void init() {
        enemyHp = 20;
        nodes = new ArrayList<>();
        nodes.add(null);
        musouFuuin=new MusouFuuin();

        bullet=new ArrayList<>();
        bullet.add(null);
        ofudas=new ArrayList<>();
        ofudas.add(null);
        props=new ArrayList<>();
        props.add(null);
        enemy=new ArrayList<>();
        enemy.add(null);
        boom=new ArrayList<>();
        boom.add(null);

        createCirno();
        createPlayer();
    }


    public boolean nextFrame(boolean isMove) {
        player.move(playerDirection, isMove);
        for (int i = 0; i < player.getOnmyo().size(); i++) {
            if (player.getOnmyo().get(i) != null) {
                player.getOnmyo().get(i).move(playerDirection, isMove);
                player.getOnmyo().get(i).move(playerDirection, isMove);
            }
        }
        return true;
    }

    public void nextFrame() {
        GameApp.refresh();
        setChanged();
        notifyObservers(nodes);


        for (int i = 0; i < props.size(); i++) {
            if (props.get(i) != null) {
                props.get(i).move(Direction.DOWN, true);
            }
        }
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof Icebomb) {
                ((Icebomb) nodes.get(i)).move(Direction.RIGHT, true);
                if (isCollision(nodes.get(i).DecisionPoint(), player.DecisionPoint())) {
                    controller.gameOver();
                }
                if (nodes.get(i).getX() > width + 30 || nodes.get(i).getY() > height + 30 || nodes.get(i).getX() < -30 || nodes.get(i).getY() < -30) {
                    nodes.remove(nodes.get(i));
                }
            }
        }
        //爆炸效果持续时间
        for (int i = 0; i < boom.size(); i++) {
            if (boom.get(i) != null) {
                if (boom.get(i).getSize() >= 100) {
                    boom.get(i).setSize(boom.get(i).getSize() + 7);
                } else {
                    boom.get(i).setSize(boom.get(i).getSize() + 1);
                }
                boom.get(i).setBoomTime(boom.get(i).getBoomTime() - 1);
                if (boom.get(i).getBoomTime() <= 0) {
                    boom.remove(boom.get(i));
                }

            }
        }
        switch (player.getScore() / 50) {
            case 1:
                enemyHp = 25;
                break;
            case 2:
                enemyHp = 35;
                break;
            case 3:
                enemyHp = 55;
                break;
            case 4:
                enemyHp = 95;
                break;
        }
        for (int i = 0; i < musouFuuin.getBody().size(); i++) {
            if (musouFuuin.getBody().get(i) != null) {
                musouFuuin.getBody().get(i).move(null, true);
                if (musouFuuin.getBody().get(i).getY() <= 10) {
                    boom.add(musouFuuin.boom(musouFuuin.getBody().get(i)));
                    musouFuuin.getBody().remove(musouFuuin.getBody().get(i));
                }
            }
        }
        // 自机吃到掉落
        for (int i = 0; i < props.size(); i++) {
            if (props.get(i) instanceof Power) {
                if (isCollision(props.get(i).DecisionPoint(), player.DecisionPoint())) {
                    player.powerUp(1);
                    setChanged();
                    notifyObservers(props.get(i));
                }
            }
        }
        judgeEnemy();
        if (cirno != null) {
            judgeCirno();
        }
        //札弹移动方式
        for (int i = 0; i < ofudas.size(); i++) {
            if (ofudas.get(i) != null) {
                ofudas.get(i).move(null, true);
                if (ofudas.get(i).getX() > width || ofudas.get(i).getX() < 0 || ofudas.get(i).getY() > height || ofudas.get(i).getY() < -18) {
                    ofudas.remove(ofudas.get(i));
                }
            }
        }
        //普通弹移动方式
        for (int i = 0; i < bullet.size(); i++) {
            if (bullet.get(i) != null) {
                bullet.get(i).move(null, true);
                if (bullet.get(i).getX() > width || bullet.get(i).getX() < 0 || bullet.get(i).getY() > height || bullet.get(i).getY() < -18) {
                    bullet.remove(bullet.get(i));
                }
            }
        }
    }

    //敌人判定
    public void judgeEnemy() {
        for (int i = 0; i < enemy.size(); i++) {
            Enemy nenemy = enemy.get(i);
            if (nenemy != null) {
//                敌人是否碰撞到自机
                if (isCollision(enemy.get(i).DecisionPoint(), player.DecisionPoint())) {
                    controller.gameOver();
                }
//                敌人超出边界  移除
                enemy.get(i).move(null, true);
                if (enemy.get(i).getX() > width + 30 || enemy.get(i).getX() < -100 || enemy.get(i).getY() > height + 30 || enemy.get(i).getY() < -100) {
                    enemy.remove(enemy.get(i));
                }
                //符卡
                for (int m = 0; m < musouFuuin.getBody().size(); m++) {
                    if (musouFuuin.getBody().get(m) != null) {
                        if (isCollision(musouFuuin.getBody().get(m).DecisionPoint(), nenemy.DecisionPoint())) {
                            boom.add(musouFuuin.boom(musouFuuin.getBody().get(m)));
                            if (nenemy.beHit(musouFuuin.getBody().get(m).dmg) <= 0) {
                                if (new Random().nextInt(2) == 1) {
                                    props.add(new Power(nenemy.getX(), nenemy.getY(), this));
                                }
                                enemy.remove(nenemy);
                                musouFuuin.getBody().remove(musouFuuin.getBody().get(m));
                                player.setScore(player.getScore() + 1);

                            }
                        }
                    }
                }
                //爆炸
                for (int b = 0; b < boom.size(); b++) {
                    if (boom.get(b) != null) {
                        if (isCollision(boom.get(b).DecisionPoint(), nenemy.DecisionPoint())) {
                            if (boom.get(b).getSize() < 100) {
                                if (nenemy.beHit(0.1) <= 0) {
                                    enemy.remove(nenemy);
                                }
                            } else enemy.remove(nenemy);
                        }
                    }
                }

                //敌人是否被子弹击中
                for (int b = 0; b < bullet.size(); b++) {
                    if (bullet.get(b) != null) {
                        if (isCollision(bullet.get(b).DecisionPoint(), nenemy.DecisionPoint())) {
                            if (bullet.get(b).getSize() == 12) {
                                boom.add(new Boom(bullet.get(b).getX(), bullet.get(b).getY(), 10, 30));
                                bullet.remove(bullet.get(b));
                            } else {
                                boolean a = nenemy.beHit(bullet.get(b).dmg) <= 0;
                                bullet.remove(bullet.get(b));
                                if (a) {
                                    if (new Random().nextInt(10) == 1) {
                                        props.add(new Power(nenemy.getX(), nenemy.getY(), this));
                                    }
                                    enemy.remove(nenemy);
                                    player.setScore(player.getScore() + 1);
                                }
                            }
                        }
                    }
                }
                //札弹判定
                for (int o = 0; o < ofudas.size(); o++) {
                    if (ofudas.get(o) != null) {
                        if (isCollision(ofudas.get(o).DecisionPoint(), nenemy.DecisionPoint())) {
                            boolean a = nenemy.beHit(ofudas.get(o).dmg) <= 0;
                            ofudas.remove(ofudas.get(o));
                            if (a) {
                                if (new Random().nextInt(10) == 1) {
                                    props.add(new Power(nenemy.getX(), nenemy.getY(), this));
                                }
                                enemy.remove(nenemy);
                                player.setScore(player.getScore() + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    //⑨判定
    public void judgeCirno() {
        //符卡
        for (int m = 0; m < musouFuuin.getBody().size(); m++) {
            if (musouFuuin.getBody().get(m) != null) {
                if (isCollision(musouFuuin.getBody().get(m).DecisionPoint(), cirno.DecisionPoint())) {
                    boom.add(musouFuuin.boom(musouFuuin.getBody().get(m)));
                    if (cirno.beHit(musouFuuin.getBody().get(m).dmg) <= 0) {
                        if (new Random().nextInt(10) == 1) {
                            props.add(new Power(cirno.getX(), cirno.getY(), this));
                        }
                        cirno = null;
                        player.setScore(player.getScore() + 100);

                    }
                    musouFuuin.getBody().remove(musouFuuin.getBody().get(m));
                }
            }
        }
        //爆炸
        for (int b = 0; b < boom.size(); b++) {
            if (boom.get(b) != null) {
                if (cirno != null)
                    if (isCollision(boom.get(b).DecisionPoint(), cirno.DecisionPoint())) {
                        if (boom.get(b).getSize() < 100) {
                            if (cirno.beHit(0.1) <= 0) {
                                cirno = null;
                            }
                        } else {
                            if (cirno.beHit(2) <= 0) {
                                cirno = null;
                            }
                        }
                    }
            }
        }

        //敌人是否被子弹击中
        for (int b = 0; b < bullet.size(); b++) {
            if (bullet.get(b) != null) {
                if (cirno != null)
                    if (isCollision(bullet.get(b).DecisionPoint(), cirno.DecisionPoint())) {
                        if (bullet.get(b).getSize() == 12) {
                            boom.add(new Boom(bullet.get(b).getX(), bullet.get(b).getY(), 10, 30));
                            bullet.remove(bullet.get(b));
                        } else {
                            boolean a = cirno.beHit(bullet.get(b).dmg) <= 0;
                            bullet.remove(bullet.get(b));
                            if (a) {
                                if (new Random().nextInt(10) == 1) {
                                    props.add(new Power(cirno.getX(), cirno.getY(), this));
                                }
                                cirno = null;
                                player.setScore(player.getScore() + 100);
                            }
                        }
                    }
            }
        }
        //札弹判定
        for (int o = 0; o < ofudas.size(); o++) {
            if (ofudas.get(o) != null) {
                if (cirno != null)
                    if (isCollision(ofudas.get(o).DecisionPoint(), cirno.DecisionPoint())) {
                        boolean a = cirno.beHit(ofudas.get(o).dmg) <= 0;
                        ofudas.remove(ofudas.get(o));
                        if (a) {
                            if (new Random().nextInt(10) == 1) {
                                props.add(new Power(cirno.getX(), cirno.getY(), this));
                            }
                            cirno = null;
                            player.setScore(player.getScore() + 100);
                        }
                    }
            }
        }
    }

    @Override
    public void isMove(GameEvent gameEvent) {

    }

    @Override
    public void isCollision(GameEvent gameEvent) {

    }

    private boolean isCollision(int[] a, int[] b) {
        int x1 = a[0], y1 = a[1], r1 = a[2];
        int x2 = b[0], y2 = b[1], r2 = b[2];
        int d = (int) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
        if (r1 + r2 == d) {
            return true;
        } else if (Math.abs(r1 - r2) < d && r1 + r2 > d) {
            return true;
        } else if (Math.abs(r1 - r2) == d) {
            return true;
        } else if (Math.abs(r1 - r2) > d) {
            return true;
        }
        return false;
    }

    public void changeDirection(Direction direction) {
        playerDirection = direction;
    }

    private Cirno createCirno() {
       cirno = new Cirno(width / 2, 80);
        return cirno;
    }

    private Player createPlayer() {
         player = new Player(width / 2, height - 50);
        return player;
    }


    public double getEnemyHp() {
        return enemyHp;
    }

    public void setEnemyHp(int enemyHp) {
        this.enemyHp = enemyHp;
    }

    public void addController(GameController gameController) {
        controller = gameController;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameController getController() {
        return controller;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }


    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cirno getCirno() {
        return cirno;
    }

    public void setCirno(Cirno cirno) {
        this.cirno = cirno;
    }

    public MusouFuuin getMusouFuuin() {
        return musouFuuin;
    }

    public void setMusouFuuin(MusouFuuin musouFuuin) {
        this.musouFuuin = musouFuuin;
    }

    public ArrayList<Bullet> getBullet() {
        return bullet;
    }

    public void setBullet(ArrayList<Bullet> bullet) {
        this.bullet = bullet;
    }

    public ArrayList<Ofuda> getOfudas() {
        return ofudas;
    }

    public void setOfudas(ArrayList<Ofuda> ofudas) {
        this.ofudas = ofudas;
    }

    public ArrayList<Prop> getProps() {
        return props;
    }

    public void setProps(ArrayList<Prop> props) {
        this.props = props;
    }

    public ArrayList<Enemy> getEnemy() {
        return enemy;
    }

    public void setEnemy(ArrayList<Enemy> enemy) {
        this.enemy = enemy;
    }

    public ArrayList<Boom> getBoom() {
        return boom;
    }

    public void setBoom(ArrayList<Boom> boom) {
        this.boom = boom;
    }
}
