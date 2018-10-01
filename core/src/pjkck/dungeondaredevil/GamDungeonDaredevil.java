package pjkck.dungeondaredevil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pjkck.dungeondaredevil.screens.ScrLoadingscreen;
import pjkck.dungeondaredevil.screens.ScrGame;
import pjkck.dungeondaredevil.screens.ScrMenu;


public class GamDungeonDaredevil extends Game {
	SpriteBatch batch;
	private ScrMenu scrMenu;
	private ScrGame scrGame;
	private ScrLoadingscreen scrLoadingscreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		scrMenu = new ScrMenu(this);
		scrGame = new ScrGame(this);
		scrLoadingscreen = new ScrLoadingscreen(this);
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
				setScreen(scrLoadingscreen);
				break;
			case 1:
				setScreen(scrMenu);
				break;
			case 2:
				setScreen(scrGame);
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
