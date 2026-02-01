package threadfool.op.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Renderer;

public class GameLoop
{
	private final Window window;
	private final Renderer renderer;

	public GameLoop(Window window){
		this.window = window;
		this.renderer = new Renderer();
	}

	public void run(){
		while(!window.shouldClose()){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			renderer.renderTriangle();
			window.update();
		}
	}
}
