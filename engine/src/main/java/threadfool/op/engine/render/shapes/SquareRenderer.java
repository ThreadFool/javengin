package threadfool.op.engine.render.shapes;

import static threadfool.op.engine.util.ShaderUtlis.FRAGMENT;
import static threadfool.op.engine.util.ShaderUtlis.VERTEX;

import org.joml.Matrix4f;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;
import threadfool.op.engine.math.MathUtil;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.render.Renderer;
import threadfool.op.engine.scene.Transform;

public class SquareRenderer extends Renderer
{
	private final Mesh square;

	public float[] squareVertices = {
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

	public void render(Transform transform, Camera cam, Window window){
		shader.bind();

		Matrix4f mvp = MathUtil.buildMVP(
				transform,
				cam,
				800,
				600
		);

		shader.setMat4("u_MVP", mvp);
		square.draw();
	}

}
