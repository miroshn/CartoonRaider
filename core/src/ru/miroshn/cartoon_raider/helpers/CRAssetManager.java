package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;

/**
 * Created by miroshn on 09.04.15.
 * менеджер ресурсов в виде сингтона
 */
public class CRAssetManager extends AssetManager {
    private static CRAssetManager instance;


    /**
     * Счет игры
     */
    private int score;

    /**
     * Ссылка на текущего игрока
     */
    private Istrebitel player;

    public static CRAssetManager getInstance() {
        if (instance == null) {
            instance = new CRAssetManager();
        }
        return instance;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
        instance = null;
    }

    public Istrebitel getPlayer() {
        return player;
    }

    public void setPlayer(Istrebitel player) {
        this.player = player;
    }

    public float getRof() {
        if (player == null) return Istrebitel.MIN_ROF;
        return player.getRof();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Res value) {
        T obj = null;
        switch (value.getType()) {
            case TEXTURE:
                //noinspection unchecked
                obj = (T) get(value.getName(), Texture.class);
                break;
            case TEXTURE_REGION:
                obj = (T) get(Res.GRAPHIC_PACK.getName(), TextureAtlas.class).findRegion(value.getName());
                break;
            case BITMAP_FONT:
                if (Gdx.graphics.getDensity() > 1)
                    obj = (T) get(Res.FONT.getName(), BitmapFont.class);
                else
                    obj = (T) get(Res.FONT_16.getName(), BitmapFont.class);
        }
        return obj;
    }

}
