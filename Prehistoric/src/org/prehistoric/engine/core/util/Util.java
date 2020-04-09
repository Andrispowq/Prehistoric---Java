package org.prehistoric.engine.core.util;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.math.vector.Quaternion;
import org.math.vector.Vector2f;
import org.math.vector.Vector3f;
import org.prehistoric.engine.core.loaders.obj.Face;
import org.prehistoric.engine.core.loaders.obj.SmoothingGroup;
import org.prehistoric.engine.core.model.Mesh;
import org.prehistoric.engine.core.model.Vertex;

public class Util
{
    public static String[] RemoveEmptyStrings(String[] data)
    {
	ArrayList<String> result = new ArrayList<String>();

	for (int i = 0; i < data.length; i++)
	    if (!data[i].equals(""))
		result.add(data[i]);

	String[] res = new String[result.size()];
	result.toArray(res);

	return res;
    }

    public static int[] ToIntArray(Integer[] data)
    {
	int[] result = new int[data.length];

	for (int i = 0; i < data.length; i++)
	    result[i] = data[i].intValue();

	return result;
    }

    public static Vertex[] ToVertexArray(FloatBuffer data)
    {
	Vertex[] vertices = new Vertex[data.limit() / Vertex.FLOATS];

	for (int i = 0; i < vertices.length; i++)
	{
	    vertices[i] = new Vertex();
	    vertices[i].setPosition(new Vector3f(data.get(), data.get(), data.get()));
	    vertices[i].setTextureCoord(new Vector2f(data.get(), data.get()));
	    vertices[i].setNormal(new Vector3f(data.get(), data.get(), data.get()));
	}

	return vertices;
    }

    public static Vertex[] ToVertexArray(ArrayList<Vertex> data)
    {
	Vertex[] vertices = new Vertex[data.size()];

	for (int i = 0; i < vertices.length; i++)
	{
	    vertices[i] = new Vertex();
	    vertices[i].setPosition(data.get(i).getPosition());
	    vertices[i].setTextureCoord(data.get(i).getTextureCoord());
	    vertices[i].setNormal(data.get(i).getNormal());
	}

	return vertices;
    }

    public static void GenerateNormalsCW(Vertex[] vertices, int[] indices)
    {
	for (int i = 0; i < indices.length; i += 3)
	{
	    Vector3f v0 = vertices[indices[i]].getPosition();
	    Vector3f v1 = vertices[indices[i + 1]].getPosition();
	    Vector3f v2 = vertices[indices[i + 2]].getPosition();

	    Vector3f normal = v1.Sub(v0).Cross(v2.Sub(v0)).Normalize();

	    vertices[indices[i]].setNormal(vertices[indices[i]].getNormal().Add(normal));
	    vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().Add(normal));
	    vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().Add(normal));
	}

	for (int i = 0; i < vertices.length; ++i)
	{
	    vertices[i].setNormal(vertices[i].getNormal().Normalize());
	}
    }

    public static void GenerateNormalsCCW(Vertex[] vertices, int[] indices)
    {
	for (int i = 0; i < indices.length; i += 3)
	{
	    Vector3f v0 = vertices[indices[i]].getPosition();
	    Vector3f v1 = vertices[indices[i + 1]].getPosition();
	    Vector3f v2 = vertices[indices[i + 2]].getPosition();

	    Vector3f normal = v2.Sub(v0).Cross(v1.Sub(v0)).Normalize();

	    vertices[indices[i]].setNormal(vertices[indices[i]].getNormal().Add(normal));
	    vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().Add(normal));
	    vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().Add(normal));
	}

	for (int i = 0; i < vertices.length; ++i)
	{
	    vertices[i].setNormal(vertices[i].getNormal().Normalize());
	}
    }

    public static void GenerateNormalsCW(ArrayList<Vertex> vertices, ArrayList<Integer> indices)
    {
	for (int i = 0; i < indices.size(); i += 3)
	{
	    Vector3f v0 = vertices.get(indices.get(i)).getPosition();
	    Vector3f v1 = vertices.get(indices.get(i + 1)).getPosition();
	    Vector3f v2 = vertices.get(indices.get(i + 2)).getPosition();

	    Vector3f normal = v1.Sub(v0).Cross(v2.Sub(v0)).Normalize();

	    vertices.get(indices.get(i)).setNormal(vertices.get(indices.get(i)).getNormal().Add(normal));
	    vertices.get(indices.get(i + 1)).setNormal(vertices.get(indices.get(i + 1)).getNormal().Add(normal));
	    vertices.get(indices.get(i + 2)).setNormal(vertices.get(indices.get(i + 2)).getNormal().Add(normal));
	}

	for (int i = 0; i < vertices.size(); ++i)
	{
	    vertices.get(i).setNormal(vertices.get(i).getNormal().Normalize());
	}
    }

    public static void GenerateNormalsCCW(ArrayList<Vertex> vertices, ArrayList<Integer> indices)
    {
	for (int i = 0; i < indices.size(); i += 3)
	{
	    Vector3f v0 = vertices.get(indices.get(i)).getPosition();
	    Vector3f v1 = vertices.get(indices.get(i)).getPosition();
	    Vector3f v2 = vertices.get(indices.get(i)).getPosition();

	    Vector3f normal = v2.Sub(v0).Cross(v1.Sub(v0)).Normalize();

	    vertices.get(indices.get(i)).setNormal(vertices.get(indices.get(i)).getNormal().Add(normal));
	    vertices.get(indices.get(i + 1)).setNormal(vertices.get(indices.get(i + 1)).getNormal().Add(normal));
	    vertices.get(indices.get(i + 2)).setNormal(vertices.get(indices.get(i + 2)).getNormal().Add(normal));
	}

	for (int i = 0; i < vertices.size(); ++i)
	{
	    vertices.get(i).setNormal(vertices.get(i).getNormal().Normalize());
	}
    }

    public static void GenerateTangentsBitangents(Mesh mesh)
    {
	for (int i = 0; i < mesh.getIndices().length; i += 3)
	{
	    Vector3f v0 = mesh.getVertices().get(mesh.getIndices()[i]).getPosition();
	    Vector3f v1 = mesh.getVertices().get(mesh.getIndices()[i + 1]).getPosition();
	    Vector3f v2 = mesh.getVertices().get(mesh.getIndices()[i + 2]).getPosition();

	    Vector2f uv0 = mesh.getVertices().get(mesh.getIndices()[i]).getTextureCoord();
	    Vector2f uv1 = mesh.getVertices().get(mesh.getIndices()[i + 1]).getTextureCoord();
	    Vector2f uv2 = mesh.getVertices().get(mesh.getIndices()[i + 2]).getTextureCoord();

	    Vector3f e1 = v1.Sub(v0);
	    Vector3f e2 = v2.Sub(v0);

	    Vector2f deltaUV1 = uv1.Sub(uv0);
	    Vector2f deltaUV2 = uv2.Sub(uv0);

	    float r = (1.0f / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x));

	    Vector3f tangent = new Vector3f();
	    tangent.x = r * deltaUV2.y * e1.x - deltaUV1.y * e2.x;
	    tangent.y = r * deltaUV2.y * e1.y - deltaUV1.y * e2.y;
	    tangent.z = r * deltaUV2.y * e1.z - deltaUV1.y * e2.z;
	    Vector3f bitangent = new Vector3f();
	    Vector3f normal = mesh.getVertices().get(mesh.getIndices()[i]).getNormal()
		    .Add(mesh.getVertices().get(mesh.getIndices()[i + 1]).getNormal())
		    .Add(mesh.getVertices().get(mesh.getIndices()[i + 2]).getNormal());
	    normal = normal.Normalize();

	    bitangent = tangent.Cross(normal);

	    tangent = tangent.Normalize();
	    bitangent = bitangent.Normalize();

	    if (mesh.getVertices().get(mesh.getIndices()[i]).getTangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i]).setTangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i]).getBitangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i]).setBitangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 1]).getTangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 1]).setTangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 1]).getBitangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 1]).setBitangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 2]).getTangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 2]).setTangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 2]).getBitangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 2]).setBitangent(new Vector3f(0, 0, 0));

	    mesh.getVertices().get(mesh.getIndices()[i])
		    .setTangent(mesh.getVertices().get(mesh.getIndices()[i]).getTangent().Add(tangent));
	    mesh.getVertices().get(mesh.getIndices()[i])
		    .setBitangent(mesh.getVertices().get(mesh.getIndices()[i]).getBitangent().Add(bitangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 1])
		    .setTangent(mesh.getVertices().get(mesh.getIndices()[i + 1]).getTangent().Add(tangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 1])
		    .setBitangent(mesh.getVertices().get(mesh.getIndices()[i + 1]).getBitangent().Add(bitangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 2])
		    .setTangent(mesh.getVertices().get(mesh.getIndices()[i + 2]).getTangent().Add(tangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 2])
		    .setBitangent(mesh.getVertices().get(mesh.getIndices()[i + 2]).getBitangent().Add(bitangent));
	}

	for (Vertex vertex : mesh.getVertices())
	{
	    vertex.setTangent(vertex.getTangent().Normalize());
	    vertex.setBitangent(vertex.getBitangent().Normalize());
	}
    }
    
    public static void GenerateNormalsCW(SmoothingGroup smoothingGroup)
    {
	for (Face face : smoothingGroup.getFaces())
	{
	    Vector3f v0 = smoothingGroup.getVertices().get(face.getIndices()[0]).getPosition();
	    Vector3f v1 = smoothingGroup.getVertices().get(face.getIndices()[1]).getPosition();
	    Vector3f v2 = smoothingGroup.getVertices().get(face.getIndices()[2]).getPosition();

	    Vector3f normal = v1.Sub(v0).Cross(v2.Sub(v0)).Normalize();

	    smoothingGroup.getVertices().get(face.getIndices()[0])
		    .setNormal(smoothingGroup.getVertices().get(face.getIndices()[0]).getNormal().Add(normal));
	    smoothingGroup.getVertices().get(face.getIndices()[1])
		    .setNormal(smoothingGroup.getVertices().get(face.getIndices()[1]).getNormal().Add(normal));
	    smoothingGroup.getVertices().get(face.getIndices()[2])
		    .setNormal(smoothingGroup.getVertices().get(face.getIndices()[2]).getNormal().Add(normal));
	}

	for (Vertex vertex : smoothingGroup.getVertices())
	{
	    vertex.setNormal(vertex.getNormal().Normalize());
	}
    }

    public static void GenerateNormalsCCW(SmoothingGroup smoothingGroup)
    {
	for (Face face : smoothingGroup.getFaces())
	{
	    Vector3f v0 = smoothingGroup.getVertices().get(face.getIndices()[0]).getPosition();
	    Vector3f v1 = smoothingGroup.getVertices().get(face.getIndices()[1]).getPosition();
	    Vector3f v2 = smoothingGroup.getVertices().get(face.getIndices()[2]).getPosition();

	    Vector3f normal = v2.Sub(v0).Cross(v1.Sub(v0)).Normalize();

	    smoothingGroup.getVertices().get(face.getIndices()[0])
		    .setNormal(smoothingGroup.getVertices().get(face.getIndices()[0]).getNormal().Add(normal));
	    smoothingGroup.getVertices().get(face.getIndices()[1])
		    .setNormal(smoothingGroup.getVertices().get(face.getIndices()[1]).getNormal().Add(normal));
	    smoothingGroup.getVertices().get(face.getIndices()[2])
		    .setNormal(smoothingGroup.getVertices().get(face.getIndices()[2]).getNormal().Add(normal));
	}

	for (Vertex vertex : smoothingGroup.getVertices())
	{
	    vertex.setNormal(vertex.getNormal().Normalize());
	}
    }

    public static void generateTangentsBitangents(Mesh mesh)
    {
	for (int i = 0; i < mesh.getIndices().length; i += 3)
	{
	    Vector3f v0 = mesh.getVertices().get(mesh.getIndices()[i]).getPosition();
	    Vector3f v1 = mesh.getVertices().get(mesh.getIndices()[i+1]).getPosition();
	    Vector3f v2 = mesh.getVertices().get(mesh.getIndices()[i+2]).getPosition();

	    Vector2f uv0 = mesh.getVertices().get(mesh.getIndices()[i]).getTextureCoord();
	    Vector2f uv1 = mesh.getVertices().get(mesh.getIndices()[i]).getTextureCoord();
	    Vector2f uv2 = mesh.getVertices().get(mesh.getIndices()[i]).getTextureCoord();

	    Vector3f e1 = v1.Sub(v0);
	    Vector3f e2 = v2.Sub(v0);

	    Vector2f deltaUV1 = uv1.Sub(uv0);
	    Vector2f deltaUV2 = uv2.Sub(uv0);

	    float r = (1.0f / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x));

	    Vector3f tangent = new Vector3f();
	    
	    tangent.x = r * deltaUV2.y * e1.x - deltaUV1.y * e2.x;
	    tangent.y = r * deltaUV2.y * e1.y - deltaUV1.y * e2.y;
	    tangent.z = r * deltaUV2.y * e1.z - deltaUV1.y * e2.z;
	    
	    Vector3f bitangent = new Vector3f();
	    Vector3f normal = mesh.getVertices().get(mesh.getIndices()[i]).getNormal()
		    .Add(mesh.getVertices().get(mesh.getIndices()[i + 1]).getNormal())
		    .Add(mesh.getVertices().get(mesh.getIndices()[i + 2]).getNormal());
	    
	    normal = normal.Normalize();

	    bitangent = tangent.Cross(normal);

	    tangent = tangent.Normalize();
	    bitangent = bitangent.Normalize();

	    if (mesh.getVertices().get(mesh.getIndices()[i]).getTangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i]).setTangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i]).getBitangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i]).setBitangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 1]).getTangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 1]).setTangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 1]).getBitangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 1]).setBitangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 2]).getTangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 2]).setTangent(new Vector3f(0, 0, 0));
	    if (mesh.getVertices().get(mesh.getIndices()[i + 2]).getBitangent() == null)
		mesh.getVertices().get(mesh.getIndices()[i + 2]).setBitangent(new Vector3f(0, 0, 0));

	    mesh.getVertices().get(mesh.getIndices()[i])
		    .setTangent(mesh.getVertices().get(mesh.getIndices()[i]).getTangent().Add(tangent));
	    mesh.getVertices().get(mesh.getIndices()[i])
		    .setBitangent(mesh.getVertices().get(mesh.getIndices()[i]).getBitangent().Add(bitangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 1])
		    .setTangent(mesh.getVertices().get(mesh.getIndices()[i + 1]).getTangent().Add(tangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 1])
		    .setBitangent(mesh.getVertices().get(mesh.getIndices()[i + 1]).getBitangent().Add(bitangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 2])
		    .setTangent(mesh.getVertices().get(mesh.getIndices()[i + 2]).getTangent().Add(tangent));
	    mesh.getVertices().get(mesh.getIndices()[i + 2])
		    .setBitangent(mesh.getVertices().get(mesh.getIndices()[i + 2]).getBitangent().Add(bitangent));
	}

	for (Vertex vertex : mesh.getVertices())
	{
	    vertex.setTangent(vertex.getTangent().Normalize());
	    vertex.setBitangent(vertex.getBitangent().Normalize());
	}
    }

    public static Quaternion NormalizePlane(Quaternion plane)
    {
	float mag;
	mag = (float) Math
		.sqrt(plane.x * plane.x + plane.y * plane.y + plane.z * plane.z);
	plane.x = plane.x / mag;
	plane.y = plane.y / mag;
	plane.z = plane.z / mag;
	plane.w = plane.w / mag;

	return plane;
    }

    public static Vector2f[] TexCoordsFromFontMap(char x)
    {
	float x_ = (x % 16) / 16.0f;
	float y_ = (x / 16) / 16.0f;
	Vector2f[] texCoords = new Vector2f[4];
	texCoords[0] = new Vector2f(x_, y_ + 1.0f / 16.0f);
	texCoords[1] = new Vector2f(x_, y_);
	texCoords[2] = new Vector2f(x_ + 1.0f / 16.0f, y_ + 1.0f / 16.0f);
	texCoords[3] = new Vector2f(x_ + 1.0f / 16.0f, y_);

	return texCoords;
    }
    
    public static Mesh GenerateFullscreenQuad()
    {
	Mesh mesh = new Mesh();
	mesh.getVertices().add(new Vertex(new Vector3f(-1, 1, 0), new Vector2f(0, 1), new Vector3f(0, 1, 0)));
	mesh.getVertices().add(new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0, 0), new Vector3f(0, 1, 0)));
	mesh.getVertices().add(new Vertex(new Vector3f(1, 1, 0), new Vector2f(1, 1), new Vector3f(0, 1, 0)));
	mesh.getVertices().add(new Vertex(new Vector3f(1, -1, 0), new Vector2f(1, 0), new Vector3f(0, 1, 0)));

	mesh.setIndices(new int[6]);
	mesh.getIndices()[0] = 1;
	mesh.getIndices()[1] = 0;
	mesh.getIndices()[2] = 2;
	mesh.getIndices()[3] = 1;
	mesh.getIndices()[4] = 2;
	mesh.getIndices()[5] = 3;
	
	return mesh;
    }
    
    public static <T> T NullCheck(T t)
    {
	if(t == null) System.err.println("We've just recognized a null object!");
	return t;
    }
}