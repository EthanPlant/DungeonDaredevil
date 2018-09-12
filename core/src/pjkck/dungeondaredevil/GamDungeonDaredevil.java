package pjkck.dungeondaredevil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pjkck.dungeondaredevil.screens.ScrMap;
import pjkck.dungeondaredevil.screens.ScrMapScratch;
import pjkck.dungeondaredevil.screens.ScrMenu;

public class GamDungeonDaredevil extends Game {
	SpriteBatch batch;
	Texture img;
	private ScrMapScratch scrMapScratch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		scrMapScratch = new ScrMapScratch(this);
		updateState(0);
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void updateState(int nScreen) {
		switch (nScreen) {
			case 0:
				setScreen(scrMapScratch);
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
