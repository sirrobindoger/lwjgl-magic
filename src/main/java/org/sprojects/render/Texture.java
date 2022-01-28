package org.sprojects.render;

import java.io.File;
import java.nio.ByteBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL33.*;
public class Texture {
    float coords[];
    ByteBuffer data;
    int width;
    int height;
    int nrChannels;
    int self;
    public Texture(String filepath) {
        int[] rawWidth = new int[1], rawHeight = new int[1], rawnrChannels = new int[1];
        data = stbi_load(getClass().getClassLoader().getResource(filepath).getFile(), rawWidth, rawHeight, rawnrChannels, 0);
        width = rawWidth[0]; height = rawHeight[0]; nrChannels = rawnrChannels[0];
        self = glGenTextures();
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }
    public void bind() {glBindTexture(GL_TEXTURE_2D, self);}
    public void mipmap() {
        bind();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width,height, 0, GL_RGB, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(data);
    }

}
