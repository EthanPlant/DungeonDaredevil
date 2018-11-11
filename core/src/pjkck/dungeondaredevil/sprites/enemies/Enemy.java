package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import pjkck.dungeondaredevil.sprites.Bullet;
import pjkck.dungeondaredevil.utils.Gun;

public abstract class Enemy extends Sprite {

    protected float fElapsedTime;
    protected float fRange;
    protected boolean isPlayerInRange;

    protected Vector2 vTargetPos;

    protected Animation<TextureRegion> animMovement;

    protected Rectangle rectHitbox;

    protected Gun gun;
    protected float fAttackCooldown;
    private Array<Bullet> arBullets;

    private float fHealth;

    public Enemy(float fX, float fY) {
        super();
        setPosition(fX, fY);
        setBounds(fX, fY, 32, 32);
        arBullets = new Array<Bullet>();
        gun = new Json().fromJson(Gun.class, Gdx.files.internal("json/smg.json"));
        fAttackCooldown = gun.getAttackSpeed();
        fRange = gun.getRange();
        vTargetPos = Vector2.Zero;
    }

    public Rectangle getHitbox() {
        return rectHitbox;
    }

    public Array<Bullet> getBullets() {
        return arBullets;
    }

    public Gun getGun() {
        return gun;
    }

    public float getHealth() {
        return  fHealth;
    }

    public float getRange() {
        return fRange;
    }

    public void setHealth(float value) {
        fHealth += value;
    }

    public void setPlayerInRange(boolean value) {
        isPlayerInRange = value;
    }

    public void setTargetPos(Vector2 pos) {
        vTargetPos = pos;
    }

    public void setTargetPos(float x, float y) {
        setTargetPos(new Vector2(x, y));
    }

    public abstract void move();

    public abstract void shoot();

    public abstract void update(float delta);
}
