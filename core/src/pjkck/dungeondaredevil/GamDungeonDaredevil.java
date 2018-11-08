package pjkck.dungeondaredevil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pjkck.dungeondaredevil.screens.ScrLoadingscreen;
import pjkck.dungeondaredevil.screens.ScrGame;
import pjkck.dungeondaredevil.screens.ScrMenu;


public class GamDungeonDaredevil extends Game {
	SpriteBatch batch;
	private ScrMenu scrMenu;
	private ScrLoadingscreen scrLoadingscreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		scrMenu = new ScrMenu(this, batch);
		scrLoadingscreen = new ScrLoadingscreen(this, batch);
		updateState(0);
	}

	@Override
	public void render () {
		super.render();
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
				setScreen(new ScrGame(this, batch));
				break;
		}
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
