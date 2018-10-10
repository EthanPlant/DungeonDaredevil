package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite {
    public enum DIRECTION {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public enum STATE {
        STANDING,
        WALKING,
        DASHING
    }

    private Animation<TextureRegion>[] arWalkingAnimations;

    private DIRECTION direction;
    private STATE state;

    private float fElapsedTime;

    private float fDeltaX;
    private float fDeltaY;

    public Player(float fX, float fY) {
        super();

        Texture txSpritesheet = new Texture("player.png");
        TextureRegion[][] tmp = TextureRegion.split(txSpritesheet, txSpritesheet.getWidth() / 8, txSpritesheet.getHeight() / 2);

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
        for (int i = 0; i < 1; i++) {
            for (int j = 4; j < 8; j++) {
                forwardFrames[index++] = tmp[i][j];
            }
        }

        index = 0;
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                leftFrames[index++] = tmp[i][j];
            }
        }

        index = 0;
        for (int i = 1; i < 2; i++) {
            for (int j = 4; j < 8; j++) {
                rightFrames[index++] = tmp[i][j];
            }
        }

        arWalkingAnimations = new Animation[4];

        arWalkingAnimations[0] = new Animation<TextureRegion>(1/7f, forwardFrames);
        arWalkingAnimations[1] = new Animation<TextureRegion>(1/7f, backwardFrames);
        arWalkingAnimations[2] = new Animation<TextureRegion>(1/7f, rightFrames);
        arWalkingAnimations[3] = new Animation<TextureRegion>(1/7f, leftFrames);

        direction = DIRECTION.FORWARD;
        state = STATE.STANDING;

        setRegion(arWalkingAnimations[0].getKeyFrame(0));
        setBounds(fX, fY, 32, 32);
        setOriginCenter();

        fDeltaX = 0;
        fDeltaY  = 0;
    }

    public void dash() {
        switch (direction) {
            case FORWARD:
                setY(getY() - 5000 * Gdx.graphics.getDeltaTime());
                break;
            case BACKWARD:
                setY(getY() + 5000 * Gdx.graphics.getDeltaTime());
                break;
            case LEFT:
                setX(getX() - 5000 * Gdx.graphics.getDeltaTime());
                break;
            case RIGHT:
                setX(getX() + 5000 * Gdx.graphics.getDeltaTime());
                break;
        }
        setState(STATE.DASHING);
    }

    public void walk(Vector3 vMousePos, int nDirection) {
        float fAngle = MathUtils.atan2((vMousePos.y - getY()), (vMousePos.x - getX()));
        switch (nDirection) {
            case 0:
                fDeltaX = 300 * MathUtils.cos(fAngle);
                fDeltaY = 300 * MathUtils.sin(fAngle);
                break;
            case 1:
                fDeltaX = -300 * MathUtils.cos(fAngle);
                fDeltaY = -300 * MathUtils.sin(fAngle);
                break;
            case 2:
                fDeltaX = -300 * MathUtils.cos(fAngle + 90);
                fDeltaY = -300 * MathUtils.sin(fAngle + 90);
                break;
            case 3:
                fDeltaX = -300 * MathUtils.cos(fAngle -90);
                fDeltaY = -300 * MathUtils.sin(fAngle - 90);
        }
        System.out.println(Math.toDegrees(fAngle));
    }

    public void setDeltaX(float value) {
        fDeltaX = value;
    }

    public void setDeltaY(float value) {
        fDeltaY = value;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public STATE getState() {
        return state;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void update(float delta) {
        fElapsedTime += delta;

        setPosition(getX() + fDeltaX * Gdx.graphics.getDeltaTime(), getY() + fDeltaY * Gdx.graphics.getDeltaTime());

        if (fDeltaX != 0 | fDeltaY != 0) {
            setState(STATE.WALKING);
        } else {
            setState(STATE.STANDING);
        }

        if (state == STATE.STANDING || state == STATE.DASHING) {
            switch (direction) {
                case FORWARD:
                    setRegion(arWalkingAnimations[0].getKeyFrame(0));
                    break;
                case BACKWARD:
                    setRegion(arWalkingAnimations[1].getKeyFrame(0));
                    break;
                case RIGHT:
                    setRegion(arWalkingAnimations[2].getKeyFrame(0));
                    break;
                case LEFT:
                    setRegion(arWalkingAnimations[3].getKeyFrame(0));
                    break;
            }
        } else {
            switch (direction) {
                case FORWARD:
                    setRegion(arWalkingAnimations[0].getKeyFrame(fElapsedTime, true));
                    break;
                case BACKWARD:
                    setRegion(arWalkingAnimations[1].getKeyFrame(fElapsedTime, true));
                    break;
                case RIGHT:
                    setRegion(arWalkingAnimations[2].getKeyFrame(fElapsedTime, true));
                    break;
                case LEFT:
                    setRegion(arWalkingAnimations[3].getKeyFrame(fElapsedTime, true));
                    break;
            }
        }
    }
}