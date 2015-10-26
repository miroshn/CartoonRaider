package ru.miroshn.cartoon_raider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.Background;
import ru.miroshn.cartoon_raider.gameobjects.ui.Hud;
import ru.miroshn.cartoon_raider.gameobjects.ui.Title;
import ru.miroshn.cartoon_raider.gameobjects.ui.Titles;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Created by miroshn on 07.04.15.
 * Экран - конец игры
 */
public class GameOverScreen implements Screen {
    private final Stage stage;
    private final Title title;
    private final Hud hud;

    private final Table table;
    private final BitmapFont font;
    private final Preferences preferences;

    private long placeTime[];
    private int placeScore[];
    private int place;

    public GameOverScreen() {
        placeTime = new long[Conf.GO_NUM_RECORDS];
        placeScore = new int[Conf.GO_NUM_RECORDS];
        preferences = Gdx.app.getPreferences(Conf.OPTIONS_NAME);
        for (int i = 0; i < Conf.GO_NUM_RECORDS; i++) {
            placeTime[i] = preferences.getLong(Conf.PLACE_TIME + i, System.currentTimeMillis());
            placeScore[i] = preferences.getInteger(Conf.PLACE_SCORE + i, 0);
        }

        stage = new Stage();
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().show(CustomScreen.MENU_SCREEN);
                return true;
            }
        });
        title = new Title(Titles.GAME_OVER_TITLE);
        float scale = (Gdx.graphics.getWidth() * 3.0f / 5.0f / title.getWidth());
        title.setWidth(title.getWidth() * scale);
        title.setHeight(title.getHeight() * scale);

        hud = new Hud();

        table = new Table();
        table.setFillParent(true);
        table.setDebug(Conf.DEBUG);
        font = CRAssetManager.getInstance().get(Res.FONT);
    }

    @Override
    public void show() {
        title.clearActions();
//        title.setPosition((Gdx.graphics.getWidth() - title.getWidth() * title.getScaleX()) / 2.0f,
//                (Gdx.graphics.getHeight() - title.getHeight() * title.getScaleY()) / 2.0f);

        stage.getActors().clear();
        stage.addActor(Background.getInstance());
//        stage.addActor(title);

        table.clear();
        table.add(title).colspan(3).padBottom(title.getHeight() / 3).row();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, CartoonRaider.NORMAL_COLOR);
        place = 0;
        for (int i = 0; i < Conf.GO_NUM_RECORDS; i++) {
            if (CRAssetManager.getInstance().getScore() > placeScore[i]) {
                for (int j = Conf.GO_NUM_RECORDS - 1; j > i; j--) {
                    placeScore[j] = placeScore[j - 1];
                    placeTime[j] = placeTime[j - 1];
                }
                placeScore[i] = CRAssetManager.getInstance().getScore();
                placeTime[i] = System.currentTimeMillis();
                place = i + 1;
                break;
            }
        }
        if (place != 0)
            table.add(new Label("Поздравляю у вас " + place + " место", labelStyle)).colspan(3).row();
        else
            table.add(new Label("Вы не заняли призового места", labelStyle)).colspan(3).row();
        table.add(new Label("Таблица рекордов", labelStyle)).colspan(3).row();
        table.add(new Label(Conf.PLACE_TEXT, labelStyle));
        table.add(new Label(Conf.SCORE_TEXT, labelStyle));
        table.add(new Label(Conf.PLACE_TIME_TEXT, labelStyle)).row();


//        if (CRAssetManager.getInstance().getScore() > placeScore[0]) { // first place
//            for (int i = 0; i < Conf.GO_NUM_RECORDS; i++) {
//
//            }
//            place3Score = place2Score;
//            place2Score = place1Score;
//            place1Score = CRAssetManager.getInstance().getScore();
//            place3Time = place2Time;
//            place2Time = place1Time;
//            place1Time = System.currentTimeMillis();
//        } else if (CRAssetManager.getInstance().getScore() > place2Score) { // second place
//            place3Score = place2Score;
//            place2Score = CRAssetManager.getInstance().getScore();
//            place3Time = place2Time;
//            place2Time = System.currentTimeMillis();
//        } else if (CRAssetManager.getInstance().getScore() > place3Score) { // treed place
//            place3Score = CRAssetManager.getInstance().getScore();
//            place3Time = System.currentTimeMillis();
//        }

        for (int i = 0; i < Conf.GO_NUM_RECORDS; i++) {
            table.add(new Label("" + (i + 1), labelStyle));
            table.add(new Label("" + placeScore[i], labelStyle));
//            table.add(new Label(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "",labelStyle)).row();
            if (placeScore[i] == 0) placeTime[i] = System.currentTimeMillis();
            table.add(new Label((System.currentTimeMillis() - placeTime[i]) / 1000 / 60 + " мин.", labelStyle)).row();
        }

        stage.addActor(table);
        stage.addActor(hud);
        Gdx.input.setInputProcessor(stage);

        for (int i = 0; i < Conf.GO_NUM_RECORDS; i++) {
            preferences.putLong(Conf.PLACE_TIME + i, placeTime[i]);
            preferences.putInteger(Conf.PLACE_SCORE + i, placeScore[i]);
        }
        preferences.flush();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
//        stage.dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.debug(getClass().getSimpleName(), "dispose called");
        stage.dispose();
    }

}
