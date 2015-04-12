package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by CAHEK on 11.04.2015.
 * кнопка с изображением истребителя и текстом под ней
 */
public class IstrebitelButton extends Istrebitel {
    private String text;
    private BitmapFont font;

    public IstrebitelButton(String text) {
        super();
        this.text = text;
        font = new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        BitmapFont.TextBounds tb = font.getBounds(text);
//        tb.width
        font.draw(batch, text, getX() + getWidth() * getScaleX() / 2 - tb.width / 2, getY() - 10);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
