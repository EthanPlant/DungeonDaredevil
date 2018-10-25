package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import pjkck.dungeondaredevil.sprites.Bullet;
import pjkck.dungeondaredevil.utils.Gun;

public abstract class Enemy extends Sprite {

    protected float fElapsedTime;

    protected Animation<TextureRegion> animMovement;

    protected Rectangle rectHitbox;

    protected Gun gun;
    protected float fAttackCooldown;
    private Array<Bullet> arBullets;

    public Enemy(float fX, float fY) {
        super();
        setPosition(fX, fY);
        setBounds(fX, fY, 32, 32);
        arBullets = new Array<Bullet>();
        gun = new Json().fromJson(Gun.class, Gdx.files.internal("json/smg.json"));
        fAttackCooldown = gun.getAttackSpeed();
    }

    public Rectangle getHitbox() {
        return rectHitbox;
    }
    public Array<Bullet> getBullets() {
        return arBullets;
    }

    public abstract void move();

    public abstract void shoot();

    public abstract void update(float delta);
}
