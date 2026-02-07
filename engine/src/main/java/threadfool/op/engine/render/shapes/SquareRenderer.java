package threadfool.op.engine.render.shapes;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;
import threadfool.op.engine.render.Renderer;

public class SquareRenderer extends Renderer
{
	private final Mesh square;

	float[] squareVertices = {
			// triangle 1
			-0.5f, -0.5f, 0f,
			0.5f, -0.5f, 0f,
			0.5f,  0.5f, 0f,

			// triangle 2
			-0.5f, -0.5f, 0f,
			0.5f,  0.5f, 0f,
			-0.5f,  0.5f, 0f
	};

	public SquareRenderer()
	{
		shader = new Shader(VERTEX, FRAGMENT);

		square = new Mesh(squareVertices);
	}

	public void renderSquare(float x, float y){
		shader.bind();

		shader.setVec2("u_Offset",x,y);

		square.draw();
	}

}
