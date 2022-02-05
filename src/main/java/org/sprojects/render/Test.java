package org.sprojects.render;


import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;
public class Test {
    static final String vertex_file = "/shaders/vt_triangle.glsl";
    static final String frag_file = "/shaders/fs_triangle.glsl";
    VertexArray vertexArray;
    Texture tex;
    Matrix4f model;
    Matrix4f view;
    Matrix4f ortho;
    Matrix4f projection;
    int modelLoc;
    int viewLoc;
    int projLoc;
    Vector3f[] cubePos;
    public Test() {
        vertexArray = new VertexArray();
        float[] vertices = {
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
        };
        cubePos = new Vector3f[]{
                new Vector3f(0.0f, 0.0f, 0.0f),
                new Vector3f(2.0f, 5.0f, -15.0f),
                new Vector3f(-1.5f, -2.2f, -2.5f),
                new Vector3f(-3.8f, -2.0f, -12.3f),
                new Vector3f(2.4f, -0.4f, -3.5f),
                new Vector3f(-1.7f, 3.0f, -7.5f),
                new Vector3f(1.3f, -2.0f, -2.5f),
                new Vector3f(1.5f, 2.0f, -2.5f),
                new Vector3f(1.5f, 0.2f, -1.5f),
                new Vector3f(-1.3f, 1.0f, -1.5f)
        };
        vertexArray.addBuffer(GL_ARRAY_BUFFER, vertices);
        //vertexArray.addBuffer(GL_ELEMENT_ARRAY_BUFFER, indices);
        vertexArray.setAttributes(0,3,GL_FLOAT,5*Float.BYTES,0);
        vertexArray.setAttributes(1,2,GL_FLOAT,5*Float.BYTES,3*Float.BYTES);
        vertexArray.addShader(vertex_file,frag_file);
        vertexArray.addTexture("images/wall.jpg");
        vertexArray.texture.mipmap(false);
        tex = new Texture("images/fumo.png");
        tex.mipmap(true);
        vertexArray.shader.setInt("texture1", 0);
        vertexArray.shader.setInt("texture2",1);

        ortho = new Matrix4f().ortho(
            0.0f,
            800.0f,
            0.0f,
            600f,
            0.1f,
            100f
        );
        model = new Matrix4f().identity();

        view = new Matrix4f().identity();
        view.translate(0.0f, 0.0f, -2.0f);
        projection = new Matrix4f();
        projection.perspective(
                45.0f,
                800.0f/600.0f,
                .1f,
                100.0f
        );

        viewLoc = glGetUniformLocation(vertexArray.shader.id, "view");
        projLoc = glGetUniformLocation(vertexArray.shader.id, "projection");

        vertexArray.shader.setMatrix4f("view", view);
        vertexArray.shader.setMatrix4f("projection", projection);
        glEnable(GL_DEPTH_TEST);
    }
    public void draw() {
        vertexArray.texture.bind(GL_TEXTURE0);
        tex.bind(GL_TEXTURE1);
        vertexArray.bindAndUseShader();

        for (int i = 0; i < cubePos.length; i++) {
            model.identity();
            model.translate(cubePos[i]);
            float angle = 20.0f * i;
            model.rotate(angle, 1.0f,0.3f,0.5f);
            vertexArray.shader.setMatrix4f("model", model);
            glDrawArrays(GL_TRIANGLES, 0,36);
        }
    }
}
