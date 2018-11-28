package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SprBullet extends Sprite {

    private Vector2 vTargetPos;
    private Vector2 vVelocity;

    private float fSpeed;

    private boolean shouldRotate;

    private Class origin;

    public SprBullet(float x, float y, Texture tex, float fSpeed, float width, float height, boolean shouldRotate, Class origin) {
        super(tex);

        this.shouldRotate = shouldRotate;

        vVelocity = Vector2.Zero;
        this.fSpeed = fSpeed;

        this.origin = origin;

        setPosition(x, y);
        setOriginCenter();
        setBounds(x, y, width, height);
    }

    // Set the location to shoot towards
    public void setTargetPos(Vector2 vTargetPos, float spray) {
        this.vTargetPos = vTargetPos;
        // Calculate angle to target location
        float fSprayAngle = MathUtils.random(-spray, spray);
        // Set velocity vector
        vVelocity = vTargetPos.sub(new Vector2(getX(), getY())).nor().scl(fSpeed).rotate(fSprayAngle);
        if (shouldRotate) setRotation(vVelocity.angle());
    }

    public void setTargetPos(float x, float y, float spray) {
        setTargetPos(new Vector2(x, y), spray);
    }

    public Vector2 getVelocity() {
        return vVelocity;
    }

    public Class getOrigin() {
        return origin;
    }

    public void update(float delta) {
        setPosition(getX() + vVelocity.x * delta, getY() + vVelocity.y * delta);
    }
}
