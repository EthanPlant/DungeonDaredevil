package pjkck.dungeondaredevil.utils;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {
    class InputState {
        public boolean isPressed = false;
        public boolean isDown = false;
        public boolean isReleased = false;
    }

    class KeyState extends InputState {
        public int nKey;

        public KeyState(int nKey) {
            this.nKey = nKey;
        }
    }

    class MouseState extends InputState {
        public int nPointer;

        public Vector2  vCoords;

        public int nButton;

        public MouseState(int nX, int nY, int nPointer, int nButton) {
            this.nPointer = nPointer;
            vCoords = new Vector2(nX, nY);
            this.nButton = nButton;
        }
    }

    public Array<KeyState> keyStates = new Array<KeyState>();
    public MouseState mouseState;

    public InputManager() {
        for (int i = 0; i < 256; i++) {
            keyStates.add(new KeyState(i));
        }

        mouseState = new MouseState(0, 0, 0, 0);
    }

    public boolean isKeyPressed(int nKey) {
        return keyStates.get(nKey).isPressed;
    }

    public boolean isKeyDown(int nKey) {
        return keyStates.get(nKey).isDown;
    }

    public boolean isKeyRelease(int nKey) {
        return keyStates.get(nKey).isReleased;
    }

    public boolean isMousePressed() {
        return mouseState.isPressed;
    }

    public boolean isMouseDown() {
        return mouseState.isDown;
    }

    public boolean isMouseReleased() {
        return mouseState.isReleased;
    }

    public Vector2 getMouseCoordinates() {
        return mouseState.vCoords;
    }

    public void update() {
        for (int i = 0; i < 256; i++) {
            KeyState k = keyStates.get(i);
            k.isPressed = false;
            k.isReleased = false;
        }

        mouseState.isPressed = false;
        mouseState.isReleased = false;


    }

    @Override
    public boolean keyDown(int keycode) {
        keyStates.get(keycode).isPressed = true;
        keyStates.get(keycode).isDown = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyStates.get(keycode).isDown = false;
        keyStates.get(keycode).isReleased = true;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseState.isDown = true;
        mouseState.isPressed = true;

        mouseState.vCoords = new Vector2(screenX, screenY);
        mouseState.nButton = button;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseState.isDown = false;
        mouseState.isReleased = true;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseState.isDown = true;
        mouseState.isPressed = true;

        mouseState.vCoords = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseState.vCoords = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
