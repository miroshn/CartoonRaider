package ru.miroshn.cartoon_raider;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.miroshn.cartoon_raider.screens.WelcomeScreen;

public class CartoonRaider extends Game {
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture istrebitelTexture;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Vector3 leftUpCorner,rightDownCorner;
    private Vector3 istrebitelPos;

    private ShapeRenderer debugShape;

    private Vector3 tapPos;

    @Override
    public void create() {

        debugShape = new ShapeRenderer();
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.jpg");
        istrebitelTexture = new Texture("istrebitel1.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        camera.translate(Gdx.graphics.getWidth() / 2 - 20,Gdx.graphics.getHeight() / 2 -20 );
        leftUpCorner = new Vector3(0,0,0);
        rightDownCorner = new Vector3(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),0);

//        camera.translate(100f, 0f);
//        camera.update();

//        Gdx.input.setInputProcessor(this);
        istrebitelPos = new Vector3(-istrebitelTexture.getWidth() / 2, 0, 0);
        tapPos = new Vector3(0, 0, 0);

        setScreen(new WelcomeScreen(this));
    }

/*
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//        camera.zoom -= 0.01f;

        leftUpCorner.x = leftUpCorner.y = 0;
        rightDownCorner.x = Gdx.graphics.getWidth();
        rightDownCorner.y = Gdx.graphics.getHeight();

        camera.unproject(leftUpCorner);
        camera.unproject(rightDownCorner);

        batch.setProjectionMatrix(camera.combined);
        camera.translate(0f, 1f);
        camera.update();

        batch.begin();

        int x = leftUpCorner.x < 0 ?
                (int) (leftUpCorner.x / backgroundTexture.getWidth() - 1) * backgroundTexture.getWidth() :
                (int) (leftUpCorner.x / backgroundTexture.getWidth()) * backgroundTexture.getWidth();
        for (; x < rightDownCorner.x; x += backgroundTexture.getWidth()) {
            int y = rightDownCorner.y < 0 ?
                    (int) (rightDownCorner.y / backgroundTexture.getHeight() - 1) * backgroundTexture.getHeight() :
                    (int) (rightDownCorner.y / backgroundTexture.getHeight()) * backgroundTexture.getHeight();
            for (; y < leftUpCorner.y; y += backgroundTexture.getHeight()) {
                batch.draw(backgroundTexture, x, y); // рисовать в мировых координатах!!!
            }
        }

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
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        batch.dispose();
        debugShape.dispose();
        istrebitelTexture.dispose();
    }
}
