package org.sprojects.render;


import static org.lwjgl.opengl.GL33.*;

public class Vertex {
    static final String vertex_file = "/shaders/vt_triangle.glsl";
    static final String frag_file = "/shaders/fs_triangle.glsl";
    VAO vao;
    Texture tex;
    public Vertex() {
        vao = new VAO();
        float vertices[] = {
                // positions         // colors
                // positions          // colors           // texture coords
                0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
                0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // top left
        };
        int indices[] = {
                0, 1, 3, // first triangle
                1, 2, 3  // second triangle
        };
        vao.addBuffer(GL_ARRAY_BUFFER, vertices);
        vao.addBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);
        vao.setAttributes(0,3,GL_FLOAT,8*Float.BYTES,0);
        vao.setAttributes(1,3,GL_FLOAT,8*Float.BYTES,3*Float.BYTES);
        vao.setAttributes(2,2,GL_FLOAT,8*Float.BYTES,6*Float.BYTES);
        vao.addShader(vertex_file,frag_file);
        tex = new Texture("images/wall.jpg");
        tex.mipmap();
    }
    public void draw() {
        tex.bind();
        vao.bindAndUseShader();
        glDrawElements(GL_TRIANGLES,6,GL_UNSIGNED_INT,0);
    }
}
