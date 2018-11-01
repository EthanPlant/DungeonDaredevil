package pjkck.dungeondaredevil.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import pjkck.dungeondaredevil.utils.UIUtils;

public class HealthBar extends ProgressBar {
    private BitmapFont font;

    public HealthBar(int nWidth, int nHeight, float fMaxHealth) {
        super(0.0f, fMaxHealth, 1f, false, new ProgressBarStyle());
        getStyle().background = UIUtils.getColoredDrawable(nWidth, nHeight, Color.RED);
        getStyle().knob = UIUtils.getColoredDrawable(0, nHeight, Color.GREEN);
        getStyle().knobBefore = UIUtils.getColoredDrawable(nWidth, nHeight, Color.GREEN);

        setWidth(nWidth);
        setHeight(nHeight);

        setValue(fMaxHealth);
        setAnimateDuration(0.25f);
        font = new BitmapFont();
    }

    public String getHealthString() {
        return (int) getValue() + "/" + (int) getMaxValue();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, getHealthString(), getX(), getY() + font.getLineHeight());
    }
}
