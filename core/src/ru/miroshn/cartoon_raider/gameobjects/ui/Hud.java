package ru.miroshn.cartoon_raider.gameobjects.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.miroshn.cartoon_raider.CartoonRaider;
import ru.miroshn.cartoon_raider.gameobjects.Istrebitel;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * отображение информации на игровом экране
 * Created by miroshn on 15.04.15.
 */
public class Hud extends Actor {
    private final int OFFSET = 5;  // Отступ от края экрана до элементов интерфейса
    private final BitmapFont font; // Шрифт для вывода счета
    private final TextureRegion borderBarTR; // Текстура границы индикаторов жизни и скорострельности
    private final TextureRegion barTR;  // Заполнитель индикаторов жизни и скорострельности
    private final TextureRegion hpTR; // Текстура - слово "hp"
    private final TextureRegion rofTR; // Teкстура - слово "rof"
    private final TextureRegion pauseTR; // Текстура - значек паузы
    private final Color tmp_color; // Временный объект цвета, сделан для того чтоб каждый цикл отрисовки не создавать новый
    private final int scrW; // Ширина экрана, для упрощения записи
    private final int scrH; // Высота экрана, для упрощения записи
//    private int hp; // Числовое значение жизни
//    private float rof; // Числовое значение скорострельности

    /**
     * Первичная инициализация игрового интерфейса
     */
    public Hud() {
        super();
        scrW = Gdx.graphics.getWidth();
        scrH = Gdx.graphics.getHeight();
//        hp = 0;
//        rof = 1;
        font = CRAssetManager.getInstance().get(Res.FONT);
        font.setColor(Color.BLUE);
        setPosition(OFFSET, scrH - font.getCapHeight());
        borderBarTR = CRAssetManager.getInstance().get(Res.BORDER_BAR);
        barTR = CRAssetManager.getInstance().get(Res.BAR);
        hpTR = CRAssetManager.getInstance().get(Res.HP);
        rofTR = CRAssetManager.getInstance().get(Res.ROF);
        pauseTR = CRAssetManager.getInstance().get(Res.PAUSE_BUTTON);
        tmp_color = new Color();
        setScaleX(scrW * 5.0f / (borderBarTR.getRegionWidth() * 100.f));
        setScaleY(scrH * 80.0f / (borderBarTR.getRegionHeight() * 100.f));
        setColor(CartoonRaider.NORMAL_COLOR);
    }

    /**
     * Отрисовка интерфейса.
     * Вывод счета,
     * Вывод количества жизни и скорострельности в виде вертикальных прогрессбаров, выводится в масштабе рамка
     * индикатора, подпись и затем в цикле заполняется индикатор в зависимости от значений hp и rof
     * Последним выводится значек установки паузы.
     *
     * @param batch       Упаковщик через который будет отрисовываться объект
     * @param parentAlpha Прозрачность объекта
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        int hp = CRAssetManager.getInstance().getPlayer().getHp();
        float rof = CRAssetManager.getInstance().getRof();


        font.drawMultiLine(batch, "Score: " + CRAssetManager.getInstance().getScore(), getX(), getY());

        tmp_color.set(batch.getColor());
        batch.setColor(getColor().r, getColor().g, getColor().b, 0.5f);

        // output HP
        batch.draw(borderBarTR, OFFSET, OFFSET, borderBarTR.getRegionWidth() * getScaleX(), borderBarTR.getRegionHeight() * getScaleY());
        batch.draw(hpTR, OFFSET, OFFSET + borderBarTR.getRegionHeight() * getScaleY() + OFFSET,
                borderBarTR.getRegionWidth() * getScaleX(), hpTR.getRegionHeight() * getScaleX());
        float height = borderBarTR.getRegionHeight() * getScaleY();
        for (int y = 0; y < hp * height / 100.f; y += barTR.getRegionHeight()) {
            batch.draw(barTR, OFFSET, OFFSET + y, barTR.getRegionWidth() * getScaleX(), barTR.getRegionHeight());
        }

        // output Rof
        batch.draw(borderBarTR, scrW - borderBarTR.getRegionWidth() * getScaleX() - OFFSET, OFFSET,
                borderBarTR.getRegionWidth() * getScaleX(), borderBarTR.getRegionHeight() * getScaleY());
        batch.draw(rofTR, scrW - borderBarTR.getRegionWidth() * getScaleX() - OFFSET, OFFSET + borderBarTR.getRegionHeight() * getScaleY() + OFFSET,
                borderBarTR.getRegionWidth() * getScaleX(), rofTR.getRegionHeight() * getScaleX());

        float prcRof = -(rof - Istrebitel.MIN_ROF) * 100 / (Istrebitel.MIN_ROF - Istrebitel.MAX_ROF);

        for (int y = 0; y < prcRof * height / 100.f; y += barTR.getRegionHeight()) {
            batch.draw(barTR, scrW - borderBarTR.getRegionWidth() * getScaleX() - OFFSET,
                    OFFSET + y, barTR.getRegionWidth() * getScaleX(), barTR.getRegionHeight());
        }

        batch.draw(pauseTR, scrW - pauseTR.getRegionWidth() * CartoonRaider.SCALE - OFFSET, scrH - pauseTR.getRegionHeight() * CartoonRaider.SCALE - OFFSET,
                pauseTR.getRegionWidth() * CartoonRaider.SCALE, pauseTR.getRegionHeight() * CartoonRaider.SCALE);

        batch.setColor(tmp_color);
    }

    /**
     * Метод определяющий при обрабоке касаний экрана или щелчка мышью, была ли игра поставлена на паузу
     * @param screenX координата х касания экрана
     * @param screenY координата y касания экрана
     * @return true в случае касания значка паузы иначе false
     */
    public boolean pauseTouched(int screenX, int screenY) {
        return screenX > scrW - pauseTR.getRegionWidth() * CartoonRaider.SCALE - OFFSET && screenY > scrH - pauseTR.getRegionHeight() * CartoonRaider.SCALE - OFFSET;
    }
}
