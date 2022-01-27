package org.sprojects.render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Vertex {
    static final String vertex_file = "/shaders/vt_triangle.glsl";
    static final String frag_file = "/shaders/fs_triangle.glsl";
    int shaderProgram;
    VAO vao;
    public Vertex() {
        vao = new VAO();
        float[] vertices = new float[] {
                0.5f,  0.5f, 0.0f,  // top right
                0.5f, -0.5f, 0.0f,  // bottom right
                -0.5f, -0.5f, 0.0f,  // bottom left
                -0.5f,  0.5f, 0.0f   // top left
        };
        int[] indices = new int[] {
                0, 1, 3,   // first triangle
                1, 2, 3    // second triangle
        };
        FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();
        IntBuffer index_data = BufferUtils.createIntBuffer(indices.length).put(indices).flip();
        vao.bind();
        vao.addBuffer(GL_ARRAY_BUFFER, vertex_data);
        vao.addBuffer(GL_ELEMENT_ARRAY_BUFFER,index_data);
        vao.setAttributes(0,3,GL_FLOAT,0,0);
        shaderProgram = Shader.ShaderProgram(vertex_file, frag_file);
        glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
    }
    public void draw() {
        glUseProgram(shaderProgram);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT,0);

    }
}
