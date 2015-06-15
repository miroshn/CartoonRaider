package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.utils.Pools;
import ru.miroshn.cartoon_raider.CartoonRaider;

/**
 * Снаряд выпущенный вражиной <br/>
 * Created by miroshn on 16.04.15.
 */
public class EnemyBullet extends Bullet {
    private float oldx, oldy; // предыдущее положение объекта


    /**
     * Конструктор, устанавливает цвет снаряда.
     */
    private EnemyBullet() {
        super();
        setColor(CartoonRaider.ENEMY_COLOR);
        oldx = getX();
        oldy = getY();
    }

    /**
     * Выдает экземпляр класса из пула
     *
     * @return экземпляр класса
     */
    public static EnemyBullet createInstance() {
        EnemyBullet bullet = Pools.obtain(EnemyBullet.class);
        bullet.init();
        return bullet;
    }

    /**
     * Работа игровой логики объекта <br/>
     * Если движение объекта пекратилось - он уничтожается <br/>
     * Если объект вышел за пределы экрана вниз он уничтожается
     *
     * @param delta время в секундах прошедшее с последнего вызова act
     */
    @Override
    public void act(float delta) {
        if (oldx == getX() && oldy == getY()) {
            setState(GOState.DEAD);
        }
        else {
            oldx = getX();
            oldy = getY();
        }
        if (getY() < -getHeight())
            setState(GOState.DEAD);
        if (this.getState() == GOState.DEAD) {
            getStage().getActors().removeValue(this, true);
//            Pools.free(this);
        }
        super.act(delta);
    }

    /**
     * самоидентификация объекта
     * @return Всегда GameObjects.ENEMY_BULLET
     * @see GameObjects#ENEMY_BULLET
     */
    @Override
    public GameObjects who() {
        return GameObjects.ENEMY_BULLET;
    }

    /**
     * Определение с каким объектами имеет смысл проверять столкновения, обрабатываются столкновения только с игроком и
     * ракетой
     * @param gameObjects объект с которым возможно столкновение
     * @return true если есть смысл проверять столкновение, false - если столкновение не важно
     */
    @Override
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case PLAYER:
                ret = true;
                break;
            case ROCKET:
                ret = true;
                break;
        }
        return ret;
    }

    /**
     * Столкновение с объектом gameObject произошло - проводится обработка столкновения
     * @param gameObject объект с которым произошло столкновение
     */
    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case PLAYER:
                gameObject.contact(this);
                break;
            case ROCKET:
                gameObject.contact(this);
                break;
            default:
                break;
        }
    }
}
