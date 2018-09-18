package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    public Player(float x, float y) {
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
        setBounds(x, y, 32, 32);
    }


    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public void update(float delta) {
        fElapsedTime += delta;

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