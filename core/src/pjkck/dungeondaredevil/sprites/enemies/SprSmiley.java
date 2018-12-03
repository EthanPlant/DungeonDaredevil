package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import pjkck.dungeondaredevil.sprites.SprBullet;

public class SprSmiley extends SprEnemy  {

    float fMoveCooldown;
    float fAngle;
    float fMoveTime;
    float fSpeed = 100;
    Vector2 vVelocity = Vector2.Zero;

    public SprSmiley(float fX, float fY, Array<SprBullet> arBullets) {
        super(fX, fY, arBullets);

        Texture txSpriteSheet = new Texture("spritesheets/idk.png");
        TextureRegion[][] tmp = TextureRegion.split(txSpriteSheet, txSpriteSheet.getWidth() / 4, txSpriteSheet.getHeight());

        TextureRegion[] arWalkingframes = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            arWalkingframes[i] = tmp[0][i];
        }

        animMovement = new Animation<TextureRegion>(1 / MathUtils.random(10f), arWalkingframes);

        setRegion(animMovement.getKeyFrame(0));

        rectHitbox = new Rectangle(getX() + 5, getY(), 27, 15);

        setHealth(30);
        fMaxhealth = 30;

        fMoveCooldown = MathUtils.random();
        fMoveTime = fMoveCooldown;
    }

    @Override
    public void move() {
        if (fMoveTime >= fMoveCooldown) {
            // Move in a random direction
            fAngle = MathUtils.random(0f, 6.28319f);
            vVelocity = new Vector2(fSpeed * MathUtils.cos(fAngle), fSpeed * MathUtils.sin(fAngle));
            fMoveTime = 0;
            fMoveCooldown = MathUtils.random();
        }
        setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void shoot() {

    }

    @Override
    public void update(float delta) {
        move();
        fMoveTime += delta;
        fElapsedTime += delta;

        setRegion(animMovement.getKeyFrame(fElapsedTime, true));

        rectHitbox.setPosition(getX() + 5, getY());
    }
}
