package pjkck.dungeondaredevil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pjkck.dungeondaredevil.GamDungeonDaredevil;
import pjkck.dungeondaredevil.sprites.Player;
import pjkck.dungeondaredevil.sprites.enemies.Enemy;
import pjkck.dungeondaredevil.sprites.enemies.Guck;
import pjkck.dungeondaredevil.utils.SpriteColisionHandler;
import pjkck.dungeondaredevil.utils.TiledMapCollisionHandler;
import java.util.Random;

public class ScrMap implements Screen {
    Player player;

    private Array<Guck> arEnemies;

    private GamDungeonDaredevil game;

    private FitViewport port;
    private OrthographicCamera cam;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapCollisionHandler collisionHandler;

    private SpriteColisionHandler spriteColisionHandler;

    public ScrMap(GamDungeonDaredevil game) {
        this.game = game;

        cam = new OrthographicCamera();
        port = new FitViewport(640, 360, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        collisionHandler = new TiledMapCollisionHandler(map);

        player = new Player(port.getWorldWidth() / 2, port.getWorldHeight() / 2);
        arEnemies = new Array<Guck>();

        spriteColisionHandler = new SpriteColisionHandler();

    }


    @Override
    public void show() {
        for (int i = 0; i < 25; i++) {
            arEnemies.add(new Guck(MathUtils.random(64, 704), MathUtils.random(64, 672)));
        }
    }

    public void handleInput() {
        player.setState(Player.STATE.STANDING);
        float fStartX = player.getX();
        float fStartY = player.getY();
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
            player.dash();
        }

        if (collisionHandler.isColliding(player, 2)) {
            player.setPosition(fStartX, fStartY);
        }

        for (Enemy e : arEnemies) {
            if (spriteColisionHandler.isColliding(player, e)) {
                player.setPosition(fStartX, fStartY);
            }
        }
    }

    public void update() {
        handleInput();

        for (Enemy e : arEnemies) {
            float fStartX = e.getX();
            float fStartY = e.getY();
            if(new Random().nextInt(4) == 0){
                e.setX(e.getX() - 100 * Gdx.graphics.getDeltaTime());
            }
            if(new Random().nextInt(4) == 1){
                e.setY(e.getY() - 100 * Gdx.graphics.getDeltaTime());
            }
            if(new Random().nextInt(4) == 2){
                e.setX(e.getX() + 100 * Gdx.graphics.getDeltaTime());
            }
            if(new Random().nextInt(4) == 3){
                e.setY(e.getY() + 100 * Gdx.graphics.getDeltaTime());
            }
            if (collisionHandler.isColliding(e, 2)) {
                e.setPosition(fStartX, fStartY);
            }

            if (spriteColisionHandler.isColliding(player, e)) {
                e.setPosition(fStartX, fStartY);
            }

            e.update(Gdx.graphics.getDeltaTime());
        }

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
        player.draw(game.getBatch());
        for (Enemy e : arEnemies) {
            e.draw(game.getBatch());
        }
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
