package org.prehistoric.engine.core.movement;

import org.lwjgl.glfw.GLFW;
import org.math.matrix.Matrix4f;
import org.math.vector.Quaternion;
import org.math.vector.Vector3f;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.config.FrameworkConfig;
import org.prehistoric.engine.core.framework.Input;
import org.prehistoric.engine.core.framework.Window;

public class Camera
{
    private final Vector3f yAxis = new Vector3f(0, 1, 0);

    private Vector3f position;
    private Vector3f previousPosition;
    private Vector3f forward;
    private Vector3f previousForward;
    private Vector3f up;
    private float movAmt = 0.1f;
    private float rotAmt = 0.8f;
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private Matrix4f viewProjectionMatrix;
    private Matrix4f previousViewMatrix;
    private Matrix4f previousViewProjectionMatrix;
    private boolean cameraMoved;
    private boolean cameraRotated;

    private float width;
    private float height;
    private float fovY;

    private float rotYstride;
    private float rotYamt;
    private float rotYcounter;
    private boolean rotYInitiated = false;
    private float rotXstride;
    private float rotXamt;
    private float rotXcounter;
    private boolean rotXInitiated = false;
    private float mouseSensitivity = 0.8f;

    private Quaternion[] frustumPlanes = new Quaternion[6];
    private Vector3f[] frustumCorners = new Vector3f[8];
    
    public Camera(Vector3f position)
    {
	this.position = position;
	forward = new Vector3f(0, 0, 1);
	up = new Vector3f(0, 1, 0);
	
	SetProjection(EngineConfig.getFov(), FrameworkConfig.getWidth(), FrameworkConfig.getHeight());
	
	viewMatrix = new Matrix4f().View(forward, up)
		.Mul(new Matrix4f().Translation(position.Mul(-1)));
	viewProjectionMatrix = new Matrix4f().Zero();
	
	setPreviousViewMatrix(new Matrix4f().Zero());
	setPreviousViewProjectionMatrix(new Matrix4f().Zero());
    }
    
    public void LogStage()
    {
	System.out.println("Camera position -> " + position);
	System.out.println("Camera forward -> " + forward);
	System.out.println("Camera up-vector -> " + up);
	System.out.println("Camera right-vector -> " + GetRight());
	System.out.println("Camera left-vector -> " + GetLeft());
	System.out.println();
    }

    public void Input(Window window)
    {
	previousPosition = new Vector3f(position);
	previousForward = new Vector3f(forward);
	cameraMoved = false;
	cameraRotated = false;

	movAmt += (0.04f * Input.getScrollOffset());
	movAmt = Math.max(0.02f, movAmt);

	if (Input.IsKeyHold(GLFW.GLFW_KEY_W))
	    Move(forward, movAmt);
	if (Input.IsKeyHold(GLFW.GLFW_KEY_S))
	    Move(forward, -movAmt);
	if (Input.IsKeyHold(GLFW.GLFW_KEY_A))
	    Move(GetLeft(), movAmt);
	if (Input.IsKeyHold(GLFW.GLFW_KEY_D))
	    Move(GetLeft(), -movAmt);

	if (Input.IsKeyHold(GLFW.GLFW_KEY_UP))
	    RotateX(-rotAmt / 8f);
	if (Input.IsKeyHold(GLFW.GLFW_KEY_DOWN))
	    RotateX(rotAmt / 8f);
	if (Input.IsKeyHold(GLFW.GLFW_KEY_LEFT))
	    RotateY(-rotAmt / 8f);
	if (Input.IsKeyHold(GLFW.GLFW_KEY_RIGHT))
	    RotateY(rotAmt / 8f);

	// free mouse rotation
	if (Input.IsButtonHolding(2))
	{
	    float dy = Input.getLockedCursorPosition().y
		    - Input.getCursorPosition().y;
	    float dx = Input.getLockedCursorPosition().x
		    - Input.getCursorPosition().x;

	    // y-axis rotation
	    if (dy != 0)
	    {
		rotYstride = Math.abs(dy * 0.01f);
		rotYamt = -dy;
		rotYcounter = 0;
		rotYInitiated = true;
	    }

	    if (rotYInitiated)
	    {

		// up-rotation
		if (rotYamt < 0)
		{
		    if (rotYcounter > rotYamt)
		    {
			RotateX(-rotYstride * mouseSensitivity);
			rotYcounter -= rotYstride;
			rotYstride *= 0.98;
		    } else
			rotYInitiated = false;
		}
		// down-rotation
		else if (rotYamt > 0)
		{
		    if (rotYcounter < rotYamt)
		    {
			RotateX(rotYstride * mouseSensitivity);
			rotYcounter += rotYstride;
			rotYstride *= 0.98;
		    } else
			rotYInitiated = false;
		}
	    }

	    // x-axis rotation
	    if (dx != 0)
	    {
		rotXstride = Math.abs(dx * 0.01f);
		rotXamt = dx;
		rotXcounter = 0;
		rotXInitiated = true;
	    }

	    if (rotXInitiated)
	    {

		// up-rotation
		if (rotXamt < 0)
		{
		    if (rotXcounter > rotXamt)
		    {
			RotateY(rotXstride * mouseSensitivity);
			rotXcounter -= rotXstride;
			rotXstride *= 0.96;
		    } else
			rotXInitiated = false;
		}
		// down-rotation
		else if (rotXamt > 0)
		{
		    if (rotXcounter < rotXamt)
		    {
			RotateY(-rotXstride * mouseSensitivity);
			rotXcounter += rotXstride;
			rotXstride *= 0.96;
		    } else
			rotXInitiated = false;
		}
	    }

	    GLFW.glfwSetCursorPos(window.getID(), Input.getLockedCursorPosition().x,
		    Input.getLockedCursorPosition().y);
	}

	if (!position.Equals(previousPosition))
	{
	    cameraMoved = true;
	}

	if (!forward.Equals(previousForward))
	{
	    cameraRotated = true;
	}
	
	if(Input.IsKeyPushed(GLFW.GLFW_KEY_E))
	{
	    System.out.println(forward);
	    RotateY(90.0f);
	    System.out.println(forward);
	}

	setPreviousViewMatrix(viewMatrix);
	setPreviousViewProjectionMatrix(viewProjectionMatrix);
	
	viewMatrix = new Matrix4f().View(forward, up)
		.Mul(new Matrix4f().Translation(position.Mul(-1)));
	viewProjectionMatrix = projectionMatrix.Mul(viewMatrix);
    }

    public void Move(Vector3f dir, float amount)
    {
	Vector3f newPos = position.Add(dir.Mul(amount));
	position = newPos;
    }

    public void RotateY(float angle)
    {
	Vector3f hAxis = yAxis.Cross(forward).Normalize();

	forward.Rotate(angle, yAxis).Normalize();

	up = forward.Cross(hAxis).Normalize();
    }

    public void RotateX(float angle)
    {
	Vector3f hAxis = yAxis.Cross(forward).Normalize();

	forward.Rotate(angle, hAxis).Normalize();

	up = forward.Cross(hAxis).Normalize();
    }

    public Vector3f GetLeft()
    {
	Vector3f left = forward.Cross(up);
	left.Normalize();
	return left;
    }

    public Vector3f GetRight()
    {
	Vector3f right = up.Cross(forward);
	right.Normalize();
	return right;
    }

    public void SetProjection(float fovY, float width, float height)
    {
	this.setFovY(fovY);
	this.width = width;
	this.height = height;

	projectionMatrix = new Matrix4f().PerspectiveProjection(fovY, width, height, EngineConfig.getNear_plane(), EngineConfig.getFar_plane());
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public Vector3f getForward()
    {
        return forward;
    }

    public void setForward(Vector3f forward)
    {
        this.forward = forward;
    }

    public Matrix4f getViewMatrix()
    {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix)
    {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix)
    {
        this.projectionMatrix = projectionMatrix;
    }

    public Matrix4f getViewProjectionMatrix()
    {
        return viewProjectionMatrix;
    }

    public void setViewProjectionMatrix(Matrix4f viewProjectionMatrix)
    {
        this.viewProjectionMatrix = viewProjectionMatrix;
    }

    public boolean isCameraMoved()
    {
        return cameraMoved;
    }

    public void setCameraMoved(boolean cameraMoved)
    {
        this.cameraMoved = cameraMoved;
    }

    public boolean isCameraRotated()
    {
        return cameraRotated;
    }

    public void setCameraRotated(boolean cameraRotated)
    {
        this.cameraRotated = cameraRotated;
    }

    public float getWidth()
    {
        return width;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public float getHeight()
    {
        return height;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }

    public Vector3f getyAxis()
    {
        return yAxis;
    }

    public Matrix4f getPreviousViewMatrix()
    {
	return previousViewMatrix;
    }

    public void setPreviousViewMatrix(Matrix4f previousViewMatrix)
    {
	this.previousViewMatrix = previousViewMatrix;
    }

    public Matrix4f getPreviousViewProjectionMatrix()
    {
	return previousViewProjectionMatrix;
    }

    public void setPreviousViewProjectionMatrix(Matrix4f previousViewProjectionMatrix)
    {
	this.previousViewProjectionMatrix = previousViewProjectionMatrix;
    }

    public float getFovY()
    {
	return fovY;
    }

    public void setFovY(float fovY)
    {
	this.fovY = fovY;
    }

    public Quaternion[] getFrustumPlanes()
    {
	return frustumPlanes;
    }

    public void setFrustumPlanes(Quaternion[] frustumPlanes)
    {
	this.frustumPlanes = frustumPlanes;
    }

    public Vector3f[] getFrustumCorners()
    {
	return frustumCorners;
    }

    public void setFrustumCorners(Vector3f[] frustumCorners)
    {
	this.frustumCorners = frustumCorners;
    }
}
