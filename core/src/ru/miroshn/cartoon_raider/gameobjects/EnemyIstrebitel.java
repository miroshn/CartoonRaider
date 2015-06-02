package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Класс вражеского истребителя <br/>
 * Created by miroshn on 07.04.15.
 */
public class EnemyIstrebitel extends GameObject {

    private final float BULLET_FIRE_TIME = 1; // пауза между выстрелами в секундах
    private final int bulletPrc;  // вероятность с которой выстрел произойдет
    private float bulletTime;  // сколько времени осталось до попытки выстрела
    private IstrebitelType istrebitelType; // тип истребителя

    /**
     * конструктор, первичная инициализация объекта
     * <ul>
     * <li>Установка цвета</li>
     * <li>Подготовка графики</li>
     * <li>Установка вероятности выстрела и времени выстрела</li>
     * </ul>
     */
    public EnemyIstrebitel() {
        super();

        bulletTime = BULLET_FIRE_TIME;
        bulletPrc = 5;

        setSize(getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        setColor(Color.BLACK);
    }

    /**
     * Вычисление описывающего многоугольника, дла вычисления столкновений
     * @param create создавать ли неинициализированный полигон
     * @return описывающий полигон
     */
    @Override
    public PolygonOverlaps getBoundingPolygon(boolean create) {
        if (!create) return super.getBoundingPolygon(false);
        if (super.getBoundingPolygon(false) == null) {
            float ver[] = null;
            switch (istrebitelType) {
                case I_16:
                    ver = new float[]{getWidth() / 2, 0, getWidth(), getHeight() - 10, 0, getHeight() - 10};
                    break;
                case Il_2:
                    ver = new float[]{getWidth() / 2, 0, getWidth(), getHeight() - 10, 0, getHeight() - 10};
                    break;
                case SU:
                    ver = new float[]{0, 0, getWidth(), 0, getWidth() / 2, getHeight()};
                    break;
            }
            setBoundingPolygon(new PolygonOverlaps(ver));
        }
        return super.getBoundingPolygon(true);
    }

    /**
     * Повторная инициализация объекта <br/>
     * <ul>
     *     <li>Случайным образом в зависимости от набраный очков игроком менять тип истребителя</li>
     *     <li>В зависимости от выбора подставить текстуру и установить количество жизни</li>
     *     <li>Изменить позицию истребителя</li>
     *     <li>Задать перемещение</li>
     * </ul>
     */
    @Override
    public void init() {
        int rnd = MathUtils.random(CRAssetManager.getInstance().getScore() + 1);// + CRAssetManager.getInstance().getScore();
        if (rnd >= Conf.BOSS2_BATTLE_BEGIN_SCORE) istrebitelType = IstrebitelType.SU;
        if (rnd < Conf.BOSS2_BATTLE_BEGIN_SCORE) istrebitelType = IstrebitelType.Il_2;
        if (rnd < Conf.BOSS1_BATTLE_BEGIN_SCORE) istrebitelType = IstrebitelType.I_16;

        switch (istrebitelType) {
            case I_16:
                setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.I_16));
                setHp(30);
                break;
            case Il_2:
                setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.IL_2));
                setHp(40);
                break;
            case SU:
                setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.ISTREBITEL));
                setHp(100);
                break;
        }
        setPosition(getRnd().nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(),
                Gdx.graphics.getHeight() + getHeight() + getRnd().nextInt(300));
        clearActions();
        addAction(Actions.moveTo(getRnd().nextInt(Gdx.graphics.getWidth() - (int) (getWidth() * getScaleX())) + getWidth() * getScaleX(), -200, (getRnd().nextInt(100) + 50) / 10.f));
        super.init();
    }

    /**
     * Обработка столкновения с объектом gameObject
     * @param gameObject Объект с которым произошло столкновение
     */
    @Override
    public void contact(GameObject gameObject) {
        switch (gameObject.who()) {
            case PLAYER:
                gameObject.contact(this);
                break;
            case PLAYER_BULLET:
                gameObject.contact(this);
                break;
            case ROCKET:
                gameObject.contact(this);
                break;
            default:
        }
    }

    /**
     * самоидентификация объекта
     * @return всегда GameObjects.ENEMY_ISTREBITEL
     */
    @Override
    public GameObjects who() {
        return GameObjects.ENEMY_ISTREBITEL;
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
            case PLAYER:
                ret = true;
                break;
            case PLAYER_BULLET:
                ret = true;
                break;
            case ROCKET:
                ret = true;
                break;
        }
        return ret;
    }

    /**
     * Обработка игровой логики объекта
     * <ul>
     *     <li>Состояние объекта - NORMAL - проверка на возможность выстрела</li>
     *     <li>Состояние объекта - DEAD - случайно создать звезду и проинициализировать объект для повторного использования</li>
     * </ul>
     * @param delta время в секундах прошедшее с последнего вызова метода act
     */
    @Override
    public void act(float delta) {
        switch (getState()) {
            case NORMAL:
                bulletTime -= delta;
                if (bulletTime < 0) {
                    bulletTime = BULLET_FIRE_TIME;
                    if (getRnd().nextInt(100) < bulletPrc) {
                        fireBullet();
                    }
                }
                if (getY() < -100) {
                    this.init();
                }
                break;
            case DEAD:
                if (MathUtils.random(100) < Conf.STAR_DROP_PRC) {
                    Star star = new Star();
                    star.setPosition(getX() - getWidth() * getScaleX() / 2, getY() - getHeight() * getScaleY() / 2);
                    getStage().addActor(star);
                }
                init();
                break;
            default:
        }
        super.act(delta);
    }

    /**
     * Создание снаряда (выстрел)
     */
    private void fireBullet() {
        EnemyBullet bullet = new EnemyBullet();
        bullet.setPosition(getX() - getWidth() / 2 * CartoonRaider.SCALE, getY() - getHeight() * CartoonRaider.SCALE);
        bullet.setScale(CartoonRaider.SCALE);
//        bullet.addAction(Actions.moveBy(0, Gdx.graphics.getHeight()*2,5f));
        bullet.addAction(Actions.moveBy(0, -Gdx.graphics.getHeight() * 2, 3f));
        this.getStage().addActor(bullet);
    }

    /**
     * Возможные типы истребителей
     */
    private enum IstrebitelType {
        /**
         * маленький винтовой
         */
        I_16,
        /**
         * средний винтовой
         */
        Il_2,
        /**
         * реактивный
         */
        SU
    }
}
