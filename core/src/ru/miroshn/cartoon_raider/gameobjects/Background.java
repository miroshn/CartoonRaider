package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Фон игры, так как используется на многих экранах и присутствует всегда - сделан в виде синглтона <br/>
 * Created by miroshn on 08.04.15.
 */
public class Background extends Actor implements Disposable {
    private static Background instance; // экземпляр класса
    private final TextureRegion backgroundTR; // текстура фона
    private MoveByAction action; // постоянное действие для фона - смещение
    private int SPEED_SCROLL = 5; // скорость перемещения фона


    /**
     * Конструктор, подготовка текстуры, установка размера и масштаба в зависимости от DPI экрана
     */
    private Background() {
        backgroundTR = CRAssetManager.getInstance().get(Res.BACKGROUND);
        setPosition(0, 0);
        setSize(backgroundTR.getRegionWidth(), backgroundTR.getRegionHeight());
        if (Gdx.graphics.getDensity() > 1)
            setScale(Gdx.graphics.getDensity());
        this.addAction(createAction());
        this.setParent(null);
    }

    /**
     * Получить ссылку на экземпляр класса
     *
     * @return Экземпляр класса Background
     */
    public static Background getInstance() {
        if (instance == null) instance = new Background();
        return instance;
    }

    /**
     * настройка движения фона
     * @return Объект сдвига
     */
    private MoveByAction createAction() {
        action = new MoveByAction();
        action.setAmountY(-backgroundTR.getRegionHeight());
        action.setDuration(SPEED_SCROLL);
        return action;
    }

    /**
     * Отрисовка фона, фон застилается по горизонтали и вертикали в стык, пока не будет заполнен весь видимый экран
     * @param batch упаковщик с помошью которого рисуется объект
     * @param parentAlpha прозрачность
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.disableBlending();
        super.draw(batch, parentAlpha);
        Color tmpColor = batch.getColor();
        batch.setColor(getColor());
        for (int x = 0; x < Gdx.graphics.getWidth(); x += backgroundTR.getRegionWidth()) {
            for (int y = 0; y < Gdx.graphics.getHeight() * 2; y += backgroundTR.getRegionHeight()) {
                batch.draw(backgroundTR, getX() + x, getY() + y, getWidth(), getHeight(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        }
        batch.enableBlending();
        batch.setColor(tmpColor);
    }

    /**
     * Обсчет логики фона, если элемент фона ушел за пределы экрана он перемещается обратно, при этом обновляется
     * действие движения, в итоге получается плавное, постоянное движение фона
     * @param delta время прошедшее от предыдущего пересчета кадра
     */
    @Override
    public void act(float delta) {
        super.act(delta);
//        Gdx.app.log("Debug", "Actions size = " + getActions().size);
        if (getY() <= -getHeight()) {
            setPosition(0, 0);

            this.removeAction(action);
            this.addAction(createAction());
        }
    }

    /**
     * Уничтожение синглтона, его необходимо проводить обязательно, иначе появляются проблемы на мобильных устройствах
     */
    @Override
    public void dispose() {
        Gdx.app.debug(getClass().getSimpleName(), "dispose called");
//        texture.dispose();
        instance = null;
    }


}
