package org.sprojects.render;



import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
public class VBO {
    int type;
    boolean dynamic;
    int ptr;
    public VBO(int pType, boolean pDynamic) {
        type = pType;
        dynamic = pDynamic;
        ptr = glGenBuffers();
    }
    public void bind() {
        glBindBuffer(type, ptr);
    }
    public void dispose() {
        glDeleteBuffers(ptr);
    }
    public void buffer(
            FloatBuffer data
    ) {
        this.bind();
        glBufferData(type, data, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }
    public void buffer(
            IntBuffer data
    ) {
        this.bind();
        glBufferData(type, data, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }
    public void buffer(
        ByteBuffer data
    ) {
        this.bind();
        glBufferData(type, data, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }
}
