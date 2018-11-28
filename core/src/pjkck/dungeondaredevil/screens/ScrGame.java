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

import pjkck.dungeondaredevil.GamDungeonDaredevil;
import pjkck.dungeondaredevil.sprites.SprBullet;
import pjkck.dungeondaredevil.sprites.SprPlayer;
import pjkck.dungeondaredevil.sprites.enemies.SprEnemy;
import pjkck.dungeondaredevil.sprites.enemies.SprGuck;
import pjkck.dungeondaredevil.ui.HealthBar;
import pjkck.dungeondaredevil.utils.CollisionHandler;
import pjkck.dungeondaredevil.utils.InputManager;

public class ScrGame implements Screen {
    private SprPlayer player;

    private Array<SprGuck> arEnemies;

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

    private InputManager inputManager;

    private Array<SprBullet> arBullets;

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

        player = new SprPlayer(port.getWorldWidth() / 2, port.getWorldHeight() / 2);
        arEnemies = new Array<SprGuck>();

        arBullets = new Array<SprBullet>();

        // Spawn enemies
        for (int i = 0; i < 10; i++) {
            arEnemies.add(new SprGuck(MathUtils.random(64, 704), MathUtils.random(64, 672), arBullets));
        }

        // Set custom cursor
        Pixmap pm = new Pixmap(Gdx.files.internal("cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 16, 16));
        pm.dispose();

        stage = new Stage(new FitViewport(port.getWorldWidth(), port.getWorldHeight()));
        healthBar = new HealthBar(250, 20, player.getMaxHealth());
        healthBar.setPosition(10, stage.getViewport().getWorldHeight() - 30);
        stage.addActor(healthBar);

        inputManager = new InputManager();
        Gdx.input.setInputProcessor(inputManager);
    }


    @Override
    public void show() {
    }

    public void handleInput() {
        player.setVelocity(Vector2.Zero);
        // Get location of mouse cursor
        Vector3 vMousePos = new Vector3(inputManager.getMouseCoordinates(), 0);
        cam.unproject(vMousePos);
        player.setAngle(vMousePos);

        if (inputManager.isKeyDown(Input.Keys.W) && inputManager.isKeyDown(Input.Keys.A)) {
            player.move(7, 300);
        } else if (inputManager.isKeyDown(Input.Keys.W) && inputManager.isKeyDown(Input.Keys.D)) {
            player.move(1, 300);
        } else if (inputManager.isKeyDown(Input.Keys.S) && inputManager.isKeyDown(Input.Keys.A)) {
            player.move(5, 300);
        } else if (inputManager.isKeyDown(Input.Keys.S) && inputManager.isKeyDown(Input.Keys.D)) {
            player.move(3, 300);
        } else if (inputManager.isKeyDown(Input.Keys.W)) {
            player.move(0, 300);
        } else if (inputManager.isKeyDown(Input.Keys.S)) {
            player.move(4, 300);
        } else if (inputManager.isKeyDown(Input.Keys.A)) {
            player.move(6, 300);
        } else if (inputManager.isKeyDown(Input.Keys.D)) {
            player.move(2, 300);
        }
        if (inputManager.isKeyPressed(Input.Keys.SPACE)) {
            player.move(player.getDir(), 3000);
        }
        if (inputManager.isMouseDown()) {
            player.shoot(vMousePos, arBullets);
        }

        inputManager.update();
    }

    public void update() {
        handleInput();

        float fStartX = player.getX();
        float fStartY = player.getY();

        player.update(Gdx.graphics.getDeltaTime());

        for (SprBullet b : arBullets) {
            b.update(Gdx.graphics.getDeltaTime());

            if (collisionHandler.findDistance(new Vector2(b.getX(), b.getY()), new Vector2(player.getX(), player.getY())) >= player.getGun().getRange()) {
                arBullets.removeValue(b, true);
            }

            if (collisionHandler.isCollidingWithMap(b.getBoundingRectangle(), 2)) {
                arBullets.removeValue(b, true);
            }

            if (collisionHandler.isSpriteColliding(b.getBoundingRectangle(), player.getHitbox()) && b.getOrigin() != SprPlayer.class) {
                arBullets.removeValue(b, true);
                player.setHealth(-10);
            }

            for (SprEnemy e : arEnemies) {
                if (b.getVelocity() == Vector2.Zero && b.getOrigin() != SprPlayer.class) {
                    b.setTargetPos(player.getX(), player.getY(), e.getGun().getSpray());
                }

                if (collisionHandler.isSpriteColliding(b.getBoundingRectangle(), e.getHitbox()) && b.getOrigin() == SprPlayer.class) {
                    arBullets.removeValue(b, true);
                    e.setHealth(-10);
                }
            }
        }

        // Update enemy location and check for collisions
        for (SprEnemy e : arEnemies) {
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

            if (collisionHandler.isSpriteColliding(player.getHitbox(), e.getHitbox())) {
                e.setPosition(fEStartX, fEStartY);

                player.setPosition(fStartX, fStartY);
            }

            if (e.getHealth() <= 0) {
                arEnemies.removeValue((SprGuck) e, true);
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

       //Gdx.app.log("Delta Time", Float.toString(Gdx.graphics.getDeltaTime()));
//        Gdx.app.log("DT", Float.toString(Gdx.graphics.getDeltaTime()));

        renderer.setView(cam);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(); // Draw the map

        // Draw the sprites
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (SprBullet b : arBullets) {
            b.draw(batch);
        }
        player.draw(batch);
        for (SprEnemy e : arEnemies) {
            e.draw(batch);
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
        arBullets.clear();
        map.dispose();
    }
}
