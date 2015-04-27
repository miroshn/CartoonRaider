package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 15.04.15.
 * отображение информации на игровом экране
 */
public class Hud extends Actor {
    private BitmapFont font;
    private int hp;
    private float rof;
    private TextureRegion texture;
    private TextureRegion barTR;
    private TextureRegion hpTR;
    private TextureRegion rofTR;
    private Color tmp_color;
    private int scrW, scrH;

    public Hud() {
        super();
        scrW = Gdx.graphics.getWidth();
        scrH = Gdx.graphics.getHeight();
        hp = 0;
        rof = 1;
        font = CRAssetManager.getInstance().getFont();
        font.setColor(Color.BLUE);
        setPosition(5, scrH - font.getCapHeight());
        texture = new TextureRegion((Texture) CRAssetManager.getInstance().get("border_bar.png"));
        barTR = new TextureRegion((Texture) CRAssetManager.getInstance().get("bar.png"));
        hpTR = new TextureRegion((Texture) CRAssetManager.getInstance().get("hp.png"));
        rofTR = new TextureRegion((Texture) CRAssetManager.getInstance().get("rof.png"));
        tmp_color = new Color();
        setScaleX(scrW * 5.0f / (texture.getRegionWidth() * 100.f));
        setScaleY(scrH * 80.0f / (texture.getRegionHeight() * 100.f));
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
        batch.setColor(tmp_color.r, tmp_color.g, tmp_color.b, 0.5f);

        // output HP
        batch.draw(texture, 5, 5, texture.getRegionWidth() * getScaleX(), texture.getRegionHeight() * getScaleY());
        batch.draw(hpTR, 5, 5 + texture.getRegionHeight() * getScaleY() + 5,
                texture.getRegionWidth() * getScaleX(), hpTR.getRegionHeight() * getScaleX());
        float height = texture.getRegionHeight() * getScaleY();
        for (int y = 0; y < hp * height / 100.f; y += barTR.getRegionHeight()) {
            batch.draw(barTR, 5, 5 + y, barTR.getRegionWidth() * getScaleX(), barTR.getRegionHeight());
        }

        // output Rof
        batch.draw(texture, scrW - texture.getRegionWidth() - 5, 5,
                texture.getRegionWidth() * getScaleX(), texture.getRegionHeight() * getScaleY());
        batch.draw(rofTR, scrW - texture.getRegionWidth() - 5, 5 + texture.getRegionHeight() * getScaleY() + 5,
                texture.getRegionWidth() * getScaleX(), rofTR.getRegionHeight() * getScaleX());

        float prcRof = -(rof - Istrebitel.MIN_ROF) * 100 / (Istrebitel.MIN_ROF - Istrebitel.MAX_ROF);

        for (int y = 0; y < prcRof * height / 100.f; y += barTR.getRegionHeight()) {
            batch.draw(barTR, scrW - texture.getRegionWidth() - 5,
                    5 + y, barTR.getRegionWidth() * getScaleX(), barTR.getRegionHeight());
        }

        batch.setColor(tmp_color);
    }
}
