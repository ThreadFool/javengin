package threadfool.op.engine.platform.window;

import org.lwjgl.glfw.GLFW;

public class Window
{
	private long handle;

	public void create(final int w, final int h, final String title)
	{
		if(!GLFW.glfwInit()) throw new IllegalStateException("GLFW init failed");

		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

		handle = GLFW.glfwCreateWindow(w, h , title, 0 ,0);

		if(handle == 0) throw new RuntimeException("Window creation failed");

		GLFW.glfwMakeContextCurrent(handle);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(handle);
	}

	public boolean shouldClose(){
		return GLFW.glfwWindowShouldClose(handle);
	}

	public void update(){
		GLFW.glfwSwapBuffers(handle);
		GLFW.glfwPollEvents();
	}

	public long getHandle()
	{
		return handle;
	}
}

