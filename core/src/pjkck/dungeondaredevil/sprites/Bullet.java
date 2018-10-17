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

    public Bullet(float x, float y, Texture tex, float fSpeed) {
        super(tex);

        vVelocity = Vector2.Zero;
        this.fSpeed = fSpeed;

        setPosition(x, y);
        setOriginCenter();
        setBounds(x, y, 8,8);
    }

    public void setTargetPos(Vector2 value, float spray) {
        vTargetPos = value;
        float fAngle = (float) Math.toDegrees(MathUtils.atan2(vTargetPos.y - getY(), vTargetPos.x - getX()));
        int nSprayChooser = MathUtils.random(100);
        float fSprayAmount = MathUtils.random(spray);
        if (nSprayChooser != 0 && nSprayChooser < 50) {
            fAngle += fSprayAmount;
        } else if (nSprayChooser >= 50) {
            fAngle -= fSprayAmount;
        }
        setRotation(fAngle);
        vVelocity = new Vector2(fSpeed * MathUtils.cos((float) Math.toRadians(fAngle)), fSpeed * MathUtils.sin((float) Math.toRadians(fAngle)));
    }

    public void setTargetPos(float x, float y, float spray) {
        setTargetPos(new Vector2(x, y), spray);
    }

    public void update() {
        setPosition(getX() + vVelocity.x * Gdx.graphics.getDeltaTime(), getY() + vVelocity.y * Gdx.graphics.getDeltaTime());
    }

    public float findDistance(Vector2 vPos) {
        return (float) Math.sqrt(Math.pow((getX() - vPos.x), 2) + Math.pow((getY() - vPos.y), 2));
    }
}
