package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by miroshn on 15.04.15.
 * отображение информации на игровом экране
 */
public class Hud extends Actor {
    private final int OFFSET = 5;
    private final BitmapFont font;
    private final TextureRegion texture;
    private final TextureRegion barTR;
    private final TextureRegion hpTR;
    private final TextureRegion rofTR;
    private final TextureRegion pauseTR;
    private final Color tmp_color;
    private final int scrW;
    private final int scrH;
    private int hp;
    private float rof;

    public Hud() {
        super();
        scrW = Gdx.graphics.getWidth();
        scrH = Gdx.graphics.getHeight();
        hp = 0;
        rof = 1;
        font = CRAssetManager.getInstance().get(Res.FONT);
        font.setColor(Color.BLUE);
        setPosition(OFFSET, scrH - font.getCapHeight());
        texture = CRAssetManager.getInstance().get(Res.BORDER_BAR);
        barTR = CRAssetManager.getInstance().get(Res.BAR);
        hpTR = CRAssetManager.getInstance().get(Res.HP);
        rofTR = CRAssetManager.getInstance().get(Res.ROF);
        pauseTR = CRAssetManager.getInstance().get(Res.PAUSE_BUTTON);
        tmp_color = new Color();
        setScaleX(scrW * 5.0f / (texture.getRegionWidth() * 100.f));
        setScaleY(scrH * 80.0f / (texture.getRegionHeight() * 100.f));
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hp = CRAssetManager.getInstance().getPlayer().getHp();
        rof = CRAssetManager.getInstance().getRof();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        font.drawMultiLine(batch, "Score: " + CRAssetManager.getInstance().getScore(), getX(), getY());

        tmp_color.set(batch.getColor());
        batch.setColor(getColor().r, getColor().g, getColor().b, 0.5f);

        // output HP
        batch.draw(texture, OFFSET, OFFSET, texture.getRegionWidth() * getScaleX(), texture.getRegionHeight() * getScaleY());
        batch.draw(hpTR, OFFSET, OFFSET + texture.getRegionHeight() * getScaleY() + OFFSET,
                texture.getRegionWidth() * getScaleX(), hpTR.getRegionHeight() * getScaleX());
        float height = texture.getRegionHeight() * getScaleY();
        for (int y = 0; y < hp * height / 100.f; y += barTR.getRegionHeight()) {
            batch.draw(barTR, OFFSET, OFFSET + y, barTR.getRegionWidth() * getScaleX(), barTR.getRegionHeight());
        }

        // output Rof
        batch.draw(texture, scrW - texture.getRegionWidth() * getScaleX() - OFFSET, OFFSET,
                texture.getRegionWidth() * getScaleX(), texture.getRegionHeight() * getScaleY());
        batch.draw(rofTR, scrW - texture.getRegionWidth() * getScaleX() - OFFSET, OFFSET + texture.getRegionHeight() * getScaleY() + OFFSET,
                texture.getRegionWidth() * getScaleX(), rofTR.getRegionHeight() * getScaleX());

        float prcRof = -(rof - Istrebitel.MIN_ROF) * 100 / (Istrebitel.MIN_ROF - Istrebitel.MAX_ROF);

        for (int y = 0; y < prcRof * height / 100.f; y += barTR.getRegionHeight()) {
            batch.draw(barTR, scrW - texture.getRegionWidth() * getScaleX() - OFFSET,
                    OFFSET + y, barTR.getRegionWidth() * getScaleX(), barTR.getRegionHeight());
        }

        batch.draw(pauseTR, scrW - pauseTR.getRegionWidth() * CartoonRaider.SCALE - OFFSET, scrH - pauseTR.getRegionHeight() * CartoonRaider.SCALE - OFFSET,
                pauseTR.getRegionWidth() * CartoonRaider.SCALE, pauseTR.getRegionHeight() * CartoonRaider.SCALE);

        batch.setColor(tmp_color);
    }

    public boolean pauseTouched(int screenX, int screenY) {
        return screenX > scrW - pauseTR.getRegionWidth() * CartoonRaider.SCALE - OFFSET && screenY > scrH - pauseTR.getRegionHeight() * CartoonRaider.SCALE - OFFSET;
    }
}
