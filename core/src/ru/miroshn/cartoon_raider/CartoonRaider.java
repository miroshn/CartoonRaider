package ru.miroshn.cartoon_raider;

import com.badlogic.gdx.Game;
import ru.miroshn.cartoon_raider.screens.WelcomeScreen;

public class CartoonRaider extends Game {
//    private Vector3 tapPos;

    @Override
    public void create() {
//        tapPos = new Vector3(0, 0, 0);
        setScreen(new WelcomeScreen(this));
    }

//    @Override
//    public void setScreen(Screen screen) {
//        if (this.getScreen() != null)
//            this.getScreen().dispose();
//        super.setScreen(screen);
//    }

    /*
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//        camera.zoom -= 0.01f;



        if (Gdx.input.isTouched()) {
            tapPos.set(Gdx.input.getX() - istrebitelTexture.getWidth() / 2, Gdx.input.getY() - 20, 0);
            camera.unproject(tapPos);
            istrebitelPos.x = istrebitelPos.x >= tapPos.x ? istrebitelPos.x - 3 : istrebitelPos.x + 3;
            istrebitelPos.y = istrebitelPos.y >= tapPos.y ? istrebitelPos.y - 3 : istrebitelPos.y + 3;
            if (Math.abs(istrebitelPos.x - tapPos.x) <= 3) istrebitelPos.x = tapPos.x;
            if (Math.abs(istrebitelPos.y - tapPos.y) <= 3) istrebitelPos.y = tapPos.y;
        }

        if (istrebitelPos.y - 20 < rightDownCorner.y) istrebitelPos.y = rightDownCorner.y + 20;
        batch.draw(istrebitelTexture, istrebitelPos.x, istrebitelPos.y);
        batch.end();
//        DrawDebugGraphics();
    }

    private void DrawDebugGraphics() {
        debugShape.setProjectionMatrix(camera.combined);

        Vector3 tmp = viewport.unproject(new Vector3(100, 200, 0));
        Vector3 tmp1 = camera.unproject(new Vector3(110, 210, 0));

        debugShape.setColor(Color.RED);
        debugShape.x(tmp.x, tmp.y, 10);
        debugShape.setColor(Color.GREEN);
        debugShape.x(tmp1.x, tmp1.y, 10);
        debugShape.end();


        //camera.position
        //Gdx.app.log("DEBUG",camera.toString());
    }
*/

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
//        super.dispose();
    }
}
