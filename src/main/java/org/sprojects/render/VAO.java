package org.sprojects.render;

import static org.lwjgl.opengl.GL33.*;
public class VAO {
    int self;
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
