package threadfool.op.engine.gpu;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Mesh
{
	private final int vao;
	private final int vbo;
	private int vertexCount;

	private static final float[] QUAD_VERTICES = {
		-0.5f, -0.5f, 0f,  0f, 0f,
		 0.5f, -0.5f, 0f,  1f, 0f,
		 0.5f,  0.5f, 0f,  1f, 1f,

		-0.5f, -0.5f, 0f,  0f, 0f,
		 0.5f,  0.5f, 0f,  1f, 1f,
		-0.5f,  0.5f, 0f,  0f, 1f,
	};

	public static Mesh createQuad() {
		return new Mesh(QUAD_VERTICES, true);
	}

	public Mesh(float[] vertices) {
		this(vertices, false);
	}

	public Mesh(float[] vertices, boolean hasUVs)
	{
		vao = glGenVertexArrays();
		vbo = glGenBuffers();

		glBindVertexArray(vao);

		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
		buffer.put(vertices).flip();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

		if (hasUVs) {
			// Stride 5: [X, Y, Z, U, V]
			vertexCount = vertices.length / 5;
			int stride = 5 * Float.BYTES;
			glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 3L * Float.BYTES);
			glEnableVertexAttribArray(1);
		} else {
			// Stride 3: [X, Y, Z]
			vertexCount = vertices.length / 3;
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
			glEnableVertexAttribArray(0);
		}

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void draw(){
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);
		glBindVertexArray(0);
	}
}
