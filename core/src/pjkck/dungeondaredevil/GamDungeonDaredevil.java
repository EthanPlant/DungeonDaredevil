package pjkck.dungeondaredevil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pjkck.dungeondaredevil.screens.ScrMap;
import pjkck.dungeondaredevil.screens.ScrMenu;

import static java.awt.SystemColor.menu;

public class GamDungeonDaredevil extends Game {
	SpriteBatch batch;
	Texture img;
	private ScrMenu scrMenu;
	private ScrMap scrMap;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		scrMenu = new ScrMenu(this);
		scrMap = new ScrMap(this);
		setScreen(scrMenu);
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
