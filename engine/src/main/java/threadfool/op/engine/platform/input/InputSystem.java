package threadfool.op.engine.platform.input;

import org.lwjgl.glfw.GLFW;

import threadfool.op.engine.platform.window.Window;

public class InputSystem
{
	private final long windowHandle;

	public InputSystem(Window window)
	{
		this.windowHandle = window.getHandle();
	}

	public boolean isKeyDown(int key){
		return GLFW.glfwGetKey(windowHandle, key) == GLFW.GLFW_PRESS;
	}
}
