package pjkck.dungeondaredevil.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class CollisionHandler {

    private TiledMap map;

    public CollisionHandler(TiledMap map) {
        this.map = map;
    }

    public boolean isCollidingWithMap(Rectangle rectHitbox, int nLayer) {
        for (MapObject object : map.getLayers().get(nLayer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            if (rectHitbox.overlaps(rect)) {
                return true;
            }
        }

        return false;
    }

    public boolean isSpriteColliding(Rectangle rectHitbox1, Rectangle rectHitbox2) {
        return rectHitbox1.overlaps(rectHitbox2);
    }
}
