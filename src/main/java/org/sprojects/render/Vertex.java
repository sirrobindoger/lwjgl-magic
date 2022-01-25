package org.sprojects.render;

import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Vertex {
    int vbo_vertex_handle;
    int vao_vertex_handle;
    final int vertex_size = 3;
    int shaderProgram;
    static final String vertex_file = "/shaders/vt_triangle.glsl";
    static final String frag_file = "/shaders/fs_triangle.glsl";
    public Vertex() {
        vbo_vertex_handle = glGenBuffers();
        vao_vertex_handle = glGenVertexArrays();
        float[] vertices = new float[] {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        };
        FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices.length);
        vertex_data.put(vertices).flip();
        glBindVertexArray(vao_vertex_handle);
        // copy our vertices into a VBO
        glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
        glBufferData(GL_ARRAY_BUFFER, vertex_data, GL_STATIC_DRAW);
        // set the vertex attributes pointers
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
        // get out shaders and set them up
        int vertexShader = loadShader(vertex_file, GL_VERTEX_SHADER);
        int fragShader = loadShader(frag_file, GL_FRAGMENT_SHADER);
        // use our shader program when we want to render an object
        shaderProgram = linkShaders(vertexShader, fragShader);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    public void draw() {
        glUseProgram(shaderProgram);
        glBindVertexArray(vao_vertex_handle);
        glDrawArrays(GL_TRIANGLES, 0,3);

    }
    private static int loadShader(String file, int type) {
        // Retrieve glsl file and convert it into string
        StringBuilder shaderSource = new StringBuilder();
        try {
            InputStream is = Vertex.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // compile shader
        int vertexShader = glCreateShader(type);
        glShaderSource(vertexShader, shaderSource);
        glCompileShader(vertexShader);
        int success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        // check for success
        if (success == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(vertexShader, 500));
            System.err.println("ERROR::SHADER::VERTEX::COMPILATION_FAILED");
            System.exit(-1);
        }
        return vertexShader;
    }
    public static int linkShaders(int shaderOne, int shaderTwo) {
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, shaderOne);
        glAttachShader(shaderProgram, shaderTwo);
        glLinkProgram(shaderProgram);
        int success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderProgram, 500));
            System.err.println("ERROR::SHADER::PROGRAM::LINKING_FAILED");
            System.exit(-1);
        }
        glDeleteShader(shaderOne);
        glDeleteShader(shaderTwo);
        return shaderProgram;
    }
}
