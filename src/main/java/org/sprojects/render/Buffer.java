package org.sprojects.render;



import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
public class Buffer {
    int type;
    boolean dynamic;
    int ptr;
    public Buffer(int pType, boolean pDynamic) {
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
            float[] data
    ) {
        this.bind();
        glBufferData(type, BufferUtils.createFloatBuffer(data.length).put(data).flip(), dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }
    public void buffer(
            int[] data
    ) {
        this.bind();
        glBufferData(type, BufferUtils.createIntBuffer(data.length).put(data).flip(), dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }
    public void buffer(
        byte[] data
    ) {
        this.bind();
        glBufferData(type, BufferUtils.createByteBuffer(data.length).put(data).flip(), dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }
}
