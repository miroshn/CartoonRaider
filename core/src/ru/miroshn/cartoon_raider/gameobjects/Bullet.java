package ru.miroshn.cartoon_raider.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.miroshn.cartoon_raider.helpers.CRAssetManager;
import ru.miroshn.cartoon_raider.helpers.Conf;
import ru.miroshn.cartoon_raider.helpers.Res;

/**
 * Простой снаряд <br/>
 * Created by miroshn on 15.04.15.
 */
public abstract class Bullet extends GameObject {
    private final int damagePower; // Количество повреждений которые нанесет снаряд
    private Sound shotSound; // Звук выстрела

    /**
     * Конструктор, устанавливает начальную силу снаряда, подготавливает графику и проигрывает звук выстрела.
     */
    protected Bullet() {
        super();
        damagePower = 30;
        setTextureRegion((TextureRegion) CRAssetManager.getInstance().get(Res.BULLET));
        shotSound = CRAssetManager.getInstance().get(Res.SHOT_SOUND);
    }

    @Override
    public void init() {
        super.init();
        setState(GOState.NORMAL);
        playShoot();
        clearActions();
    }

    /**
     * Проигрывает звук выстрела
     */
    protected void playShoot() {
        if (shotSound != null) {
            if (Gdx.app.getPreferences(Conf.OPTIONS_NAME).getBoolean(Conf.SOUND_ENABLE_PREF_KEY, true)) {
                shotSound.play(Gdx.app.getPreferences(Conf.OPTIONS_NAME).getFloat(Conf.SOUND_VOLUME_PREF_KEY, Conf.SOUD_VOLUME));
            }
        }
    }


    /**
     * Получить силу снаряда
     *
     * @return сила снаряда
     */
    public int getDamagePower() {
        return damagePower;
    }

}
