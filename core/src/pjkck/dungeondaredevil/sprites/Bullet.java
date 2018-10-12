package pjkck.dungeondaredevil.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite {

    private final int width;
    private final int height;
    private final Vector2 position;
    public Vector2 velocity;

    public Bullet(float x, float y, int width, int height, float targetX, float targetY) {
        this.width = width;
        this.height = height;
        position = new Vector2( x , y );
        velocity = new Vector2( 0 , 0 );
        velocity.set(targetX - position.x,targetY - position.y);
        //velocity.set(targetX - position.x, targetY - position.y).nor().scl(Math.min(position.dst(targetX, targetY), speedMax));
    }
    public void update(float deltaTime) {
        //position.add(position.x + speedMax * deltaTime * ax,position.y + speedMax * deltaTime * ay);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        //velocity.scl(1 - (0.98f * deltaTime));
        // Linear dampening, otherwise the ball will keep going at the original velocity forever
    }

}
