package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite {

    private Vector2 vTargetPos;
    private Vector2 vVelocity;

    private float fSpeed;

    private boolean shouldRotate;

    public Bullet(float x, float y, Texture tex, float fSpeed, float width, float height, boolean shouldRotate) {
        super(tex);

        this.shouldRotate = shouldRotate;

        vVelocity = Vector2.Zero;
        this.fSpeed = fSpeed;

        setPosition(x, y);
        setOriginCenter();
        setBounds(x, y, width, height);
    }

    // Set the location to shoot towards
    public void setTargetPos(Vector2 value, float spray) {
        vTargetPos = value;
        // Calculate angle to target location
        float fAngle = (float) Math.toDegrees(MathUtils.atan2(vTargetPos.y - getY(), vTargetPos.x - getX()));
        int nSprayChooser = MathUtils.random(100);
        float fSprayAmount = MathUtils.random(spray);
        if (nSprayChooser != 0 && nSprayChooser < 50) {
            fAngle += fSprayAmount;
        } else if (nSprayChooser >= 50) {
            fAngle -= fSprayAmount;
        }
        if (shouldRotate) setRotation(fAngle);
        // Set velocity vector
        vVelocity = new Vector2(fSpeed * MathUtils.cos((float) Math.toRadians(fAngle)), fSpeed * MathUtils.sin((float) Math.toRadians(fAngle)));
    }

    public void setTargetPos(float x, float y, float spray) {
        setTargetPos(new Vector2(x, y), spray);
    }

    public Vector2 getVelocity() {
        return vVelocity;
    }

    public Vector2 getvTargetPos() {
        return vTargetPos;
    }

    public void update() {
        setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());
    }
}
