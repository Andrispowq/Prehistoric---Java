package org.prehistoric.engine;

import org.lwjgl.glfw.GLFW;
import org.prehistoric.engine.config.FrameworkConfig;
import org.prehistoric.engine.core.framework.Input;
import org.prehistoric.engine.core.framework.Time;
import org.prehistoric.engine.engines.Engine;

public class CoreEngine
{
    private boolean running;
    private double frameTime;
    
    private Engine engine;
    
    public CoreEngine() 
    {
	engine = new Engine();
	
	frameTime = 1 / (double) FrameworkConfig.getMax_fps();
    }
    
    public void Start()
    {
	if(running)
	    return;
	
	Run();
    }
    
    private void Stop()
    {
	if(!running)
	    
	    return;
	
	running = false;
	CleanUp();
    }
    
    private void Run()
    {
	running = true;
	
	int frames = 0;
	double frameCounter = 0;
	
	double lastTime = Time.GetTime();
	double unprocessedTime = 0;
	
	while(running)
	{
	    boolean render = false;
	    
	    double startTime = Time.GetTime();
	    double passedTime = startTime - lastTime;
	    lastTime = startTime;
	    
	    unprocessedTime += passedTime;
	    frameCounter += passedTime;
	    
	    while(unprocessedTime > frameTime)
	    {
		render = true;
		
		unprocessedTime -= frameTime;
		
		if(engine.getRenderingEngine().getWindow().ShouldClose() || Input.IsKeyPushed(GLFW.GLFW_KEY_ESCAPE))
		{
		    Stop();
		    break;
		}

		Input();
		
		Update();
		
		if(frameCounter >= 1.0)
		{
		    System.out.println(frames + " FPS");
		    frames = 0;
		    frameCounter = 0;
		}
	    }

	    if(render)
	    {
		Render();
		frames++;
	    } else
	    {
		try
		{
		    Thread.sleep(1);
		} catch (InterruptedException e)
		{
		    e.printStackTrace();
		}
	    }
	}
    }
    
    private void Input()
    {
	engine.Input((float) frameTime);
    }
    
    private void Update()
    {
	engine.Update();
    }
    
    private void Render()
    {
	engine.Render();
    }
    
    private void CleanUp()
    {
	engine.CleanUp();
    }
}
