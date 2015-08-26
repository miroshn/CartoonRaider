package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Кнопка с изображением истребителя и текстом под ней, используется в главном меню программы
 * Автор miroshn on 11.04.2015.
 */
public class IstrebitelButton extends Actor {
    private final String text;  // подпись к кнопке
    private final BitmapFont font; // шрифт которым выводится подпись
    private final TextureRegion region; // изображение истребителя

    /**
     * Конструктор, использует шрифт загруженный менеджером ресурсов
     *
     * @param text Надпись под кнопкой
     * @see CRAssetManager
     */
    public IstrebitelButton(String text) {
        super();
        this.text = text;
        font = CRAssetManager.getInstance().get(Res.FONT);
        region = CRAssetManager.getInstance().get(Res.ISTREBITEL);
        setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    /**
     * Отрисовка кнопки и текста под ней
     * @param batch Упаковщик с помощью которого идет отрисовка
     * @param parentAlpha Прозрачность - не используется
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color tmpColor = batch.getColor();
        batch.setColor(getColor());
        BitmapFont.TextBounds tb = font.getBounds(text);
        font.setColor(CartoonRaider.NORMAL_COLOR);
        font.draw(batch, text, getX() + getWidth() * getScaleX() / 2 - tb.width / 2, getY() - 10);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.setColor(tmpColor);
    }


}
