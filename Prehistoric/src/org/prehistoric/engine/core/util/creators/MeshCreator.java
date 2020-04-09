package org.prehistoric.engine.core.util.creators;

import java.util.ArrayList;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.core.model.Mesh;
import org.prehistoric.engine.core.model.Vertex;

public class MeshCreator
{
    public static Mesh Quad(boolean generateTexCoords, boolean generateNormals)
    {
	Mesh mesh = new Mesh();

	ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	vertices.add(new Vertex(Vector3f.Get(-1f, -1f, 0)));
	vertices.add(new Vertex(Vector3f.Get(-1f, 1f, 0)));
	vertices.add(new Vertex(Vector3f.Get(1f, 1f, 0)));
	vertices.add(new Vertex(Vector3f.Get(1f, -1f, 0)));

	/*
	 * (-1;  1) ( 1;  1)
	 *
	 *
	 * (-1; -1) ( 1; -1)
	 */

	if (generateTexCoords)
	{
	    vertices.get(0).setTextureCoord(Vector2f.Get(0, 0));
	    vertices.get(1).setTextureCoord(Vector2f.Get(0, 1));
	    vertices.get(2).setTextureCoord(Vector2f.Get(1, 1));
	    vertices.get(3).setTextureCoord(Vector2f.Get(1, 0));
	}

	if (generateNormals)
	{
	    vertices.get(0).setNormal(Vector3f.Get(1, 0, 0));
	    vertices.get(1).setNormal(Vector3f.Get(1, 0, 0));
	    vertices.get(2).setNormal(Vector3f.Get(1, 0, 0));
	    vertices.get(3).setNormal(Vector3f.Get(1, 0, 0));
	}

	int[] indices =
	{ 0, 1, 2, 2, 3, 0 };

	mesh.setVertices(vertices);
	mesh.setIndices(indices);

	return mesh;
    }

    public static Mesh Cube(Vector3f scale, boolean generateTexCoords, boolean generateNormals)
    {
	Mesh mesh = new Mesh();

	ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	vertices.add(new Vertex(Vector3f.Get(-1, -1, -1).Mul(scale)));
	vertices.add(new Vertex(Vector3f.Get(1, -1, -1).Mul(scale)));
	vertices.add(new Vertex(Vector3f.Get(1, 1, -1).Mul(scale)));
	vertices.add(new Vertex(Vector3f.Get(1, -1, -1).Mul(scale)));

	vertices.add(new Vertex(Vector3f.Get(-1, 1, 1).Mul(scale)));
	vertices.add(new Vertex(Vector3f.Get(1, 1, 1).Mul(scale)));
	vertices.add(new Vertex(Vector3f.Get(1, -1, 1).Mul(scale)));
	vertices.add(new Vertex(Vector3f.Get(-1, -1, 1).Mul(scale)));

	int[] indices = 
	    { 
		0, 2, 1, 
		0, 3, 2, 
		2, 3, 4, 
		2, 4, 5, 
		1, 2, 5, 
		1, 5, 6,
		0, 7, 4,
		0, 4, 3,
		5, 4, 7,
		5, 7, 6,
		0, 6, 7,
		0, 1, 6
	    };
	
	mesh.setVertices(vertices);
	mesh.setIndices(indices);

	return mesh;
    }
}
