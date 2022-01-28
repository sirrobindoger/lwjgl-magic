package org.sprojects.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;
public class VAO {
    int self;
    List<VAO> vaoList;
    public VAO() {
        self = glGenVertexArrays();
    }

    public void dispose(){
        glDeleteVertexArrays(self);
    }
    public void bind() {
        glBindVertexArray(self);
    }
    public void unbind(){
        glBindVertexArray(0);
    }
    public void addBuffer(int type, IntBuffer data){
        VBO buffer = new VBO(type, false);
        buffer.buffer(data);
    }
    public void addBuffer(int type, FloatBuffer data){
        VBO buffer = new VBO(type, false);
        buffer.buffer(data);
    }
    public void addBuffer(int type, ByteBuffer data){
        VBO buffer = new VBO(type, false);
        buffer.buffer(data);
    }
    public void setAttributes(
            int index,
            int size,
            int type,
            int stride,
            int offset
    ) {

        switch (type) {
            case GL_BYTE:
            case GL_UNSIGNED_BYTE:
            case GL_SHORT:
            case GL_UNSIGNED_SHORT:
            case GL_INT:
            case GL_UNSIGNED_INT:
            case GL_INT_2_10_10_10_REV:
            case GL_UNSIGNED_INT_2_10_10_10_REV:
                glVertexAttribIPointer(index, size, type, stride, offset);
                break;
            default:
                glVertexAttribPointer(index, size, type, false, stride, offset);
                break;
        }
        glEnableVertexAttribArray(index);
    }
}
