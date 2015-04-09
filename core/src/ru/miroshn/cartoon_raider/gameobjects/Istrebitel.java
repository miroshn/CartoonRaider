package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.CRAssetManager;

/**
 * Created by miroshn on 06.04.15.
 * главное действующее лицо
 */
public class Istrebitel extends GameObject implements Disposable {
    private Texture texture;
    private TextureRegion region;

    public Istrebitel() {
        texture = CRAssetManager.getInstance().get("istrebitel1.png");
        region = new TextureRegion(texture);
        setTextureRegion(region);
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
