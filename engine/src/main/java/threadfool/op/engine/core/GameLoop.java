package threadfool.op.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.Random;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.render.Renderer;
import threadfool.op.engine.render.shapes.SquareRenderer;
import threadfool.op.engine.render.shapes.TriangleRenderer;
import threadfool.op.engine.scene.GameObject;
import threadfool.op.engine.scene.PlayerController;
import threadfool.op.engine.scene.Scene;
import threadfool.op.engine.scene.Transform;

public class GameLoop
{
	float x = 200f;
	float y = 200f;
	float speed = 100.5f;
	private final Window window;
	private final TriangleRenderer triangleRenderer;
	private final SquareRenderer squareRenderer;

	public GameLoop(Window window){
		this.window = window;
		this.triangleRenderer = new TriangleRenderer();
		this.squareRenderer = new SquareRenderer();
	}

	public void run() throws InterruptedException
	{
		Time.init();

		Scene scene = new Scene();
		Camera cam = new Camera();

		GameObject player = new GameObject(squareRenderer);
		player.addComponent(new PlayerController());
		scene.add(player);
		player.transform.scale = new Vector2f(100f, 100f);
		player.transform.position = new Vector2f(300f,400f);
		InputSystem input = new InputSystem(window);
		GameObject gameObject = new GameObject(triangleRenderer);
		gameObject.transform.position.set(100f, 100f);
		gameObject.transform.scale.set(50f,50f);

		scene.add(gameObject);

		while(!window.shouldClose()){
			Time.update();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			cam.followCentered(player, 800, 600);
			scene.update(input);
			scene.render(cam, window);
			window.update();
		}
	}
}
