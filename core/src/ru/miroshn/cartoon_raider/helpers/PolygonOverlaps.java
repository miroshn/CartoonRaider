package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.math.Polygon;

/**
 * Created by miroshn on 13.04.15.
 * Полигон с возможностью проверки наложений
 */
public class PolygonOverlaps extends Polygon {

    public PolygonOverlaps(float[] ver) {
        super(ver);
    }

    public PolygonOverlaps() {
        super();
    }

    public boolean overlaps(Polygon polygon) {
        boolean res = false;
        float[] pVer = polygon.getTransformedVertices();
        for (int i = 0; i < pVer.length; i += 2) {
            res |= this.contains(pVer[i], pVer[i + 1]);
        }
        return res;
    }
}
