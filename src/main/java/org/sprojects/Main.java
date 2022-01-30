package org.sprojects;

import org.sprojects.render.Window;

public class Main {
    static Window window;
    public static void main(String[] args) {
        window = Window.Window(800,800,"Magic!");
        window.initRendering();

        while (!window.shouldClose()){
            window.render();
        }


    }
}
