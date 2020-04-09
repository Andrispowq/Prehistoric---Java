package org.prehistoric.engine.core.framework;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.opengl.GL;
import org.prehistoric.engine.config.FrameworkConfig;

public class Window
{    
    private long id;

    public Window()
    {
	glfwInit();
	
	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, (int) FrameworkConfig.getOgl_version().x);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, (int) FrameworkConfig.getOgl_version().y);
	
	glfwWindowHint(GLFW_SAMPLES, FrameworkConfig.getMultisample_samples());

	id = glfwCreateWindow(FrameworkConfig.getWidth(), FrameworkConfig.getHeight(), FrameworkConfig.getTitle(), FrameworkConfig.isFullscreen() ? glfwGetPrimaryMonitor() : 0, 0);
	
	if (id == 0)
	{
	    System.err.println("The creation of the window has failed!");
	}

	glfwMakeContextCurrent(id);
	GL.createCapabilities();
	glfwShowWindow(id);
    }

    public void SetSize(float fov, int width, int height)
    {
	glfwSetWindowSize(id, width, height);
	
	glViewport(0, 0, width, height);
	
	FrameworkConfig.setWidth(width);
	FrameworkConfig.setHeight(height);
    }

    public void Input()
    {
	glfwPollEvents();
    }

    public void Render()
    {
	glfwSwapBuffers(id);
    }

    public boolean ShouldClose()
    {
	return glfwWindowShouldClose(id);
    }

    public void Destroy()
    {
	glfwTerminate();
    }
    
    public long getID()
    {
	return id;
    }
}
