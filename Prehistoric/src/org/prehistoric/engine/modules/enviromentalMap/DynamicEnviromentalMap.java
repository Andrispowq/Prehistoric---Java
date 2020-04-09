package org.prehistoric.engine.modules.enviromentalMap;

import org.math.matrix.Matrix4f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.config.FrameworkConfig;
import org.prehistoric.engine.core.buffers.FBO;
import org.prehistoric.engine.core.buffers.MeshVBO;
import org.prehistoric.engine.core.configs.Default;
import org.prehistoric.engine.core.configs.RenderConfig;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.model.texture.TextureUtil;
import org.prehistoric.engine.core.model.texture.TextureUtil.ImageFormat;
import org.prehistoric.engine.core.model.texture.TextureUtil.SamplerFilter;
import org.prehistoric.engine.core.model.texture.TextureUtil.TextureWrapMode;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.GLUtil;
import org.prehistoric.engine.core.util.creators.Fabricator;
import org.prehistoric.engine.engines.rendering.RenderingEngine;
import org.prehistoric.engine.engines.rendering.shader.enviromentalMap.BRDFIntegrateShader;
import org.prehistoric.engine.engines.rendering.shader.enviromentalMap.EnviromentMapShader;
import org.prehistoric.engine.engines.rendering.shader.enviromentalMap.EnviromentShader;
import org.prehistoric.engine.engines.rendering.shader.enviromentalMap.IrradianceShader;
import org.prehistoric.engine.engines.rendering.shader.enviromentalMap.PrefilterShader;

public class DynamicEnviromentalMap
{
    private static Matrix4f projectionMatrix;
    private static Matrix4f[] viewMatrices = new Matrix4f[6];
    private static MeshVBO cube = Fabricator.CreateMesh("res/models", "cube", null, 0);
    private static FBO fbo;

    private static Texture equirectangularMap;
    
    // Converting equirectangular map into an enviroment map
    private static Texture enviromentMap;
    private static EnviromentMapShader enviromentMapShader = EnviromentMapShader.getInstance();
    
    //Converting enviroment map to irradiance map
    private static Texture irradianceMap;
    private static IrradianceShader irradianceShader = IrradianceShader.getInstance();
    
    //Create enviroment map to pre-filtered enviromental map
    private static Texture prefilterMap;
    private static PrefilterShader prefilterShader = PrefilterShader.getInstance();
    
    //Generate brdf integrate map
    private static Texture brdfMap;
    private static BRDFIntegrateShader brdfIntegrateShader = BRDFIntegrateShader.getInstance();
    private static MeshVBO vboQuad = Fabricator.CreateMesh("res/models", "quad", null, 0);
    
    //Rendering the enviroment as a background
    private static EnviromentShader enviromentShader = EnviromentShader.getInstance();
    
    //Rendering in counter clock-wise mode
    private static RenderConfig mode;
    
    public static void InitializeEnviromentalMap()
    {
	// Initializing the textures, mesh, and shaders to render the cube map	
	projectionMatrix = new Matrix4f().PerspectiveProjection(90, 1, 1, .1f, 100);
	
	viewMatrices[0] = new Matrix4f().View(Vector3f.Get( 1, 0, 0), Vector3f.Get(0, -1, 0));
	viewMatrices[1] = new Matrix4f().View(Vector3f.Get(-1, 0, 0), Vector3f.Get(0, -1, 0));
	viewMatrices[2] = new Matrix4f().View(Vector3f.Get(0,  1, 0), Vector3f.Get(0, 0, -1));
	viewMatrices[3] = new Matrix4f().View(Vector3f.Get(0, -1, 0), Vector3f.Get(0, 0,  1));
	viewMatrices[4] = new Matrix4f().View(Vector3f.Get(0, 0, -1), Vector3f.Get(0, -1, 0));
	viewMatrices[5] = new Matrix4f().View(Vector3f.Get(0, 0,  1), Vector3f.Get(0, -1, 0));

	equirectangularMap = new Texture(EngineConfig.getEnviromentalMapLocation());
	
	mode = new Default();
	
	fbo = new FBO();
	
	//Rendering the brdf integrate map
	brdfMap = TextureUtil.Storage2D(EngineConfig.getEnviromentalMapResolution(), EngineConfig.getEnviromentalMapResolution(), ImageFormat.RG16FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge);

	GLUtil.CullBackFace(false);
	
	fbo.Bind();
	fbo.CreateDepthAttachment(EngineConfig.getEnviromentalMapResolution(), EngineConfig.getEnviromentalMapResolution());
	
	fbo.SetViewport(EngineConfig.getEnviromentalMapResolution(), EngineConfig.getEnviromentalMapResolution());
	
	brdfIntegrateShader.Bind();
	
	mode.Enable();
	
	fbo.AddColourTextureAttachment(brdfMap.getID());
	fbo.ClearBuffer(true, true);
	
	vboQuad.Bind();
	vboQuad.Draw();
	vboQuad.UnBind();
	
	mode.Disable();
	
	brdfIntegrateShader.UnBind();
	
	fbo.Unbind();
	fbo.SetViewport(FrameworkConfig.getWidth(), FrameworkConfig.getHeight());
    }

    public static void GenerateEnviromentalMap(RenderingEngine renderingEngine)
    {
	mode.Enable();
	fbo.Bind();
	GLUtil.CullBackFace(false);
	
	// Rendering the cube map
	fbo.CreateDepthAttachment(EngineConfig.getEnviromentalMapResolution(), EngineConfig.getEnviromentalMapResolution());
	fbo.SetViewport(EngineConfig.getEnviromentalMapResolution(), EngineConfig.getEnviromentalMapResolution());
	
	enviromentMap = TextureUtil.Storage3D(EngineConfig.getEnviromentalMapResolution(), EngineConfig.getEnviromentalMapResolution(), ImageFormat.RGBA16FLOAT, SamplerFilter.Bilinear);
	
	/*renderingEngine.getAtmosphere().getScatteringShader().Bind();
	
	for(int i = 0; i < 6; ++i)
	{
	    fbo.AddColourTextureAttachment3D(i, enviromentMap.getID());
	    fbo.ClearBuffer(true, true);
	    
	    renderingEngine.getAtmosphere().getScatteringShader().UpdateUniforms(viewMatrices[i], projectionMatrix);

	    cube.Bind();
	    cube.Draw();
	    cube.UnBind();
	}
	
	renderingEngine.getAtmosphere().getScatteringShader().UnBind();*/
	
	enviromentMapShader.Bind();
	
	for(int i = 0; i < 6; ++i)
	{
	    fbo.AddColourTextureAttachment3D(i, enviromentMap.getID());
	    fbo.ClearBuffer(true, true);
	    
	    enviromentMapShader.UpdateUniforms(projectionMatrix, viewMatrices[i], equirectangularMap);
	    
	    cube.Bind();
	    cube.Draw();
	    cube.UnBind();
	}
	
	enviromentMap.Bind(0);
	enviromentMap.Filter(SamplerFilter.Trilinear);
	
	//Rendering the diffuse irradiance map
	irradianceMap = TextureUtil.Storage3D(EngineConfig.getDiffuseIrradianceMapResolution(), EngineConfig.getDiffuseIrradianceMapResolution(), ImageFormat.RGBA16FLOAT, SamplerFilter.Bilinear);
	
	fbo.CreateDepthAttachment(EngineConfig.getDiffuseIrradianceMapResolution(), EngineConfig.getDiffuseIrradianceMapResolution());
	fbo.SetViewport(EngineConfig.getDiffuseIrradianceMapResolution(), EngineConfig.getDiffuseIrradianceMapResolution());
	
	irradianceShader.Bind();
	
	for(int i = 0; i < 6; ++i)
	{
	    fbo.AddColourTextureAttachment3D(i, irradianceMap.getID());
	    fbo.ClearBuffer(true, true);
	    
	    irradianceShader.UpdateUniforms(projectionMatrix, viewMatrices[i], enviromentMap);

	    cube.Bind();
	    cube.Draw();
	    cube.UnBind();
	}
	
	irradianceShader.UnBind();
	
	//Rendering the pre-filter enviroment map
	prefilterMap = TextureUtil.Storage3D(EngineConfig.getPrefilterMapResolution(), EngineConfig.getPrefilterMapResolution(), ImageFormat.RGBA16FLOAT, SamplerFilter.Trilinear, TextureWrapMode.ClampToEdge);
	
	prefilterShader.Bind();
	
	for(int level = 0; level < EngineConfig.getMaxMipmapLevels(); ++level)
	{
	    int levelWidth = (int) (EngineConfig.getPrefilterMapResolution() * Math.pow(0.5f, level));
	    int levelHeight = (int) (EngineConfig.getPrefilterMapResolution() * Math.pow(0.5f, level));

	    fbo.CreateDepthAttachment(levelWidth, levelHeight);
	    fbo.SetViewport(levelWidth, levelHeight);
	    
	    float roughness = (float) level / (EngineConfig.getMaxMipmapLevels() - 1);
	    
	    for(int i = 0; i < 6; ++i)
	    {
		fbo.AddColourTextureAttachmentMipmap3D(i, prefilterMap.getID(), level);
		fbo.ClearBuffer(true, true);
		
		prefilterShader.UpdateUniforms(projectionMatrix, viewMatrices[i], enviromentMap, roughness);
		
		cube.Bind();
		cube.Draw();
		cube.UnBind();
	    }
	}
	
	prefilterShader.UnBind();
	
	fbo.Unbind();
	fbo.SetViewport(FrameworkConfig.getWidth(), FrameworkConfig.getHeight());
	mode.Disable();
    }

    public static void RenderCube(Camera camera)
    {	
	// Rendering the enviroment
	enviromentShader.Bind();
	GLUtil.LequalDepthFunction(true);
	GLUtil.CullBackFace(false);
	
	enviromentShader.UpdateUniforms(camera.getProjectionMatrix(), camera.getViewMatrix(), enviromentMap);
	    
	cube.Bind(); 
	cube.Draw();
	cube.UnBind();
	
	GLUtil.LequalDepthFunction(false);
	enviromentShader.UnBind();
    }
    
    public static void CleanUp()
    {
	cube.Delete();
	vboQuad.Delete();
	
	enviromentMap.Delete();
	enviromentMapShader.CleanUp();
	
	irradianceMap.Delete();
	irradianceShader.CleanUp();
	
	prefilterMap.Delete();
	prefilterShader.CleanUp();
	
	brdfMap.Delete();
	brdfIntegrateShader.CleanUp();
    }
    
    public static Texture getEnviromentMap()
    {
	return enviromentMap;
    }
    
    public static Texture getIrradianceMap()
    {
	return irradianceMap;
    }
    
    public static Texture getPrefilterMap()
    {
	return prefilterMap;
    }
    
    public static Texture getBrdfMap()
    {
	return brdfMap;
    }
}
