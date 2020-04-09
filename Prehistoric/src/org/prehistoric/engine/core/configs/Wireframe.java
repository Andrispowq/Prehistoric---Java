package org.prehistoric.engine.core.configs;

import org.lwjgl.opengl.GL11;

public class Wireframe implements RenderConfig
{
    public void Enable()
    {
	GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINES);
    }

    public void Disable()
    {
	GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
    }
}
