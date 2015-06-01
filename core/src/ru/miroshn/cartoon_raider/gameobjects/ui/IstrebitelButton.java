package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Кнопка с изображением истребителя и текстом под ней, используется в главном меню программы
 * Автор miroshn on 11.04.2015.
 */
public class IstrebitelButton extends Istrebitel {
    private final String text;  // подпись к кнопке
    private final BitmapFont font; // шрифт которым выводится подпись

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
    }

    /**
     * Метод заглушка, используется для подавления логики работы класса Istrebitel, если его не будет, или будет вызван
     * super.act(delta); кнопка начнет стрелять и вести себя не как кнопка..
     * @param delta время прошедшее с предыдущего вызова метода act(delta) - в данном случае не используется
     */
    @Override
    public void act(float delta) {
//        super.act(delta);
    }

    /**
     * Отрисовка кнопки и текста под ней
     * @param batch Упаковщик с помощью которого идет отрисовка
     * @param parentAlpha Прозрачность - не используется
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        BitmapFont.TextBounds tb = font.getBounds(text);
//        tb.width
        font.setColor(CartoonRaider.NORMAL_COLOR);
        font.draw(batch, text, getX() + getWidth() * getScaleX() / 2 - tb.width / 2, getY() - 10);
    }

}
