package threadfool.op.engine.render;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;

public class Renderer
{

	protected Shader shader;

	protected static final String VERTEX = """
			#version 330 core
			layout (location = 0) in vec3 aPos;

			uniform vec2 u_Offset;
						
			void main() {
			    gl_Position = vec4(aPos.xy + u_Offset, 0.0, 1.0);
			}""";

	protected static final String FRAGMENT = """
			#version 330 core
			out vec4 FragColor;
			void main() {
			    FragColor = vec4(0.0, 0.0, 1.0, 0.5);
			}""";

	public Renderer()
	{
	}
}
