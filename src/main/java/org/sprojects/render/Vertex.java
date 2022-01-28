package org.sprojects.render;

import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL33.*;

public class Vertex {
    static final String vertex_file = "/shaders/vt_triangle.glsl";
    static final String frag_file = "/shaders/fs_triangle.glsl";
    Shader shaderProgram;
    public Vertex() {
        VAO vao = new VAO();
        float vertices[] = {
                // positions         // colors
                0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
                0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top
        };
        FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();
        vao.bind();
        vao.addBuffer(GL_ARRAY_BUFFER, vertex_data);
        vao.setAttributes(0,3,GL_FLOAT,6*Float.BYTES,0);
        vao.setAttributes(1,3,GL_FLOAT,6*Float.BYTES,3 * Float.BYTES);
        shaderProgram = new Shader(vertex_file, frag_file);

    }
    public void draw() {
        shaderProgram.use();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
