package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;

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
        float ver[] = {getX(), getY()
                , getX() + getWidth(), getY()
                , getX() + getWidth() / 2, getY() + getHeight()};
        setBoundingPolygon(new PolygonOverlaps(ver));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
