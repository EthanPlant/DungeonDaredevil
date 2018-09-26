package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Enemy extends Sprite {

    protected float fElapsedTime;

    protected Animation<TextureRegion> animMovement;

    public Enemy(float fX, float fY) {
        super();
        setPosition(fX, fY);
        setBounds(fX, fY, 32, 32);
    }

    public abstract void update(float delta);
}
