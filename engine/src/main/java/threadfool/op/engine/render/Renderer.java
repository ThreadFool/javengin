package threadfool.op.engine.render;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;

public class Renderer {

	private final Shader shader;
	private final Mesh triangle;

	public Renderer() {
		shader = new Shader(VERTEX, FRAGMENT);

		triangle = new Mesh(new float[]{
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.0f,  0.5f, 0f
		});
	}

	public void renderTriangle() {
		shader.bind();
		triangle.draw();
	}

	private static final String VERTEX = """
        #version 330 core
        layout (location = 0) in vec3 aPos;
        void main() {
            gl_Position = vec4(aPos, 1.0);
        }
        """;

	private static final String FRAGMENT = """
        #version 330 core
        out vec4 FragColor;
        void main() {
            FragColor = vec4(0.2, 0.8, 0.3, 1.0);
        }
        """;
}
