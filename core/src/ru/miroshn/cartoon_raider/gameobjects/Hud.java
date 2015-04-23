package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.screens.GameScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

/**
 * Created by miroshn on 15.04.15.
 * отображение информации на игровом экране
 */
public class Hud extends Actor {
    private BitmapFont font;
    private int score;
    private int hp;
    private TextureRegion texture;
    private TextureRegion bar_texture;
    private TextureRegion hpTextureRegion;
    private Color tmp_color;

    public Hud() {
        super();
        score = 0;
        hp = 0;
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        setPosition(5, Gdx.graphics.getHeight() - font.getCapHeight());
        texture = new TextureRegion((Texture) CRAssetManager.getInstance().get("border_bar.png"));
        bar_texture = new TextureRegion((Texture) CRAssetManager.getInstance().get("bar.png"));
        hpTextureRegion = new TextureRegion((Texture) CRAssetManager.getInstance().get("hp.png"));
        tmp_color = new Color();
        setScaleX(Gdx.graphics.getWidth() * 5.0f / (texture.getRegionWidth() * 100.f));
        setScaleY(Gdx.graphics.getHeight() * 80.0f / (texture.getRegionHeight() * 100.f));
        Gdx.app.log(getClass().getSimpleName(), "ScaleX = " + getScaleX());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (ScreenManager.getInstance().getCurrentScreen() instanceof GameScreen) {
            score = ((GameScreen) (ScreenManager.getInstance().getCurrentScreen())).getScore();
            hp = ((GameScreen) (ScreenManager.getInstance().getCurrentScreen())).getPlayerHp();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        font.drawMultiLine(batch, "Score: " + score, getX(), getY());

        tmp_color.set(batch.getColor());
        batch.setColor(tmp_color.r, tmp_color.g, tmp_color.b, 0.5f);

        batch.draw(texture, 5, 5, texture.getRegionWidth() * getScaleX(), texture.getRegionHeight() * getScaleY());
        batch.draw(hpTextureRegion, 5, 5 + texture.getRegionHeight() * getScaleY() + 5,
                hpTextureRegion.getRegionWidth() * getScaleX(), hpTextureRegion.getRegionHeight() * getScaleX());
        float height = texture.getRegionHeight() * getScaleY();
        for (int y = 0; y < hp * height / 100.f; y += bar_texture.getRegionHeight()) {
            batch.draw(bar_texture, 5, 5 + y, bar_texture.getRegionWidth() * getScaleX(), bar_texture.getRegionHeight());
        }

        batch.setColor(tmp_color);
    }
}
