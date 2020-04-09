package org.prehistoric.engine.engines.rendering.rendererModules;

import java.util.ArrayList;

import org.math.matrix.Matrix4f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.components.renderer.Renderable;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.buffers.InstanceMeshVBO;
import org.prehistoric.engine.core.buffers.VBO;
import org.prehistoric.engine.core.util.GLBufferUtil;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class InstanceRenderer
{
    private static final int INSTANCED_DATA_LENGTH = 16;
    private static int pointer = 0;
    
    public static void Render(VBO vbo, Shader shader, ArrayList<Renderable> renderers, ArrayList<Light> lights)
    {
	int amount = renderers.size();
	pointer = 0;
	
	InstanceMeshVBO instanceVBO = new InstanceMeshVBO();
	
	instanceVBO.Allocate(vbo.getMesh());
	instanceVBO.setNumberOfInstances(amount);
	
	int vboID = GLBufferUtil.CreateEmptyVBO(INSTANCED_DATA_LENGTH * EngineConfig.getMax_instances());
	
	instanceVBO.AddInstancedAttribute(vboID, 3, 4, INSTANCED_DATA_LENGTH, 0);
	instanceVBO.AddInstancedAttribute(vboID, 4, 4, INSTANCED_DATA_LENGTH, 4);
	instanceVBO.AddInstancedAttribute(vboID, 5, 4, INSTANCED_DATA_LENGTH, 8);
	instanceVBO.AddInstancedAttribute(vboID, 6, 4, INSTANCED_DATA_LENGTH, 12);
	
	float[] vboData = new float[INSTANCED_DATA_LENGTH * amount];
	
	for(int i = 0; i < renderers.size(); i++)
	{
	    Renderable renderer = renderers.get(i);
	    
	    StoreMatrixData(renderer.getParent().getWorldTransform().getTransformationMatrix(), vboData);
	}

	GLBufferUtil.UpdateVBO(vboID, vboData);
	
	shader.Bind();
	shader.UpdateUniforms(renderers.get(0).getParent(), lights);
	
	instanceVBO.Bind();
	instanceVBO.Draw();
	instanceVBO.UnBind();
	
	shader.UnBind();
	
	GLBufferUtil.DeleteVBO(vboID);
	instanceVBO.Delete();
    }
    
    private static void StoreMatrixData(Matrix4f matrix, float[] vboData)
    {
	for(int x = 0; x < 4; x++)
	{
	    for(int y = 0; y < 4; y++)
	    {
		vboData[pointer++] = matrix.Get(y, x);
	    }
	}
    }
}
