package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.PolygonOverlaps;

/**
 * Created by miroshn on 04.05.15.
 * Истребитель и16
 */
public class I_16 extends EnemyIstrebitel {
    @Override
    public void init() {
        super.init();
        if (getRnd().nextInt(100) < CRAssetManager.getInstance().getScore()) return;
        setTextureRegion(new TextureRegion((Texture) CRAssetManager.getInstance().get("i_16.png")));
        setHp(30);
    }

    @Override
    public PolygonOverlaps getBoundingPolygon(boolean create) {
        if (!create) return super.getBoundingPolygon(false);
        if (super.getBoundingPolygon(false) == null) {
            float ver[] = {getWidth() / 2, 0, getWidth(), getHeight() - 10, 0, getHeight() - 10};
            setBoundingPolygon(new PolygonOverlaps(ver));
        }
        return super.getBoundingPolygon(true);
    }
}
