package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;

/**
 * <br/>Created by miroshn on 01.09.15.
 */
public class Toast extends Table {
    private final float TOAST_WIDTH = Conf.TOAST_WIDTH * Gdx.graphics.getWidth();
    private Label label;
    //    private final Table table;
    private float fadeTime = Conf.TOAST_FADE_TIME;
    private float showTime = Conf.TOAST_SHOW_TIME;

    public Toast(String text) {
        label = new Label(text, CRAssetManager.getInstance().getLabelStyle());
        label.setFontScale(TOAST_WIDTH / label.getWidth());
        setFillParent(true);
//        setDebug(true);
        add(label).center().top().expandY().padTop(Gdx.graphics.getHeight() * Conf.TOAST_TOP_PAD);
        row();
        addAction(Actions.sequence(Actions.fadeOut(0)
                , Actions.fadeIn(fadeTime)
                , Actions.delay(showTime)
                , Actions.fadeOut(fadeTime),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        if (getParent() != null) {
                            getParent().getChildren().removeValue(Toast.this, true);
                        }
                        return true;
                    }
                }));
    }
}
