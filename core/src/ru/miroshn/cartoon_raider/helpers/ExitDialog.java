package ru.miroshn.cartoon_raider.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.miroshn.cartoon_raider.screens.CustomScreen;
import ru.miroshn.cartoon_raider.screens.ScreenManager;

/**
 * <br/>Created by miroshn on 28.08.15.
 */
public class ExitDialog extends Dialog {
    private final float DIALOG_PROPORTION = 2.0f / 3.0f;
    private final float DIALOG_WIDTH = Gdx.graphics.getWidth() * 0.9f;
    private final float TEXT_WIDTH = 2.f / 5.f;
    private final float DIALOG_HEIGHT = DIALOG_WIDTH * DIALOG_PROPORTION;
    private final float BUTTON_SIZE = DIALOG_WIDTH / 4.5f;
    private final float BUTTON_STEP = DIALOG_WIDTH / 9.0f;
    private Button ok, cancel;
    private CustomScreen backScreen;

    public ExitDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
        init(null);
    }

    public ExitDialog(String title, WindowStyle windowStyle, CustomScreen backScreen) {
        super(title, windowStyle);
        init(backScreen);
    }

    private void init(CustomScreen backScreen) {
        setVisible(false);
        CRAssetManager asset = CRAssetManager.getInstance();
        this.backScreen = backScreen;

        Button.ButtonStyle okStyle = new Button.ButtonStyle();
        okStyle.up = new TextureRegionDrawable(((TextureRegion) asset.get(Res.EXIT_DIALOG_OK)));
        okStyle.down = new TextureRegionDrawable(((TextureRegion) asset.get(Res.EXIT_DIALOG_OK_PRESSED)));
        ok = new Button(okStyle);
        ok.setColor(Color.YELLOW);

        Button.ButtonStyle cancelStyle = new Button.ButtonStyle();
        cancelStyle.up = new TextureRegionDrawable(((TextureRegion) asset.get(Res.EXIT_DIALOG_CANCEL)));
        cancelStyle.down = new TextureRegionDrawable(((TextureRegion) asset.get(Res.EXIT_DIALOG_CANCEL_PRESSED)));
        cancel = new Button(cancelStyle);
        cancel.setColor(Color.YELLOW);

        Label.LabelStyle labelStyle = new Label.LabelStyle(((BitmapFont) asset.get(Res.FONT)), Color.YELLOW);
        text("Exit?", labelStyle);
        Label label = ((Label) getContentTable().getCells().get(0).getActor());
        label.setFontScale(DIALOG_WIDTH / label.getWidth() * TEXT_WIDTH);

        button(ok, Boolean.TRUE);
        button(cancel, Boolean.FALSE);
        getButtonTable().getCell(ok).size(BUTTON_SIZE).padRight(BUTTON_STEP);
        getButtonTable().getCell(cancel).size(BUTTON_SIZE);
        setColor(Color.YELLOW);
    }

    @Override
    public float getPrefWidth() {
        return DIALOG_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return DIALOG_HEIGHT;
    }

    @Override
    protected void result(Object object) {
        if (object.equals(Boolean.TRUE)) {
            setStage(null);
            if (backScreen == null) {
                Gdx.app.exit();
            } else {
                ScreenManager.getInstance().show(backScreen);
            }
        }
    }

    @Override
    public Dialog show(Stage stage) {
        setVisible(true);
        return super.show(stage);
    }

    @Override
    public void hide() {
        setVisible(false);
        super.hide();
    }
}
