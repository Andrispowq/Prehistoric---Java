package org.prehistoric.engine.engines.rendering.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL31.glGetUniformBlockIndex;
import static org.lwjgl.opengl.GL31.glUniformBlockBinding;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

import java.util.ArrayList;
import java.util.HashMap;

import org.math.matrix.Matrix4f;
import org.math.vector.Quaternion;
import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.math.vector.Vector4f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.BufferUtil;

public abstract class Shader
{
    public int program;
    public HashMap<String, Integer> uniforms;
    private int[] shaders;
    private int counter = 0;

    public Shader()
    {
	program = glCreateProgram();
	uniforms = new HashMap<String, Integer>();
	shaders = new int[6];

	if (program == 0)
	{
	    System.err.println("Shader creation failed");
	    System.exit(1);
	}
    }

    public void UpdateUniforms(GameObject object, ArrayList<Light> lights, Camera camera) {}
    public void UpdateUniforms(GameObject object, ArrayList<Light> lights) {}
    public void UpdateUniforms(GameObject object, Camera camera) {}
    public void UpdateUniforms(Matrix4f view, Matrix4f projection) {}
    public void UpdateUniforms(GameObject object) {}

    public void Bind()
    {
	glUseProgram(program);
    }
    
    public void UnBind()
    {
	glUseProgram(0);
    }

    public void AddUniform(String uniform)
    {
	int uniformLocation = glGetUniformLocation(program, uniform);
	
	if (uniformLocation == 0xFFFFFFFF)
	{
	    System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
	    /*new Exception().printStackTrace();
	    System.exit(1);*/
	}

	uniforms.put(uniform, uniformLocation);
    }

    public void AddUniformBlock(String uniform)
    {
	int uniformLocation = glGetUniformBlockIndex(program, uniform);

	if (uniformLocation == 0xFFFFFFFF)
	{
	    System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
	    new Exception().printStackTrace();
	    System.exit(1);
	}

	uniforms.put(uniform, uniformLocation);
    }
    
    public void BindAttribute(String name, int location)
    {
	glBindAttribLocation(program, location, name);
    }

    public void AddVertexShader(String text)
    {
	AddProgram(text, GL_VERTEX_SHADER);
    }

    public void AddGeometryShader(String text)
    {
	AddProgram(text, GL_GEOMETRY_SHADER);
    }

    public void AddFragmentShader(String text)
    {
	AddProgram(text, GL_FRAGMENT_SHADER);
    }

    public void AddTessellationControlShader(String text)
    {
	AddProgram(text, GL_TESS_CONTROL_SHADER);
    }

    public void AddTessellationEvaluationShader(String text)
    {
	AddProgram(text, GL_TESS_EVALUATION_SHADER);
    }

    public void AddComputeShader(String text)
    {
	AddProgram(text, GL_COMPUTE_SHADER);
    }

    public void CompileShader()
    {
	glLinkProgram(program);

	if (glGetProgrami(program, GL_LINK_STATUS) == 0)
	{
	    System.out.println(this.getClass().getName() + " " + glGetProgramInfoLog(program, 1024));
	    System.exit(1);
	}

	glValidateProgram(program);

	if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
	{
	    System.err.println(this.getClass().getName() + " " + glGetProgramInfoLog(program, 1024));
	    System.exit(1);
	}
    }

    private void AddProgram(String text, int type)
    {
	int shader = glCreateShader(type);
	
	shaders[counter++] = shader;

	if (shader == 0)
	{
	    System.err.println(this.getClass().getName() + " Shader creation failed");
	    System.exit(1);
	}

	glShaderSource(shader, text);
	glCompileShader(shader);

	if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
	{
	    System.err.println(this.getClass().getName() + " " + glGetShaderInfoLog(shader, 1024));
	    System.exit(1);
	}

	glAttachShader(program, shader);
    }

    public void SetUniformi(String uniformName, int value)
    {
	glUniform1i(uniforms.get(uniformName), value);
    }

    public void SetUniformf(String uniformName, float value)
    {
	glUniform1f(uniforms.get(uniformName), value);
    }

    public void SetUniform(String uniformName, Vector2f value)
    {
	glUniform2f(uniforms.get(uniformName), value.x, value.y);
    }

    public void SetUniform(String uniformName, Vector3f value)
    {
	glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    public void SetUniform(String uniformName, Quaternion value)
    {
	glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void SetUniform(String uniformName, Vector4f value)
    {
	glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void SetUniform(String uniformName, Matrix4f value)
    {
	glUniformMatrix4fv(uniforms.get(uniformName), true, BufferUtil.CreateFlippedBuffer(value));
    }

    public void BindUniformBlock(String uniformBlockName, int uniformBlockBinding)
    {
	glUniformBlockBinding(program, uniforms.get(uniformBlockName), uniformBlockBinding);
    }

    public void BindFragDataLocation(String name, int index)
    {
	glBindFragDataLocation(program, index, name);
    }
    
    public void CleanUp()
    {
	for(int i = 0; i < counter; i++)
	{
	    glDetachShader(program, shaders[i]);
	    glDeleteShader(shaders[i]);
	}
	
	glDeleteProgram(program);	
    }
}
