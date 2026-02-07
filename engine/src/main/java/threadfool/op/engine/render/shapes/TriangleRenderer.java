package threadfool.op.engine.render.shapes;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;
import threadfool.op.engine.render.Renderer;

public class TriangleRenderer extends Renderer
{
	protected final Mesh triangle;

	public TriangleRenderer()
	{
		shader = new Shader(VERTEX, FRAGMENT);

		triangle = new Mesh(new float[]{-0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.0f, 0.5f, 0f});
	}

	public void renderTriangle(float x, float y)
	{
		shader.bind();

		shader.setVec2("u_Offset", x, y);

		triangle.draw();
	}
}
