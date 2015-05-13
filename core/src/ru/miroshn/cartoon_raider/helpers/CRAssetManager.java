package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private BitmapFont font;

    public static CRAssetManager getInstance() {
        if (instance == null) {
            instance = new CRAssetManager();
        }
        return instance;
    }

    public BitmapFont getFont() {
        return (BitmapFont) get("comic_sans.fnt");
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public void init() {

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
        if (font != null) font.dispose();
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

}
