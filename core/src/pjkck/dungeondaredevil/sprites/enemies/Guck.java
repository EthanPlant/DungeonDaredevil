package pjkck.dungeondaredevil.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Guck extends Enemy {

    public Guck(float fX, float fY) {
        super(fX, fY);

        Texture txSpriteSheet = new Texture("guck.png");
        TextureRegion[][] tmp = TextureRegion.split(txSpriteSheet, txSpriteSheet.getWidth() / 2, txSpriteSheet.getHeight());

        TextureRegion[] arFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            arFrames[i] = tmp[0][i];
        }

        animMovement = new Animation<TextureRegion>(1/5f, arFrames);

        setRegion(animMovement.getKeyFrame(0));
    }

    @Override
    public void move() {
        if(new Random().nextInt(4) == 0){
            setX(getX() - 100 * Gdx.graphics.getDeltaTime());
        }
        if(new Random().nextInt(4) == 1){
            setY(getY() - 100 * Gdx.graphics.getDeltaTime());
        }
        if(new Random().nextInt(4) == 2){
            setX(getX() + 100 * Gdx.graphics.getDeltaTime());
        }
        if(new Random().nextInt(4) == 3){
            setY(getY() + 100 * Gdx.graphics.getDeltaTime());
        }


    }

    @Override
    public void update(float delta) {
        move();
        fElapsedTime += delta;
        setRegion(animMovement.getKeyFrame(fElapsedTime, true));
    }
}
