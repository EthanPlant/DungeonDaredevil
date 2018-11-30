package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import pjkck.dungeondaredevil.sprites.SprBullet;
import pjkck.dungeondaredevil.utils.Gun;

public class SprGuck extends SprEnemy {

    private float fMoveCooldown;
    private float fMoveTime;
    private Vector2 vVelocity = Vector2.Zero;
    private float fSpeed = 75;
    private float fAngle;
    private Animation<TextureRegion>[] animation;

    public SprGuck(float fX, float fY, Array<SprBullet> arBullets) {
        super(fX, fY, arBullets);

        gun = new Json().fromJson(Gun.class, Gdx.files.internal("json/guck.json"));
        fAttackCooldown = gun.getAttackSpeed();

        Texture txSpriteSheet = new Texture("spritesheets/guck.png");
        TextureRegion[][] tmp = TextureRegion.split(txSpriteSheet, txSpriteSheet.getWidth() / 6, txSpriteSheet.getHeight());

        TextureRegion[] arGreenframes = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            arGreenframes[i] = tmp[0][i];
        }

        TextureRegion[] arYellowframes = new TextureRegion[2];
        arYellowframes[0] = tmp[0][4];
        arYellowframes[1] = tmp[0][5];

        TextureRegion[] arRedframes = new TextureRegion[2];
        arRedframes[0] = tmp[0][2];
        arRedframes[1] = tmp[0][3];

        animation = new Animation[3];

        animation[0] = new Animation<TextureRegion>(1 / 5f, arGreenframes);
        animation[1] = new Animation<TextureRegion>(1 / 5f, arYellowframes);
        animation[2] = new Animation<TextureRegion>(1 / 5f, arRedframes);

        setRegion(animation[0].getKeyFrame(0));

        rectHitbox = new Rectangle(getX() + 5, getY(), 27, 15);

        setHealth(30);
        fMaxhealth = 30;

        fMoveCooldown = MathUtils.random();
        fMoveTime = fMoveCooldown;
        isPlayerInRange = false;

        fRange = 100;
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
        if (isPlayerInRange) {
            SprBullet b = new SprBullet(getX(), getY(), new Texture("textures/guckbullet.png"), gun.getBulletSpeed(), 16, 16, false, this.getClass());
            getBullets().add(b);
        }
    }

    @Override
    public void update(float delta) {
        move();
        fElapsedTime += delta;
        fMoveTime += delta;
        if (isPlayerInRange) fAttackCooldown += delta;
        if (fAttackCooldown >= 1 / gun.getAttackSpeed()) {
            shoot();
            fAttackCooldown = 0;
        }
        if (getHealth() <= fMaxhealth / 3) {
            setRegion(animation[2].getKeyFrame(fElapsedTime, true));
        } else if (getHealth() <= fMaxhealth / 3 * 2) {
            setRegion(animation[1].getKeyFrame(fElapsedTime, true));
        } else {
            setRegion(animation[0].getKeyFrame(fElapsedTime, true));
        }
        rectHitbox.setPosition(getX() + 5, getY());
    }
}
