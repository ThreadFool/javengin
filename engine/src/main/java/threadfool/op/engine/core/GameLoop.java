package threadfool.op.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.scene.GameScene;
import threadfool.op.engine.scene.SceneManager;

public class GameLoop
{
	private final Window window;
	private final SceneManager sceneManager;
	private final Camera camera;

	public GameLoop(Window window, GameScene initialScene) {
		this.window = window;
		InputSystem input = new InputSystem(window);
		this.camera = new Camera();
		this.sceneManager = new SceneManager(window, input);
		sceneManager.switchTo(initialScene);
	}

	public void run()
	{
		Time.init();
		while (!window.shouldClose()) {
			Time.update();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			sceneManager.update();
			sceneManager.render(camera);
			window.update();
		}
	}
}
