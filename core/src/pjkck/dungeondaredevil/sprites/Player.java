package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Player extends Sprite {

    private enum STATE {
        STANDING,
        WALKING
    }

    private Animation<TextureRegion>[] arWalkingAnimations;
    private float fElapsedTime;
    private float fAttackCooldown;

    private float fAngle;

    private Vector2 vVelocity;

    private Array<Bullet> arBullets;

    private Gun gun;

    private STATE state;

    private Rectangle rectHitbox;

    public Player(float fX, float fY) {
        super();

        // Create animation data from spritesheet
        Texture txSpritesheet = new Texture("spritesheets/player.png");
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

        arBullets = new Array<Bullet>();

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
    }

    public void setAngle(Vector3 vMousePos) {
        fAngle = MathUtils.atan2((vMousePos.y - getY()), (vMousePos.x - getX()));
    }

    public void move(int nDirection, float fSpeed) {
        switch (nDirection) {
            case 0:
                vVelocity = (new Vector2(0, 300));
                break;
            case 1:
                vVelocity = (new Vector2(0, -300));
                break;
            case 2:
                vVelocity = (new Vector2(-300, 0));
                break;
            case 3:
                vVelocity = (new Vector2(300, 0));
        }
    }

    public void shoot(Vector3 vMousePos) {
        if (fAttackCooldown >= 1 / gun.getAttackSpeed()) {
            Bullet b = new Bullet(getX(), getY(), new Texture("textures/bullet.png"), gun.getBulletSpeed(), 8, 8, true);
            b.setTargetPos(vMousePos.x, vMousePos.y, gun.getSpray());
            arBullets.add(b);
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

    public Array<Bullet> getBullets() {
        return arBullets;
    }

    public Rectangle getHitbox() {
        return rectHitbox;
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

        for (Bullet b : arBullets) {
            b.update();
            if (b.findDistance(new Vector2(getX(), getY())) >= gun.getRange()) {
                arBullets.removeValue(b, true);
            }
        }

        setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());
        rectHitbox.setPosition(getX() + 5, getY());
    }
}

