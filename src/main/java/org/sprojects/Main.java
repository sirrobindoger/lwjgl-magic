package org.sprojects;

import org.sprojects.util.Window;

public class Main {
    static Window window;
    public static void main(String[] args) {
        window = Window.Window(800,600,"Magic!");
        window.initRendering();

        while (!window.shouldClose()){
            window.initRendering();
        }

    }
}
