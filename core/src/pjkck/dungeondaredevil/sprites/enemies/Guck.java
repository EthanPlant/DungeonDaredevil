package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class Guck extends Enemy {

    private float fMoveCooldown;
    private Vector2 vVelocity = Vector2.Zero;
    private float fSpeed = 75;
    private float fAngle;

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

        rectHitbox = new Rectangle(getX() + 5, getY(), 27, 15);
    }

    @Override
    public void move() {
        // Move in a random direction
        vVelocity = Vector2.Zero;
        fAngle = MathUtils.random(0f, 6.28319f);
                vVelocity = new Vector2(fSpeed * MathUtils.cos(fAngle), fSpeed * MathUtils.sin(fAngle));
                setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());

        }

    @Override
    public void update(float delta) {
        move();
        fElapsedTime += delta;
        setRegion(animMovement.getKeyFrame(fElapsedTime, true));
        rectHitbox.setPosition(getX() + 5, getY());
    }
}
