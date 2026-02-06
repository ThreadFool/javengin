package threadfool.op.engine.render;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;

public class Renderer
{

	private final Shader shader;
	private final Mesh triangle;

	public Renderer()
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

	private static final String VERTEX = """
			#version 330 core
			layout (location = 0) in vec3 aPos;

			uniform vec2 u_Offset;
					\t
			void main() {
			    gl_Position = vec4(aPos.xy + u_Offset, 0.0, 1.0);
			}""";

	private static final String FRAGMENT = """
			#version 330 core
			out vec4 FragColor;
			void main() {
			    FragColor = vec4(0.0, 1.0, 0.0, 1.0);
			}""";
}
