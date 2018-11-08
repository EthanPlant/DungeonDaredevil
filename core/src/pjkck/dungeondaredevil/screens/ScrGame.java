package pjkck.dungeondaredevil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pjkck.dungeondaredevil.GamDungeonDaredevil;
import pjkck.dungeondaredevil.sprites.Bullet;
import pjkck.dungeondaredevil.sprites.Player;
import pjkck.dungeondaredevil.sprites.enemies.Enemy;
import pjkck.dungeondaredevil.sprites.enemies.Guck;
import pjkck.dungeondaredevil.ui.HealthBar;
import pjkck.dungeondaredevil.utils.CollisionHandler;

public class ScrGame implements Screen {
    private Player player;

    private Array<Guck> arEnemies;

    private GamDungeonDaredevil game;

    private FitViewport port;
    private OrthographicCamera cam;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private CollisionHandler collisionHandler;

    private SpriteBatch batch;

    private Stage stage;
    private HealthBar healthBar;

    public ScrGame(GamDungeonDaredevil game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        // Set up camera
        cam = new OrthographicCamera();
        port = new FitViewport(640, 360, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        // Load the map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        collisionHandler = new CollisionHandler(map);

        player = new Player(port.getWorldWidth() / 2, port.getWorldHeight() / 2);
        arEnemies = new Array<Guck>();

        // Spawn enemies
        for (int i = 0; i < 10; i++) {
            arEnemies.add(new Guck(MathUtils.random(64, 704), MathUtils.random(64, 672)));
        }

        // Set custom cursor
        Pixmap pm = new Pixmap(Gdx.files.internal("cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 16, 16));
        pm.dispose();

        stage = new Stage(new FitViewport(port.getWorldWidth(), port.getWorldHeight()
        ));
        healthBar = new HealthBar(250, 20, player.getMaxHealth());
        healthBar.setPosition(10, stage.getViewport().getWorldHeight() - 30);
        stage.addActor(healthBar);
    }


    @Override
    public void show() {
    }

    public void handleInput() {
        player.setVelocity(Vector2.Zero);
        // Get location of mouse cursor
        Vector3 vMousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(vMousePos);
        player.setAngle(vMousePos);


        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(4, 200);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(5, 200);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(6, 200);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(7, 200);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(0, 300);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.move(1, 300);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(2, 300);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(3, 300);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.move(player.getDir(), 3000);
        }
        if (Gdx.input.isTouched()) {
            player.shoot(vMousePos);
        }
    }

    public void update() {
        handleInput();

        float fStartX = player.getX();
        float fStartY = player.getY();

        player.update(Gdx.graphics.getDeltaTime());

        for (Bullet b : player.getBullets()) {
            if (collisionHandler.findDistance(new Vector2(b.getX(), b.getY()), new Vector2(player.getX(), player.getY())) >= player.getGun().getRange()) {
                player.getBullets().removeValue(b, true);
            }
        }

        // Update enemy location and check for collisions
        for (Enemy e : arEnemies) {
            float fEStartX = e.getX();
            float fEStartY = e.getY();

            e.update(Gdx.graphics.getDeltaTime());
            if (collisionHandler.isCollidingWithMap(e.getHitbox(), 2)) {
                e.setPosition(fEStartX, fEStartY);
            }

            if (collisionHandler.findDistance(new Vector2(player.getX(), player.getY()), new Vector2(e.getX(), e.getY())) <= e.getRange()) {
                e.setPlayerInRange(true);
                e.setTargetPos(player.getX(), player.getY());
            } else {
                e.setPlayerInRange(false);
            }

            for (Bullet b : player.getBullets()) {
                if (collisionHandler.isCollidingWithMap(b.getBoundingRectangle(), 2)) {
                    player.getBullets().removeValue(b, true);
                }

                if (collisionHandler.isSpriteColliding(b.getBoundingRectangle(), e.getHitbox())) {
                    player.getBullets().removeValue(b, true);
                    e.setHealth(-10);
                }
            }

            for (Bullet b : e.getBullets()) {
                if (b.getVelocity() == Vector2.Zero) {
                    b.setTargetPos(player.getX(), player.getY(), e.getGun().getSpray());
                }

                if (collisionHandler.isCollidingWithMap(b.getBoundingRectangle(), 2)) {
                    e.getBullets().removeValue(b, true);
                }

                if (collisionHandler.isSpriteColliding(b.getBoundingRectangle(), player.getHitbox())) {
                    e.getBullets().removeValue(b, true);
                    player.setHealth(-10);
                }
            }

            if (collisionHandler.isSpriteColliding(player.getHitbox(), e.getHitbox())) {
                e.setPosition(fEStartX, fEStartY);

                player.setPosition(fStartX, fStartY);
            }

            if (e.getHealth() <= 0) {
                arEnemies.removeValue((Guck) e, true);
            }
        }

        if (collisionHandler.isCollidingWithMap(player.getHitbox(), 2)) {
            player.setPosition(fStartX, fStartY);
        }

        healthBar.setValue(player.getHealth());

        if (player.getHealth() <= 0) {
            game.updateState(1);
        }

        cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0); // Set camera location to player's

        cam.update();
    }

    @Override
    public void render(float delta) {
        update();

        Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
//        Gdx.app.log("DT", Float.toString(Gdx.graphics.getDeltaTime()));

        renderer.setView(cam);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(); // Draw the map

        // Draw the sprites
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Bullet b : player.getBullets()) {
            b.draw(batch);
        }
        player.draw(batch);
        for (Enemy e : arEnemies) {
            e.draw(batch);
            for (Bullet b : e.getBullets()) {
                b.draw(batch);
            }
        }
        batch.end();
        stage.getBatch().setProjectionMatrix(cam.combined);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
        stage.getViewport().update(width, height, true);
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
        arEnemies.clear();
        player.getBullets().clear();
        map.dispose();
    }
}
