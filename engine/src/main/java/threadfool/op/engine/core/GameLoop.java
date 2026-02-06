package threadfool.op.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.glfw.GLFW;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Renderer;

public class GameLoop
{
	float x = 0f;
	float y = 0f;
	float speed = 1.5f;

	private final Window window;
	private final Renderer renderer;


	public GameLoop(Window window){
		this.window = window;
		this.renderer = new Renderer();
	}

	public void run(){
		Time.init();

		InputSystem input = new InputSystem(window);

		while(!window.shouldClose()){
			Time.update();

			if(input.isKeyDown(GLFW.GLFW_KEY_A)) x -= speed * Time.delta();
			if(input.isKeyDown(GLFW.GLFW_KEY_D)) x += speed * Time.delta();
			if(input.isKeyDown(GLFW.GLFW_KEY_W)) y += speed * Time.delta();
			if(input.isKeyDown(GLFW.GLFW_KEY_S)) y -= speed * Time.delta();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			renderer.renderTriangle(x,y);

			window.update();
		}
	}
}
