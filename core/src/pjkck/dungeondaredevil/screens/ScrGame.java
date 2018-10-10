package pjkck.dungeondaredevil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pjkck.dungeondaredevil.GamDungeonDaredevil;
import pjkck.dungeondaredevil.sprites.Player;
import pjkck.dungeondaredevil.utils.SpriteColisionHandler;
import pjkck.dungeondaredevil.utils.TiledMapCollisionHandler;
import java.util.Random;

public class ScrGame implements Screen {
    private Player player;

    private GamDungeonDaredevil game;

    private FitViewport port;
    private OrthographicCamera cam;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapCollisionHandler collisionHandler;

    private SpriteColisionHandler spriteColisionHandler;

    private SpriteBatch batch;

    public ScrGame(GamDungeonDaredevil game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        cam = new OrthographicCamera();
        port = new FitViewport(640, 360, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        collisionHandler = new TiledMapCollisionHandler(map);

        player = new Player(port.getWorldWidth() / 2, port.getWorldHeight() / 2);
        spriteColisionHandler = new SpriteColisionHandler();

    }


    @Override
    public void show() {
    }

    public void handleInput() {
        player.setDeltaX(0);
        player.setDeltaY(0);
        Vector3 vMousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(vMousePos);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.walk(vMousePos, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.walk(vMousePos, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.walk(vMousePos, 2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.walk(vMousePos, 3);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.dash();
        }
    }

    public void update() {
        handleInput();

        float fStartX = player.getX();
        float fStartY = player.getY();

        player.update(Gdx.graphics.getDeltaTime());

        if (collisionHandler.isColliding(player, 2)) {
            if (player.getState() == Player.STATE.DASHING) {
                switch (player.getDirection()) {
                    case FORWARD:
                        while (collisionHandler.isColliding(player, 2)) {
                            player.setY(player.getY() + 1);
                        }
                        break;
                    case BACKWARD:
                        while (collisionHandler.isColliding(player, 2)) {
                            player.setY(player.getY() - 1);
                        }
                        break;
                    case LEFT:
                        while (collisionHandler.isColliding(player, 2)) {
                            player.setX(player.getX() + 1);
                        }
                        break;
                    case RIGHT:
                        while (collisionHandler.isColliding(player, 2)) {
                            player.setX(player.getX() - 1);
                        }
                        break;
                }
            } else {
                player.setPosition(fStartX, fStartY);
            }
        }

        cam.position.set(player.getX(), player.getY(), 0);

        cam.update();
    }

    @Override
    public void render(float delta) {
        update();

//        Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
//        Gdx.app.log("DT", Float.toString(Gdx.graphics.getDeltaTime()));

        renderer.setView(cam);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
