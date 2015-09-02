package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
 * Первый в игре босс <br/>
 * Created by miroshn on 19.05.15.
 */
public class Boss1 extends GameObject {
    private final float BULLET_FIRE_TIME = Conf.BOSS1_BULLET_FIRE_TIME; // Скорострельность босса
    private int bulletPrc = 70; // шанс того что выстрел произойдет
    private float bulletTime; // текущее время оставшееся до попытки выстрела

    private float oldx, oldy; // старые координаты объекта, для обнаружения остановки объекта
    private float moveToX, moveToY; // куда будет двигаться объект

    private Sound alramSound; // Звук появления босса

    /**
     * Конструктор, подготовка графики, установка количества жизни, установка цвета и проигрывание звука появления босса
     */
    public Boss1() {
        super();
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.BOSS1));
        setHp(1000 * CRAssetManager.getInstance().getScore() / 100);
        bulletTime = BULLET_FIRE_TIME;
        setColor(Color.RED);
        oldx = getX();
        oldy = getY();
        moveToX = Gdx.graphics.getWidth() - getWidth() * getScaleX();
        moveToY = Gdx.graphics.getHeight() / 2.0f - getHeight() * getScaleY();
        if (Gdx.app.getPreferences(Conf.OPTIONS_NAME).getBoolean(Conf.SOUND_ENABLE_PREF_KEY, true)) {
            alramSound = CRAssetManager.getInstance().get(Res.ALRAM_SOUND);
            alramSound.play(Gdx.app.getPreferences(Conf.OPTIONS_NAME).getFloat(Conf.SOUND_VOLUME_PREF_KEY, Conf.SOUD_VOLUME));
        }
    }


    /**
     * Самоидентификация объекта
     *
     * @return Всегда возвращает значение GameObjects.BOSS1
     * @see GameObjects
     * @see GameObject#who()
     */
    @Override
    public GameObjects who() {
        return GameObjects.BOSS1;
    }


    /**
     * Обработка логики работы объекта <br/>
     * Если пришло время выстрела делается попытка выстрела <br/>
     * Проверка на то что объект с прошлого вызова методе не изменил позицию - означает что нужно пересоздать команду
     * на перемещение. <br/>
     * Если состояние объекта стало GOState.DEAD вместо объекта создается группа звезд (Star)
     * @param delta время прошедшее с последнего вызова метода act в секундах
     * @see ru.miroshn.cartoon_raider.gameobjects.GameObject.GOState
     * @see Star
     */
    @Override
    public void act(float delta) {
        bulletTime -= delta;
        if (bulletTime < 0) {
            int ttt = getRnd().nextInt(100);
            bulletTime = BULLET_FIRE_TIME;
            if (ttt <= bulletPrc) {
                fireBullet();
            }
        }

        if (oldx == getX() && oldy == getY()) {
            float tox = MathUtils.random(moveToX);
            float toy = Gdx.graphics.getHeight() / 2.0f + MathUtils.random(moveToY);
            addAction(Actions.moveTo(tox, toy, Conf.BOSS_MOVE_TIME));
        }
        oldx = getX();
        oldy = getY();

        if (getState() == GOState.DEAD) {
            for (int i = 0; i < Conf.NUM_STARS_AFTER_KILL_BOSS; i++) {
                Star star = Star.createInstance(Star.class);
                float posx = getX() + getWidth() / 2.0f;
                float posy = getY() + getHeight() / 2.0f;
                star.setPosition(posx, posy);
                star.addAction(Actions.moveBy(MathUtils.random(-Conf.STARS_MOVE_RANGE, Conf.STARS_MOVE_RANGE)
                        , MathUtils.random(-Conf.STARS_MOVE_RANGE, Conf.STARS_MOVE_RANGE), 1));

                getStage().addActor(star);
            }
        }

        super.act(delta);
    }


    /**
     * Выстрел. <br/>
     * Создает снаряд который летит в игрока. Расчитывается скорость пули.
     */
    private void fireBullet() {
        Istrebitel player = CRAssetManager.getInstance().getPlayer();
        float distToPlayer = (float) Math.sqrt(Math.pow(getX() - player.getX(), 2) + Math.pow(getY() - player.getY(), 2));
        float bt = distToPlayer / Gdx.graphics.getHeight() * 1.5f;
//        EnemyBullet bullet = new EnemyBullet();
        EnemyBullet bullet = EnemyBullet.createInstance(EnemyBullet.class);
        float bulletX = getX() + getWidth() / 2 * CartoonRaider.SCALE;
        float bulletY = getY() + getHeight() / 4.0f * CartoonRaider.SCALE;
        bullet.setPosition(bulletX, bulletY);
        bullet.setScale(CartoonRaider.SCALE);

        float dx = (player.getX() + player.getWidth() / 2.0f * CartoonRaider.SCALE - bulletX) * 3.0f;
        float dy = (player.getY() + player.getHeight() / 2.0f * CartoonRaider.SCALE - bulletY) * 3.0f;
        bullet.addAction(Actions.moveBy(dx, dy, bt * 3));
        this.getStage().addActor(bullet);
    }

    /**
     * Обработка столкновений, босс реагирует только на столкновения с игроком, снарядом игрока и ракетой
     * @param gameObjects Объект с которым возможно столкновение
     * @return true - обрабатывать столкновение, false - столкновение не важно
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
     * Обработка столкновений
     * @param gameObject объект с которым произошло столкновение
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
        }
    }

    /**
     * Генерация описывающего многоугольника, необходимого для определения столкновений
     * @param create создавать ли многоугольник если он еще не готов к эксплуатации
     * @return Описывающий многоугольник
     */
    @Override
    public PolygonOverlaps getBoundingPolygon(boolean create) {
        if (!create) return super.getBoundingPolygon(false);
        if (super.getBoundingPolygon(false) == null) {
            float[] ver = new float[]{0, getHeight() * getScaleY() * 2.0f / 3.0f
                    , getWidth() * getScaleX() / 2.0f, getHeight() * getScaleY()
                    , getWidth() * getScaleX(), getHeight() * getScaleY() * 2.0f / 3.0f
                    , getWidth() * getScaleX() / 2.0f, 0};
            setBoundingPolygon(new PolygonOverlaps(ver));
        }
        return super.getBoundingPolygon(true);
    }
}
