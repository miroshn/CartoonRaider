package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.miroshn.cartoon_raider.CartoonRaider;

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

//        this.setDebug(false);
//        this.setTouchable(Touchable.enabled);
        // Назначить на клик кнопки действие, для начала просто выход
/*
        boolean res = this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                return super.touchDown(event, x, y, pointer, button);
                Gdx.app.exit();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                Gdx.app.exit();
            }

        });
*/
    }

//    @Override
//    public Actor hit(float x, float y, boolean touchable) {
//        if (x != -100) {
//            Gdx.app.log("Hit", "x=" + x + " y=" + y);
//            if (x > 0 && x < (getWidth() * getScaleX()) && y > 0 && y < (getHeight() * getScaleY()))
//                Gdx.app.exit();
//        }
//        return super.hit(x, y, touchable);
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        BitmapFont.TextBounds tb = font.getBounds(text);
//        tb.width
        font.setColor(CartoonRaider.COLOR);
        font.draw(batch, text, getX() + getWidth() * getScaleX() / 2 - tb.width / 2, getY() - 10);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
