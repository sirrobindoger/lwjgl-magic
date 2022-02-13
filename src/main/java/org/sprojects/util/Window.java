package org.sprojects.util;

import com.google.common.eventbus.EventBus;
import org.lwjgl.opengl.GL;
import org.sprojects.render.Test;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL33.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    // singleton instance
    private static Window instance = null;
    private static Test tri;
    // class variables
    public String displayName;
    public int width;
    public int height;
    public long ptr;
    public EventBus eventRegistry;
    // constructors
    private Window(int w, int h, String name) {
        width = w;
        height = h;
        displayName = name;
        ptr = -1;
        eventRegistry = new EventBus();
    }
    public static Window Window(int w, int h, String name) {
        if (instance == null) {
            instance = new Window(w,h,name);
        }
        return instance;
    }
    public void initRendering(){
        // open screen
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        ptr = glfwCreateWindow(width, height, displayName, NULL, NULL);
        if (ptr == NULL) {
            System.out.println("Failed to create GLFW window");
            glfwTerminate();
        }
        // init OpenGL
        glfwMakeContextCurrent(ptr);
        glfwShowWindow(ptr);
        GL.createCapabilities();
        glViewport(0,0,800,600);
        glfwSetFramebufferSizeCallback(ptr, (long win, int w, int h) -> glViewport(0,0,w,h));
    }
    public void clearBuffers(){
        glClearColor(0,0,0, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(ptr);
        glfwPollEvents();
    }
    public boolean shouldClose() {
        return glfwWindowShouldClose(ptr);
    }
}
