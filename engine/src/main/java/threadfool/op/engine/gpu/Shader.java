package threadfool.op.engine.gpu;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

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

	public void setMat4(String name, Matrix4f mat){
		int loc = glGetUniformLocation(programId, name);
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		mat.get(fb);
		glUniformMatrix4fv(loc, false, fb);
	}

	public void setInt(String name, int value) {
		glUniform1i(glGetUniformLocation(programId, name), value);
	}

	public void setVec4(String name, float r, float g, float b, float a) {
		glUniform4f(glGetUniformLocation(programId, name), r, g, b, a);
	}
}
