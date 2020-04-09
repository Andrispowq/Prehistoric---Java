package org.prehistoric.engine.engines.rendering;

import static org.lwjgl.opengl.ARBSeamlessCubeMap.GL_TEXTURE_CUBE_MAP_SEAMLESS;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.math.vector.Vector3f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.components.renderer.Renderable;
import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.config.EngineConfig;
import org.prehistoric.engine.core.buffers.VBO;
import org.prehistoric.engine.core.configs.Default;
import org.prehistoric.engine.core.framework.Input;
import org.prehistoric.engine.core.framework.Window;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.core.util.GLUtil;
import org.prehistoric.engine.engines.Engine;
import org.prehistoric.engine.modules.atmosphere.Atmosphere;
import org.prehistoric.engine.modules.enviromentalMap.DynamicEnviromentalMap;
import org.prehistoric.engine.engines.rendering.rendererModules.InstanceRenderer;
import org.prehistoric.engine.engines.rendering.rendererModules.RayTracingRenderer;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public class RenderingEngine
{
    private HashMap<VBO, HashMap<Shader, ArrayList<Renderable>>> models;
    private ArrayList<Light> lights;
    
    private Window window;
    private Camera camera;
    
    private Atmosphere atmosphere;
    
    private RayTracingRenderer rayTracer;
    
    private boolean wireframe;
    
    public RenderingEngine()
    {
	window = new Window();
	camera = new Camera(Vector3f.Get());
	
	atmosphere = new Atmosphere();
	
	models = new HashMap<VBO, HashMap<Shader, ArrayList<Renderable>>>();
	lights = new ArrayList<Light>();
	
	rayTracer = new RayTracingRenderer();
	
	wireframe = false;
    }
    
    public void Init()
    {
	glFrontFace(GL_CW);
	
	if(EngineConfig.isBackface_culling())
	{
	    GLUtil.CullBackFace(true);
	}
	
	glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);
	glEnable(GL_TEXTURE_2D);

	DynamicEnviromentalMap.InitializeEnviromentalMap();
	DynamicEnviromentalMap.GenerateEnviromentalMap(this);
    }
    
    public void Input(Engine engine)
    {
	window.Input();
	
	if(Input.IsKeyPushed(GLFW.GLFW_KEY_E))
	    wireframe = !wireframe;

	atmosphere.PreInput(engine);
    }
    
    public void Update(Engine engine)
    {
	camera.Input(window);
	
	atmosphere.PreUpdate(engine);
    }
    
    private void Prepare()
    {
	Default.Init();
	
	glClearColor(0, 0, 0, 1);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glEnable(GL_DEPTH_TEST);
    }
    
    public void Render(Engine engine)
    {
	Prepare();
	
	if(EngineConfig.isBackface_culling())
	{
	    GLUtil.CullBackFace(true);
	} else
	{
	    GLUtil.CullBackFace(false);
	}
	
	engine.getRootObject().PreRender(engine);
	
	if(EngineConfig.isRayTracingEnable())
	{
	    rayTracer.Render(this);
	} else
	{
	    RenderScene();
	}
	
	window.Render();
	
	models.clear();
	lights.clear();
    }
    
    public void RenderScene()
    {
	DynamicEnviromentalMap.RenderCube(camera);
	
	for(VBO vbo : models.keySet())
	{
	    vbo.Bind();
	    
	    HashMap<Shader, ArrayList<Renderable>> sr = models.get(vbo);
	    
	    for(Shader shader : sr.keySet())
	    {
		shader.Bind();
		
		if(sr.get(shader).size() > 1000)
		{		    
		    InstanceRenderer.Render(vbo, ((Renderer) sr.get(shader).get(0)).getRenderInfo().getInstanceShader(), sr.get(shader), lights);
		} else
		{
		    for(Renderable renderer : sr.get(shader))
		    {
			renderer.Render(this);
		    }
		}
		
		shader.UnBind();
	    }
	    
	    vbo.UnBind();
	}
    }
    
    public void CleanUp()
    {
	DynamicEnviromentalMap.CleanUp();
	
	window.Destroy();
    }
    
    public void RenderAtmosphere()
    {
	((Renderer) atmosphere.GetComponent(Consts.RENDERER_COMPONENT)).Render(this);
    }
    
    public void AddModel(Renderable renderer)
    {
	VBO vbo = Renderer.getVbos().get(renderer.getVboIndex());
	
	Shader shader = renderer.getShader();
	
	HashMap<Shader, ArrayList<Renderable>> shaderRenderers = models.get(vbo);
	
	if(shaderRenderers == null)
	{
	    shaderRenderers = new HashMap<Shader, ArrayList<Renderable>>();
	}
	
	ArrayList<Renderable> renderers = shaderRenderers.get(shader);
	
	if(renderers == null)
	{
	    renderers = new ArrayList<Renderable>();
	}
	
	renderers.add(renderer);
	shaderRenderers.put(shader, renderers);
	
	models.put(vbo, shaderRenderers);
    }
    
    public void AddLight(Light light)
    {
	lights.add(light);
    }
    
    public HashMap<VBO, HashMap<Shader, ArrayList<Renderable>>> getModels()
    {
	return models;
    }
    
    public Atmosphere getAtmosphere()
    {
	return atmosphere;
    }
    
    public ArrayList<Light> getLights()
    {
	return lights;
    }
    
    public Camera getCamera()
    {
	return camera;
    }
    
    public Window getWindow()
    {
	return window;
    }
    
    public boolean isWireframe()
    {
	return wireframe;
    }
}
