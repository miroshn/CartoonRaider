package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.helpers.Res;
import ru.miroshn.cartoon_raider.screens.CustomScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

/**
 * Игрок - главное действующее  лицо <br/>
 * Created by miroshn on 06.04.15.
 */
public class Istrebitel extends GameObject {
    public static final float MAX_ROF = 0.2f; // максимальная скорострельность
    public static final float MIN_ROF = 0.5f; // минимальная скорострельность
    private boolean iddqd = Conf.IDDQD;  // неуязвимость, для тестовых целей
    //    public static final float MIN_ROF = 1f;
    private float speedBulletFire; // текущая скорострельность
    private float bulletTime;   // время до очередного выстрела
    private IntAction intAction; // действие для плавного изменения жизни
    private int bulletLevel; // уровень прокачки стрельбы

    public Istrebitel() {
        super();
        speedBulletFire = MIN_ROF;
        bulletTime = 0f;
        CRAssetManager.getInstance().setPlayer(this);
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    /**
     * Обработка игровой логики игрока
     * <ul>
     * <li>Статус игрока DEAD - сменить игровой экран на GameOver</li>
     * <li>Статус игрока NORMAL - если пришло время выстрела, выстрелить и обновить количество жизни</li>
     * <li>Статус игрока EXPLODING - обновить показатели жизни</li>
     * </ul>
     *
     * @param delta время в секундах прошедшее с последнего вызова act
     */
    @Override
    public void act(float delta) {
        switch (getState()) {
            case DEAD:
                ScreenManager.getInstance().show(CustomScreen.GAME_OVER);
                break;
            case NORMAL:
                bulletTime += delta;
                if (bulletTime >= speedBulletFire) {
                    bulletTime = 0;
                    fireBullet();
                }
                super.setHp(intAction.getValue());
                break;
            case EXPLODING:
                this.clearActions();
                super.setHp(intAction.getValue());
                setHp(0);
                break;
        }
        super.act(delta);
    }

    /**
     * выстрел <br/>
     * В зависимости от уровня прокачки орудия создактся 1, 2 или 3 снаряда и ракеты
     */
    private void fireBullet() {
        switch (bulletLevel) {
            case 1:
                fireBullet((int) (getX() + getWidth() * getScaleX() / 2), (int) (getY() + (getHeight()) * getScaleY()));
                break;
            case 2:
                fireBullet((int) (getX() + getWidth() * getScaleX() / 4), (int) (getY() + (getHeight() * getScaleY()) / 2));
                fireBullet((int) (getX() + getWidth() * getScaleX() * 3.0f / 4.0f), (int) (getY() + (getHeight() * getScaleY()) / 2));
                break;
            case 3:
                fireBullet((int) (getX() + getWidth() * getScaleX() / 5.0f), (int) (getY() + (getHeight() * getScaleY()) / 2));
                fireBullet((int) (getX() + getWidth() * getScaleX() * 4.0f / 5.0f), (int) (getY() + (getHeight() * getScaleY()) / 2));
                fireBullet((int) (getX() + getWidth() * getScaleX() / 2), (int) (getY() + (getHeight() * getScaleY())));
                break;
            case 4:
                fireBullet((int) (getX() + getWidth() * getScaleX() / 5.0f), (int) (getY() + (getHeight() * getScaleY()) / 2));
                fireBullet((int) (getX() + getWidth() * getScaleX() * 4.0f / 5.0f), (int) (getY() + (getHeight() * getScaleY()) / 2));
                fireBullet((int) (getX() + getWidth() * getScaleX() / 2), (int) (getY() + (getHeight() * getScaleY())));
                fireRocket(getX() + getWidth() * getScaleX() / 2, getY() + getHeight() * getScaleY());
//              todo: разделить огонь снарядами и пуск ракет, у каждого должен быть свой таймер запуска
                break;
            default:
                bulletLevel = 4;
                break;
        }
    }

    /**
     * Запуск ракеты
     * @param x координата запуска ракеты
     * @param y координата запуска ракеты
     */
    private void fireRocket(float x, float y) {
        Rocket r = Rocket.createInstance();
        r.setPosition(x, y);
        r.setScale(CartoonRaider.SCALE);
        this.getStage().addActor(r);
    }

    /**
     * Выстрел снарядом
     * @param x координата выстрела
     * @param y координата выстрела
     */
    private void fireBullet(int x, int y) {

        PlayerBullet bullet = PlayerBullet.createInstance();
        bullet.setPosition(x - bullet.getWidth() / 2, y);
        bullet.setScale(CartoonRaider.SCALE);
        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight() * 2, 2f));
        this.getStage().addActor(bullet);
    }


    /**
     * Повторная нинициализация объекта
     */
    @Override
    public void init() {
        intAction = new IntAction();
        intAction.setValue(100);
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.ISTREBITEL));
        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        float ver[] = {0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
        setBoundingPolygon(new PolygonOverlaps(ver));

        bulletLevel = Conf.PLAYER_LEVEL;
        speedBulletFire = MIN_ROF;
        super.init();
    }

    /**
     * самоидентификация объекта
     * @return всегда возвращает {@link GameObjects#PLAYER}
     */
    @Override
    public GameObjects who() {
        return GameObjects.PLAYER;
    }

    /**
     * Проверка целесообразности проведения проверки на столкновение с объектом gameObject
     * @param gameObjects объект с которым возможно столкновение
     * @return <ul><li>true - проводить проверку столкновения</li><li>false - не проводить проверку столкновений</li></ul>
     */
    @Override
    public boolean processCollision(GameObjects gameObjects) {
        boolean ret = false;
        switch (gameObjects) {
            case ENEMY_BULLET:
            case ENEMY_ISTREBITEL:
            case STAR:
            case BOSS1:
                ret = true;
                break;
        }
        return ret;
    }

    /**
     * Обработка столкновения с объектом gameObject
     * @param gameObject объект с которым произошло столкновение
     */
    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case ENEMY_BULLET:
                if (iddqd) break;
                damageDeal(((EnemyBullet) gameObject).getDamagePower());
                gameObject.setState(GOState.DEAD);
                break;
            case BOSS1:
            case ENEMY_ISTREBITEL:
                if (iddqd) break;
                if (gameObject.getState() == GOState.NORMAL) {
                    gameObject.setState(GOState.EXPLODING);
                    this.setState(GOState.EXPLODING);
                }
                break;
            case STAR:
                if (getHp() < 100) {
                    setHp(getHp() + ((Star) gameObject).getPower() * 30 / 100);
                } else {
                    speedBulletFire -= ((Star) gameObject).getPower() / 10000.0f;
                    if (speedBulletFire < MAX_ROF) {
                        speedBulletFire = MIN_ROF;
                        bulletLevel++;

                    }
                }
                gameObject.setState(GOState.DEAD);

                break;
            default:
                break;
        }
    }

    /**
     * Устанавливает количество жизни.
     * @param hp количество жизни которое нужно установить
     */
    @Override
    protected void setHp(int hp) {
//        this.getActions().removeValue(intAction,true);
        if (hp > 100) hp = 100;
        intAction.reset();
        intAction.setStart(getHp());
        intAction.setEnd(hp);
        intAction.setDuration(0.5f);
        intAction.setValue(getHp());
        this.addAction(intAction);
    }

    /**
     * Получить значение скорострельности
     * @return текущее значение скорострельности
     */
    public float getRof() {
        return speedBulletFire;
    }
}
