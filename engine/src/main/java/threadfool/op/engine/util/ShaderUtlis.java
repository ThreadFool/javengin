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
}
