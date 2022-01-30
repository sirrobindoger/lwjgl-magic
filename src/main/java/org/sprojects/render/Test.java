package org.sprojects.render;


import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL33.*;
import org.joml.*;
import org.joml.Math;

public class Test {
    static final String vertex_file = "/shaders/vt_triangle.glsl";
    static final String frag_file = "/shaders/fs_triangle.glsl";
    VertexArray vertexArray;
    Texture tex;
    Matrix4f trans;
    public Test() {
        vertexArray = new VertexArray();
        float vertices[] = {
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
        vertexArray.addBuffer(GL_ARRAY_BUFFER, vertices);
        vertexArray.addBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);
        vertexArray.setAttributes(0,3,GL_FLOAT,8*Float.BYTES,0);
        vertexArray.setAttributes(1,3,GL_FLOAT,8*Float.BYTES,3*Float.BYTES);
        vertexArray.setAttributes(2,2,GL_FLOAT,8*Float.BYTES,6*Float.BYTES);
        vertexArray.addShader(vertex_file,frag_file);
        vertexArray.addTexture("images/wall.jpg");
        vertexArray.texture.mipmap(false);
        tex = new Texture("images/fumo.png");
        tex.mipmap(true);
        vertexArray.shader.setInt("texture1", 0);
        vertexArray.shader.setInt("texture2",1);





    }
    public void draw() {
        vertexArray.texture.bind(GL_TEXTURE0);
        tex.bind(GL_TEXTURE1);
        vertexArray.bindAndUseShader();
        trans = new Matrix4f();
        trans.rotate((float) glfwGetTime(), 0.0f,0.0f,1.0f);
        vertexArray.shader.setMatrix4f("transform",trans);
        glDrawElements(GL_TRIANGLES,6,GL_UNSIGNED_INT,0);
    }
}
