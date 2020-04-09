package org.prehistoric.engine.components.renderer;

import java.util.ArrayList;

import org.prehistoric.engine.components.Component;
import org.prehistoric.engine.core.buffers.VBO;
import org.prehistoric.engine.core.configs.RenderConfig;
import org.prehistoric.engine.engines.rendering.RenderingEngine;
import org.prehistoric.engine.engines.rendering.shader.Shader;

public abstract class Renderable extends Component
{
    protected static ArrayList<VBO> vbos = new ArrayList<VBO>();
    protected int vboIndex;
    
    protected RenderConfig config;
    protected Shader shader;
    
    public abstract void Render(RenderingEngine renderingEngine);

    public static ArrayList<VBO> getVbos()
    {
        return vbos;
    }

    public int getVboIndex()
    {
        return vboIndex;
    }

    public RenderConfig getConfig()
    {
        return config;
    }

    public Shader getShader()
    {
        return shader;
    }
}
