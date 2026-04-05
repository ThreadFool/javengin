package threadfool.op.engine.util;

public class ShaderUtlis
{
	public static final String VERTEX = """
			#version 330 core
			layout (location = 0) in vec3 aPos;

			uniform mat4 u_MVP;

			void main(){
			    gl_Position = u_MVP * vec4(aPos, 1.0);
			}""";

	public static final String FRAGMENT = """
			#version 330 core
			out vec4 FragColor;

			void main(){
			    FragColor = vec4(0.2, 0.6, 1.0, 1.0);
			}""";

	public static final String TEX_VERTEX = """
			#version 330 core
			layout(location = 0) in vec3 aPos;
			layout(location = 1) in vec2 aTexCoord;

			uniform mat4 u_MVP;

			out vec2 v_TexCoord;

			void main() {
			    gl_Position = u_MVP * vec4(aPos, 1.0);
			    v_TexCoord = aTexCoord;
			}""";

	public static final String TEX_FRAGMENT = """
			#version 330 core
			in vec2 v_TexCoord;

			uniform sampler2D u_Texture;
			uniform vec4 u_Tint;

			out vec4 FragColor;

			void main() {
			    FragColor = texture(u_Texture, v_TexCoord) * u_Tint;
			}""";
}
