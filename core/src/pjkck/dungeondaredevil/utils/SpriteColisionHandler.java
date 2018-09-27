package pjkck.dungeondaredevil.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteColisionHandler {

    public boolean isColliding(Sprite sprOne, Sprite sprTwo) {
        return sprOne.getBoundingRectangle().overlaps(sprTwo.getBoundingRectangle());
    }
}
