package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;

/**
 * Created by miroshn on 04.05.15.
 * Истребитель и16
 */
public class I_16 extends EnemyIstrebitel {
    @Override
    public void init() {
        super.init();
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("i_16.png")));
        setHp(30);
    }
}
