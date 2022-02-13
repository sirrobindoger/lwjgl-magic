package org.sprojects.input;

import com.google.common.eventbus.EventBus;
import org.sprojects.util.Window;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
public class Keyboard {
    private Window window;
    private HashMap<Integer, Integer> keys;
    private EventBus keyevent;
    public static class KeyboardEvent {
        int key;
        int action;
        public KeyboardEvent(int k, int a) {
            key = k;
            action = a;
        }
    }
    public Keyboard(Window parent, EventBus events) {
        window = parent;
        keys = new HashMap<>();
        keyevent = events;
    }
    public void registerListeners(){
        glfwSetKeyCallback(window.ptr, (long window, int key,int scancode, int action, int mods) -> {
            keys.put(key, action);
            keyevent.post(new KeyboardEvent(key, action));
        });
    }
    public boolean keyDown(int key) {
        return keys.get(key) == GLFW_REPEAT || keys.get(key) == GLFW_PRESS;
    }
    public boolean keyUp(int key) {
        return keys.get(key) == GLFW_PRESS;
    }

}
