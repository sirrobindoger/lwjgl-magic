package org.sprojects.render;

import org.joml.Matrix4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL33.GL_FALSE;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL33.glGetShaderInfoLog;

public class Shader {
    int id;
    float[] matrixBuffer = new float[16];
    HashMap<String, Integer> uniformLoc;
    public Shader(String vert, String frag) {
        id = ShaderProgram(vert, frag);
        uniformLoc = new HashMap<String,Integer>();
    }
    public void use() {
        glUseProgram(id);
    }
    public void setBool(String name, boolean value) {
        use();
        if (uniformLoc.containsKey(name)) {
            glUniform1i(uniformLoc.get(name), value ? 1 : 0);
            return;
        }
        int location = glGetUniformLocation(id, name);
        uniformLoc.put(name, location);
        glUniform1i(location, value ? 1 : 0);
    }
    public void setInt(String name, int value) {
        use();
        if (uniformLoc.containsKey(name)) {
            glUniform1i(uniformLoc.get(name), value);
            return;
        }
        int location = glGetUniformLocation(id, name);
        uniformLoc.put(name, location);
        glUniform1i(location, value);
    }
    public void setMatrix4f(String name, Matrix4f value) {
        use();
        if (uniformLoc.containsKey(name)) {
            glUniformMatrix4fv(uniformLoc.get(name),false, value.get(matrixBuffer));
            return;
        }
        int location = glGetUniformLocation(id, name);
        uniformLoc.put(name, location);
        glUniformMatrix4fv(location, false, value.get(matrixBuffer));
    }
    public void setFloat(String name, float value){
        use();
        if (uniformLoc.containsKey(name)) {
            glUniform1f(uniformLoc.get(name), value);
            return;
        }
        int location = glGetUniformLocation(id, name);
        uniformLoc.put(name, location);
        glUniform1f(location, value);
    }
    public static int ShaderProgram(String vertexFile, String fragFile) {
        // get out shaders and set them up
        int vertex_shader = loadShader(vertexFile, GL_VERTEX_SHADER);
        int frag_shader = loadShader(fragFile, GL_FRAGMENT_SHADER);
        // use our shader program when we want to render an object
        return linkShaders(vertex_shader, frag_shader);
    }

    public static int loadShader(String file, int type) {
        // Retrieve glsl file and convert it into string
        StringBuilder shaderSource = new StringBuilder();
        try {
            InputStream is = Test.class.getResourceAsStream(file);
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
