package org.prehistoric.engine.core.framework;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetJoystickCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWJoystickCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.math.vector.Vector2f;
import org.prehistoric.engine.config.EngineConfig;

public class Input
{
    private static ArrayList<Integer> pushedKeys = new ArrayList<Integer>();
    private static ArrayList<Integer> keysHolding = new ArrayList<Integer>();
    private static ArrayList<Integer> releasedKeys = new ArrayList<Integer>();

    private static ArrayList<Integer> pushedButtons = new ArrayList<Integer>();
    private static ArrayList<Integer> buttonsHolding = new ArrayList<Integer>();
    private static ArrayList<Integer> releasedButtons = new ArrayList<Integer>();

    private static Vector2f cursorPosition;
    private static Vector2f lockedCursorPosition;
    private static Vector2f joystickPosition;
    private static float scrollOffset;

    private static boolean pause = false;

    @SuppressWarnings("unused")
    private static GLFWKeyCallback keyCallback;

    @SuppressWarnings("unused")
    private static GLFWCursorPosCallback cursorPosCallback;

    @SuppressWarnings("unused")
    private static GLFWMouseButtonCallback mouseButtonCallback;

    @SuppressWarnings("unused")
    private static GLFWScrollCallback scrollCallback;

    @SuppressWarnings("unused")
    private static GLFWJoystickCallback joystickCallback;
    
    @SuppressWarnings("unused")
    private static GLFWFramebufferSizeCallback framebufferSizeCallback;

    public static void Init(final Window window)
    {
	cursorPosition = new Vector2f();
	
	glfwSetFramebufferSizeCallback(window.getID(),
		(framebufferSizeCallback = new GLFWFramebufferSizeCallback()
		{
		    @Override
		    public void invoke(long wind, int width, int height)
		    {
			window.SetSize(EngineConfig.getFov(), width, height);
		    }
		}));

	glfwSetKeyCallback(window.getID(), (keyCallback = new GLFWKeyCallback()
	{

	    @Override
	    public void invoke(long window, int key, int scancode, int action, int mods)
	    {
		if (action == GLFW_PRESS)
		{
		    if (!pushedKeys.contains(key))
		    {
			pushedKeys.add(key);
			keysHolding.add(key);
		    }
		}

		if (action == GLFW_RELEASE)
		{
		    pushedKeys.remove((Object)key);
		    keysHolding.remove((Object)key);
		}
	    }
	}));

	glfwSetMouseButtonCallback(window.getID(),
		(mouseButtonCallback = new GLFWMouseButtonCallback()
		{

		    @Override
		    public void invoke(long window, int button, int action, int mods)
		    {
			if (button == 2 && action == GLFW_PRESS)
			{
			    setLockedCursorPosition(new Vector2f(cursorPosition));
			    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
			}

			if (button == 2 && action == GLFW_RELEASE)
			{
			    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
			}

			if (action == GLFW_PRESS)
			{
			    if (!pushedButtons.contains(button))
			    {
				pushedButtons.add(button);
				buttonsHolding.add(button);
			    }
			}

			if (action == GLFW_RELEASE)
			{
			    releasedButtons.add(button);
			    buttonsHolding.remove((Object)button);
			}
		    }
		}));

	glfwSetCursorPosCallback(window.getID(), (cursorPosCallback = new GLFWCursorPosCallback()
	{

	    @Override
	    public void invoke(long window, double xpos, double ypos)
	    {
		cursorPosition.x = (float) xpos;
		cursorPosition.y = (float) ypos;
	    }

	}));

	glfwSetScrollCallback(window.getID(), (scrollCallback = new GLFWScrollCallback()
	{
	    @Override
	    public void invoke(long window, double xoffset, double yoffset)
	    {
		setScrollOffset((float) yoffset);
	    }
	}));
	
	glfwSetJoystickCallback((joystickCallback = new GLFWJoystickCallback()
	{
	    @Override
	    public void invoke(int xpos, int ypos)
	    {
		joystickPosition.x = (float) xpos;
		joystickPosition.y = (float) ypos;
	    }
	}));
    }

    public static void Update()
    {
	setScrollOffset(0);
	pushedKeys.clear();
	releasedKeys.clear();
	pushedButtons.clear();
	releasedButtons.clear();
    }

    public static boolean IsKeyPushed(int key)
    {
	return pushedKeys.contains(key);
    }

    public static boolean IsKeyReleased(int key)
    {
	return releasedKeys.contains(key);
    }

    public static boolean IsKeyHold(int key)
    {
	return keysHolding.contains(key);
    }

    public static boolean IsButtonPushed(int key)
    {
	return pushedButtons.contains(key);
    }

    public static boolean IsButtonReleased(int key)
    {
	return releasedButtons.contains(key);
    }

    public static boolean IsButtonHolding(int key)
    {
	return buttonsHolding.contains(key);
    }

    public static boolean IsPause()
    {
	return pause;
    }

    public static void SetPause(boolean pause)
    {
	Input.pause = pause;
    }

    public static void SetCursorPosition(Vector2f cursorPosition, Window window)
    {
	Input.cursorPosition = cursorPosition;

	glfwSetCursorPos(window.getID(), cursorPosition.x, cursorPosition.y);
    }
    
    public static Vector2f getCursorPosition()
    {
	return cursorPosition;
    }
    
    public static void setCursorPosition(Vector2f cursorPosition)
    {
	Input.cursorPosition = cursorPosition;
    }

    public static Vector2f getLockedCursorPosition()
    {
	return lockedCursorPosition;
    }
    
    public static Vector2f getJoystickPosition()
    {
	return joystickPosition;
    }
    
    public static void setJoystickPosition(Vector2f joystickPosition)
    {
	Input.joystickPosition = joystickPosition;
    }

    public static void setLockedCursorPosition(Vector2f lockedCursorPosition)
    {
	Input.lockedCursorPosition = lockedCursorPosition;
    }

    public static float getScrollOffset()
    {
	return scrollOffset;
    }

    public static void setScrollOffset(float scrollOffset)
    {
	Input.scrollOffset = scrollOffset;
    }
}