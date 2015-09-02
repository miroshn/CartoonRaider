package ru.miroshn.cartoon_raider.helpers;

/**
 * Created by miroshn on 20.05.15.
 * Класс настроек
 */
public class Conf {
    public static final int DESKTOP_WIDTH = 320 * 2;
    public static final int DESKTOP_HEIGHT = 480 * 2;
    //    public static final int DESKTOP_HEIGHT = 850;
    public static final boolean DEBUG = false;
    public static final boolean IDDQD = false;
    public static final int STAR_DROP_PRC = 50;
    // Boss1
    public static final float BOSS_MOVE_TIME = 10;
    public static final float BOSS1_BULLET_FIRE_TIME = 1f;
    public static final int NUM_STARS_AFTER_KILL_BOSS = 10;
    public static final float STARS_MOVE_RANGE = 100;
    public static final int BOSS1_BATTLE_BEGIN_SCORE = 100;
    public static final int TIME_TO_BATTLE = 100;
    //boss2
    public static final int BOSS2_BATTLE_BEGIN_SCORE = 150;

    public static final float SOUD_VOLUME = 0.5f;
    public static final int PLAYER_LEVEL = 1;
    public static final float ROCKET_LIFE_TIME = 5.0f;

    // Toast
    public static final float TOAST_FADE_TIME = 0.5f;
    public static final float TOAST_SHOW_TIME = 1.0f;
    public static final float TOAST_WIDTH = 2.f / 3.f; // in scale factor
    public static final float TOAST_TOP_PAD = 0.1f; // in scale factor

    // config
    public static final String OPTIONS_NAME = "Options";
    public static final String SOUND_ENABLE_PREF_KEY = "soundEnabled";
    public static final String SOUND_VOLUME_PREF_KEY = "soundValue";

    // Strings
    public static final String SOUND_VOLUME_TEXT = "Громкость";
    public static final String SOUND_ENABLE_TEXT = "Разрешить звук";
    public static final String PLAY_TEXT = "Играть";
    public static final String EXIT_TEXT = "Уйти";
    public static final String ABOUT_TEXT = "Инфо";
    public static final String SETTINGS_TEXT = "Настройки";
    public static final String OK_STRING = "Ок";
    public static final String SCORE_TEXT = "Счет";
    public static final String BOSS1_BEGIN_TEXT = "Враг приближается! ! !";
    public static final String WEAPON_UPGRADED = "Оружие улучшено! ! !";

}
