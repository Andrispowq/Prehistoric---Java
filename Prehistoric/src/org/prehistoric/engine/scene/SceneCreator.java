package org.prehistoric.engine.scene;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.components.light.Light;
import org.prehistoric.engine.components.renderer.RenderInfo;
import org.prehistoric.engine.components.renderer.Renderer;
import org.prehistoric.engine.core.GameObject;
import org.prehistoric.engine.core.buffers.MeshVBO;
import org.prehistoric.engine.core.configs.Default;
import org.prehistoric.engine.core.configs.Wireframe;
import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.movement.Camera;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.core.util.creators.Fabricator;
import org.prehistoric.engine.engines.rendering.shader.general.GeneralShader;
import org.prehistoric.engine.engines.rendering.shader.general.InstanceShader;
import org.prehistoric.engine.engines.rendering.shader.tessellation.TessellationShader;

public class SceneCreator
{
    private static MeshVBO sphere = Fabricator.CreateMesh("res/models", "sphere", null, 0);
    private static MeshVBO plane = Fabricator.CreateMesh("res/models", "quad", null, 3);
    private static Material woodenFloor = Fabricator.CreateMaterial("res/textures/pbr/oakFloor/oakFloor", "png", true, true, true, true, true, true, false, 1, -1, -1f, 0.01f);
    private static Material light = Fabricator.CreateMaterial(Vector3f.Get(1), 1f, 0.3f, 1f, Vector3f.Get(.3f, .3f, 0.1f));

    private static Vector2f numSpheres = Vector2f.Get(7);
    private static Vector2f interleaving = Vector2f.Get(4);
    private static Vector2f start = numSpheres.Mul(interleaving.Mul(-1 / 2f));

    public static void CreateScene(GameObject object, Camera camera)
    {
	object.AddChild(new GameObject()
		.AddComponent(Consts.RENDERER_COMPONENT, 
			new Renderer(light, sphere, new Default(), GeneralShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), GeneralShader.GetInstance())))
		.Move(40, 40, -40)
		.Scale(2, 2, 2));
	
	/*Terrain terrain = new Terrain(camera);
	terrain.UpdateQuadtree(camera);*/
	
	//object.AddChild(terrain);
	
	for(int dx = 0; dx <= -start.x * 2; dx += interleaving.x)
	{
	    for(int dy = 0; dy <= -start.y * 2; dy += interleaving.y)
	    {
		float z = 0; 
		
		AddSphere(dx, dy, z, object);
	    }
	}

	AddFloorPiece(-2, -2, object);
	AddFloorPiece( 2, -2, object);
	AddFloorPiece(-2,  2, object);
	AddFloorPiece( 2,  2, object);
	AddFloorPiece( 0,  0, object);

	object.AddChild(new GameObject()
		.AddComponent(Consts.LIGHT_COMPONENT,
			new Light(Vector3f.Get(1, 1, 1), Vector3f.Get(1000)))
		.AddComponent(Consts.RENDERER_COMPONENT, 
			new Renderer(light, sphere, new Default(), GeneralShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), GeneralShader.GetInstance())))
		.Move(40, 40, -40)
		.Scale(2, 2, 2));

	object.AddChild(new GameObject()
		.AddComponent(Consts.LIGHT_COMPONENT,
			new Light(Vector3f.Get(1, 1, 1), Vector3f.Get(1000)))
		.AddComponent(Consts.RENDERER_COMPONENT, 
			new Renderer(light, sphere, new Default(), GeneralShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), GeneralShader.GetInstance())))
		.Move(40, -40, -40)
		.Scale(2, 2, 2));

	object.AddChild(new GameObject()
		.AddComponent(Consts.LIGHT_COMPONENT,
			new Light(Vector3f.Get(1, 1, 1), Vector3f.Get(1000)))
		.AddComponent(Consts.RENDERER_COMPONENT, 
			new Renderer(light, sphere, new Default(), GeneralShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), GeneralShader.GetInstance())))
		.Move(-40, 40, -40)
		.Scale(2, 2, 2));

	object.AddChild(new GameObject()
		.AddComponent(Consts.LIGHT_COMPONENT,
			new Light(Vector3f.Get(1, 1, 1), Vector3f.Get(1000)))
		.AddComponent(Consts.RENDERER_COMPONENT, 
			new Renderer(light, sphere, new Default(), GeneralShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), GeneralShader.GetInstance())))
		.Move(-40, -40, -40)
		.Scale(2, 2, 2));
    }

    private static void AddSphere(float dx, float dy, float z, GameObject object)
    {
	Material gold = new Material();
	
	gold.AddVector3f(Consts.COLOUR, Vector3f.Get(0.955f, 0.637f, 0.538f));
	
	gold.AddFloat(Consts.METALLIC, dy / (-start.x * interleaving.x / 2));
	gold.AddFloat(Consts.ROUGHNESS, Consts.clampf(dx / (-start.y * interleaving.y / 2), 0.07f, 1));
	
	gold.AddFloat(Consts.OCCLUSION, 1f);
	gold.AddVector3f(Consts.EMISSION, Vector3f.Get());
	//gold.AddTexture(Consts.NORMAL_MAP, new Texture("res/textures/pbr/oakFloor/oakFloor_NRM.png"));
	
	object.AddChild(new GameObject()
		.AddComponent(
			Consts.RENDERER_COMPONENT, new Renderer(gold, sphere, new Default(), GeneralShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), GeneralShader.GetInstance())))
		//.AddComponent(
		//	Consts.PHYSICS_OBJECT_COMPONENT, new PhysicsObject(false, 1000))
		.Move(start.x + dx, start.y + dy, z)
		.Scale(1, 1, 1));
    }
    
    private static void AddFloorPiece(float x, float z, GameObject object)
    {
	object.AddChild(new GameObject()
		.AddComponent(Consts.RENDERER_COMPONENT, 
			new Renderer(woodenFloor, plane, new Default(), TessellationShader.GetInstance(), new RenderInfo(new Wireframe(), InstanceShader.GetInstance(), TessellationShader.GetInstance())))
		.Move(x, -25, z)
		.Rotate(90, 0, 0)
		.Scale(1, 1, 1));
    }
}
