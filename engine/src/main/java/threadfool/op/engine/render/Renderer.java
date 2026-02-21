package threadfool.op.engine.render;

import static threadfool.op.engine.util.ShaderUtlis.FRAGMENT;
import static threadfool.op.engine.util.ShaderUtlis.VERTEX;

import org.joml.Matrix4f;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;
import threadfool.op.engine.math.MathUtil;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.scene.Transform;

public class Renderer
{

	protected Shader shader;


	public Renderer()
	{
		shader = new Shader(VERTEX,FRAGMENT);
	}

	public void render(Transform transform, Camera cam, Window window)
	{
		shader.bind();
		var triangle = new Mesh(new float[]{-0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.0f, 0.5f, 0f});
		Matrix4f mvp = MathUtil.buildMVP(transform, cam, 800, 600);

		shader.setMat4("u_MVP", mvp);
		triangle.draw();
	}
}
