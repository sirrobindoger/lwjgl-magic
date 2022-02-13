package org.sprojects.util;

import com.google.common.eventbus.EventBus;
import org.sprojects.input.Keyboard;
import org.sprojects.render.Camera;

public class Engine {
    public Window window;
    public Keyboard keyboard;
    public Camera camera;
    public EventBus eventRegistry;
    public Engine(String name) {
        window = Window.Window(800, 800,name);
        keyboard = new Keyboard(window, eventRegistry);
        camera = new Camera();
        eventRegistry = new EventBus();
    }
    public void init() {
        window.initRendering();
        keyboard.registerListeners();

    }

}
