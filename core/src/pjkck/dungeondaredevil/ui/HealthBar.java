package pjkck.dungeondaredevil.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import pjkck.dungeondaredevil.utils.UIUtils;

public class HealthBar extends ProgressBar {
    private BitmapFont font;

    public HealthBar(int nWidth, int nHeight, float fMaxHealth) {
        super(0.0f, fMaxHealth, 1f, false, new ProgressBarStyle());
        getStyle().background = UIUtils.getColoredDrawable(nWidth, nHeight, Color.LIGHT_GRAY);
        getStyle().knob = UIUtils.getColoredDrawable(0, nHeight, Color.GREEN);
        getStyle().knobBefore = UIUtils.getColoredDrawable(nWidth, nHeight, Color.GREEN);

        setWidth(nWidth);
        setHeight(nHeight);

        setValue(fMaxHealth);
        setAnimateDuration(0.25f);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    public String getHealthString() {
        return (int) getValue() + "/" + (int) getMaxValue();
    }

    public Drawable pickColourBasedOffHealth(int nWidth) {
        if (getValue() <= getMaxValue() / 10) {
            return UIUtils.getColoredDrawable(nWidth, (int) getHeight(), Color.RED);
        } else if (getValue() <= getMaxValue() / 2) {
            return UIUtils.getColoredDrawable(nWidth, (int) getHeight(), Color.YELLOW);
        }
        return UIUtils.getColoredDrawable(nWidth, (int) getHeight(), Color.GREEN);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getStyle().knob = pickColourBasedOffHealth(0);
        getStyle().knobBefore = pickColourBasedOffHealth((int) getStyle().knobBefore.getMinWidth());
        setStyle(getStyle());
        super.draw(batch, parentAlpha);
        font.draw(batch, getHealthString(), getX(), getY() + font.getLineHeight());
    }
}