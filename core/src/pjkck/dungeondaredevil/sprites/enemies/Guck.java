package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Guck extends Enemy {

    private float fMoveCooldown;
    private Vector2 vVelocity;

    public Guck(float fX, float fY) {
        super(fX, fY);

        Texture txSpriteSheet = new Texture("spritesheets/guck.png");
        TextureRegion[][] tmp = TextureRegion.split(txSpriteSheet, txSpriteSheet.getWidth() / 2, txSpriteSheet.getHeight());

        TextureRegion[] arFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            arFrames[i] = tmp[0][i];
        }

        animMovement = new Animation<TextureRegion>(1/5f, arFrames);

        setRegion(animMovement.getKeyFrame(0));
    }

    @Override
    public void move() {
        vVelocity = Vector2.Zero;
        fMoveCooldown += Gdx.graphics.getDeltaTime();
        if (fMoveCooldown >= 1) {
            fMoveCooldown = 0;
            int nDir = new Random().nextInt(5);
            if (nDir == 0) {
                setX(getX() - 100 * Gdx.graphics.getDeltaTime());
            }
            if (nDir == 1) {
                setY(getY() - 100 * Gdx.graphics.getDeltaTime());
            }
            if (nDir == 2) {
                setX(getX() + 100 * Gdx.graphics.getDeltaTime());
            }
            if (nDir == 3) {
                setY(getY() + 100 * Gdx.graphics.getDeltaTime());
            }

            if (nDir == 4) {
                setX(getX());
                setY(getY());
            }
        }
    }

    @Override
    public void update(float delta) {
        fMoveCooldown += delta;
        move();
        fElapsedTime += delta;
        setRegion(animMovement.getKeyFrame(fElapsedTime, true));
    }
}
