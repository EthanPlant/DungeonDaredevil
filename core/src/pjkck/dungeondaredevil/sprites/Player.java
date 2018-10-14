package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite {

    private Animation<TextureRegion>[] arWalkingAnimations;
    private float fElapsedTime;

    private Vector2 vVelocity;

    private Array<Bullet> arBullets;

    public Player(float fX, float fY) {
        super();

        Texture txSpritesheet = new Texture("player.png");
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
        setBounds(fX, fY, 32, 32);

        vVelocity = Vector2.Zero;
    }

    public void move(Vector3 vMousePos, int nDirection, float fSpeed) {
        float fAngle = MathUtils.atan2((vMousePos.y - getY()), (vMousePos.x - getX()));
        System.out.println(fAngle);
        setImageBasedOnRotation(fAngle);
        switch (nDirection) {
            case 0:
                vVelocity = (new Vector2(fSpeed * MathUtils.cos(fAngle), 300 * MathUtils.sin(fAngle)));
                break;
            case 1:
                vVelocity = (new Vector2(-fSpeed * MathUtils.cos(fAngle), -300 * MathUtils.sin(fAngle)));
                break;
            case 2:
                vVelocity = (new Vector2(fSpeed * MathUtils.cos((float) (fAngle + 1.5708)), 300 * MathUtils.sin((float) (fAngle + 1.5708))));
                break;
            case 3:
                vVelocity = (new Vector2(fSpeed * MathUtils.cos((float) (fAngle - 1.5708)), 300 * MathUtils.sin((float) (fAngle - 1.5708))));
        }
    }

    public void shoot(Vector3 vMousePos) {
        Bullet b = new Bullet(getX(), getY(), new Texture("bullet.png"));
        b.setTargetPos(vMousePos.x, vMousePos.y);
        arBullets.add(b);
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
        return  arBullets;
    }

    public void update(float delta) {
        fElapsedTime += delta;

        for (Bullet b : arBullets) {
            b.update();
        }

        setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());
    }
}