package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Заголовки игры - такие как название игры, пауза и другие
 * Created by miroshn on 11.04.15.
 *
 */
public class Title extends Actor {
    private final TextureRegion titleTR; // Текстура которая будет использоваться при рисовании заголовка

    /**
     * Конструктор, подготавливает текстуру загруженную менеджером ресурсов и инициализирует размеры и цвет заголовка
     *
     * @param type Тип заголовка (название, пауза или др.)
     * @see Titles
     */
    public Title(Titles type) {
        titleTR = CRAssetManager.getInstance().get(type.getRes());
        setSize(titleTR.getRegionWidth(), titleTR.getRegionHeight());
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    /**
     * Отрисовка заголовка
     * @param batch Упаковщик спрайтов с помощью которого производится отрисовка
     * @param parentAlpha прозрачность
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        batch.setColor(getColor());
        batch.draw(titleTR, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
        batch.setColor(color);
    }
}

