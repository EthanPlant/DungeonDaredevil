package pjkck.dungeondaredevil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pjkck.dungeondaredevil.GamDungeonDaredevil;

public class ScrMap implements Screen {
    Sprite sprite;

    private GamDungeonDaredevil game;

    private FitViewport port;
    private OrthographicCamera cam;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    public ScrMap(GamDungeonDaredevil game) {
        this.game = game;

        cam = new OrthographicCamera();
        port = new FitViewport(640, 360, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        sprite = new Sprite(new Texture("player.jpg"));
        sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
                Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
    }


    @Override
    public void show() {

    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.position.y += 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.position.y -= 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.position.x -= 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.position.x += 10;
        }
    }

    public void update() {
        handleInput();
        
        cam.update();
    }

    @Override
    public void render(float delta) {
        update();

        renderer.setView(cam);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            sprite.translateX(-5f);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            sprite.translateX(5f);
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            sprite.translateY(5f);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            sprite.translateY(-5f);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        game.getBatch().begin();
        game.getBatch().draw(sprite, sprite.getX(), sprite.getY());
        game.getBatch().end();
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

    }
}
