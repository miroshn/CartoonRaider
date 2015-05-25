package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by CAHEK on 11.04.2015.
 * кнопка с изображением истребителя и текстом под ней
 */
public class IstrebitelButton extends Istrebitel {
    private final String text;
    private final BitmapFont font;

    public IstrebitelButton(String text) {
        super();
        this.text = text;
        font = CRAssetManager.getInstance().get(Res.FONT);
    }


    @Override
    public void act(float delta) {
//        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        BitmapFont.TextBounds tb = font.getBounds(text);
//        tb.width
        font.setColor(CartoonRaider.NORMAL_COLOR);
        font.draw(batch, text, getX() + getWidth() * getScaleX() / 2 - tb.width / 2, getY() - 10);
    }

//    @Override
//    public void dispose() {
//        super.dispose();
//    }
}
