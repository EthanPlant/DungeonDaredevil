package pjkck.dungeondaredevil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pjkck.dungeondaredevil.GamDungeonDaredevil;
import pjkck.dungeondaredevil.sprites.Player;

public class ScrMap implements Screen {
    Player player;

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
        player = new Player(port.getWorldWidth() / 2, port.getWorldHeight() / 2);
    }


    @Override
    public void show() {

    }

    public void handleInput() {
        player.setState(Player.STATE.STANDING);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setY(player.getY() + 300 * Gdx.graphics.getDeltaTime());
            player.setDirection(Player.DIRECTION.BACKWARD);
            player.setState(Player.STATE.WALKING);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setY(player.getY() - 300 * Gdx.graphics.getDeltaTime());
            player.setDirection(Player.DIRECTION.FORWARD);
            player.setState(Player.STATE.WALKING);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setX(player.getX() - 300 * Gdx.graphics.getDeltaTime());
            player.setDirection(Player.DIRECTION.LEFT);
            player.setState(Player.STATE.WALKING);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setX(player.getX() + 300 * Gdx.graphics.getDeltaTime());
            player.setDirection(Player.DIRECTION.RIGHT);
            player.setState(Player.STATE.WALKING);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.setY(player.getY() + 10000 * Gdx.graphics.getDeltaTime());
            player.setState(Player.STATE.DASHING);
        }
    }

    public void update() {
        handleInput();

        player.update(Gdx.graphics.getDeltaTime());

        cam.position.set(player.getX(), player.getY(), 0);

        cam.update();
    }

    @Override
    public void render(float delta) {
        update();

        renderer.setView(cam);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        game.getBatch().draw(player, player.getX(), player.getY());
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
