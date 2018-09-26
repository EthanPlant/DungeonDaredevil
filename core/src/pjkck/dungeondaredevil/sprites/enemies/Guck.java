package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Guck extends Enemy {

    public Guck(float fX, float fY) {
        super(fX, fY);

        Texture txSpriteSheet = new Texture("guck.png");
        TextureRegion[][] tmp = TextureRegion.split(txSpriteSheet, txSpriteSheet.getWidth() / 2, txSpriteSheet.getHeight());

        TextureRegion[] arFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            arFrames[i] = tmp[0][i];
        }

        animMovement = new Animation<TextureRegion>(1/5f, arFrames);

        setRegion(animMovement.getKeyFrame(0));
    }

    @Override
    public void update(float delta) {
        fElapsedTime += delta;
        setRegion(animMovement.getKeyFrame(fElapsedTime, true));
    }
}
