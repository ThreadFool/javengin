package threadfool.op.engine.gpu;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Mesh
{
	private final int vao;
	private final int vbo;
	private int vertexCount;

	public Mesh(float[] verticles)
	{
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		vertexCount = verticles.length / 3;

		glBindVertexArray(vao);

		FloatBuffer buffer = BufferUtils.createFloatBuffer(verticles.length);
		buffer.put(verticles).flip();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
		glEnableVertexAttribArray(0);

		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindVertexArray(0);
	}

	public void draw(){
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES,0 ,vertexCount);
		glBindVertexArray(0);
	}
}
