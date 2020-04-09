package org.prehistoric.engine.core.loaders.objLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.core.model.Mesh;
import org.prehistoric.engine.core.model.Model;
import org.prehistoric.engine.core.model.material.Material;
import org.prehistoric.engine.core.model.texture.Texture;
import org.prehistoric.engine.core.util.Consts;
import org.prehistoric.engine.core.util.Util;

public class OBJLoader
{
    public static Model loadOBJ(String path, String objFile, String mtlFile)
    {
	long time = System.currentTimeMillis();

	BufferedReader meshReader = null;
	BufferedReader mtlReader = null;
	String currentMtl = "";

	// load .mtl
	HashMap<String, Material> materials = new HashMap<String, Material>();

	if (mtlFile != null)
	{
	    try
	    {
		mtlReader = new BufferedReader(new FileReader(path + "/" + mtlFile + ".mtl"));
		String line;

		while ((line = mtlReader.readLine()) != null)
		{

		    String[] tokens = line.split(" ");
		    tokens = Util.RemoveEmptyStrings(tokens);

		    if (tokens.length == 0)
			continue;
		    if (tokens[0].equals("newmtl"))
		    {
			Material material = new Material();
			material.setName(tokens[1]);
			materials.put(tokens[1], material);
			currentMtl = tokens[1];
		    }
		    if (tokens[0].equals("map_Kd"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.ALBEDO_MAP,
				    new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("map_bump"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.NORMAL_MAP,
				    new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("map_Kr"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.ROUGHNESS_MAP,
				    new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("map_Km"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.METALLIC_MAP,
				    new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("map_Ko"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.OCCLUSION_MAP,
				    new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("map_Ke"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.EMISSION_MAP,
				    new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("map_Ka"))
		    {
			if (tokens.length > 1)
			{
			    materials.get(currentMtl).AddTexture(Consts.ALPHA_MAP, new Texture(path + "/" + tokens[1]));
			}
		    }
		    if (tokens[0].equals("Kd"))
		    {
			Vector3f color = new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]),
				Float.valueOf(tokens[3]));
			materials.get(currentMtl).AddVector3f(Consts.COLOUR, color);
		    }
		    if (tokens[0].equals("Km"))
		    {
			float metallic = Float.valueOf(tokens[1]);
			materials.get(currentMtl).AddFloat(Consts.METALLIC, metallic);
		    }
		    if (tokens[0].equals("Kr"))
		    {
			float roughness = Float.valueOf(tokens[1]);
			materials.get(currentMtl).AddFloat(Consts.ROUGHNESS, roughness);
		    }
		    if (tokens[0].equals("Ko"))
		    {
			float occlusion = Float.valueOf(tokens[1]);
			materials.get(currentMtl).AddFloat(Consts.OCCLUSION, occlusion);
		    }
		    if (tokens[0].equals("Ke"))
		    {
			Vector3f emission = new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]),
				Float.valueOf(tokens[3]));
			materials.get(currentMtl).AddVector3f(Consts.EMISSION, emission);
		    }
		    if (tokens[0].equals("Ka"))
		    {
			Vector3f alpha = new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]),
				Float.valueOf(tokens[3]));
			materials.get(currentMtl).AddVector3f(Consts.ALPHA, alpha);
		    }
		    if (tokens[0].equals("illum"))
		    {
			if (tokens.length > 1)
			    materials.get(currentMtl).AddFloat("illumiation", Float.valueOf(tokens[1]));
		    }
		    if (tokens[0].equals("Ns"))
		    {
			if (tokens.length > 1)
			    materials.get(currentMtl).AddFloat("shininess", Float.valueOf(tokens[1]));
		    }
		}

		mtlReader.close();
	    } catch (Exception e)
	    {
		e.printStackTrace();
		System.exit(1);
	    }
	}

	try
	{
	    meshReader = new BufferedReader(new FileReader(path + "/" + objFile + ".obj"));
	} catch (FileNotFoundException e1)
	{
	    e1.printStackTrace();
	}

	String line;

	List<Vertex> vertices = new ArrayList<Vertex>();
	List<Vector2f> textures = new ArrayList<Vector2f>();
	List<Vector3f> normals = new ArrayList<Vector3f>();
	List<Integer> indices = new ArrayList<Integer>();

	try
	{
	    while (true)
	    {
		line = meshReader.readLine();
		String[] tokens = line.split(" ");
		Util.RemoveEmptyStrings(tokens);

		if (tokens[0].equals("v"))
		{
		    Vector3f vertex = new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]),
			    Float.valueOf(tokens[3]));
		    Vertex newVertex = new Vertex(vertices.size(), vertex);
		    vertices.add(newVertex);

		} else if (tokens[0].equals("vt"))
		{
		    Vector2f texture = new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]));
		    textures.add(texture);
		} else if (tokens[0].equals("vn"))
		{
		    Vector3f normal = new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]),
			    Float.valueOf(tokens[3]));
		    normals.add(normal);
		} else if (tokens[0].equals("f"))
		{
		    break;
		}
	    }

	    while (line != null && line.startsWith("f "))
	    {
		String[] currentLine = line.split(" ");

		String[] vertex0 = currentLine[1].split("/");
		String[] vertex1 = currentLine[2].split("/");
		String[] vertex2 = currentLine[3].split("/");

		processVertex(vertex0, vertices, indices);
		processVertex(vertex1, vertices, indices);
		processVertex(vertex2, vertices, indices);

		line = meshReader.readLine();
	    }

	    meshReader.close();
	} catch (IOException e)
	{
	    System.err.println("Error reading the file");
	}

	removeUnusedVertices(vertices);

	float[] verticesArray = new float[vertices.size() * 3];
	float[] texturesArray = new float[vertices.size() * 2];
	float[] normalsArray = new float[vertices.size() * 3];

	convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray, normalsArray);

	int[] indicesArray = convertIndicesListToArray(indices);

	ArrayList<org.prehistoric.engine.core.model.Vertex> meshVertices = new ArrayList<org.prehistoric.engine.core.model.Vertex>();

	for (int i = 0; i < vertices.size(); i++)
	{
	    meshVertices.add(new org.prehistoric.engine.core.model.Vertex(
		    Vector3f.Get(verticesArray[i * 3 + 0], verticesArray[i * 3 + 1], verticesArray[i * 3 + 2]),
		    Vector2f.Get(texturesArray[i * 2 + 0], texturesArray[i * 2 + 1]),
		    Vector3f.Get(normalsArray[i * 3 + 0], normalsArray[i * 3 + 1], normalsArray[i * 3 + 2])));
	}
	
	Mesh mesh = new Mesh();

	mesh.setVertices(meshVertices);
	mesh.setIndices(indicesArray);

	long delta = System.currentTimeMillis() - time;

	System.out.println("Time taken to load OBJ file '" + objFile + "': " + delta + " ms");

	return new Model(mesh, materials.get(currentMtl));
    }

    private static void processVertex(String[] tokens, List<Vertex> vertices, List<Integer> indices)
    {
	// Vertex will always have to be specified
	int index = Integer.parseInt(tokens[0]) - 1;
	Vertex currentVertex = vertices.get(index);

	int textureIndex = -1;
	int normalIndex = -1;

	if (tokens.length > 1)
	{
	    if (!tokens[1].equals(""))
	    {
		textureIndex = Integer.parseInt(tokens[1]) - 1;
	    }
	    
	    if (!tokens[2].equals(""))
	    {
		normalIndex = Integer.parseInt(tokens[2]) - 1;
	    }
	}

	if (!currentVertex.isSet())
	{
	    currentVertex.setTextureIndex(textureIndex);
	    currentVertex.setNormalIndex(normalIndex);

	    indices.add(index);
	} else
	{
	    dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices, vertices);
	}
    }

    private static int[] convertIndicesListToArray(List<Integer> indices)
    {
	int[] indicesArray = new int[indices.size()];

	for (int i = 0; i < indicesArray.length; i++)
	{
	    indicesArray[i] = indices.get(i);
	}

	return indicesArray;
    }

    private static void convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures, List<Vector3f> normals,
	    float[] verticesArray, float[] texturesArray, float[] normalsArray)
    {
	for (int i = 0; i < vertices.size(); i++)
	{
	    Vertex currentVertex = vertices.get(i);

	    Vector3f position = currentVertex.getPosition();
	    Vector2f textureCoord = textures.size() > 0 ? textures.get(currentVertex.getTextureIndex())
		    : Vector2f.Get();
	    Vector3f normalVector = normals.size() > 0 ? normals.get(currentVertex.getNormalIndex()) : Vector3f.Get();

	    verticesArray[i * 3] = position.x;
	    verticesArray[i * 3 + 1] = position.y;
	    verticesArray[i * 3 + 2] = position.z;

	    texturesArray[i * 2] = textureCoord.x;
	    texturesArray[i * 2 + 1] = textureCoord.y;

	    normalsArray[i * 3] = normalVector.x;
	    normalsArray[i * 3 + 1] = normalVector.y;
	    normalsArray[i * 3 + 2] = normalVector.z;
	}
    }

    private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex, int newNormalIndex,
	    List<Integer> indices, List<Vertex> vertices)
    {
	if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex))
	{
	    indices.add(previousVertex.getIndex());
	} else
	{
	    Vertex anotherVertex = previousVertex.getDuplicateVertex();
	    if (anotherVertex != null)
	    {
		dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex, indices, vertices);
	    } else
	    {
		Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());

		duplicateVertex.setTextureIndex(newTextureIndex);
		duplicateVertex.setNormalIndex(newNormalIndex);

		previousVertex.setDuplicateVertex(duplicateVertex);
		vertices.add(duplicateVertex);
		indices.add(duplicateVertex.getIndex());
	    }

	}
    }

    private static void removeUnusedVertices(List<Vertex> vertices)
    {
	for (Vertex vertex : vertices)
	{
	    if (!vertex.isSet())
	    {
		vertex.setTextureIndex(0);
		vertex.setNormalIndex(0);
	    }
	}
    }

}