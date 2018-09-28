package pjkck.dungeondaredevil.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class TiledMapCollisionHandler {

    TiledMap map;

    public TiledMapCollisionHandler(TiledMap map){
        this.map = map;
    }

    public boolean isColliding(Sprite sprite, int nLayer) {
        for (MapObject object : map.getLayers().get(nLayer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            if (sprite.getBoundingRectangle().overlaps(rect)) {
                return true;
            }
        }

        return false;
    }
}
