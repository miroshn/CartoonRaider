package ru.miroshn.cartoon_raider;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by miroshn on 09.04.15.
 */
public class CRAssetManager extends AssetManager {
    private static CRAssetManager instance;

    public static CRAssetManager getInstance() {
        if (instance == null) {
            instance = new CRAssetManager();
        }
        return instance;
    }

    public void init() {

    }
}
