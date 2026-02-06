package threadfool.op.engine.gpu;

import static org.lwjgl.opengl.GL20.*;

public class Shader
{
	public final int programId;

	public Shader(String vertexSrc, String fragmentSrc){
		int vertex = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex, vertexSrc);
		glCompileShader(vertex);
		check(vertex);

		int fragment = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(fragment, fragmentSrc);
		glCompileShader(fragment);
		check(fragment);

		programId = glCreateProgram();
		glAttachShader(programId, vertex);
		glAttachShader(programId, fragment);
		glLinkProgram(programId);

		glDeleteShader(vertex);
		glDeleteShader(fragment);
	}

	private void check(int shader){
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0){
			throw new RuntimeException(glGetShaderInfoLog(shader));
		}
	}

	public void bind() {
		glUseProgram(programId);
	}

	public void setVec2(String name, float x, float y) {
		int loc = glGetUniformLocation(programId, name);
		glUniform2f(loc, x, y);
	}
}
