package pjkck.dungeondaredevil.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import pjkck.dungeondaredevil.utils.UIUtils;

public class HealthBar extends ProgressBar {
    public HealthBar(int nWidth, int nHeight, float fMaxHealth) {
        super(0.0f, fMaxHealth, 1f, false, new ProgressBarStyle());
        getStyle().background = UIUtils.getColoredDrawable(nWidth, nHeight, Color.RED);
        getStyle().knob = UIUtils.getColoredDrawable(0, nHeight, Color.GREEN);
        getStyle().knobBefore = UIUtils.getColoredDrawable(nWidth, nHeight, Color.GREEN);

        setWidth(nWidth);
        setHeight(nHeight);

        setValue(fMaxHealth);
        setAnimateDuration(0.25f);
    }
}
