package pjkck.dungeondaredevil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pjkck.dungeondaredevil.GamDungeonDaredevil;

public class ScrLoadingscreen implements Screen {

    private Texture img;
    private GamDungeonDaredevil game;
    private FitViewport port;
    private OrthographicCamera cam;

    Sound startupSound;
 
    public ScrLoadingscreen(GamDungeonDaredevil game) {
        cam = new OrthographicCamera();
        port = new FitViewport(1920, 1080, cam);
        cam.position.set(port.getWorldWidth()/2, port.getWorldHeight()/2, 0);
        this.game = game;
        img = new Texture("LoadingScreen.png");

        startupSound = Gdx.audio.newSound(Gdx.files.internal("startup.mp3"));
     //   startupSound.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        cam.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        game.getBatch().draw(img, 0, 0);
        game.getBatch().end();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.updateState(0);
        }
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
