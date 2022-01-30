package org.sprojects.render;



import static org.lwjgl.opengl.GL33.*;
public class VertexArray {
    int self;
    Shader shader;
    Texture texture;
    public VertexArray() {
        self = glGenVertexArrays();
    }

    public void dispose(){
        glDeleteVertexArrays(self);
    }
    public void bind() {
        glBindVertexArray(self);
    }
    public void bindShader() {glUseProgram(shader.id);};
    public void bindAndUseShader() {glBindVertexArray(self); glUseProgram(shader.id);}
    public void unbind(){
        glBindVertexArray(0);
    }
    public void addShader(String vertex, String frag) {shader = new Shader(vertex,frag);};
    public Buffer addBuffer(int type, int[] data){
        bind();
        Buffer buffer = new Buffer(type, false);
        buffer.buffer(data);
        return buffer;
    }
    public Buffer addBuffer(int type, float[] data){
        bind();
        Buffer buffer = new Buffer(type, false);
        buffer.buffer(data);
        return buffer;
    }
    public Buffer addBuffer(int type, byte[] data){
        bind();
        Buffer buffer = new Buffer(type, false);
        buffer.buffer(data);
        return buffer;
    }
    public Texture addTexture(String filepath) {
        texture = new Texture(filepath);
        return texture;
    }
    public void setAttributes(
            int index,
            int size,
            int type,
            int stride,
            int offset
    ) {
        bind();
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
