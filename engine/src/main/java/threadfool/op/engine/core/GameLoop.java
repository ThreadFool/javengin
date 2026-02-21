package threadfool.op.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static threadfool.op.engine.util.ShaderUtlis.FRAGMENT;
import static threadfool.op.engine.util.ShaderUtlis.VERTEX;

import java.util.Random;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;
import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.render.shapes.SquareRenderer;
import threadfool.op.engine.render.shapes.TriangleRenderer;
import threadfool.op.engine.scene.NewGameObject;
import threadfool.op.engine.scene.NewScene;
import threadfool.op.engine.scene.SpriteRenderer;
import threadfool.op.engine.scene.Transform;
import threadfool.op.engine.systems.PlayerInput;
import threadfool.op.engine.systems.PlayerMovementSystem;
import threadfool.op.engine.systems.RenderSystem;

public class GameLoop
{
	private final Window window;
	private final TriangleRenderer triangleRenderer;
	private final SquareRenderer squareRenderer;

	public GameLoop(Window window){
		this.window = window;
		this.triangleRenderer = new TriangleRenderer();
		this.squareRenderer = new SquareRenderer();
	}

	RenderSystem renderSystem = new RenderSystem();
	Camera cam = new Camera();
	NewGameObject player = new NewGameObject();
	NewGameObject object = new NewGameObject();

	public void run()
	{
		Time.init();
		InputSystem input = new InputSystem(window);
		object.addComponent(new Transform());

		object.addComponent(new SpriteRenderer(new Mesh(new float[]{-0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.0f, 0.5f, 0f}), new Shader(VERTEX, FRAGMENT)));

		player.addComponent(new Transform());
		player.addComponent(new PlayerInput());
		player.addComponent(new SpriteRenderer(new Mesh(new float[]{-0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.0f, 0.5f, 0f}), new Shader(VERTEX, FRAGMENT)));
		PlayerMovementSystem playerMovementSystem = new PlayerMovementSystem();
		NewScene scene = new NewScene();
		scene.add(object);

		player.getComponent(Transform.class).scale.set(100, 100);
		object.getComponent(Transform.class).scale.set(100,100);
		object.getComponent(Transform.class).position.x=100;
		object.getComponent(Transform.class).position.y=100;

		scene.add(player);
		while(!window.shouldClose()){
			Time.update();
			cam.followCentered(player, 800, 600);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			playerMovementSystem.update(scene, input);
			renderSystem.render(scene, cam);
			window.update();

		}
	}
}
