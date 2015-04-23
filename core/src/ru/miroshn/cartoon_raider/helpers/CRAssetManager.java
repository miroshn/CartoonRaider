package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.assets.AssetManager;
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
        instance = null;
    }

    public Istrebitel getPlayer() {
        return player;
    }

    public void setPlayer(Istrebitel player) {
        this.player = player;
    }

}
