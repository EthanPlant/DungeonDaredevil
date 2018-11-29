package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import pjkck.dungeondaredevil.utils.Gun;

public class SprPlayer extends Sprite {

    private enum STATE {
        STANDING,
        WALKING
    }

    private Animation<TextureRegion>[] arWalkingAnimations;
    private float fElapsedTime;
    private float fAttackCooldown;

    private float fAngle;

    private Vector2 vVelocity;

    private Gun gun;

    private STATE state;

    private Rectangle rectHitbox;

    private int nDir;

    private float fMaxHealth;
    private float fHealth;

    public SprPlayer(float fX, float fY, Texture txSpritesheet) {
        super();

        // Create animation data from spritesheet
        TextureRegion[][] tmp = TextureRegion.split(txSpritesheet, txSpritesheet.getWidth() / 4, txSpritesheet.getHeight() / 4);

        TextureRegion[] forwardFrames = new TextureRegion[4];
        TextureRegion[] backwardFrames = new TextureRegion[4];
        TextureRegion[] rightFrames = new TextureRegion[4];
        TextureRegion[] leftFrames = new TextureRegion[4];

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                backwardFrames[index++] = tmp[i][j];
            }
        }

        index = 0;
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                forwardFrames[index++] = tmp[i][j];
            }
        }

        index = 0;
        for (int i = 2; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                leftFrames[index++] = tmp[i][j];
            }
        }

        index = 0;
        for (int i = 3; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rightFrames[index++] = tmp[i][j];
            }
        }

        arWalkingAnimations = new Animation[4];

        arWalkingAnimations[0] = new Animation<TextureRegion>(1 / 7f, forwardFrames);
        arWalkingAnimations[1] = new Animation<TextureRegion>(1 / 7f, backwardFrames);
        arWalkingAnimations[2] = new Animation<TextureRegion>(1 / 7f, rightFrames);
        arWalkingAnimations[3] = new Animation<TextureRegion>(1 / 7f, leftFrames);

        setRegion(arWalkingAnimations[0].getKeyFrame(0));
        setOriginCenter();
        setBounds(fX, fY, 32, 32);

        vVelocity = Vector2.Zero;

        // Create gun from JSON file
        Json json = new Json();
        gun = json.fromJson(Gun.class, Gdx.files.internal("json/revolver.json"));
        fAttackCooldown = gun.getAttackSpeed();

        state = STATE.STANDING;

        rectHitbox = new Rectangle(getX() + 5, getY(), 27, 27);

        fHealth = fMaxHealth = 100;
    }

    public void setAngle(Vector3 vMousePos) {
        fAngle = MathUtils.atan2((vMousePos.y - getY()), (vMousePos.x - getX()));
    }

    public void move(int nDirection, float fSpeed) {
        nDir = nDirection;
        switch (nDirection) {
            case 0:
                vVelocity = (new Vector2(0, fSpeed));
                break;
            case 1:
                vVelocity = (new Vector2(fSpeed, fSpeed));
                break;
            case 2:
                vVelocity = (new Vector2(fSpeed, 0));
                break;
            case 3:
                vVelocity = (new Vector2(fSpeed, -fSpeed));
                break;
            case 4:
                vVelocity = (new Vector2(0, -fSpeed));
                break;
            case 5:
                vVelocity = (new Vector2(-fSpeed, -fSpeed));
                break;
            case 6:
                vVelocity = (new Vector2(-fSpeed, 0));
                break;
            case 7:
                vVelocity = (new Vector2(-fSpeed, fSpeed));
                break;
        }
    }

    public void shoot(Vector3 vMousePos, Array<SprBullet> bullets) {
        if (fAttackCooldown >= 1 / gun.getAttackSpeed()) {
            SprBullet b = new SprBullet(getX(), getY(), new Texture("textures/bullet.png"), gun.getBulletSpeed(), 8, 8, true, this.getClass());
            b.setTargetPos(vMousePos.x, vMousePos.y, gun.getSpray());
            bullets.add(b);
            fAttackCooldown = 0;
        }
    }

    private void setImageBasedOnRotation(float fAngle) {
        if (fAngle <= 2 && fAngle >= 1) {
            setRegion(arWalkingAnimations[1].getKeyFrame(fElapsedTime, true));
        } else if (fAngle <= 1 && fAngle >= -1) {
            setRegion(arWalkingAnimations[2].getKeyFrame(fElapsedTime, true));
        } else if (fAngle <= -1 && fAngle >= -2) {
            setRegion(arWalkingAnimations[0].getKeyFrame(fElapsedTime, true));
        } else {
            setRegion(arWalkingAnimations[3].getKeyFrame(fElapsedTime, true));
        }
    }

    public void setVelocity(Vector2 value) {
        vVelocity = value;
    }

    public void setVelocity(float x, float y) {
        setVelocity(new Vector2(x, y));
    }

    public void setHealth(float value) {
        fHealth += value;
    }

    public Rectangle getHitbox() {
        return rectHitbox;
    }

    public float getMaxHealth() {
        return fMaxHealth;
    }

    public float getHealth() {
        return fHealth;
    }

    public int getDir() {
        return nDir;
    }

    public STATE getState() {
        return state;
    }

    public Gun getGun() {
        return gun;
    }

    public void update(float delta) {
        fElapsedTime += delta;
        fAttackCooldown += delta;

        if (vVelocity != Vector2.Zero) {
            state = STATE.WALKING;
        } else {
            fElapsedTime = 0;
            state = STATE.STANDING;
        }

        setImageBasedOnRotation(fAngle);

        setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());
        rectHitbox.setPosition(getX() + 5, getY());
    }
}
